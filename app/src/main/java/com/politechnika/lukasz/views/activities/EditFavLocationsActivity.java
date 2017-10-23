package com.politechnika.lukasz.views.activities;

import com.politechnika.lukasz.R;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.politechnika.lukasz.views.MyListViewAdapter;
import java.util.ArrayList;

public class EditFavLocationsActivity extends AppCompatActivity {

    private ListView locationsListView;
    private MyListViewAdapter myListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_fav_locations);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        locationsListView = (ListView)findViewById(R.id.locationsListView);

        ArrayList<String> listOfLocations = new ArrayList();

        listOfLocations.add("Lódź");
        listOfLocations.add("Warszawa");
        listOfLocations.add("Kraków");
        listOfLocations.add("Rzgów");

        myListViewAdapter = new MyListViewAdapter(getApplicationContext(), listOfLocations);
        locationsListView.setAdapter(myListViewAdapter);

        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String locationItem = (String)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), locationItem, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
