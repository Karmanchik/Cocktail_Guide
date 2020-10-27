package app.nocamelstyle.cocktailguide.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.nocamelstyle.cocktailguide.activities.CategoryActivity
import app.nocamelstyle.cocktailguide.activities.CocktailActivity
import app.nocamelstyle.cocktailguide.databinding.ItemCocktailBinding
import app.nocamelstyle.cocktailguide.databinding.ItemSkeletonBinding
import app.nocamelstyle.cocktailguide.models.Drink
import app.nocamelstyle.cocktailguide.utils.startActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrinksAdapter(
    val ctx: CategoryActivity,
    var drinks: List<Drink>,
    var isLoaded: Boolean
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var isLoadeds = mutableListOf<Boolean>()

    init {
        isLoadeds.clear()
        isLoadeds.addAll(drinks.map { false })
        if (drinks.isNotEmpty()) loadIcons()
    }

    private fun loadIcons() {
        drinks.indices.forEach {
            Glide.with(ctx)
                .load(drinks[it])
                .override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        isLoadeds[it] = true
                        if (isLoadeds.none { false })
                            showInfo()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        isLoadeds[it] = true
                        if (isLoadeds.none { false })
                            showInfo()
                        return true
                    }

                })
                .submit()
        }
    }

    fun showInfo() =
        ctx.reloadAdapter(drinks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (isLoaded)
            SkeletonHolder(ItemSkeletonBinding.inflate(layoutInflater, parent, false))
        else
            Holder(ItemCocktailBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) holder.bind(position)
    }

    fun setList(list: List<Drink>) {
        drinks = list
        isLoaded = false
        isLoadeds.clear()
        isLoadeds.addAll(drinks.map { false })
        notifyDataSetChanged()
        if (list.isNotEmpty())
            loadIcons()
    }

    override fun getItemCount() = drinks.size

    inner class SkeletonHolder(view: ItemSkeletonBinding): RecyclerView.ViewHolder(view.root)

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
                        putExtra("drink", Gson().toJson(this@apply))
                    }
                }
                //todo diff utils
            }
        }

    }

}