package com.software.yapespots.repository.firebase.functions;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.HashMap;

public interface IFirebaseFunction {
    void getPlaces(OnCompleteListener<HashMap<String, Object>> eventListener);

    void searchPlaces(OnCompleteListener<HashMap<String, Object>> eventListener);

    void likePlace(OnCompleteListener<HashMap<String, Object>> eventListener);

    void reportPlace(OnCompleteListener<HashMap<String, Object>> eventListener);
}
