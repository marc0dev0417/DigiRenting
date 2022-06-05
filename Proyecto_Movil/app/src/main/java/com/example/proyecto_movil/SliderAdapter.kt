package com.example.proyecto_movil

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class SliderAdapter : PagerAdapter() {

    lateinit var context : Context
    lateinit var layoutInflater: LayoutInflater

    //arrays
    private val slideImages = arrayOf(R.drawable.ic_round_home_24,
                                R.drawable.ic_round_favorite_24,
                                R.drawable.ic_round_add_home_24)

    override fun getCount(): Int {

        return slideImages.size

    }

    override fun isViewFromObject(view: View, o : Any): Boolean {

        return view == o as ConstraintLayout

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var slideTitles : Array<String> = context.resources.getStringArray(R.array.titlesSlider)
        var slideDescriptions : Array<String> = context.resources.getStringArray(R.array.descriptionsSlider)

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

        container.removeView(`object` as ConstraintLayout)

    }

}