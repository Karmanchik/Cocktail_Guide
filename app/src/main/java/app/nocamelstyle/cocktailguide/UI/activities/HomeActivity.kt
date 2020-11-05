package app.nocamelstyle.cocktailguide.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.databinding.ActivityHomeBinding
import app.nocamelstyle.cocktailguide.UI.fragments.CategoriesFragment
import app.nocamelstyle.cocktailguide.UI.fragments.FavouritesFragment
import app.nocamelstyle.cocktailguide.UI.fragments.SearchFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigetionView.setOnNavigationItemSelectedListener {
            supportFragmentManager.commit {
                replace(
                    R.id.frame,
                    when (it.itemId) {
                        R.id.bottom_menu_search -> SearchFragment()
                        R.id.bottom_menu_categories -> CategoriesFragment()
                        else -> FavouritesFragment()
                    }
                )
            }
            true
        }

    }

}