package app.nocamelstyle.cocktailguide.Presenter

import app.nocamelstyle.cocktailguide.Repository.models.Drink

interface SearchContract {

    interface View {

        fun showHistory(history: List<Drink>)

        fun openCocktail(drink: Drink?)

    }

    interface Presenter {

        fun onResume()

        fun searchDrink(name: String)

        fun loadRandom()

    }

}