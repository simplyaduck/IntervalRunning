package com.example.intervalrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.TextView;

public class StartRun extends AppCompatActivity {


    SQLiteDatabase runSettings;

    TextView runNameView;
    TextView runDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_run);
        runSettings = this.openOrCreateDatabase("RunSettings", MODE_PRIVATE, null);
        Intent intent = getIntent();
        String runName = intent.getStringExtra("runName");

        runNameView = findViewById(R.id.runNameGo);
        runNameView.setText(runName);
        runDescription = findViewById(R.id.runSettings);


        String sql = "SELECT * FROM run_settings WHERE runName=?";
        SQLiteStatement statement = runSettings.compileStatement(sql);
        statement.bindString(1, runName);

        String[] name = new String[1];
        name[1] = runName;
        Cursor c = runSettings.rawQuery("SELECT * FROM run_settings WHERE runName=?", name);
        
        int runType = c.getColumnIndex("runType");
        int runLength = c.getColumnIndex("runLength");
        int runUnit = c.getColumnIndex("runUnit");
        int breakLength = c.getColumnIndex("breakLength");
        int breakUnit = c.getColumnIndex("breakUnit");
        int repititions = c.getColumnIndex("repititions");

        String description = "";
        if (c.getString(runType) == "Time Based Intervals" ){
            description += String.format("Time Based Intervals with run times of %s %s and break times of %s %s, repeated %s times",
                    c.getString(runLength), c.getString(runUnit), c.getString(breakLength), c.getString(breakUnit), c.getString(repititions));
        }else {
            description += String.format("Distance Based Intervals with run lengths of %s%s and break lengths of %s%s, repeated %s times",
                    c.getString(runLength), c.getString(runUnit), c.getString(breakLength), c.getString(breakUnit), c.getString(repititions));
        }

        c.moveToFirst();
    }
}