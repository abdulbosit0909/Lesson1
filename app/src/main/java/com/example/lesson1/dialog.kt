package com.example.lesson1

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.lesson1.databinding.DialogMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class dialog(context: Context) : Dialog(context) {

    companion object {
        const val FILE_NAME = "your_file_name"
        const val REQUEST_CODE = 123
        lateinit var filePhoto: File
    }

    private val binding by lazy { DialogMainBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
        binding.btnChoosePhoto.setOnClickListener {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            filePhoto = getPhotoFile(FILE_NAME)
            val providerFile = FileProvider.getUriForFile(
                context,
                "com.example.androidcamera.fileprovider",
                filePhoto
            )
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

            (context as? Activity)?.startActivityForResult(takePhotoIntent, REQUEST_CODE)
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    fun handleImageRotation(filePath: String): Bitmap {
        val bitmap = BitmapFactory.decodeFile(filePath)
        val rotation = getRotation(filePath)
        return rotateBitmap(bitmap, rotation)
    }

    @SuppressLint("ExifInterface")
    private fun getRotation(filePath: String): Float {
        return try {
            val exif = android.media.ExifInterface(filePath)
            when (exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, 0)) {
                android.media.ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                android.media.ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                android.media.ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
        } catch (e: IOException) {
            e.printStackTrace()
            0f
        }
    }

   fun rotateBitmap(bitmap: Bitmap, rotation: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotation)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}

