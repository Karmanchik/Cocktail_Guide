package app.nocamelstyle.cocktailguide.UI.activities

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.ShortcutManagerCompat.getMaxShortcutCountPerActivity
import androidx.fragment.app.commit
import app.nocamelstyle.cocktailguide.R
import app.nocamelstyle.cocktailguide.UI.fragments.CategoriesFragment
import app.nocamelstyle.cocktailguide.UI.fragments.FavouritesFragment
import app.nocamelstyle.cocktailguide.UI.fragments.SearchFragment
import app.nocamelstyle.cocktailguide.databinding.ActivityHomeBinding
import app.nocamelstyle.cocktailguide.toast
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toast("shortcuts ${intent.getStringExtra("message")}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setDynamicShortcut()
        }

        binding.navigetionView.setOnNavigationItemSelectedListener {
            supportFragmentManager.commit {
                replace(
                    R.id.frame,
                    when (it.itemId) {
                        R.id.bottom_menu_search -> SearchFragment()
                        R.id.bottom_menu_categories -> CategoriesFragment()
                        else -> FavouritesFragment()
                    }
                )
            }
            true
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDynamicShortcut() {

        Log.e("maxShortcuts", getMaxShortcutCountPerActivity(this).toString())

        val shortcutManager = getSystemService(ShortcutManager::class.java)

        val webShortcut = ShortcutInfo.Builder(this, "shortcut_dynamic")
            .setShortLabel("Dynamic shortcut")
            .setLongLabel("Open dynamic shortcut")
            .setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_arrow_back_24))
            .setIntent(Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, HomeActivity::class.java).apply {
                putExtra("message", "worked")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            .build()


        shortcutManager.dynamicShortcuts = Collections.singletonList(webShortcut)

        if (shortcutManager.isRequestPinShortcutSupported) {
            val pinShortcutInfo = ShortcutInfo.Builder(this, "pinned")
                .setShortLabel("short pinned")
                .setLongLabel("long pinned")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_arrow_back_24))
                .setIntent(
                    Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, HomeActivity::class.java).apply {
                        putExtra("message", "worked")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
                .build()

//            // Create the PendingIntent object only if your app needs to be notified
//            // that the user allowed the shortcut to be pinned. Note that, if the
//            // pinning operation fails, your app isn't notified. We assume here that the
//            // app has implemented a method called createShortcutResultIntent() that
//            // returns a broadcast intent.
//            val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)
//
//            // Configure the intent so that your app's broadcast receiver gets
//            // the callback successfully.For details, see PendingIntent.getBroadcast().
//            val successCallback = PendingIntent.getBroadcast(
//                context, /* request code */ 0,
//                pinnedShortcutCallbackIntent, /* flags */ 0
//            )

            shortcutManager.requestPinShortcut(
                pinShortcutInfo,
                null
            )
        }
    }

}