@file:OptIn(ExperimentalEncodingApi::class)

package com.example.lesson1

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.view.ViewCompat.getRotation
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


const val PREFS_NAME = "LoginPref"

class ShairPreferenses {
    companion object {
        private fun getInstance(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }

        fun setUsername(context: Context, username: String) {
            val editor = getInstance(context).edit()
            editor.putString("username", username)
            editor.apply()
        }

        fun getCode(context: Context, code: String) {
            val cod = getInstance(context).edit()
            cod.putString("userfirstname", code)
            cod.apply()
        }
        fun saveImage(context: Context, imagePath: String) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("image", imagePath)
            editor.apply()
        }

        fun getImage(context: Context): String? {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getString("image", null)
        }

        fun getCodes(context: Context): String? {
            return getInstance(context).getString("userfirstname", null)
        }

        fun getUsername(context: Context): String? {
            return getInstance(context).getString("username", null)
        }
    }
}
