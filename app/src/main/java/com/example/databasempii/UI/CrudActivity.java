package com.example.databasempii.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasempii.R;
import com.example.databasempii.Adapter.RecyclerViewAdapter;
import com.example.databasempii.Data.Database.MyApp;
import com.example.databasempii.Data.Model.Mahasiswa;
import com.example.databasempii.Data.common.DataListListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CrudActivity extends AppCompatActivity {
    private RecyclerView rvListMahasiswa;
    private FloatingActionButton fabTambah;
    private RecyclerViewAdapter adapter;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        rvListMahasiswa = findViewById(R.id.rv_list_mahasiswa);
        fabTambah = findViewById(R.id.fab_tambah_data);
        adapter = new RecyclerViewAdapter();
        rvListMahasiswa.setAdapter(adapter);
        builder = new AlertDialog.Builder(this);


        adapter.setRemoveListener(new DataListListener() {
            @Override
            public void onRemoveClick(Mahasiswa mahasiswa) {
                adapter.removeData(mahasiswa);
                adapter.setRemoveListener(view -> builder.setTitle("Alert..!!")
                        .setMessage("Apakah anda yakin untuk menghapus data")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            finish();
                            startActivity(new Intent(CrudActivity.this, CrudActivity.class));
                            Toast.makeText(builder.getContext(),"Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .show());
            }

        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CrudActivity.this, AddRoomDataActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Mahasiswa> datas = MyApp.getInstance().getDatabase().userDao().getAll();
        adapter.setData(datas);

    }

}