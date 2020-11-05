package app.nocamelstyle.cocktailguide.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.UI.adapters.CategoriesAdapter
import app.nocamelstyle.cocktailguide.databinding.FragmentCategoriesBinding
import app.nocamelstyle.cocktailguide.Repository.models.AnswerCategories
import app.nocamelstyle.cocktailguide.Repository.services.ApiService
import app.nocamelstyle.cocktailguide.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
            try {
                onCategoriesLoaded(ApiService.loadCategories())
            } catch (e: Exception) {
                onCategoriesLoaded(null)
            }
        }
    }

    private fun onCategoriesLoaded(answer: Response<AnswerCategories>?) {
        binding?.refreshLayout?.isRefreshing = false
        GlobalScope.launch(Dispatchers.Main) {
            if (answer == null)
                toast(R.string.internet_error)
            else if (answer.body() != null) {
                if (categoriesAdapter == null) {
                    binding?.categoriesList?.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        categoriesAdapter = CategoriesAdapter(requireContext(), answer.body()!!.drinks)
                        adapter = categoriesAdapter
                    }

                } else {
                    categoriesAdapter?.categories = answer.body()!!.drinks
                    categoriesAdapter?.notifyDataSetChanged()
                }

            } else
                toast(R.string.internet_error)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}