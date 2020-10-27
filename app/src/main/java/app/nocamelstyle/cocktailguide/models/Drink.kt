package app.nocamelstyle.cocktailguide.models

import io.realm.RealmObject

data class Drink(
    val idDrink: String? = null,
    val strDrink: String? = null,
    val strCategory: String? = null,
    val strDrinkThumb: String? = null
) {
    fun toRealVersion() = DrinkRealm(idDrink, strDrink, strCategory, strDrinkThumb)
}

open class DrinkRealm(
    val idDrink: String? = null,
    val strDrink: String? = null,
    val strCategory: String? = null,
    val strDrinkThumb: String? = null
) : RealmObject() {
    fun toBasicVersion() = Drink(idDrink, strDrink, strCategory, strDrinkThumb)
}