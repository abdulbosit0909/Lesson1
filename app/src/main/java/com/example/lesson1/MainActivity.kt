package com.example.lesson1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val rotatedBitmap = ShairPreferenses.getImage(this)?.let { dialog(this).handleImageRotation(it) }
        binding.myImageView.setImageBitmap(rotatedBitmap)

        binding.myCardView2.setOnClickListener {
            val customDialog = dialog(this)
            customDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == dialog.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val rotatedBitmap = dialog(this).handleImageRotation(dialog.filePhoto.absolutePath)
            binding.myImageView.setImageBitmap(rotatedBitmap)

            binding.cordend?.setOnClickListener {
                ShairPreferenses.saveImage(this, dialog.filePhoto.absolutePath)
            }
        }
    }
}
