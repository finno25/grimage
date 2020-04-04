package com.aura.grimage.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aura.grimage.R
import com.aura.grimage.models.Hit
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*

const val EXTRA_SELECTED_HIT = "EXTRA_SELECTED_HIT"
class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val hit = intent.getSerializableExtra(EXTRA_SELECTED_HIT) as Hit
        Glide.with(this)
            .load(hit.largeImageURL)
            .into(ivImage)
    }
}