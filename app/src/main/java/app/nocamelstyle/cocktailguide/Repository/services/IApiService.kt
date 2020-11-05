package app.nocamelstyle.cocktailguide.Repository.services

import app.nocamelstyle.cocktailguide.Repository.models.AnswerCategories
import app.nocamelstyle.cocktailguide.Repository.models.AnswerDrinks
import app.nocamelstyle.cocktailguide.Repository.models.AnswerIngredients
import retrofit2.Response

interface IApiService {

    fun loadRandomDrink(): Response<AnswerDrinks>

    fun loadIngredients(drinkId: String): Response<AnswerIngredients>

    fun searchDrinks(drinkName: String): Response<AnswerDrinks>

    fun loadDrinks(categotyName: String): Response<AnswerDrinks>

    fun loadCategories(): Response<AnswerCategories>

}