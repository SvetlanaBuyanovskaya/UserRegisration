package com.fandroid.mytest;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener {
    ImageView im_pas2, im_pas, im_user;
    RadioButton rb_man, rb_woman;
    RadioGroup rb_group;
    String[] countries = {"Ukraine", "France", "Poland", "Italy", "USA"};
    EditText ed_name, ed_password, ed_password2;
    Button btn_ok, btn_show;
    String Gender;
    DBHelper dbHelper;
    String selectedItem;
    final String TAG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countries);
        Spinner Spinner = (Spinner) findViewById(R.id.spinner);
        Spinner.setAdapter(adapter);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_show = (Button) findViewById(R.id.btn_show);
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_password2 = (EditText) findViewById(R.id.ed_password2);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_group = (RadioGroup) findViewById(R.id.rb_group);
        im_pas2 = (ImageView) findViewById(R.id.im_pas2);
        im_pas = (ImageView) findViewById(R.id.im_pas);
        im_user = (ImageView) findViewById(R.id.im_user);
        Spinner.setOnItemSelectedListener(this);
        rb_group.setOnCheckedChangeListener(this);
        MyTextWatcher watcher = new MyTextWatcher();
        ed_name.addTextChangedListener(watcher);
        ed_password.addTextChangedListener(watcher);
        ed_password2.addTextChangedListener(watcher);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_name.getText().toString();
                String Password = ed_password.getText().toString();
                dbHelper = new DBHelper(getBaseContext());
                dbHelper.AddUsers(name, Password, Gender, selectedItem);
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, Password;
                int id;
                dbHelper = new DBHelper(getBaseContext());
                Cursor cursor = dbHelper.getUsers();
                if (cursor.moveToFirst()) {
                    do {
                        id = cursor.getInt(0);
                        name = cursor.getString(1);
                        Password = cursor.getString(2);
                        Gender = cursor.getString(3);
                        selectedItem = cursor.getString(4);
                        Log.d(TAG, "ID=" + id + "," + "name:" + name + "," + "Password:" + Password + "," + "Gender:" +
                                Gender + "," + "Country:" + selectedItem);
                    } while (cursor.moveToNext());
                } else
                    Log.d(TAG, "0 rows");
                cursor.close();
                dbHelper.close();
            }

            ;
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_man:
                Gender = "male";
                break;
            case R.id.rb_woman:
                Gender = "female";
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ed_password.length() != 0) {
                im_pas.setImageResource(R.drawable.ic_ok);
            }
            if (ed_name.length() != 0) {
                im_user.setImageResource(R.drawable.ic_ok);
            }
            if(ed_name.getText().toString().equals("")){
                im_user.setImageResource(R.drawable.ic_false);
            }
            if(ed_password.getText().toString().equals("")){
                im_pas.setImageResource(R.drawable.ic_false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (ed_password2.getText().toString().equals(ed_password.getText().toString())) {
                im_pas2.setImageResource(R.drawable.ic_ok);
                btn_ok.setEnabled(true);
            } else {
                im_pas2.setImageResource(R.drawable.ic_false);
                btn_ok.setEnabled(false);

            }
            if (ed_name.getText().toString().equals("")|| ed_password.getText().toString().equals("")||
                    ed_password2.getText().toString().equals("")) {
                btn_ok.setEnabled(false);
                im_pas2.setImageResource(R.drawable.ic_false);
            }

            }

        }

    }





