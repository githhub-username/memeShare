package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    private lateinit var memeImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var shareButton: Button
    private lateinit var nextButton: Button

    private var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ImageView and ProgressBar
        memeImage = findViewById(R.id.memeImageView)
        progressBar = findViewById(R.id.progressBar)
        shareButton = findViewById(R.id.shareButton)
        nextButton = findViewById(R.id.nextButton)

        fun loadMeme() {
        progressBar.visibility=View.VISIBLE

        val queue= Volley.newRequestQueue(this)

        val url = "https://vast-puce-mite-fez.cyclic.app/animeme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val currentImageUrl=response.getString("url")

                memeImage=findViewById(R.id.memeImageView)

                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(memeImage)
            },
            {
               Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
           }
        )
        queue.add(jsonObjectRequest)

    }

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

            intent.putExtra(Intent.EXTRA_TEXT, "Bhai yeh meme dekh $currentImageUrl")

            val chooser = Intent.createChooser(intent, "")
            startActivity(chooser)
        }


    nextButton.setOnClickListener {
            loadMeme()

    }
}}