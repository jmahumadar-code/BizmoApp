package com.bizmolimited.bizapp.utils;

import android.util.Log;

import com.firebase.jobdispatcher.JobService;

/**
 * Created by guillermofuentesquijada on 04-11-17.
 */

public class MSGJobService extends JobService {

    private static final String TAG = MSGJobService.class.getSimpleName();


    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "Performing long running task in scheduled job");
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}