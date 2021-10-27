package com.example.databasempii.Data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.databasempii.Data.Model.Mahasiswa;

import java.util.List;

@Dao
public interface MahasiswaDAO {

    @Query("SELECT * FROM mahasiswa")
    List<Mahasiswa> getAll();


    @Query("SELECT * FROM mahasiswa WHERE id LIKE :mahasiswaId LIMIT 1")
    Mahasiswa findById(int mahasiswaId);

    @Insert
    void insertData(Mahasiswa mahasiswa);

    @Update
    void update(Mahasiswa mahasiswa);

    @Delete
    void delete(Mahasiswa mahasiswa);


}