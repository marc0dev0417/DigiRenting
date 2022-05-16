package com.example.proyecto_movil.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.proyecto_movil.R
import com.example.proyecto_movil.TravelLocation
import com.example.proyecto_movil.model.Adapter.TravelLocationsAdapter
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var likedImageView: ImageView

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


/*
        var liked = false

        likedImageView = view.findViewById(R.id.ivLiked_HomeFragment)

        likedImageView.setOnClickListener {

            if (!liked) {

                liked = true
                likedImageView.setImageResource(R.drawable.ic_baseline_favorite_24)

            } else if (liked) {

                liked = false
                likedImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            }

        }

        var cardImage : KenBurnsView = view.findViewById(R.id.kbvLocation)
        cardImage.setOnClickListener {

            startActivity(Intent(view.context, CardDetailActivity::class.java))

        }
        */

        var locationsViewPager : ViewPager2 = view.findViewById(R.id.locationsViewPager)

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

        locationsViewPager.adapter = TravelLocationsAdapter(context ,travelLocations)

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