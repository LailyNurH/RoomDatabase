package com.example.databasempii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.databasempii.UI.RoomDataActivity;

public class MainActivity extends AppCompatActivity {

    Button roomdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roomdb = findViewById(R.id.btnRoomDatabase);
        roomdb.setOnClickListener(new Click());

    }

    public class Click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRoomDatabase:
                    Intent roomdb = new Intent(MainActivity.this, RoomDataActivity.class);
                    startActivity(roomdb);
                    break;
            }
        }
    }
}