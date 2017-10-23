package com.politechnika.lukasz.views.activities;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.helpers.ISharedPreferenceHelper;
import com.politechnika.lukasz.validators.IStringInputValidator;

import javax.inject.Inject;

public class SettingsActivity extends AppCompatActivity {

    @Inject
    IStringInputValidator stringInputValidator;

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    private EditText longitudeEditText;
    private EditText latitudeEditText;
    private EditText refreshTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication.component().inject(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        longitudeEditText = (EditText)findViewById(R.id.longitudeEditText);
        latitudeEditText = (EditText)findViewById(R.id.latitudeEditText);
        refreshTimeEditText = (EditText)findViewById(R.id.refreshTimeEditText);

        latitudeEditText.setText(sharedPreferenceHelper.getString("latitude", ""));
        longitudeEditText.setText(sharedPreferenceHelper.getString("longitude", ""));
        refreshTimeEditText.setText(sharedPreferenceHelper.getString("refreshTime", "15"));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        addTextChangedListenerToLongitude();
        addTextChangedListenerToLatitude();
    }

    @Override
    public void onBackPressed(){
        String latitudeString = latitudeEditText.getText().toString();
        String longitudeString = longitudeEditText.getText().toString();
        String refreshTimeString = refreshTimeEditText.getText().toString();

        try{
            double refreshTimeDouble = Double.parseDouble(refreshTimeString);
            if(refreshTimeDouble < 0.1)
                refreshTimeString = "0.1";
        }
        catch (Exception e){
            refreshTimeString = "1";
        }

        if(latitudeString.equals(""))
            latitudeString = "0";

        if(longitudeString.equals(""))
            longitudeString = "0";

        if(refreshTimeString.equals("0") || refreshTimeString.equals(""))
            refreshTimeString = "1";

        sharedPreferenceHelper.saveString("latitude", latitudeString);
        sharedPreferenceHelper.saveString("longitude", longitudeString);
        sharedPreferenceHelper.saveString("refreshTime", refreshTimeString);

        super.onBackPressed();
    }

    private void addTextChangedListenerToLongitude(){
        longitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String afterChanged = longitudeEditText.getText().toString();
                if(!stringInputValidator.validateLonLang(afterChanged)){
                    if(afterChanged.length() != 0){
                        afterChanged = afterChanged.substring(0, afterChanged.length()-1);
                        longitudeEditText.setText(afterChanged);
                        longitudeEditText.setSelection(afterChanged.length());
                    }
                    Toast.makeText(getApplicationContext(), "Wrong input", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addTextChangedListenerToLatitude(){
        latitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String afterChanged = latitudeEditText.getText().toString();
                if(!stringInputValidator.validateLonLang(afterChanged)){
                    if(afterChanged.length() != 0){
                        afterChanged = afterChanged.substring(0, afterChanged.length()-1);
                        latitudeEditText.setText(afterChanged);
                        latitudeEditText.setSelection(afterChanged.length());
                    }
                    Toast.makeText(getApplicationContext(), "Wrong input", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
