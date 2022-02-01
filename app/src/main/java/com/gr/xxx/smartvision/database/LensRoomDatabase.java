package com.gr.xxx.smartvision.database;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Lens.class, version = 1, exportSchema = false)
public abstract class LensRoomDatabase extends RoomDatabase {

    public abstract LensDao lensDao();

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);
    private static volatile LensRoomDatabase INSTANCE;

    public static LensRoomDatabase getDatabase(Application application) {

        if (INSTANCE == null) {

            synchronized (LensRoomDatabase.class) {

                    INSTANCE = Room.databaseBuilder(application,
                            LensRoomDatabase.class,
                            "lens_database")
                            .build();

            }
        }

        return INSTANCE;
    }


}
