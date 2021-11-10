package com.example.databasempii.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.databasempii.R;
import com.example.databasempii.Data.Database.MyApp;
import com.example.databasempii.Data.Model.Mahasiswa;
import com.example.databasempii.Data.common.DataListListener;
import com.example.databasempii.UI.AddRoomDataActivity;
import com.example.databasempii.UI.CrudActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Mahasiswa> dataList = new ArrayList<>();
    private DataListListener listener;

    public void setData(List<Mahasiswa> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            Mahasiswa data = dataList.get(i);
            int position = findPosition(data);
            if (position == -1) {
                this.dataList.add(data);
                notifyItemInserted(this.dataList.size() - 1);
            } else {
                this.dataList.remove(position);
                this.dataList.add(position, data);
                notifyItemChanged(position);
            }
        }
    }

    private int findPosition(Mahasiswa data) {
        int position = -1;

        if (!this.dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (this.dataList.get(i).getId() == data.getId()) {
                    position = i;
                }
            }
        }

        return position;
    }

    public void removeData(Mahasiswa data) {
        if (this.dataList.isEmpty()) {
            return;
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (this.dataList.get(i).getId() == data.getId()) {
                this.dataList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void setRemoveListener(DataListListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tvNama, tvNim, tvAlamat, tvKejuruan, tvSks;
        private ImageView btnHapus;
        private ImageView imageView;
        private Mahasiswa data;
        private DataListListener listener;
        private AlertDialog.Builder rvListMahasiswa;
        private ListAdapter adapter;
        private RequestOptions requestOptions;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_account);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvNim = itemView.findViewById(R.id.tvNim);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvKejuruan = itemView.findViewById(R.id.tvKejuruan);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            tvSks = itemView.findViewById(R.id.tvSKS);
            imageView = itemView.findViewById(R.id.image);
            btnHapus.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        void bind(Mahasiswa data, DataListListener listener) {
            this.data = data;
            this.listener = listener;

            tvNama.setText(data.getNama());
            tvNim.setText(data.getNim());
            tvAlamat.setText(data.getAlamat());
            tvKejuruan.setText(data.getKejuruan());
            tvSks.setText(String.valueOf(data.getSks()));

            loadImage(new File(data.getImage()));
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_hapus) {

                AlertDialog.Builder builder = new AlertDialog.Builder(btnHapus.getContext());
                builder.setTitle("Peringatan !!! ")
                        .setMessage("Apakah Anda Ingin Menghapus Data Ini ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyApp.getInstance().getDatabase().userDao().delete(data);
                                listener.onRemoveClick(data);
                                Toast.makeText(itemView.getContext(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            } else if (view.getId() == R.id.item_list) {

                Intent intent = new Intent(itemView.getContext(), AddRoomDataActivity.class);
                intent.putExtra(AddRoomDataActivity.TAG_DATA_INTENT, data.getId());
                itemView.getContext().startActivity(intent);

            }

        }

        private void loadImage(File image) {
            if (image == null) return;

            Glide.with(itemView.getContext())
                    .asBitmap()
                    .apply(requestOptions)
                    .load(image)
                    .into(imageView);
        }
    }
}



