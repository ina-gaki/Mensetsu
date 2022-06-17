package com.example.suzuki.mensetsu;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;


public class FukusyuActivity extends ActionBarActivity implements OnClickListener{

    private final static String DB_NAME = "fukusyu.db";
    private final static String DB_TABLE = "mensetu";
    private final static int DB_VERSION = 1;

    DBHelper helper = null;
    SQLiteDatabase db = null;

    int[] BUTTONS = { R.id.insert, R.id.update, R.id.delete, R.id.view,R.id.search };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fukusyu);

        for (int btnId : BUTTONS) {
            Button btn = (Button) findViewById(btnId);
            btn.setOnClickListener(this);
        }
        helper = new DBHelper(FukusyuActivity.this);
    }

    @Override
    public void onClick(View v) {

        String message = "";
        TextView res = (TextView) findViewById(R.id.res);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editSitumon = (EditText) findViewById(R.id.editsitumon);
        EditText editHentou = (EditText) findViewById(R.id.edithentou);

        TableLayout t_layout = (TableLayout) findViewById(R.id.list);

        t_layout.removeAllViews();

        db = helper.getWritableDatabase();

        if (v.getId() == R.id.insert) {
            try {
                db.beginTransaction();
                ContentValues val = new ContentValues();
                val.put("name", editName.getText().toString());
                val.put("situmon", editSitumon.getText().toString());
                val.put("hentou", editHentou.getText().toString());
                db.insert(DB_TABLE, null, val);
                db.setTransactionSuccessful();
                db.endTransaction();
                editName.setText("");
                editSitumon.setText("");
                editHentou.setText("");
                message = "データ登録が完了しました";
            } catch (Exception e) {
                message = "データ登録に失敗しました";
                Log.e("登録エラー", e.toString());
            }
        } else if (v.getId() == R.id.update) {
            try {
                db.beginTransaction();
                ContentValues val = new ContentValues();
                val.put("situmon", editSitumon.getText().toString());
                val.put("hentou", editHentou.getText().toString());
                db.update(DB_TABLE, val, "name=?", new String[]{editName.getText().toString()});
                db.setTransactionSuccessful();
                db.endTransaction();
                message = "データの更新をしました";

            } catch (Exception e) {
                message = "更新に失敗しました";
                Log.e("更新", e.toString());
            }

        } else if (v.getId() == R.id.delete) {
            try {
                db.beginTransaction();
                db.delete(DB_TABLE, "name=?", new String[] { editName.getText().toString() });
                db.setTransactionSuccessful();
                db.endTransaction();
                message = "データを削除しました";

            } catch (Exception e) {
                message = "削除に失敗しました";
                Log.e("削除", e.toString());

            }

        } else if (v.getId() == R.id.view) {
            try {
                db = helper.getReadableDatabase();
                String[] columns = { "name", "situmon", "hentou" };
                Cursor cursor = db.query(DB_TABLE, columns, null, null, null,
                        null, "name");
                t_layout.setStretchAllColumns(true);

                TableRow headrow = new TableRow(FukusyuActivity.this);
                TextView headeTxt1 = new TextView(FukusyuActivity.this);
                headeTxt1.setText("企業名");
                headeTxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                headeTxt1.setWidth(20);
                TextView headText2 = new TextView(FukusyuActivity.this);
                headText2.setText("質問内容");
                headText2.setGravity(Gravity.CENTER_HORIZONTAL);
                headText2.setWidth(100);
                TextView headText3 = new TextView(FukusyuActivity.this);
                headText3.setText("返答内容");
                headText3.setGravity(Gravity.CENTER_HORIZONTAL);
                headText3.setWidth(100);
                headrow.addView(headeTxt1);
                headrow.addView(headText2);
                headrow.addView(headText3);
                t_layout.addView(headrow);

                while (cursor.moveToNext()) {
                    TableRow row = new TableRow(FukusyuActivity.this);
                    TextView nameText = new TextView(FukusyuActivity.this);
                    nameText.setText(cursor.getString(0));
                    nameText.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView situmonText = new TextView(FukusyuActivity.this);
                    situmonText.setText(cursor.getString(1));
                    situmonText.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView hentouTextView = new TextView(FukusyuActivity.this);
                    hentouTextView.setText(cursor.getString(2));
                    hentouTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                    row.addView(nameText);
                    row.addView(situmonText);
                    row.addView(hentouTextView);
                    t_layout.addView(row);

                    message = "データ取得をしました";

                }
            } catch (Exception e) {
                message = "データ取得に失敗しました";
                Log.e("表示", e.toString());
            }

        } else if (v.getId() == R.id.search) {
            try {
                db.beginTransaction();
                Cursor cursor = null;

                if(editName.getText().toString().length() >= 1) {
                    cursor = db.query(DB_TABLE, new String[]{"name", "situmon", "hentou"},
                            "name like ?", new String[]{editName.getText().toString()}, null, null, null);
                    t_layout.setStretchAllColumns(true);
                }else if(editSitumon.getText().toString().length() >= 1) {
                    cursor = db.query(DB_TABLE, new String[]{"name", "situmon", "hentou"},
                            "situmon like ?", new String[]{editSitumon.getText().toString()}, null, null, null);
                    t_layout.setStretchAllColumns(true);
                }else if(editHentou.getText().toString().length() >= 1) {
                    cursor = db.query(DB_TABLE, new String[]{"name", "situmon", "hentou"},
                            "hentou like ?", new String[]{editHentou.getText().toString()}, null, null, null);
                    t_layout.setStretchAllColumns(true);
                }

                TableRow headrow = new TableRow(FukusyuActivity.this);
                TextView headeTxt1 = new TextView(FukusyuActivity.this);
                headeTxt1.setText("企業名");
                headeTxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                headeTxt1.setWidth(20);
                TextView headText2 = new TextView(FukusyuActivity.this);
                headText2.setText("質問内容");
                headText2.setGravity(Gravity.CENTER_HORIZONTAL);
                headText2.setWidth(100);
                TextView headText3 = new TextView(FukusyuActivity.this);
                headText3.setText("返答内容");
                headText3.setGravity(Gravity.CENTER_HORIZONTAL);
                headText3.setWidth(100);
                headrow.addView(headeTxt1);
                headrow.addView(headText2);
                headrow.addView(headText3);
                t_layout.addView(headrow);

                while (cursor.moveToNext()) {
                    TableRow row = new TableRow(FukusyuActivity.this);
                    TextView nameText = new TextView(FukusyuActivity.this);
                    nameText.setText(cursor.getString(0));
                    nameText.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView situmonText = new TextView(FukusyuActivity.this);
                    situmonText.setText(cursor.getString(1));
                    situmonText.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView hentouTextView = new TextView(FukusyuActivity.this);
                    hentouTextView.setText(cursor.getString(2));
                    hentouTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                    row.addView(nameText);
                    row.addView(situmonText);
                    row.addView(hentouTextView);
                    t_layout.addView(row);

                    message = "データ取得をしました";

                }

                db.setTransactionSuccessful();
                db.endTransaction();

            } catch (Exception e) {
                message = "検索に失敗しました";
                Log.e("検索", e.toString());
            }

        }

        db.close();

        res.setText(message);
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table if not exists "
                    + DB_TABLE
                    + "(name text not null,situmon text not null,hentou text not null)";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DB_TABLE);
            onCreate(db);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serach, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_serach:
                Intent intent = new Intent(FukusyuActivity.this, SerachActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}