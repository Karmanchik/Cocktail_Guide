package app.nocamelstyle.cocktailguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.activities.CocktailActivity
import app.nocamelstyle.cocktailguide.adapters.DrinksAdapter
import app.nocamelstyle.cocktailguide.adapters.HistoryVisitAdapter
import app.nocamelstyle.cocktailguide.databinding.FragmentSearchBinding
import app.nocamelstyle.cocktailguide.models.AnswerDrinks
import app.nocamelstyle.cocktailguide.models.DrinkRealm
import app.nocamelstyle.cocktailguide.services.ApiService
import app.nocamelstyle.cocktailguide.utils.startActivity
import com.google.gson.Gson
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            randomButton.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    //todo: load animation
                    val answer = ApiService.loadRandomDrink()

                    if (answer.body() != null) {
                        startActivity<CocktailActivity> {
                            putExtra("drink", Gson().toJson(answer.body()!!.drinks.first()))
                        }
                    } else {
                        //todo: internet error
                    }
                }
            }

            searchButton.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val answer: Response<AnswerDrinks> = ApiService.searchDrinks(filter.text.toString())
                        if (answer.body() == null) {
                            //todo: internet error
                        } else if (answer.body()!!.drinks.isNotEmpty()) {
                            startActivity<CocktailActivity> {
                                putExtra("drink", Gson().toJson(answer.body()!!.drinks.first()))
                            }
                        }
                    }
            }

            GlobalScope.launch(Dispatchers.Main) {
                val drinks =
                    Realm.getDefaultInstance().where<DrinkRealm>().findAll().map { it.toBasicVersion() }
                historyList.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = HistoryVisitAdapter(requireContext(), drinks)
                }
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}