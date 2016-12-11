package com.example.laba7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    Button ok;
    ArrayAdapter<String> adapter;
    ArrayList<String> text = new ArrayList<String>();
    DBHalper dbHalper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.list_view);
        ok = (Button) findViewById(R.id.button);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, text);

        listView.setAdapter(adapter);

        ok.setOnClickListener(this);

        registerForContextMenu(listView);

        adapter.notifyDataSetChanged();

        dbHalper = new DBHalper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateArray();
    }

    public void updateArray() {
        SQLiteDatabase db = dbHalper.getWritableDatabase();

        Cursor cursor = db.query(DBHalper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHalper.KEY_ID);
            int name = cursor.getColumnIndex(DBHalper.KEY_NAME);

            do {
                text.add(cursor.getString(name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHalper.KEY_NAME,editText.getText().toString());

        SQLiteDatabase db = dbHalper.getWritableDatabase();

        db.insert(DBHalper.TABLE_CONTACTS, null, contentValues);

        editText.setText("");

        updateArray();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       // super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Видалити");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

      //  SQLiteDatabase db = dbHalper.getWritableDatabase();

       // db.delete(DBHalper.TABLE_CONTACTS);

     //   updateArray();

        return super.onContextItemSelected(item);
    }
}
