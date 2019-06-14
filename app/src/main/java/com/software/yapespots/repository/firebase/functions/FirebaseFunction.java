package com.software.yapespots.repository.firebase.functions;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class FirebaseFunction implements IFirebaseFunction {
    private FirebaseFunctions mFunctions;
    private Map<String, Object> data;

    public FirebaseFunction() {
        mFunctions = FirebaseFunctions.getInstance();
    }

    private Task<HashMap<String, Object>> getPlacesTask() {
        return mFunctions.getHttpsCallable("places").call(this.data).continueWith(new Continuation<HttpsCallableResult, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                return (HashMap<String, Object>) task.getResult().getData();
            }
        });
    }

    private Task<HashMap<String, Object>> getSearchPlaces() {
        return mFunctions.getHttpsCallable("searchPlaces").call(this.data).continueWith(new Continuation<HttpsCallableResult, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                return (HashMap<String, Object>) task.getResult().getData();
            }
        });
    }

    private Task<HashMap<String, Object>> setLikeTask() {
        return mFunctions.getHttpsCallable("rate").call(this.data).continueWith(new Continuation<HttpsCallableResult, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                return (HashMap<String, Object>) task.getResult().getData();
            }
        });
    }

    private Task<HashMap<String, Object>> setReportTask() {
        return mFunctions.getHttpsCallable("report").call(this.data).continueWith(new Continuation<HttpsCallableResult, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                return (HashMap<String, Object>) task.getResult().getData();
            }
        });
    }

    @Override
    public void getPlaces(OnCompleteListener<HashMap<String, Object>> eventListener) {
        Task<HashMap<String, Object>> placesFromTask = getPlacesTask();
        placesFromTask.addOnCompleteListener(eventListener);
    }

    @Override
    public void searchPlaces(OnCompleteListener<HashMap<String, Object>> eventListener) {
        Task<HashMap<String, Object>> placesFromTask = getSearchPlaces();
        placesFromTask.addOnCompleteListener(eventListener);
    }

    @Override
    public void likePlace(OnCompleteListener<HashMap<String, Object>> eventListener) {
        Task<HashMap<String, Object>> likeFromTask = setLikeTask();
        likeFromTask.addOnCompleteListener(eventListener);
    }

    @Override
    public void reportPlace(OnCompleteListener<HashMap<String, Object>> eventListener) {
        Task<HashMap<String, Object>> reportFromTask = setReportTask();
        reportFromTask.addOnCompleteListener(eventListener);
    }

    public FirebaseFunction withData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}
