package app.nocamelstyle.cocktailguide.services

import app.nocamelstyle.cocktailguide.api.API
import app.nocamelstyle.cocktailguide.models.Drink
import retrofit2.Response

class ApiService : IApiService {

    override fun loadRandomDrink() =
        API.client.getRandom().execute()

//    override fun searchByName(): Response<List<Drink>> {
//        return API.client.
//    }
//
//    override fun loadDrink(id: String): Response<Drink> {
//
//    }

}