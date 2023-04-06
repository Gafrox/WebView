package star.cas.acting.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import star.cas.acting.R
import star.cas.acting.databinding.SpinFragmentBinding
import java.lang.Math.floor
import java.util.*

class SpinFragment : Fragment(), Animation.AnimationListener {
    private lateinit var binding: SpinFragmentBinding
    private var count = 0
    private var flag = false
    private var powerButton: ImageView? = null
    private val actionText = (activity as? AppCompatActivity)?.supportActionBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SpinFragmentBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.show()
        actionText?.title = "Star Casino "
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        powerButton = view.findViewById(R.id.powerButton)
        powerButton?.setOnTouchListener(PowerTouchListener())
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

    @SuppressLint("SetTextI18n")
    override fun onAnimationStart(p0: Animation?) {
        infoText?.text = "Spinning..."
        changeValue((activity as? AppCompatActivity)?.supportActionBar)
    }

    override fun onAnimationEnd(p0: Animation?) {
        infoText?.text = prizeText
        (activity as? AppCompatActivity)?.supportActionBar?.title = prizeText
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    private fun changeValue(actionBar: ActionBar?) {
        val handler = Handler()
        val runnable = object : Runnable {
            var count = 0
            override fun run() {
                count++
                actionBar?.title = "Prize is : ${prizes.random()}"
                if (count < 28) {
                    handler.postDelayed(this, 125)
                }
            }
        }
        handler.postDelayed(runnable, 0)
    }

    private inner class PowerTouchListener : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
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