package com.example.intervalrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Class to collect run configuration
 */
public class RunSettingsActivity extends AppCompatActivity {

    SQLiteDatabase runSettings;

    EditText runLength;
    EditText breakLength;
    EditText numReps;

    Button saveBtn;

    Spinner runLengthSpinner;
    Spinner breakLengthSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_settings);
        runSettings = this.openOrCreateDatabase("RunSettings", MODE_PRIVATE, null);

        runLength = findViewById(R.id.runLength);
        breakLength = findViewById(R.id.breakLength);
        numReps = findViewById(R.id.repititionsNum);
        saveBtn = findViewById(R.id.saveButton);
        runLengthSpinner = findViewById(R.id.runLengthSpinner);
        breakLengthSpinner = findViewById(R.id.breakLengthSpinner);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.distanceRunType:
                if (checked) {
                    // populate distance based fields
                    runLength.setVisibility(View.VISIBLE);
                    breakLength.setVisibility(View.VISIBLE);
                    numReps.setVisibility(View.VISIBLE);
                    saveBtn.setVisibility(View.VISIBLE);
                    runLengthSpinner.setVisibility(View.VISIBLE);
                    breakLengthSpinner.setVisibility(View.VISIBLE);
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.distance_measurements, android.R.layout.simple_spinner_dropdown_item);
                    runLengthSpinner.setAdapter(adapter);
                    breakLengthSpinner.setAdapter(adapter);
                }
                    break;
            case R.id.timeRunType:
                if (checked) {
                    // populate time based runs
                    runLength.setVisibility(View.VISIBLE);
                    breakLength.setVisibility(View.VISIBLE);
                    numReps.setVisibility(View.VISIBLE);
                    saveBtn.setVisibility(View.VISIBLE);
                    runLengthSpinner.setVisibility(View.VISIBLE);
                    breakLengthSpinner.setVisibility(View.VISIBLE);
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.time_measurements, android.R.layout.simple_spinner_dropdown_item);
                    runLengthSpinner.setAdapter(adapter);
                    breakLengthSpinner.setAdapter(adapter);
                }
                    break;
        }
    }

    public void saveRun(View view){

        String sql = "INSERT INTO run_settings (runName, runType, runLength, breakLength, repititions) VALUES (?, ?, ?, ?, ?)";

        TextView runName = findViewById(R.id.runName);
        RadioGroup radioGroup = findViewById(R.id.runTypeRadio);
        EditText runLength = findViewById(R.id.runLength);
        EditText breakLength = findViewById(R.id.breakLength);
        EditText repetitions = findViewById(R.id.repititionsNum);

        int checked  = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(checked);

        //do validation, trim title

        SQLiteStatement statement = runSettings.compileStatement(sql);
        statement.bindString(1, runName.getText().toString());
        statement.bindString(2, String.valueOf(checked));
        statement.bindString(3, runLength.toString());
        statement.bindString(4, breakLength.toString());
        statement.bindString(5, repetitions.toString());

        statement.execute();

        finish();

    }
}