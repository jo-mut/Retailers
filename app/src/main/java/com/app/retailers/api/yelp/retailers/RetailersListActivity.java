package com.app.retailers.api.yelp.retailers;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RetailersListActivity extends AppCompatActivity {
    private static final String TAG = RetailersListActivity.class.getSimpleName();
    private RetailsRecyclerAdapter retailsRecyclerAdapter;
    private ArrayList<Retailer> retailers = new ArrayList<>();
    private Toolbar toolbar;

    @Bind(R.id.retailersListRecyclerView)RecyclerView mRetailersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers_list);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        ButterKnife.bind(this);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getRetailers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getRetailers(String location){

        final YelpService yelpService = new YelpService();

        yelpService.findRetailers(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                retailers = yelpService.processResults(response);

                RetailersListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        retailsRecyclerAdapter = new RetailsRecyclerAdapter(getApplicationContext(), retailers);
                        mRetailersRecyclerView.setAdapter(retailsRecyclerAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(RetailersListActivity.this);
                        mRetailersRecyclerView.setLayoutManager(layoutManager);
                        mRetailersRecyclerView.setHasFixedSize(true);
                    }
                });

                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.v(TAG, jsonData);
                        retailers = yelpService.processResults(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        final YelpService yelpService = new YelpService();
//        yelpService.findRetailers(location, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                retailers = yelpService.processResults(response);
//                RetailersListActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        retailsRecyclerAdapter = new RetailsRecyclerAdapter(getApplicationContext(), retailers);
//                        mRetailersRecyclerView.setAdapter(retailsRecyclerAdapter);
//                        RecyclerView.LayoutManager layoutManager =
//                                new LinearLayoutManager(RetailersListActivity.this);
//                        mRetailersRecyclerView.setLayoutManager(layoutManager);
//                        mRetailersRecyclerView.setHasFixedSize(true);
//
//                    }
//                });
//            }
//        });

    }
}
