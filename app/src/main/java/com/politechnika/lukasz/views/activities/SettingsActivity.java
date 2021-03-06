package com.politechnika.lukasz.views.activities;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.politechnika.lukasz.R;
import com.politechnika.lukasz.dagger.DaggerApplication;
import com.politechnika.lukasz.models.core.Settings;
import com.politechnika.lukasz.services.ISharedPreferenceHelper;
import com.politechnika.lukasz.validators.IStringInputValidator;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity {

    @Inject
    IStringInputValidator stringInputValidator;

    @Inject
    ISharedPreferenceHelper sharedPreferenceHelper;

    @Inject
    Settings settings;

    private EditText longitudeEditText;
    private EditText latitudeEditText;
    private EditText refreshTimeEditText;

    private RadioGroup velocityRadioGroup;
    private RadioGroup temperatureRadioGroup;

    private RadioButton kmphRadioButton, mphRadioButton, celsiusRadioButton, fahrenheitRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication.component().inject(this);
        setContentView(R.layout.activity_settings);
        makeToolbarAndActionBar();

        getViewsById();

        setSettingsToViews();

        addTextChangedListenerToLongitude();
        addTextChangedListenerToLatitude();
        addTextChangedListenerToRefreshTime();
        setVelocityRadioGroupListener();
        setTemperatureRadioGroupListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        validateFieldsAndSave();
        super.onBackPressed();
    }

    private void getViewsById(){
        longitudeEditText = (EditText)findViewById(R.id.longitudeEditText);
        latitudeEditText = (EditText)findViewById(R.id.latitudeEditText);
        refreshTimeEditText = (EditText)findViewById(R.id.refreshTimeEditText);

        velocityRadioGroup = (RadioGroup)findViewById(R.id.velocityRadioGroup);
        temperatureRadioGroup = (RadioGroup)findViewById(R.id.temperatureRadioGroup);

        kmphRadioButton = (RadioButton) findViewById(R.id.kmphRadioButton);
        mphRadioButton = (RadioButton) findViewById(R.id.mphRadioButton);
        celsiusRadioButton = (RadioButton) findViewById(R.id.celsiusRadioButton);
        fahrenheitRadioButton = (RadioButton) findViewById(R.id.fahrenheitRadioButton);
    }

    private void setSettingsToViews(){
        latitudeEditText.setText(String.valueOf(settings.getLatitude()));
        longitudeEditText.setText(String.valueOf(settings.getLongitude()));
        refreshTimeEditText.setText(String.valueOf(settings.getRefreshTime()));

        if(settings.isKmph())
            kmphRadioButton.setChecked(true);
        else
            mphRadioButton.setChecked(true);

        if(settings.isCelsius())
            celsiusRadioButton.setChecked(true);
        else
            fahrenheitRadioButton.setChecked(true);
    }

    private void validateFieldsAndSave(){
        String latitudeString = latitudeEditText.getText().toString();
        String longitudeString = longitudeEditText.getText().toString();
        String refreshTimeString = refreshTimeEditText.getText().toString();

        try{
            double refreshTimeDouble = Double.parseDouble(refreshTimeString);
            if(refreshTimeDouble < 0.1)
                refreshTimeString = "0.1";

            if(refreshTimeDouble > 100)
                refreshTimeString = "100";
        }
        catch (Exception e){
            refreshTimeString = "1";
        }

        if(latitudeString.equals(""))
            latitudeString = "0";

        if(longitudeString.equals(""))
            longitudeString = "0";

        if(refreshTimeString.equals("0") || refreshTimeString.equals(""))
            refreshTimeString = "10";

        settings.setLatitude(Float.parseFloat(latitudeString));
        settings.setLongitude(Float.parseFloat(longitudeString));
        settings.setRefreshTime(Float.parseFloat(refreshTimeString));

        sharedPreferenceHelper.saveSettings(settings);
    }

    private void addTextChangedListenerToLongitude(){
        longitudeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String afterChanged = longitudeEditText.getText().toString();
                if(!stringInputValidator.validateLongitude(afterChanged)){
                    if(afterChanged.length() != 0){
                        afterChanged = afterChanged.substring(0, afterChanged.length()-1);
                        longitudeEditText.setText(afterChanged);
                        longitudeEditText.setSelection(afterChanged.length());
                        showToast("Wrong input");
                    }
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
                if(!stringInputValidator.validateLatitude(afterChanged)){
                    if(afterChanged.length() != 0){
                        afterChanged = afterChanged.substring(0, afterChanged.length()-1);
                        latitudeEditText.setText(afterChanged);
                        latitudeEditText.setSelection(afterChanged.length());
                        showToast("Wrong input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addTextChangedListenerToRefreshTime(){
        refreshTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String afterChanged = refreshTimeEditText.getText().toString();
                if(afterChanged.length() > 8) {
                    afterChanged = afterChanged.substring(0, afterChanged.length() - 1);
                    refreshTimeEditText.setText(afterChanged);
                    refreshTimeEditText.setSelection(afterChanged.length());showToast("Wrong input");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setVelocityRadioGroupListener(){
        velocityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if(checkedId == R.id.kmphRadioButton)
                    settings.setKmph(true);
                else if(checkedId == R.id.mphRadioButton)
                    settings.setKmph(false);

                sharedPreferenceHelper.saveSettings(settings);
            }
        });
    }

    private void setTemperatureRadioGroupListener(){
        temperatureRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.celsiusRadioButton:
                        settings.setCelsius(true);
                        break;

                    case R.id.fahrenheitRadioButton:
                        settings.setCelsius(false);
                        break;

                    default:
                        settings.setCelsius(true);
                }

                sharedPreferenceHelper.saveSettings(settings);
            }
        });
    }
}
