package app.nocamelstyle.cocktailguide.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.adapters.CategoriesAdapter
import app.nocamelstyle.cocktailguide.databinding.FragmentCategoriesBinding
import app.nocamelstyle.cocktailguide.models.AnswerCategories
import app.nocamelstyle.cocktailguide.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response

class CategoriesFragment :
        Fragment(R.layout.fragment_categories),
        SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentCategoriesBinding? = null
    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.refreshLayout?.setOnRefreshListener(this)
        onRefresh()
    }

    override fun onRefresh() {
        binding?.refreshLayout?.isRefreshing = true
        GlobalScope.launch(Dispatchers.IO) {
            val answer = ApiService.loadCategories()
            onCategoriesLoaded(answer)
        }
    }

    private fun onCategoriesLoaded(answer: Response<AnswerCategories>) {
        binding?.refreshLayout?.isRefreshing = false
        GlobalScope.launch(Dispatchers.Main) {
            if (answer.body() != null) {
                if (categoriesAdapter == null) {
                    binding?.categoriesList?.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        categoriesAdapter = CategoriesAdapter(answer.body()!!.drinks)
                        adapter = categoriesAdapter
                    }

                } else {
                    categoriesAdapter?.categories = answer.body()!!.drinks
                    categoriesAdapter?.notifyDataSetChanged()
                    //todo diff utils
                }

            } else {
                //todo show error
            }
        }
    }

}