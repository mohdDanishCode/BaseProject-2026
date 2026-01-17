package com.omniful.app

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.compose.rememberNavController
import com.omniful.app.routes.AppNavigator
import com.omniful.app.routes.LocalAppNavigator
import com.omniful.app.routes.NavGraph
import com.omniful.data.manager.language.LanguageUtil
import com.omniful.data.manager.language.LanguageUtil.setOrientation
import com.omniful.data.manager.language.MyContextWrapper
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.OmnifulTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var isFirstOnCreate = true
    }



    override fun attachBaseContext(newBase: Context?) {
        Timber.d("attachBaseContext")
        val context: MyContextWrapper? = newBase?.let { MyContextWrapper.wrap(it) }
        super.attachBaseContext(context)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        Timber.d("applyOverrideConfiguration")
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Timber.d("mMessageReceiver ${intent.action}")
            if (intent.action == LanguageUtil.BROADCAST_LANGUAGE_CHANGED) {
                Timber.d("mMessageReceiver Recreate")
                recreate()
            }
        }
    }

    override fun onDestroy() {
        Timber.d("mMessageReceiver Destroy")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }



    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->  }
    private lateinit var appNavigator: AppNavigator


    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")

        if (isFirstOnCreate && savedInstanceState != null) {
            finish()
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        isFirstOnCreate = false

        window.setOrientation(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver,
            IntentFilter(LanguageUtil.BROADCAST_LANGUAGE_CHANGED),
        )

        enableEdgeToEdge()
        setContent {
            OmnifulTheme  {
                val colors = LocalOMFColors.current

                val navController = rememberNavController()
                appNavigator = AppNavigator(navController,this)

                CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
                    NavGraph(navController)
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

