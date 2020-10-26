package app.nocamelstyle.cocktailguide.services

import app.nocamelstyle.cocktailguide.models.Drink
import retrofit2.Response

interface IApiService {

    fun loadRandomDrink(): Response<Drink>

//    fun searchByName(): Response<List<Drink>>
//
//    fun loadDrink(id: String): Response<Drink>

}