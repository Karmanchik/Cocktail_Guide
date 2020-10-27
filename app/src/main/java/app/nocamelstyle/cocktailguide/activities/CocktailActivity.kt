package app.nocamelstyle.cocktailguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.databinding.ActivityCocktailBinding
import app.nocamelstyle.cocktailguide.models.Drink
import app.nocamelstyle.cocktailguide.models.DrinkRealm
import com.bumptech.glide.Glide
import com.google.gson.Gson
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CocktailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailBinding
    private var isSelected = false
    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drink = Gson().fromJson(intent.getStringExtra("drink"), Drink::class.java)
        saveToDb()

        Glide.with(this)
            .load(drink.strDrinkThumb)
            .override(binding.imageView.width, binding.imageView.height)
            .into(binding.imageView)

        binding.apply {
            setSupportActionBar(toolbar)
            toolbarLayout.title = drink.strDrink
            fab.setOnClickListener {
                fab.setImageResource(
                    if (!isSelected) R.drawable.ic_baseline_star_rate_24
                    else R.drawable.ic_baseline_star_outline_24
                )
                isSelected = !isSelected
            }
        }
        //todo: save drink to realm
    }

    private fun saveToDb() {
        Realm.getDefaultInstance().executeTransaction {
            try {
                it.createObject<DrinkRealm>()
            } catch (e: Exception) {}

            val oldDrink =
                it.where<DrinkRealm>().equalTo("idDrink", drink.idDrink).findFirst()

            if (oldDrink == null)
                it.copyToRealm(drink.toRealVersion())
        }
    }

}