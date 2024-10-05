package com.example.camarax

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.camarax.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Listener para seleccionar imagen
        viewBinding.selectImageButton.setOnClickListener { selectImageFromGallery() }

        // Listener para seleccionar video
        viewBinding.selectVideoButton.setOnClickListener { selectVideoFromGallery() }
    }

    // Lanzador para seleccionar imagen
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            viewBinding.selectedImageView.setImageURI(imageUri)
            viewBinding.videoView.visibility = View.GONE
            viewBinding.selectedImageView.visibility = View.VISIBLE
        }
    }

    // Lanzador para seleccionar video
    private val videoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            viewBinding.videoView.setVideoURI(videoUri)
            viewBinding.videoView.start()
            viewBinding.selectedImageView.visibility = View.GONE
            viewBinding.videoView.visibility = View.VISIBLE
        }
    }

    // Método para seleccionar imagen
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    // Método para seleccionar video
    private fun selectVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        videoPickerLauncher.launch(intent)
    }
}
