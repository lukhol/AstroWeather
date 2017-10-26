package com.politechnika.lukasz.views.activities;

import com.politechnika.lukasz.R;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.models.core.Weather;
import com.politechnika.lukasz.services.DBHelper;
import com.politechnika.lukasz.services.IWeatherService;
import com.politechnika.lukasz.views.MyListViewAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class EditFavLocationsActivity extends BaseActivity {;

    @Inject
    IWeatherService weatherService;

    ArrayList<Place> listOfLocations;

    private ListView locationsListView;
    private MyListViewAdapter myListViewAdapter;
    private Button addNewLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication.component().inject(this);
        setContentView(R.layout.activity_edit_fav_locations);
        makeToolbarAndActionBar();

        //Move to method
        final DBHelper dbHelper = new DBHelper(this);
        listOfLocations = dbHelper.getFavourites();
        dbHelper.close();

        locationsListView = (ListView)findViewById(R.id.locationsListView);
        addNewLocationButton = (Button)findViewById(R.id.addNewLocationButton);


        myListViewAdapter = new MyListViewAdapter(getApplicationContext(), listOfLocations);
        locationsListView.setAdapter(myListViewAdapter);

        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place locationItem = (Place)adapterView.getItemAtPosition(i);

                DBHelper dbHelper1 = new DBHelper(getActivity());
                dbHelper.removeFavourite(locationItem.getCity());
                List<Place> newPlacsList = dbHelper.getFavourites();

                listOfLocations.clear();
                for(Place tempPlace : newPlacsList){
                    listOfLocations.add(tempPlace);
                }

                myListViewAdapter.notifyDataSetChanged();
            }
        });

        addNewLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity thisAsBaseActivity = (BaseActivity)getActivity();
                if(!thisAsBaseActivity.isOnline()){
                    showToast("No internet connecton. Plese turn on wifi or mobile data.");
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Provide city:");
                LayoutInflater li = LayoutInflater.from(getActivity());
                View viewInflated = li.inflate(R.layout.dialog_new_location, null);
                final EditText input = viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new GetWeatherAsyncTask().execute(input.getText().toString());
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

    class GetWeatherAsyncTask extends AsyncTask<String, Void, Pair<Weather, String>> {

        @Override
        protected Pair<Weather, String> doInBackground(String... strings) {
            Weather weather = null;
            try{
                weather = weatherService.getWeather(strings[0]);
            } catch (Exception e){
                return new Pair<>(weather, e.getMessage());
            }
            return new Pair<>(weather, null);
        }

        protected void onPostExecute(Pair<Weather, String> weatherPair){
            if(weatherPair != null) {
                Weather weather = weatherPair.first;
                String message = weatherPair.second;

                if (message != null) {
                    showInformationDialog("Something went wrong.", message);
                    return;
                }

                Place place = new Place();
                place.setLongitude(Double.parseDouble(weather.getItem().getLongitude()));
                place.setLatitude(Double.parseDouble(weather.getItem().getLatitude()));
                place.setCity(weather.getLocation().getCity());
                place.setWeather(weather);

                DBHelper dbHelper = new DBHelper(getActivity());
                int result = dbHelper.insertFavourite(place);

                showToast("" + result);

                if (result == -2) {
                    showToast("Cannot add the same city twice.");
                    dbHelper.updateFavourite(place);
                }

                List<Place> newPlacsList = dbHelper.getFavourites();

                listOfLocations.clear();
                for(Place tempPlace : newPlacsList){
                    listOfLocations.add(tempPlace);
                }

                dbHelper.close();

                myListViewAdapter.notifyDataSetChanged();
            }
        }
    }
}
