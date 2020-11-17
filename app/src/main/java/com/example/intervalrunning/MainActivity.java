package com.example.intervalrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView runList;
    ArrayList<String> runNames = new ArrayList<>();

    SQLiteDatabase runSettings;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runSettings = this.openOrCreateDatabase("RunSettings", MODE_PRIVATE, null);
        runSettings.execSQL("CREATE TABLE IF NOT EXISTS run_settings (id integer PRIMARY KEY, " +
                "runName VARCHAR, runType INTEGER, runLength double, breakLength double, repititions integer)");


        runList = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, runNames);
        runList.setAdapter(arrayAdapter);

        updateListView();

    }

    public void createNewRun(View view){
        Intent intent = new Intent(this, RunSettingsActivity.class);
        startActivity(intent);
    }

    public void updateListView(){
        Cursor c = runSettings.rawQuery("select * from run_settings", null);

        int titleIndex = c.getColumnIndex("runName");

        if (c.moveToFirst()){
            runNames.clear();

            do {
                Log.i("titles ", c.getString(titleIndex));
                runNames.add(c.getString(titleIndex));
            } while(c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
    }


}