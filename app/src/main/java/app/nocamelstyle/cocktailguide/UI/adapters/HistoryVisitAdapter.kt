package app.nocamelstyle.cocktailguide.UI.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.nocamelstyle.cocktailguide.UI.activities.CocktailActivity
import app.nocamelstyle.cocktailguide.databinding.ItemCocktailBinding
import app.nocamelstyle.cocktailguide.Repository.models.Drink
import app.nocamelstyle.cocktailguide.startActivity
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi

class HistoryVisitAdapter(
    val ctx: Context,
    var drinks: List<Drink>
): RecyclerView.Adapter<HistoryVisitAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemCocktailBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = drinks.size

    inner class Holder(private val view: ItemCocktailBinding): RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            drinks[position].apply {

                Glide.with(ctx)
                    .load(strDrinkThumb)
                    .override(100, 100)
                    .dontAnimate()
                    .into(view.drinkImg)

                view.drinkName.text = strDrink
                view.drinkType.text = "id: $idDrink"

                view.root.setOnClickListener {
                    ctx.startActivity<CocktailActivity> {
                        putExtra("drink", Moshi.Builder().build().adapter(Drink::class.java).toJson(this@apply))
                    }
                }
            }
        }

    }

}