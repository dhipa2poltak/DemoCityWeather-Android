package com.dpfht.android.democityweather

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dpfht.android.democityweather.databinding.ActivityMainBinding
import com.dpfht.android.democityweather.framework.R as frameworkR
import com.dpfht.android.democityweather.navigation.R as navigationR
import com.dpfht.android.democityweather.view.about.AboutDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val appBarConfiguration = AppBarConfiguration(
      setOf(navigationR.id.listCityWeatherFragment)
    )

    val navHostFragment = supportFragmentManager.findFragmentById(frameworkR.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    navController.addOnDestinationChangedListener { _, destination, _ ->
      title = when (destination.id) {
        navigationR.id.listCityWeatherFragment -> "${getString(R.string.app_name)}${getString(R.string.running_mode)}"
        navigationR.id.detailsCityWeatherFragment -> getString(com.dpfht.android.democityweather.framework.R.string.framework_text_weather)
        else -> "${getString(R.string.app_name)}${getString(R.string.running_mode)}"
      }
    }

    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu?.let {
      MenuCompat.setGroupDividerEnabled(menu, true)
      menuInflater.inflate(R.menu.main_menu, menu)
    }

    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.itm_about -> {
        showAboutDialog()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showAboutDialog() {
    val dialog = AboutDialogFragment.newInstance()
    dialog.show(supportFragmentManager, "fragment_about")
  }
}
