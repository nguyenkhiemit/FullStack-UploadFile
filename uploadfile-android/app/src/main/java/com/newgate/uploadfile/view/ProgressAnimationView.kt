package com.newgate.uploadfile.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import com.newgate.uploadfile.helper.ProgressBarAnimation

/**
 * Created by apple on 2/22/18.
 */
class ProgressAnimationView: ProgressBar {

    var toPercent: Float = 0.0f

    constructor(context: Context) : super(context) {
        max = 100
    }

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)

    fun loadData(from: Float, to: Float) {
        this.toPercent = to
        val anim = ProgressBarAnimation(this, from, to)
        anim.duration = 300
        this.startAnimation(anim)
    }

    fun loadFullData() {
        if(this.progress < 100) {
            val anim = ProgressBarAnimation(this, toPercent, 100.0f)
            anim.duration = 700
            this.startAnimation(anim)
            this.progress = 100
        }
    }

}