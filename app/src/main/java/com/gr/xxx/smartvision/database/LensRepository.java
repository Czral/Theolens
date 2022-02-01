package com.gr.xxx.smartvision.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LensRepository {

    private LensDao lensDao;
    private LensRoomDatabase database;

    LensRepository(Application application) {

        database = LensRoomDatabase.getDatabase(application);
        lensDao = database.lensDao();
    }

    public LiveData<List<Lens>> getAll() {
        return lensDao.getAll();
    }

    public void deleteLens(Lens lens) {

        LensRoomDatabase.databaseWriteExecutor.execute(() -> lensDao.deleteLens(lens));
    }

    public void insertLens(Lens lens) {

        LensRoomDatabase.databaseWriteExecutor.execute(() -> lensDao.insertLens(lens));
    }

    public void deleteAll() {

        LensRoomDatabase.databaseWriteExecutor.execute(() -> lensDao.deleteAll());
    }

    public LiveData<Lens> getLens(int id) {

        return lensDao.getLens(id);
    }
}
