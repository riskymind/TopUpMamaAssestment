package com.asterisk.topupmamaassestment.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.data.local.ForecastDao
import com.asterisk.topupmamaassestment.databinding.ActivityMainBinding
import com.asterisk.topupmamaassestment.ui.service.ForecastService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LifecycleObserver {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var localRemoteSource: ForecastDao
    private var viewBackgroundCheck = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Register lifecycle observer
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHost.navController

        setupActionBarWithNavController(navController)

        CoroutineScope(Dispatchers.IO).launch {
            val forecast = localRemoteSource.getFavForecast().first()
            val intent = Intent(this@MainActivity, ForecastService::class.java)
            intent.putExtra("forecast", forecast.toString())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}