package com.example.breatheeasy.data.databases.run

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Convertors {

    //TYPECONVERTER TO ENABLE SAVING OF BITMAP TO ROOM AND DISPLAY OF BITMAP WHEN BEING RETRIEVED FROM ROOM

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray{
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        return output.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}