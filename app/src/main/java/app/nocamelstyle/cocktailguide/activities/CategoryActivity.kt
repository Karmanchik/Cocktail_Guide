package app.nocamelstyle.cocktailguide.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.nocamelstyle.cocktailguide.adapters.DrinksAdapter
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

        binding.back.setOnClickListener { finish() }
        binding.refreshLayout.setOnRefreshListener(this)
        onRefresh()
    }

    private fun onDrinksLoaded(answer: Response<AnswerDrinks>) {
        binding.refreshLayout.isRefreshing = false
        GlobalScope.launch(Dispatchers.Main) {
            if (answer.body() != null) {
                if (drinksAdapter != null) {
                    drinksAdapter = DrinksAdapter(
                        this@CategoryActivity,
                        answer.body()?.drinks ?: listOf(),
                        isLoaded = false
                    )
                    binding.drinksList.adapter = drinksAdapter
                    //drinksAdapter?.setList(answer.body()?.drinks ?: listOf())
                } else {
                    binding.drinksList.apply {
                        layoutManager = LinearLayoutManager(this@CategoryActivity)
                        drinksAdapter = DrinksAdapter(
                            this@CategoryActivity,
                            answer.body()?.drinks ?: listOf(),
                            isLoaded = false
                        )
                        adapter = drinksAdapter
                    }
                }

            } else {
                //todo: internet error
            }
        }
    }

    fun reloadAdapter(drinks: List<Drink>) {
        //drinksAdapter = DrinksAdapter(this, drinks, true)
    }

    override fun onRefresh() {
        binding.refreshLayout.isRefreshing = true
        GlobalScope.launch(Dispatchers.IO) {
            onDrinksLoaded(ApiService.loadDrinks(categoryName))
        }
    }

}