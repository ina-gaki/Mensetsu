package com.example.suzuki.mensetsu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class MensetsuActivity extends ActionBarActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensetsu);

        button1 =  (Button) findViewById(R.id.fukusyu_button);
        button2 =  (Button) findViewById(R.id.taisaku_button);
        button3 =  (Button) findViewById(R.id.manners_button);
        button4 =  (Button) findViewById(R.id.motimono_button);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =
                        new Intent(MensetsuActivity.this,FukusyuActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =
                        new Intent(MensetsuActivity.this,TaisakuActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =
                        new Intent(MensetsuActivity.this,MannersActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =
                        new Intent(MensetsuActivity.this,MotimonoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mensetsu, menu);
        return true;
    }

    @Override
       public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
