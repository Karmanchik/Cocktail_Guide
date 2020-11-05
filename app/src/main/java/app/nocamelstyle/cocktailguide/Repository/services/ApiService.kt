package app.nocamelstyle.cocktailguide.Repository.services

import app.nocamelstyle.cocktailguide.Repository.api.API

object ApiService : IApiService {

    override fun loadRandomDrink() =
            API.client.getRandom().execute()

    override fun loadIngredients(drinkId: String) =
            API.client.getIngredients(drinkId).execute()

    override fun searchDrinks(drinkName: String) =
            API.client.searchDrinks(drinkName).execute()

    override fun loadDrinks(categotyName: String) =
            API.client.getDrinks(categotyName).execute()

    override fun loadCategories() =
            API.client.getCategories().execute()

}