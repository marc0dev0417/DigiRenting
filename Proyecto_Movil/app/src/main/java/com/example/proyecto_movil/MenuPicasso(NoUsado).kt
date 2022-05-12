package com.example.proyecto_movil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class `MenuPicasso(NoUsado)` : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_picasso_no_usado)

        var menu : me.ibrahimsn.lib.SmoothBottomBar = findViewById(R.id.bottomBar)

        var locationsViewPager : ViewPager2 = findViewById(R.id.locationsViewPager)

        var travelLocationEiffelTower = TravelLocation()
        travelLocationEiffelTower.imageUrl = "https://media.revistaad.es/photos/623afa27430e667b20addca1/master/w_1600%2Cc_limit/142198198"
        travelLocationEiffelTower.title = "France"
        travelLocationEiffelTower.location = "Eiffel Tower"
        travelLocationEiffelTower.starRating = 4.8f

        var travelLocationMountainView = TravelLocation()
        travelLocationMountainView.imageUrl = "https://www.tourhero.com/en/magazine/wp-content/uploads/2020/09/hikes-in-indonesia-e1615437029289.jpg"
        travelLocationMountainView.title = "Indonesia"
        travelLocationMountainView.location = "Mountain View"
        travelLocationMountainView.starRating = 4.5f

        var travelLocationTajMahal = TravelLocation()
        travelLocationTajMahal.imageUrl = "https://1.bp.blogspot.com/-UbRzd9au3c0/X8laTtqta3I/AAAAAAAAKxU/xLlM9LGEb_4-oGK__ZhGGudxXjDYdYhaQCLcBGAsYHQ/s800/taj%2Bmahal.jpg"
        travelLocationTajMahal.title = "India"
        travelLocationTajMahal.location = "Taj Mahal"
        travelLocationTajMahal.starRating = 4.3f

        var travelLocationLakeView = TravelLocation()
        travelLocationLakeView.imageUrl = "https://cdn.thecrazytourist.com/wp-content/uploads/2018/09/ccimage-shutterstock_601048877.jpg"
        travelLocationLakeView.title = "Canada"
        travelLocationLakeView.location = "Lake View"
        travelLocationLakeView.starRating = 4.2f

        var travelLocations : List<TravelLocation> = arrayListOf(travelLocationEiffelTower, travelLocationMountainView, travelLocationTajMahal, travelLocationLakeView)

        locationsViewPager.adapter = TravelLocationsAdapter(travelLocations)
        
        locationsViewPager.clipToPadding = false
        locationsViewPager.clipChildren = false
        locationsViewPager.offscreenPageLimit = 3
        locationsViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        
        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            run {

                var r: Float = 1 - abs(position)
                page.scaleY = (0.95f + r * 0.05f)

            }
        }

        locationsViewPager.setPageTransformer(compositePageTransformer)

    }

}