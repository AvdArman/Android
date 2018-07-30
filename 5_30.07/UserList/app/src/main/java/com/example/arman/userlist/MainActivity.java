package com.example.arman.userlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> users = new ArrayList<>();
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView rv = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        setUsers();
        userAdapter = new UserAdapter(users, this);
        rv.setAdapter(userAdapter);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                userAdapter.getFilter().filter(query);
                userAdapter.notifyDataSetChanged();
                return true;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void setUsers() {
        String fullName = "Arman Avdalyan";
        String phoneNumber = "077993615";
        String emailAddress = "avdalyan1999@gmail.com";
        String description = "Description1";
        float rating = 5;
        String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Icons8_flat_businessman.svg/1024px-Icons8_flat_businessman.svg.png";
        User user = new User(fullName, phoneNumber, emailAddress, description, rating, url);
        users.add(user);

        fullName = "Name Surname";
        phoneNumber = "014578541";
        emailAddress = "mail@gmail.com";
        description = "Description2";
        rating = 4;
        url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5y3L0URosUA7io9O_b5YmaCqC80dokrs5NimYJ8xZ8o7UploB";

        user = new User(fullName, phoneNumber, emailAddress, description, rating, url);
        users.add(user);

        fullName = "Name1 Surname1";
        phoneNumber = "0144548541";
        emailAddress = "mail@gmail.com";
        description = "Description3";
        rating = 2;
        url = "https://sophieriehl.com/wp-content/uploads/2017/06/male.png";

        user = new User(fullName, phoneNumber, emailAddress, description, rating, url);
        users.add(user);

        fullName = "Name2 Surname2";
        phoneNumber = "014578541";
        emailAddress = "mail@gmail.com";
        description = "Description4";
        rating = 5;
        url = "https://png.icons8.com/color/1600/circled-user-male-skin-type-1-2.png";

        user = new User(fullName, phoneNumber, emailAddress, description, rating, url);
        users.add(user);

        fullName = "Name3 Surname3";
        phoneNumber = "0144548541";
        emailAddress = "mail@gmail.com";
        description = "Description5";
        rating = 3;
        url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5y3L0URosUA7io9O_b5YmaCqC80dokrs5NimYJ8xZ8o7UploB";

        user = new User(fullName, phoneNumber, emailAddress, description, rating, url);
        users.add(user);

    }
}