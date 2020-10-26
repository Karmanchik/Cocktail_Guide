package app.nocamelstyle.cocktailguide

import android.app.Application
import app.nocamelstyle.cocktailguide.utils.Setting

class App : Application() {

    companion object {
        lateinit var setting: Setting
    }

    override fun onCreate() {
        super.onCreate()
        setting = Setting(this)
    }

}