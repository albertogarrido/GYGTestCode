package net.albertogarrido.gygreviews.util

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

fun View?.visible() {
    this?.let {
        it.visibility = View.VISIBLE
    }
}

fun View?.invisible() {
    this?.let {
        it.visibility = View.INVISIBLE
    }
}

fun View?.gone() {
    this?.let {
        it.visibility = View.GONE
    }
}

// source: https://gist.github.com/alexfacciorusso/43010274970aa882c1e7
fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit, failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>) = success(response)
        override fun onFailure(call: Call<T>?, t: Throwable) = failure(t)
    })
}

fun View?.fadeOut() {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.duration = 500

    fadeOut.setAnimationListener(object : AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            this@fadeOut!!.visibility = View.GONE
        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    })

    this!!.startAnimation(fadeOut)
}

fun Date.format(): String = SimpleDateFormat("MMMM dd, yyyy").format(this)


