package com.instigatemobile.todolist.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.instigatemobile.todolist.R;
import com.instigatemobile.todolist.fragments.DatePickerFragment;
import com.instigatemobile.todolist.fragments.TimePickerFragment;
import com.instigatemobile.todolist.modules.TodoObject;
import com.instigatemobile.todolist.util.Util;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG = 1;
    private StorageReference mStorageRef;
    private String storageUrl;
    private DatabaseReference mDatabaseReference;
    private ImageView imageView;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private TextView tmpTextView;
    private Uri imagePath;
    private EditText etTitle;
    private EditText etDescription;
    private Button tvDate;
    private Button tvTime;
    private Query firebaseQuery;
    private RecyclerView recyclerView;
    private String userId;
    private boolean delete = true;
    private List<TodoObject> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(userId);
        firebaseQuery = mDatabaseReference;
        loadData();
        recyclerView = findViewById(R.id.recyclerview);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setUpFirebaseAdapter();
        setRecyclerViewSwipe();
        final FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddClick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert manager != null;
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                firebaseQuery = mDatabaseReference.orderByChild("mTitle")
                        .startAt(query).endAt(query + "\uf8ff");
                setUpFirebaseAdapter();
                loadData();
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            try {
                imagePath = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imagePath);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                tmpTextView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                InputStream is = getContentResolver().openInputStream(imagePath);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.image_choose_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.not_pick_image, Toast.LENGTH_LONG).show();
        }
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<TodoObject, MainActivity.FirebaseViewHolder>
                (TodoObject.class, R.layout.todo_item, MainActivity.FirebaseViewHolder.class, firebaseQuery) {
            @Override
            protected void populateViewHolder(FirebaseViewHolder viewHolder, TodoObject model, int position) {
                viewHolder.tvTitle.setText(model.getmTitle());
                viewHolder.tvDescription.setText(model.getmDescription());
                viewHolder.tvDateTime.setText(model.getmDateTime());
                if (model.getmImage() == null) {
                    viewHolder.image.setImageResource(R.drawable.to_do_icon);
                } else {
                    Picasso.get().load(model.getmImage()).into(viewHolder.image);
                }
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_choose_date:
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.et_choose_time:
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
                break;
            case R.id.tmp_text_view:
                final Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                tmpTextView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
        }
    }

    private void btnAddClick() {
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);
        findDialogViews(dialogView);
        tmpTextView.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);

        setAddListener(alertDialogBuilder);
        cancelDialog(alertDialogBuilder);
        alertDialogBuilder.show();
    }

    private void findDialogViews(View dialogView) {
        etTitle = dialogView.findViewById(R.id.title);
        tmpTextView = dialogView.findViewById(R.id.tmp_text_view);
        imageView = dialogView.findViewById(R.id.image);
        etDescription = dialogView.findViewById(R.id.description);
        tvDate = dialogView.findViewById(R.id.et_choose_date);
        tvTime = dialogView.findViewById(R.id.et_choose_time);
    }

    private void setRecyclerViewSwipe() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, final int position) {
                askForDelete();
                if (delete) {
                    mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            System.out.println(dataSnapshot.getKey());
//                            mDatabaseReference.child(userId).child(key).removeValue();
                            Toast.makeText(MainActivity.this, "Sorry can not delete item", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void setAddListener(final AlertDialog.Builder alertDialogBuilder) {
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (etTitle.getText() != null || Util.date != null) {
                            mDatabaseReference.push().setValue(new TodoObject(storageUrl,
                                    String.valueOf(etTitle.getText()),
                                    String.valueOf(etDescription.getText()),
                                    String.valueOf(Util.date + " " + Util.time)));
                            loadData();
                            recyclerView.smoothScrollToPosition(mFirebaseAdapter.getItemCount());
                        } else {
                            Toast.makeText(MainActivity.this, R.string.fill_field_text,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loadData() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todoList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    todoList.add(postSnapshot.getValue(TodoObject.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void cancelDialog(AlertDialog.Builder alertDialogBuilder) {
        alertDialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, R.string.dialog_canceled, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadImage() {
        if (imagePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();
            StorageReference ref = mStorageRef.child("images/" + String.valueOf(UUID.randomUUID()));
            ref.putFile(imagePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            storageUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                            Toast.makeText(MainActivity.this, R.string.uploaded, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, getString(R.string.failed)
                                    + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage(R.string.uploaded + " " + (int) progress + "%");
                        }
                    });
        }
    }

    private void askForDelete() {
        //delete = false;
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    delete = true;
                }
            }
        };
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete item?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    public static class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDateTime;

        public FirebaseViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
        }
    }
}