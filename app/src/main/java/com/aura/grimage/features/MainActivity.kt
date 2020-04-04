package com.aura.grimage.features

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aura.grimage.R
import com.aura.grimage.models.Hit
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gridLayoutManager = GridLayoutManager(this, 3)
        rvImages.setLayoutManager(gridLayoutManager)
        val gridImagesAdapter = GridImagesAdapter()
        gridImagesAdapter.listener = object : GridImagesAdapter.Listener {
            override fun onItemClicked(hit: Hit) {
                val intent = Intent(this@MainActivity, ImageActivity::class.java)
                intent.putExtra(EXTRA_SELECTED_HIT, hit)
                startActivity(intent)
            }
        }
        rvImages.setAdapter(gridImagesAdapter)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.event.observe(this, Observer {
            if (it.loading) {

            }
            it.hits?.let {
                gridImagesAdapter.hits.addAll(it)
                gridImagesAdapter.notifyDataSetChanged()
            }
        })
        mainViewModel.getImages()

        rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = gridLayoutManager.itemCount
                val lastVisible = gridLayoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 6 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    mainViewModel.getImages()
                }
            }
        })
    }
}
