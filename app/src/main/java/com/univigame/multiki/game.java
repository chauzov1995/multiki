package com.univigame.multiki;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.ArrayList;


public class game extends AppCompatActivity {


    //Переменная для работы с БД
    // FrameLayout contin;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    Button otv1, otv2, otv3, otv4, button3, button6;
    ArrayList<class_spis_vsego> spisokvsego;
    int lengtht;
    ImageView imageView;
    TextView textView, textView2;
    game tekactiviti;
    LinearLayout linearLayout;
    int random_vopt_btn;
    int money, score, level;
    boolean[] btn_enabl = {true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tekactiviti = this;

        MobileAds.initialize(this, "ca-app-pub-3318198202821312~9591462919");

        AdView mAdView = (AdView) findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        imageView = (ImageView) findViewById(R.id.imageView);
        otv1 = (Button) findViewById(R.id.otv1);
        otv2 = (Button) findViewById(R.id.otv2);
        otv3 = (Button) findViewById(R.id.otv3);
        otv4 = (Button) findViewById(R.id.otv4);
        button6 = (Button) findViewById(R.id.button6);
        button3 = (Button) findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);


        otv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                otvnvibor(1, r);
            }
        });
        otv2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                otvnvibor(2, r);
            }
        });
        otv3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                otvnvibor(3, r);
            }
        });
        otv4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                otvnvibor(4, r);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                onBackPressed();
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                CustomDialog1 customDialog1 = new CustomDialog1(tekactiviti);
                customDialog1.show();
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


        // get_money();

        spisokvsego = new ArrayList<class_spis_vsego>();
        Cursor c = mDb.rawQuery("SELECT id, nazv FROM t ", null);
        if (c.moveToFirst()) {
            int id = c.getColumnIndex("id");
            int nazv = c.getColumnIndex("nazv");
            do {
                spisokvsego.add(new class_spis_vsego(c.getInt(id), c.getString(nazv)));
            } while (c.moveToNext());
        }
        lengtht = spisokvsego.size();

        load_new_vopr();
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_money();
    }

    void get_money() {
        Cursor cursor = mDb.rawQuery("SELECT * FROM records ", null);
        cursor.moveToFirst();


        money = (cursor.getInt(cursor.getColumnIndex("money")));
        score = (cursor.getInt(cursor.getColumnIndex("score")));
        level = (cursor.getInt(cursor.getColumnIndex("level")));
        //  textView.setText(score);
        textView2.setText(money + "");
        textView.setText(level + "/" + lengtht);
        cursor.close();
Log.d("getmoney","dsds");
    }

    void minus_monetka(int value) {

           mDb.execSQL("UPDATE `records` SET money=money-" + value);
            get_money();



    }

    void srtimage() {


        Cursor cursor = mDb.rawQuery("SELECT * FROM t where id =" + spisokvsego.get(level).id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            byte[] bitmap1 = (cursor.getBlob(cursor.getColumnIndex("image")));


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[1024 * 32];

            Bitmap bm = BitmapFactory.decodeByteArray(bitmap1, 0, bitmap1.length, options);
            imageView.setImageBitmap(bm);


            cursor.moveToNext();
        }
        cursor.close();

        //  ubratb_1nepr();
    }

    void load_new_vopr() {

        get_money();
        random_vopt_btn = (int) (Math.random() * 4);
        enabled_btn_new();


        int[] varianti = {-1, -1, -1, -1};
        varianti[random_vopt_btn] = level;
        for (int i = 0; i < varianti.length; i++) {
            if (varianti[i] == -1) {
                boolean zikl = true;
                while (zikl) {
                    int rand = (int) (Math.random() * lengtht); // Генерация 1-го чис
                    if (rand != varianti[0] && rand != varianti[1] && rand != varianti[2] && rand != varianti[3]) {
                        varianti[i] = rand;
                        zikl = false;
                    }
                }
            }
        }


        otv1.setText(spisokvsego.get(varianti[0]).nazv);
        otv1.setTag(spisokvsego.get(varianti[0]));
        otv2.setText(spisokvsego.get(varianti[1]).nazv);
        otv2.setTag(spisokvsego.get(varianti[1]));
        otv3.setText(spisokvsego.get(varianti[2]).nazv);
        otv3.setTag(spisokvsego.get(varianti[2]));
        otv4.setText(spisokvsego.get(varianti[3]).nazv);
        otv4.setTag(spisokvsego.get(varianti[3]));

        srtimage();

    }

    void otvnvibor(int nombtn, View r) {
        if (money-10 >= 0) {

            boolean prav = false;
            switch (nombtn) {
                case 1:
                    if (((class_spis_vsego) otv1.getTag()).id == spisokvsego.get(level).id) {
                        prav = true;

                    }
                    break;
                case 2:
                    if (((class_spis_vsego) otv2.getTag()).id == spisokvsego.get(level).id) {
                        prav = true;

                    }
                    break;
                case 3:
                    if (((class_spis_vsego) otv3.getTag()).id == spisokvsego.get(level).id) {
                        prav = true;

                    }
                    break;
                case 4:
                    if (((class_spis_vsego) otv4.getTag()).id == spisokvsego.get(level).id) {
                        prav = true;

                    }
                    break;
            }

            enabled_btn_otv(nombtn - 1);


            if (prav) {

                mDb.execSQL("UPDATE `records` SET score=score+10, level=level+1");
                load_new_vopr();


            } else {
                minus_monetka(10);

            }
        }else{

            CustomDialog2 customDialog2 = new CustomDialog2(tekactiviti);
            customDialog2.show();

        }
    }



    void enabled_btn_otv(int nomen) {
        btn_enabl[nomen] = false;

        otv1.setEnabled(btn_enabl[0]);
        otv2.setEnabled(btn_enabl[1]);
        otv3.setEnabled(btn_enabl[2]);
        otv4.setEnabled(btn_enabl[3]);
    }

    void enabled_btn_new() {
        btn_enabl[0] = true;
        btn_enabl[1] = true;
        btn_enabl[2] = true;
        btn_enabl[3] = true;

        otv1.setEnabled(btn_enabl[0]);
        otv2.setEnabled(btn_enabl[1]);
        otv3.setEnabled(btn_enabl[2]);
        otv4.setEnabled(btn_enabl[3]);
    }

    public void ubratb_1nepr() {
        if (money-5 >= 0) {
        boolean stop = true;
        while (stop) {
            int randomn = (int) (Math.random() * 4);

            if (randomn != random_vopt_btn && btn_enabl[randomn] == true) {
                enabled_btn_otv(randomn);
                stop = false;
                minus_monetka(5);
            }
            if ((0 == random_vopt_btn || btn_enabl[0] == false) &&
                    (1 == random_vopt_btn || btn_enabl[1] == false) &&
                    (2 == random_vopt_btn || btn_enabl[2] == false) &&
                    (3 == random_vopt_btn || btn_enabl[3] == false)
                    ) stop = false;


        }
        }else{

            CustomDialog2 customDialog2 = new CustomDialog2(tekactiviti);
            customDialog2.show();

        }
    }

}
