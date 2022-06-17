package com.example.suzuki.mensetsu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MotimonoActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motimono);

        final CheckBox chek_test = (CheckBox) findViewById(R.id.checkBox);
        chek_test.setChecked(false);

        final CheckBox chek_test2 = (CheckBox) findViewById(R.id.checkBox2);
        chek_test.setChecked(false);

        final CheckBox chek_test3 = (CheckBox) findViewById(R.id.checkBox3);
        chek_test.setChecked(false);

        final CheckBox chek_test4 = (CheckBox) findViewById(R.id.checkBox4);
        chek_test.setChecked(false);

        final CheckBox chek_test5 = (CheckBox) findViewById(R.id.checkBox5);
        chek_test.setChecked(false);

        final CheckBox chek_test6 = (CheckBox) findViewById(R.id.checkBox6);
        chek_test.setChecked(false);

        final CheckBox chek_test7 = (CheckBox) findViewById(R.id.checkBox7);
        chek_test.setChecked(false);

        Button b = (Button) findViewById(R.id.button_test);
        b.setOnClickListener(new View.OnClickListener() {
            //ボタンを押したとき
            public void onClick(View v) {
                if (chek_test.isChecked() == true && chek_test2.isChecked()== true && chek_test3.isChecked()== true
                        && chek_test4.isChecked()== true && chek_test5.isChecked()== true
                        && chek_test6.isChecked()== true && chek_test7.isChecked()== true) {
                    showMessage("準備万端です！！");
                } else {
                    showMessage("忘れ物はありませんか？");
                }
            }
        });
    }

    /**トースト設定**/
    protected void showMessage(String msg){
        Toast.makeText(
                this,
                msg, Toast.LENGTH_LONG).show();
    }

}