package utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

/**
 * Created by ice1000 on 2016/6/4.
 *
 * @author ice1000
 */
open class BaseActivity : AppCompatActivity() {

	protected val URL = "URL"

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

	protected val DEFAULT_VALUE = "DEFAULT_VALUE"

	/**
	 * this will cache the data into SharedPreference
	 * next time when the network is invalid, it will return the data
	 * stored in the SharedPreference.
	 *
	 * this method extended String.
	 */
	fun String.webResource(submit: (String) -> Unit, default: String = DEFAULT_VALUE) {
		var ret = ""
		async() {
			ret = readString(default)
			uiThread { submit(ret) }
//        Log.i("important", "ret = $ret")
		}
		Log.i(this@BaseActivity.toString(), this@webResource)
		if (ret != DEFAULT_VALUE && !checkNetwork()) {
			async() {
				Log.i("important", "linking to SharedPreference")
				uiThread { submit(ret) }
			}
		} else {
			async() {
				Log.i("important", "linking to web")
				ret = java.net.URL(this@webResource).readText(Charsets.UTF_8)
				uiThread { submit(ret) }
				save(ret)
			}
		}
	}

	/**
	 * insert a value in2 SharedPreference
	 * any types of value is accepted.
	 *
	 * Will be smart casted.
	 */
	fun String.save(value: Any) {
		val editor = openPreference().edit()
		if (value is Int) editor.putInt(this, value)
		else if (value is Float) editor.putFloat(this, value)
		else if (value is Long) editor.putLong(this, value)
		else if (value is Boolean) editor.putBoolean(this, value)
		else if (value is String) editor.putString(this, value)
		else throw Exception("not supported type")
		editor.apply()
	}

	fun String.readString(default: String = "") = openPreference().getString(this, default) ?: ""
	fun String.readInt(default: Int = 0) = openPreference().getInt(this, default)
	fun String.readBoolean(default: Boolean = false) = openPreference().getBoolean(this, default)

	fun str(id: Int) = resources.getString(id)

	/**
	 * @return a SharedPreference
	 */
	private fun openPreference(): SharedPreferences =
			getSharedPreferences("MainPreference", MODE_ENABLE_WRITE_AHEAD_LOGGING)
}
