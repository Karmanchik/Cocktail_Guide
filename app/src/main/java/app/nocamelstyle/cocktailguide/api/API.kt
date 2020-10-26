package app.nocamelstyle.cocktailguide.api

import retrofit2.create

interface API {



    companion object {
        val client by lazy { retrofit.create<API>() }
    }

}