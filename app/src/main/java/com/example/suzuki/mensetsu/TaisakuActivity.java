package com.example.suzuki.mensetsu;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.SystemClock;
import android.widget.Chronometer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Button;
import java.util.ArrayList;
import android.util.Log;


public class TaisakuActivity extends Activity {
    private Chronometer chronometer;
    private Button stopbtn;

    private static final int REQUEST_CODE = 1000;
    private TextView textView;
    private Button buttonStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taisaku);

        chronometer=(Chronometer)findViewById(R.id.Chronometer);
        stopbtn=(Button)findViewById(R.id.Button_stop);

        //タイマーをリセット
        chronometer.setBase(SystemClock.elapsedRealtime());

        //ストップボタンクリック
        stopbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chronometer.stop();
            }
        });

        textView = (TextView)findViewById(R.id.text_view);

    }

    public void onClick_Button(View target) {
        TextView textView = (TextView) findViewById(R.id.textView1);
        String[] planets = {"なぜ弊社を志望するのですか？","入社したらどんな仕事がしたいですか？"
                ,"自己ＰＲをしてください？","なぜ中京大学へ入ったのですか？"
                ,"５年後、１０年後に自分がどうなっていると考えていますか？"
                ,"あなたの長所は？","あなたの短所は？","学生時代に力を入れた事は？ "
                ,"学生時代に学んだ事は？","あなたの能力を当社でどのように活かせると思いますか？"
                ,"アルバイトはなにをしていますか？","アルバイトで何を学びましたか？"
                ,"クラブやサークル活動はやっていますか？","他社の進行状況は？","最近関心のある事は？"
                ,"学生時代に最も力を入れたことは何ですか？","リーダーシップを取った経験はありますか？"
                ,"今までの人生で最もつらかったことはなんですか？","今まで一番感動したことは？"
                ,"友人や周りの人からどんな人だと言われますか？","あなたの夢を教えてください"
                ,"趣味を教えてください ","尊敬する人は誰ですか？また、それはなぜですか？"};
        Random rnd = new Random();
        int index = rnd.nextInt(planets.length);
        textView.setText(planets[index]);
        //リセット
        chronometer.setBase(SystemClock.elapsedRealtime());
        //スタート
        chronometer.start();

        speech();

    }

    private void speech(){
        // 音声認識が使えるか確認する
        try {
            // 音声認識の　Intent インスタンス
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力");
            // インテント発行
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (ActivityNotFoundException e) {
            textView.setText("No Activity " );
        }

    }

    // 結果を受け取るために onActivityResult を設置
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Speech", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // 認識結果を ArrayList で取得
            ArrayList<String> candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if(candidates.size() > 0) {
                // 認識結果候補で一番有力なものを表示
                textView.setText( candidates.get(0));
            }
        }
    }
}
