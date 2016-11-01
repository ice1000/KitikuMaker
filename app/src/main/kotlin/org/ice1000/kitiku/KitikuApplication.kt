package org.ice1000.kitiku

import android.app.Application
import com.nightfarmer.themer.Themer
import utils.Audio
import kotlin.concurrent.thread

/**
 * Created by ice1000 on 2016/11/1.
 *
 * @author ice1000
 */
class KitikuApplication() : Application() {
	override fun onCreate() {
		super.onCreate()
		thread { Audio.init(this@KitikuApplication) }
		Themer.init(this)
	}
}
