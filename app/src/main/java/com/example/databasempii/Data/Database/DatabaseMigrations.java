package com.example.databasempii.Data.Database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.databasempii.Data.DAO.MahasiswaDAO;

public class DatabaseMigrations {
    public static final Migration MIGRATION_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Mahasiswa ADD COLUMN SKS int DEFAULT 0");
        }
    };
    public static final Migration MIGRATION_2_TO_3 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Mahasiswa (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nama TEXT,nim TEXT, kejuruan TEXT, SKS TEXT, alamat TEXT, gambar TEXT)");
        }
    };
    public static final Migration MIGRATION_3_TO_4 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE mahasiswa ADD COLUMN image TEXT DEFAULT ''");
        }
    };
}

