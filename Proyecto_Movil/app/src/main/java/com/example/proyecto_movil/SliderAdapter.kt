package com.example.proyecto_movil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class SliderAdapter : PagerAdapter() {

    lateinit var context : Context
    lateinit var layoutInflater: LayoutInflater

    //arrays
    val slideImages = arrayOf(R.drawable.google_logo,
                                R.drawable.fb_logo,
                                R.drawable.twitter_logo)

    val slideTitles = arrayOf("Title 1",
                                "Title 2",
                                "Title 3")

    val slideDescriptions = arrayOf("Description 1",
                                    "Descrpition 2",
                                    "Description 3")

    override fun getCount(): Int {

        return slideTitles.size

    }

    override fun isViewFromObject(view: View, o : Any): Boolean {

        return view == o as RelativeLayout

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view : View = layoutInflater.inflate(R.layout.slide_layout, container, false)

        var slideImageView : ImageView = view.findViewById(R.id.ivLogo_slider) as ImageView
        var slideHeading : TextView = view.findViewById(R.id.tvTitle_slider) as TextView
        var slideDescription : TextView = view.findViewById(R.id.tvLoremIpsum_slider) as TextView

        slideImageView.setImageResource(slideImages[position])
        slideHeading.text = slideTitles[position]
        slideDescription.text = slideDescriptions[position]

        container.addView(view)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as RelativeLayout)

    }



}