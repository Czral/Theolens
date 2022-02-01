package com.gr.xxx.smartvision.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.gr.xxx.smartvision.BasketReminderUtils;
import com.gr.xxx.smartvision.BasketWorker;

import java.util.List;

public class LensViewModel extends AndroidViewModel {

    private LensRepository lensRepository;
    private WorkManager manager;

    public LensViewModel(Application application) {

        super(application);
        lensRepository = new LensRepository(application);
        manager = WorkManager.getInstance(application);
    }


    public void insertLens(Lens lens) {

        lensRepository.insertLens(lens);
    }

    public LiveData<List<Lens>> getAll() {

        return lensRepository.getAll();
    }

    public void deleteAll() {

        lensRepository.deleteAll();
    }

    public void deleteLens(Lens lens) {

        lensRepository.deleteLens(lens);
    }

   public void setNotifications() {

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BasketWorker.class).build();
        manager.enqueue(request);
    }

    public void cancelNotification() {

        manager.cancelAllWork();
    }

}
