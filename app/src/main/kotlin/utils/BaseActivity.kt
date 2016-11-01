package utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log

/**
 * Created by ice1000 on 2016/6/4.
 *
 * @author ice1000
 */
open class BaseActivity : AppCompatActivity() {

	/**
	 * if this value is null,
	 * it means I have 2 load data from Sp.
	 */
	val connection: NetworkInfo?
		get() = (getSystemService(Context.CONNECTIVITY_SERVICE)
				as ConnectivityManager).activeNetworkInfo

	val dm = DisplayMetrics()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		windowManager.defaultDisplay.getMetrics(dm)
	}

	protected fun openWeb(url: String) {
		startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
	}

	protected fun checkNetwork(): Boolean {
		Log.v("not important", "connection? = ${connection ?: "no network found!"}")
		return connection != null && connection!!.isConnected
	}

	/**
	 * @see save
	 */
	fun String.save(value: Any) = save(this, value)

	fun String.readString(default: String = "") = readString(this, default)
	fun String.readInt(default: Int = 0) = readInt(this, default)
	fun String.readBoolean(default: Boolean = false) = readBoolean(this, default)

	fun str(id: Int) = resources.getString(id)
}
