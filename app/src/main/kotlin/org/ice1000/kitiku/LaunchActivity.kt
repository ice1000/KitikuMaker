package org.ice1000.kitiku

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_launch.*
import org.jetbrains.anko.startActivity
import utils.BaseActivity

class LaunchActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_launch)
		start_button_launch.setOnClickListener { view ->
			startActivity<MainActivity>()
		}
	}
}
