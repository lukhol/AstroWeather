package com.politechnika.lukasz.views.activities;

import com.politechnika.lukasz.R;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.politechnika.lukasz.views.MyListViewAdapter;
import java.util.ArrayList;

public class EditFavLocationsActivity extends BaseActivity {

    ArrayList<String> listOfLocations = new ArrayList();

    private ListView locationsListView;
    private MyListViewAdapter myListViewAdapter;
    private Button addNewLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fav_locations);
        makeToolbarAndActionBar();

        locationsListView = (ListView)findViewById(R.id.locationsListView);
        addNewLocationButton = (Button)findViewById(R.id.addNewLocationButton);

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
                listOfLocations.remove(locationItem);
                myListViewAdapter.remove(i);
                myListViewAdapter.notifyDataSetChanged();
            }
        });

        addNewLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                builder.setTitle("Provide city:");
                LayoutInflater li = LayoutInflater.from(getContext());
                View viewInflated = li.inflate(R.layout.dialog_new_location, null);
                final EditText input = viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listOfLocations.add(input.getText().toString());
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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
