/*package com.software.yapespots.repository.map;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.gson.JsonObject;
import com.software.yapespots.repository.firebase.functions.FirebaseFunction;

import java.util.Collection;

public class PlaceRepository implements IPlaceRepository {
    @Override
    public Collection<Object> all() {
        FirebaseFunction firebase = new FirebaseFunction();
        OnCompleteListener<JsonObject> listener = new OnCompleteListener<JsonObject>() {
            @Override
            public void onComplete(@NonNull Task<JsonObject> task) {
                if (!task.isSuccessful()) {
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException firebaseException = (FirebaseFunctionsException) exception;
                    }

                    Log.w("Firebase::getPlaces::", "getPlaces:onFailure", exception);
                    return;
                }

                JsonObject result = task.getResult();
            }
        };

        firebase.getPlaces(listener);
    }

    @Override
    public boolean create(Object element) {
        return false;
    }

    @Override
    public boolean update(Object element, Object id) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean find(Object id) {
        return false;
    }
}
*/