package com.univigame.multiki;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog1  {
    private EditText dialogEditBox;
    private TextView dialogInfo;
    private Button dialogButton,button2,button4;
    private Dialog dialog;
    private game activity;

    public CustomDialog1( game activity ){
        this.activity = activity;
        init();
    }

    private void init(){

        dialog = new Dialog(activity, R.style.CustomDialog);
        dialog.setTitle("Подсказки");
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);



                button4 = (Button) dialog.findViewById( R.id.button4 );
        dialogButton = (Button) dialog.findViewById( R.id.dialog_button );
        button2 = (Button) dialog.findViewById( R.id.button2 );

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                activity.ubratb_1nepr();
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View r) {
                dialog.dismiss();
                Intent intent = new Intent(activity, nagrada_za_reklamu.class);
                activity.startActivity(intent);
                activity.get_money();


            }
        });
    }

    public void show(){
        dialog.show();
    }




}