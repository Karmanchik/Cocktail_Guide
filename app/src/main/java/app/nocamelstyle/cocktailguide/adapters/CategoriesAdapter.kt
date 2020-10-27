package app.nocamelstyle.cocktailguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.nocamelstyle.cocktailguide.databinding.ItemCategoryBinding
import app.nocamelstyle.cocktailguide.models.Category

class CategoriesAdapter(
    var categories: List<Category>
): RecyclerView.Adapter<CategoriesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(ItemCategoryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = categories.size

    inner class Holder(private val view: ItemCategoryBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(position: Int) {
            view.name.text = categories[position].strCategory
            view.root.setOnClickListener {
                //todo
                //load data
                //open new activity with data
            }
        }

    }

}