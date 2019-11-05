package com.example.animation101

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.view.View
import androidx.core.content.ContextCompat

class MyAnimation(context: Context): View(context){

    private val SPEED = 5.0f
    private val STAR_SPEED = 1.25f

    private val lrgStar = BitmapFactory.decodeResource(
        resources,
        R.drawable.star_alpha)
    private val enemyStar = Bitmap.createScaledBitmap(lrgStar, 25, 25, false)

    private val bugDroid = BitmapFactory.decodeResource(resources, R.drawable.bug_droid)

    private val smallerDroid = Bitmap.createScaledBitmap(bugDroid, 50, 50, false)

    private var xPosition = 0.0f
    private var yPosition = 0.0f

    private var xIncrement = SPEED
    private var yIncrement = SPEED


    private var starPositionX = 1.0f
    private var starOneSpeed = STAR_SPEED
    private var starLevelOne = 50f

    val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)

        if(xPosition < 0 || xPosition > (width-smallerDroid.width)){
            xIncrement *= (-1)
        }
        if(yPosition < 0 || yPosition > (height-smallerDroid.height)){
            yIncrement *= (-1)
        }

        xPosition += xIncrement
        yPosition += yIncrement

        canvas.drawBitmap(smallerDroid, xPosition, yPosition, paint)

        //Draw star One
        if(starPositionX < 0|| starPositionX > (width - enemyStar.width))
            starOneSpeed *= (-1)
        starPositionX += starOneSpeed

        canvas.drawBitmap(enemyStar, starPositionX, starLevelOne, paint)

        invalidate()
    }
}