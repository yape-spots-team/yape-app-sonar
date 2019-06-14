package com.software.yapespots.ui.detailplace;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PhotosClass {
    private Bitmap image;

    public PhotosClass(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
    public void getResizedBitmap( int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                image, 0, 0, width, height, matrix, false);
        image.recycle();
        image=resizedBitmap;
    }
}
