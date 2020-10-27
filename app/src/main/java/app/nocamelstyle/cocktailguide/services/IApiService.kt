package app.nocamelstyle.cocktailguide.services

import app.nocamelstyle.cocktailguide.models.AnswerCategories
import app.nocamelstyle.cocktailguide.models.AnswerIngredients
import app.nocamelstyle.cocktailguide.models.Drink
import retrofit2.Call
import retrofit2.Response

interface IApiService {

    fun loadRandomDrink(): Response<Drink>

    fun loadIngredients(drinkId: String): Response<AnswerIngredients>

    fun searchDrinks(drinkName: String): Response<List<Drink>>

    fun loadDrinks(categotyName: String): Response<List<Drink>>

    fun loadCategories(): Response<AnswerCategories>

}