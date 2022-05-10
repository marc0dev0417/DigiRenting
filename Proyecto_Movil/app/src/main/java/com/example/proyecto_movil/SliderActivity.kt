package com.example.proyecto_movil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager

class SliderActivity : AppCompatActivity() {

    val TOTAL_SLIDER_FRAGMENTS: Int = 3

    lateinit var viewPager : ViewPager
    lateinit var dotLayout : LinearLayout

    lateinit var mDots : Array<TextView?>
    lateinit var sliderAdapter: SliderAdapter

    lateinit var buttonNext: Button
    lateinit var buttonPrev: Button
    var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        viewPager = findViewById(R.id.viewPager)
        dotLayout = findViewById(R.id.dotLayout)
        buttonNext = findViewById(R.id.buttonNext)
        buttonPrev = findViewById(R.id.buttonPrev)

        sliderAdapter = SliderAdapter()
        sliderAdapter.context = this
        viewPager.adapter = sliderAdapter

        buttonNext.setOnClickListener {

            if (currentPage == TOTAL_SLIDER_FRAGMENTS - 1) {

                startActivity(Intent(this, LoginActivity::class.java))

            } else {

                viewPager.currentItem = currentPage + 1

            }
        }

        buttonPrev.setOnClickListener {

            viewPager.currentItem = currentPage - 1

        }

        addDotsIndicator(0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                addDotsIndicator(position)
                currentPage = position

                when (position) {

                    0 -> {

                        buttonNext.isEnabled = true
                        buttonPrev.isEnabled = false
                        buttonPrev.visibility = (View.INVISIBLE)

                        buttonNext.text = "Next"
                        buttonPrev.text = ""

                    }

                    (TOTAL_SLIDER_FRAGMENTS - 1) -> {

                        buttonNext.isEnabled = true
                        buttonPrev.isEnabled = true
                        buttonPrev.visibility = (View.VISIBLE)

                        buttonNext.text = "Finish"
                        buttonPrev.text = "Previous"

                    }

                    else -> {

                        buttonNext.isEnabled = true
                        buttonPrev.isEnabled = true
                        buttonPrev.visibility = (View.VISIBLE)

                        buttonNext.text = "Next"
                        buttonPrev.text = "Previous"

                    }
                }
            }
        })
    }

    fun addDotsIndicator(position: Int) {

        mDots = Array(TOTAL_SLIDER_FRAGMENTS) { null }
        dotLayout.removeAllViews()

        for (i in mDots.indices) {

            mDots[i] = TextView(this)
            //mDots[i]?.text = Html.fromHtml("&#8226")
            mDots[i]?.text = HtmlCompat.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY)
            mDots[i]?.textSize = 35F
            //mDots[i]?.setTextColor(resources.getColor(R.color.white))
            mDots[i]?.setTextColor(resources.getColor(R.color.white, theme))

            dotLayout.addView(mDots[i])

        }

        if (mDots.isNotEmpty()) {
           // mDots[position]?.setTextColor(resources.getColor(R.color.purple_500))
            mDots[position]?.setTextColor(resources.getColor(R.color.purple_500, theme))
        }
    }
}