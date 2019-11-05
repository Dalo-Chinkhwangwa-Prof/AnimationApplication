package com.example.animation101

import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private lateinit var imageArray: TypedArray

    private var index: Int = 0
    private var timerTask: TimerTask? = null
    private var timer = Timer()

    private val handler = Handler()

    private lateinit var myAnimation: MyAnimation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myAnimation = MyAnimation(this )
        setContentView(myAnimation)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim)
//
//        setContentView(myAnimation)
        setContentView(R.layout.activity_main)
        my_root_view.startAnimation(animation)
        imageArray = resources.obtainTypedArray(R.array.my_loader_images)
//      The views must be added twice
        main_switch.addView(ImageView(this))
        main_switch.addView(ImageView(this))

//        onAnimateNow()

        animated_button.setOnClickListener { buttonView ->

            buttonView.animation = animation
            animation.duration = 50
//            buttonView.animation.start()
            buttonView.startAnimation(animation)
        }


        val myFragment = MyAnimatedFragment()
        supportFragmentManager.beginTransaction()
            .addToBackStack(myFragment.tag)
            .setCustomAnimations(R.anim.slide_up_anim, android.R.anim.fade_out)
            .add(R.id.main_layout, myFragment)
            .commit()
    }

    private fun onAnimateNow() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                    Glide.with(this@MainActivity.baseContext).asBitmap().load(imageArray.getResourceId(index, 0)).listener(
                        object : RequestListener<Bitmap>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Bitmap?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.d("TAG_X", "loading $index")
                                handler.post {
                                    main_switch.setImageDrawable(BitmapDrawable(resources, resource))
                                }
                                index++
                                if (index == imageArray.length()-1)
                                    index = 0
                                return true
                            }

                        }
                    ).submit(500, 500).get()
                }
        }, 0, 1)

    }


}
