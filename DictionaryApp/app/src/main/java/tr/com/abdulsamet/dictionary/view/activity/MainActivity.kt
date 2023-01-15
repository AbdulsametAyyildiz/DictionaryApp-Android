package tr.com.abdulsamet.dictionary.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import tr.com.abdulsamet.dictionary.R
import tr.com.abdulsamet.dictionary.base.BaseFragmentFactory

import javax.inject.Inject

/**
 * @author Abdülsamet Ayyıldız at 10.01.2023
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: BaseFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)

    }

    fun checkConnection(): Boolean {
        val connectivityManager =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}