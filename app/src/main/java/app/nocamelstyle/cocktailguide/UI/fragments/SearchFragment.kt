package app.nocamelstyle.cocktailguide.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.nocamelstyle.cocktailguide.Presenter.SearchContract
import app.nocamelstyle.cocktailguide.Presenter.SearchPresenter
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.UI.activities.CocktailActivity
import app.nocamelstyle.cocktailguide.UI.adapters.HistoryVisitAdapter
import app.nocamelstyle.cocktailguide.databinding.FragmentSearchBinding
import app.nocamelstyle.cocktailguide.Repository.models.Drink
import app.nocamelstyle.cocktailguide.onRightDrawableClicked
import app.nocamelstyle.cocktailguide.startActivity
import app.nocamelstyle.cocktailguide.toast
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search), SearchContract.View {

    private var binding: FragmentSearchBinding? = null
    private val presenter by lazy { SearchPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            randomButton.setOnClickListener {
                refreshLayout.isRefreshing = true
                presenter.loadRandom()
            }

            refreshLayout.isEnabled = false

            searchButton.setOnClickListener {
                refreshLayout.isRefreshing = true
                presenter.searchDrink(filter.text.toString())
            }

            filter.doOnTextChanged { _, _, _, count ->
                if (count != 0) {
                    val cancel = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24)
                    cancel?.setBounds(0,0, cancel.intrinsicWidth, cancel.intrinsicHeight)
                    filter.setCompoundDrawables(null, null, cancel, null)
                } else {
                    filter.setCompoundDrawables(null, null, null, null)
                }
            }

            filter.onRightDrawableClicked { it.text.clear() }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun showHistory(history: List<Drink>) {
        binding?.historyList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HistoryVisitAdapter(requireContext(), history)
        }
    }

    override fun openCocktail(drink: Drink?) {
        GlobalScope.launch(Dispatchers.Main) {
            binding?.refreshLayout?.isRefreshing = false
            if (drink == null)
                toast(R.string.internet_error)
            else {
                startActivity<CocktailActivity> {
                    putExtra("drink", Gson().toJson(drink))
                }
            }
        }
    }

}