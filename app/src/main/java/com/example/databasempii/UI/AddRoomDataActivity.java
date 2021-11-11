package com.example.databasempii.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.databasempii.Data.DAO.MahasiswaDAO;
import com.example.databasempii.Data.Database.MyApp;
import com.example.databasempii.Data.Model.Mahasiswa;
import com.example.databasempii.Data.common.DialogImageOptionsListener;
import com.example.databasempii.R;
import com.example.databasempii.dialog.CustomDialogImageOption;

import java.io.File;
import java.io.IOException;

public class AddRoomDataActivity extends AppCompatActivity implements View.OnClickListener, DialogImageOptionsListener {

    EditText etNama, etNim, etKejuruan, etAlamat, etSKS;
    public final static String TAG_DATA_INTENT = "data_mahasiswa";
    public final static int REQUEST_CAMERA = 101;
    public final static int REQUEST_GALLERY = 202;
    public final static int PICK_CAMERA = 1001;
    public final static int PICK_GALLERY = 2002;
    private Mahasiswa mahasiswa;
    private MahasiswaDAO dao;
    private Button btnTambah;
    private ImageView imageView;
    private ImageView addImage;
    private RequestOptions requestOptions;
    private File fileImage;

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
        if (mahasiswa == null) {
            mahasiswa = new Mahasiswa();
        }
        requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false)
                .centerCrop()
                .circleCrop()
           ;

        btnTambah = findViewById(R.id.btInsert);
        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etKejuruan = findViewById(R.id.etKejuruan);
        etAlamat = findViewById(R.id.etAlamat);
        etSKS = findViewById(R.id.etSKS);
        addImage = findViewById(R.id.add_image);
        imageView = findViewById(R.id.image);

        btnTambah.setOnClickListener(this);
        addImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btInsert:
                addOrUpdate();
                if (mahasiswa.getId() == 0) {
                    dao.insertData(mahasiswa);
                } else {
                    dao.update(mahasiswa);
                }

                Toast.makeText(this, btnTambah.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.add_image:
            default:
                new CustomDialogImageOption(AddRoomDataActivity.this,
                        AddRoomDataActivity.this)
                        .show();
                break;
        }
    }

    private void addOrUpdate() {

        mahasiswa.setNama(etNama.getText().toString());
        mahasiswa.setNim(etNim.getText().toString());
        mahasiswa.setAlamat(etAlamat.getText().toString());
        mahasiswa.setKejuruan(etKejuruan.getText().toString());
        mahasiswa.setSks(Integer.parseInt(etSKS.getText().toString()));
    }


    @Override
    public void onCameraClick() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

    }

    private void openCamera() {
        try {
            fileImage = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            Uri imageUri = FileProvider.getUriForFile(this, "com.example.databasempii" + ".provider", fileImage);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PICK_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGalleryClick() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_GALLERY);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        if (mahasiswa.getId() > 0)  {
            etNama.setText(mahasiswa.getNama());
            etNim.setText(mahasiswa.getNim());
            etKejuruan.setText(mahasiswa.getKejuruan());
            etAlamat.setText(mahasiswa.getAlamat());
            etSKS.setText(String.valueOf(mahasiswa.getSks()));

            btnTambah.setText("Ubah Data");

            loadImage(new File(mahasiswa.getImage()));
        }
    }

    private void loadImage(File image) {
        if (image == null) return;

        Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(image)
                .into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path;
            if (requestCode == PICK_GALLERY) {
                path = getRealPathFromUri(data.getData());
            } else {
                path = fileImage.getAbsolutePath();
            }

            mahasiswa.setImage(path);
            loadImage(new File(path));
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null
                , MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}
