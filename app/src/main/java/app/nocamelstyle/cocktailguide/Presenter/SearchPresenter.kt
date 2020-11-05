package app.nocamelstyle.cocktailguide.Presenter

import app.nocamelstyle.cocktailguide.Repository.models.DrinkRealm
import app.nocamelstyle.cocktailguide.Repository.services.ApiService
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(val view: SearchContract.View) : SearchContract.Presenter {

    override fun onResume() {
        GlobalScope.launch(Dispatchers.Main) {
            val drinks = Realm.getDefaultInstance()
                .where<DrinkRealm>()
                .findAll()
                .map { it.toBasicVersion() }
                .filter { it.idDrink != null }
                .reversed()

            view.showHistory(drinks)
        }
    }

    override fun searchDrink(name: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                view.openCocktail(
                    ApiService.searchDrinks(name).body()?.drinks?.firstOrNull()
                )
            } catch (e: Exception) {
                view.openCocktail(null)
            }
        }
    }

    override fun loadRandom() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                view.openCocktail(ApiService.loadRandomDrink().body()?.drinks?.firstOrNull())
            } catch (e: Exception) {
                view.openCocktail(null)
            }
        }
    }

}