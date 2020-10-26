package app.nocamelstyle.cocktailguide.api

import app.nocamelstyle.cocktailguide.models.AnswerCategories
import app.nocamelstyle.cocktailguide.models.AnswerIngredients
import app.nocamelstyle.cocktailguide.models.Drink
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.GET

interface API {

    @GET("random.php")
    fun getRandom():
            Call<Drink>

    @GET("lookup.php")
    fun getIngredients(@Field("iid") drinkId: String):
            Call<AnswerIngredients>

    @GET("search.php")
    fun searchDrinks(@Field("s") drinkName: String):
            Call<List<Drink>>

    @GET("filter.php")
    fun getDrinks(@Field("c") categotyName: String):
            Call<List<Drink>>

    @GET("list.php?c=list")
    fun getCategories():
            Call<AnswerCategories>

    companion object {
        val client by lazy { retrofit.create<API>() }
    }

}