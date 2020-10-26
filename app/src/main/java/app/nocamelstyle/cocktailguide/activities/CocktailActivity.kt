package app.nocamelstyle.cocktailguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.databinding.ActivityCocktailBinding
import app.nocamelstyle.cocktailguide.models.Drink
import com.bumptech.glide.Glide
import com.google.gson.Gson

class CocktailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailBinding
    private var isSelected = false
    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        drink = Gson().fromJson(intent.getStringExtra("drink"), Drink::class.java)

        Glide.with(this)
                .load(drink.strDrinkThumb)
                .into(binding.imageView)

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