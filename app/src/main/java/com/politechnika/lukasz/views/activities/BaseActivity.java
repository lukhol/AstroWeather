package com.politechnika.lukasz.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.models.core.Place;
import com.politechnika.lukasz.services.DBHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity{

    protected AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void makeToolbarAndActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected AppCompatActivity getActivity(){
        return this;
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }

    protected AlertDialog showInformationDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title).setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    protected void waitingLayout(boolean status, String message){
        if(status)
            dialog = showInformationDialog("Loading", "...");

        if(!status && dialog != null)
            dialog.dismiss();

        if(!status && message != null)
            dialog = showInformationDialog("Error", message);
    }

    protected List<Place> getFavouritesFromDatabase(){
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Place> listOfLocations = dbHelper.getFavourites();
        dbHelper.close();
        return listOfLocations;
    }

    protected Place getFavouriteFromDatabase(String city){
        DBHelper dbHelper = new DBHelper(this);
        Place place = dbHelper.getFavourite(city);
        dbHelper.close();
        return place;
    }
}
