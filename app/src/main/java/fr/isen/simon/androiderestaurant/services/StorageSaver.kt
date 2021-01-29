package fr.isen.simon.androiderestaurant.services

import android.content.Context
import com.google.gson.JsonObject
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class StorageSaver {

    fun storeJson(context: Context, filename: String, obj : Serializable){
        val fos: FileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(obj)
        os.close()
        fos.close()
    }
}