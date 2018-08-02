package com.instigatemobile.getdatafromstorage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private String storageUrl;
    private static final int RESULT_LOAD_IMG = 1;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private DatabaseReference mReference;
    private DatabaseReference tmpReference;
    private ImageView imageView;
    private TextView tmpTextView;
    private Uri imagePath;
    private EditText etTitle;
    private EditText etDescription;
    private List<PostObject> objList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mReference = FirebaseDatabase.getInstance().getReference();
        setUpFirebaseAdapter();
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);
        FloatingActionButton fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tmpTextView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                } catch (NullPointerException ignored) {
                }
                btnEditClick();
            }
        });
        setRecyclerViewSwipe(recyclerView);
    }


    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<PostObject, MainActivity.MyViewHolder>
                (PostObject.class, R.layout.item, MainActivity.MyViewHolder.class, mReference) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, PostObject model, int position) {


            }
        };
    }

    public void btnEditClick() {
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.add_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        etTitle = prompt.findViewById(R.id.title);
        tmpTextView = prompt.findViewById(R.id.tmp_text_view);
        imageView = prompt.findViewById(R.id.image);
        etDescription = prompt.findViewById(R.id.description);

        final Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");

        tmpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmpTextView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        setAddListener(alertDialogBuilder);
        cancelDialog(alertDialogBuilder);
        alertDialogBuilder.show();
    }

    private void setRecyclerViewSwipe(RecyclerView rv) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (!objList.isEmpty()) {
                    objList.remove(direction);
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mReference.setValue(null);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//                    mReference.removeValue(objList.get(direction).delReference);
                }
                mFirebaseAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.deleted_item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);
    }

    private void setAddListener(final AlertDialog.Builder alertDialogBuilder) {
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (etTitle != null && etDescription != null && imageView != null) {
                            mReference.push().setValue(new PostObject(String.valueOf(etTitle.getText()),
                                    storageUrl, String.valueOf(etDescription.getText())));
                            mReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DataSnapshot contactSnapshot = dataSnapshot.child("contacts");
                                    Iterable<DataSnapshot> contactChildren = contactSnapshot.getChildren();
                                    for (DataSnapshot data : contactChildren) {
                                        PostObject c = data.getValue(PostObject.class);
                                        objList.add(c);
                                    }
                                    mFirebaseAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, R.string.fill_field_text,
                                    Toast.LENGTH_LONG).show();
                        }
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imagePath = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imagePath);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                tmpTextView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                uploadImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.image_choose_error, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MainActivity.this, R.string.not_pick_image, Toast.LENGTH_LONG).show();
        }
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
                            Toast.makeText(MainActivity.this, getString(R.string.failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable {

        public ImageView image;
        public TextView title;
        public TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
        }
    }
}