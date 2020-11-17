package com.example.intervalrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RunSettingsActivity extends AppCompatActivity {

    SQLiteDatabase runSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_settings);
        runSettings = this.openOrCreateDatabase("RunSettings", MODE_PRIVATE, null);
    }

    public void saveRun(View view){

        String sql = "INSERT INTO articles (runName, runType, runLength, breakLength, repititions) VALUES (?, ?, ?, ?, ?)";

        TextView runName = findViewById(R.id.runName);
        RadioGroup radioGroup = findViewById(R.id.runTypeRadio);
        EditText runLength = findViewById(R.id.runLength);
        EditText breakLength = findViewById(R.id.breakLength);
        EditText repetitions = findViewById(R.id.repititionsNum);

        int checked  = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(checked);

        //do validation

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