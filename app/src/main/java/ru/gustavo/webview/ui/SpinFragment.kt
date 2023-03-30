package ru.gustavo.webview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.gustavo.webview.R
import ru.gustavo.webview.databinding.SpinFragmentBinding
import kotlin.math.floor

class SpinFragment : Fragment(), Animation.AnimationListener {
    private lateinit var binding: SpinFragmentBinding
    private var count = 0
    private var flag = false
    private var powerButton: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SpinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        powerButton = view.findViewById(R.id.powerButton)
        powerButton!!.setOnTouchListener(PowerTouchListener())
        intSpinner()
    }

    private val prizes = intArrayOf(200, 1000, 600, 1000, 500, 400, 200, 700, 3000, 400, 1000, 1200)
    private var mSpinDuration: Long = 0
    private var mSpinRevolution = 0f
    private var pointerImageView: ImageView? = null
    private var infoText: TextView? = null
    private var prizeText = "N/A"

    private fun intSpinner() {
        pointerImageView = view?.findViewById(R.id.imageWheel)
        infoText = view?.findViewById(R.id.infoText)
    }

    fun startSpinner() {
        mSpinRevolution = 3600f
        mSpinDuration = 5000

        if (count >= 30) {
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60) {
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()
        }

        val end = floor(Math.random() * 3600).toInt()
        val numOfPrizes = prizes.size
        val degreesPerPrize = 360 / numOfPrizes
        val shift = 0
        val prizeIndex = (shift + end) % numOfPrizes

        prizeText = "Prize is : ${prizes[prizeIndex]}"

        val rotateAnim = RotateAnimation(
            0f, mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        pointerImageView!!.startAnimation(rotateAnim)

    }

    override fun onAnimationStart(p0: Animation?) {
        infoText!!.text = "Spinning..."
    }

    override fun onAnimationEnd(p0: Animation?) {
        infoText!!.text = prizeText
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    private inner class PowerTouchListener : View.OnTouchListener {
        override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

            when (motionEvent!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    flag = true
                    count = 0
                    Thread {
                        while (flag) {
                            count++
                            if (count == 100) {
                                try {
                                    Thread.sleep(100)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                count = 0
                            }
                            try {
                                Thread.sleep(10)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    }.start()
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    flag = false
                    startSpinner()
                    return false
                }
            }
            return false
        }
    }
}