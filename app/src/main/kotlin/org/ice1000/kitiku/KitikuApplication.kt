package org.ice1000.kitiku

import android.app.Application
import com.nightfarmer.themer.Themer
import utils.THEME_KEY
import utils.readInt
import utils.save

/**
 * Created by ice1000 on 2016/11/1.
 *
 * @author ice1000
 */
class KitikuApplication() : Application() {
	override fun onCreate() {
		super.onCreate()
		if (readInt(THEME_KEY) == 0) {
			save(THEME_KEY, R.style.blue)
		}
		Themer.init(this, readInt(THEME_KEY, R.style.blue))
	}
}
