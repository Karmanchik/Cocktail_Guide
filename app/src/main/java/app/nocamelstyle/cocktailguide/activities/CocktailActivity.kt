package app.nocamelstyle.cocktailguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.databinding.ActivityCocktailBinding

class CocktailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailBinding
    private var isSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            setSupportActionBar(toolbar)
            toolbarLayout.title = title
            fab.setOnClickListener {
                fab.setImageResource(
                    if (!isSelected) R.drawable.ic_baseline_star_rate_24
                    else R.drawable.ic_baseline_star_outline_24
                )
                isSelected = !isSelected
            }
        }
    }
}