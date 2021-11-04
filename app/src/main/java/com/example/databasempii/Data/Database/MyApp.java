package com.example.databasempii.Data.Database;

import android.app.Application;

import androidx.room.Room;

import com.example.databasempii.DatabaseMigrations;

public class MyApp extends Application {
private static MyApp INSTANCE;
    public static AppDatabase db;
    public static MyApp getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(this,
                AppDatabase.class,"database_mahasiswa")
                .addMigrations(
                        DatabaseMigrations.MIGRATION_1_TO_2,
                        DatabaseMigrations.MIGRATION_2_TO_3
                ).fallbackToDestructiveMigrationOnDowngrade()
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        INSTANCE = this;
    }
    public  AppDatabase getDatabase(){
        return db;
    }

}


