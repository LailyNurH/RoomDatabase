package com.example.databasempii.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databasempii.R;
import com.example.databasempii.Data.DAO.MahasiswaDAO;
import com.example.databasempii.Data.Database.MyApp;
import com.example.databasempii.Data.Model.Mahasiswa;

public class AddRoomDataActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNama, etNim, etKejuruan, etAlamat;
    public final static String TAG_DATA_INTENT = "data_mahasiswa";
    private Mahasiswa mahasiswa;
    private MahasiswaDAO dao;
    private Button btnTambah;

    @SuppressLint("setTextll8n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_data);
        dao = MyApp.getInstance().getDatabase().userDao();

        if (getIntent() != null) {
            int id = getIntent().getIntExtra(TAG_DATA_INTENT, 0);
            mahasiswa = dao.findById(id);

        }

        btnTambah = findViewById(R.id.btInsert);
        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etKejuruan = findViewById(R.id.etKejuruan);
        etAlamat = findViewById(R.id.etAlamat);

        if (mahasiswa != null) {
            etNama.setText(mahasiswa.getNama());
            etNim.setText(mahasiswa.getNim());
            etKejuruan.setText(mahasiswa.getKejuruan());
            etAlamat.setText(mahasiswa.getAlamat());

            btnTambah.setText("Ubah Data");
        }
        btnTambah.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        addOrUpdate();
        if (mahasiswa.getId() == 0) {
            dao.insertData(mahasiswa);
        } else {
            dao.update(mahasiswa);
        }
        Toast.makeText(this, btnTambah.getText().toString(), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void addOrUpdate() {
        if (mahasiswa == null) {
            mahasiswa = new Mahasiswa();
        }
        mahasiswa.setNama(etNama.getText().toString());
        mahasiswa.setNim(etNim.getText().toString());
        mahasiswa.setAlamat(etAlamat.getText().toString());
        mahasiswa.setKejuruan(etKejuruan.getText().toString());
    }


}
