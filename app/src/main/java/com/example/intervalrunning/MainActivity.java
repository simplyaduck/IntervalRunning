package com.example.intervalrunning;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        runList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //launch get ready to run with the settings loaded as labels
            }
        });

        runList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(parent.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Run?")
                        .setMessage("Would you like to delete the run? This is non reversible")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String sql = String.format("DELETE FROM run_settings where runName='%s'", runNames.get(position));
                                Log.i("statement", sql);
                                SQLiteStatement statement = runSettings.compileStatement(sql);
                                statement.execute();
                                runNames.remove(position);
                                //TODO shouldnt have to query database to update the list here
                                updateListView();
                                Toast.makeText(MainActivity.this, "Run deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return false;
            }
        });



    }

    public void createNewRun(View view){
        Intent intent = new Intent(this, RunSettingsActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateListView();
            }
        }
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