package app.nocamelstyle.cocktailguide.models

import io.realm.RealmObject

data class Drink(
    val idDrink: String? = null,
    val strDrink: String? = null,
    val strCategory: String? = null,
    val strDrinkThumb: String? = null,
    val strDescription: String? = null,
    val strGlass: String? = null
) {
    fun toRealVersion() = DrinkRealm(idDrink, strDrink, strCategory, strDrinkThumb, strDescription, strGlass)
}

open class DrinkRealm(
    var idDrink: String? = null,
    var strDrink: String? = null,
    var strCategory: String? = null,
    var strDrinkThumb: String? = null,
    var strDescription: String? = null,
    var strGlass: String? = null
) : RealmObject() {
    fun toBasicVersion() = Drink(idDrink, strDrink, strCategory, strDrinkThumb, strDescription, strGlass)
}