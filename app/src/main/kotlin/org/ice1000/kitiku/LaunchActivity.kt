package org.ice1000.kitiku

import android.graphics.PorterDuff
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_launch.*
import org.jetbrains.anko.startActivity
import utils.Audio
import utils.BaseActivity
import kotlin.concurrent.thread

class LaunchActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_launch)
		thread {
			Audio.init(this@LaunchActivity)
		}
		listOf(start_button_launch,
				settings_button_launch).forEach { view ->
			view.supportBackgroundTintMode = PorterDuff.Mode.ADD
			view.supportBackgroundTintList = colorStateList()
		}
		start_button_launch.setOnClickListener { view ->
			startActivity<MainActivity>()
		}
		settings_button_launch.setOnClickListener { view ->
			startActivity<SettingsActivity>()
		}
	}

	override fun onResume() {
		super.onResume()
	}
}
