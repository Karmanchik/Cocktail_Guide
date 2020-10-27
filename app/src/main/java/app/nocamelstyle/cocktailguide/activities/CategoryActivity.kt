package app.nocamelstyle.cocktailguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.nocamelstyle.cocktailguide.adapters.DrinksAdapter
import app.nocamelstyle.cocktailguide.api.API
import app.nocamelstyle.cocktailguide.databinding.ActivityCategoryBinding
import app.nocamelstyle.cocktailguide.models.AnswerDrinks
import app.nocamelstyle.cocktailguide.models.Drink
import app.nocamelstyle.cocktailguide.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoryActivity :
    AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityCategoryBinding
    private var drinksAdapter: DrinksAdapter? = null
    private lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName = intent.getStringExtra("name") ?: return finish()
        binding.titleView.text = categoryName

        binding.refreshLayout.setOnRefreshListener(this)
        onRefresh()
    }

    private fun onDrinksLoaded(answer: Response<AnswerDrinks>) {
        binding.refreshLayout.isRefreshing = false
        GlobalScope.launch(Dispatchers.Main) {
            if (answer.body() != null) {
                if (drinksAdapter != null) {
                    drinksAdapter?.drinks = answer.body()?.drinks ?: listOf()
                    drinksAdapter?.notifyDataSetChanged()
                } else {
                    binding.drinksList.apply {
                        layoutManager = LinearLayoutManager(this@CategoryActivity)
                        drinksAdapter = DrinksAdapter(
                            this@CategoryActivity,
                            answer.body()?.drinks ?: listOf()
                        )
                        adapter = drinksAdapter
                    }
                }

            } else {
                //todo: internet error
            }
        }
    }

    override fun onRefresh() {
        binding.refreshLayout.isRefreshing = true
        GlobalScope.launch(Dispatchers.IO) {
            onDrinksLoaded(ApiService.loadDrinks(categoryName))
        }
    }

}