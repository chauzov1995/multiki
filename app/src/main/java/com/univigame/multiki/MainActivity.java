package com.univigame.multiki;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MainActivity tekactiviti;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        tekactiviti=this;
        Button byn_start = (Button) findViewById(R.id.byn_start);


        byn_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                Intent intent = new Intent(tekactiviti, game.class);
                startActivity(intent);



            }
        });

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView textView10 = (TextView) findViewById(R.id.textView10);
        Cursor cursor = mDb.rawQuery("SELECT * FROM records ", null);
        cursor.moveToFirst();


        int score = (cursor.getInt(cursor.getColumnIndex("score")));
        textView10.setText(score+"");
    }
}
