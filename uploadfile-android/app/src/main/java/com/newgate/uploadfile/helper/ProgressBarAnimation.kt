package com.newgate.uploadfile.helper

import android.view.animation.Transformation
import android.widget.ProgressBar
import android.view.animation.Animation



/**
 * Created by apple on 2/22/18.
 */
class ProgressBarAnimation(private val progressBar: ProgressBar, private val from: Float, private val to: Float) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
    }

}