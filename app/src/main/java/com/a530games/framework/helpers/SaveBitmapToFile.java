package com.a530games.framework.helpers;

import android.graphics.Bitmap;
import android.util.Log;

import com.a530games.framework.FileIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * SAve bitmap in to file
 */
public class SaveBitmapToFile {

    /**
     * Save bitmap to file
     * in file manager file in /sdcard/Android/data/com.a530games.jackal/files/Pictures/
     */
    public static void SaveToFile(FileIO fileIo, Bitmap bitmap)
    {
        try {
            // String.format("", )
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(Calendar.getInstance().getTime());

            /*OutputStream osw = fileIo.writeFile("export_.jpg");
            // Bitmap pictureBitmap = getImageBitmap(myurl); // obtaining the Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, osw); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            osw.flush();
            osw.close();*/

            FileOutputStream fs = new FileOutputStream("/storage/emulated/0/Android/data/com.a530games.jackal/files/Pictures/export"+strDate+".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fs); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fs.flush(); // Not really required
            fs.close();

            Log.d("SaveBitmapToFile", "file saved to: " + strDate + ".jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
