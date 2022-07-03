package com.iee.trvlapp.roomEntities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.room.TypeConverter;
import java.io.ByteArrayOutputStream;

public class DataConverter {

    @TypeConverter
    public static byte[] convertIMage2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    public static Bitmap convertByteArray2IMage(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

}
