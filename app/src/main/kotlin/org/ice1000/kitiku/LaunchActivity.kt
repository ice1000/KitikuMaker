package org.ice1000.kitiku

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_launch.*
import org.jetbrains.anko.startActivity

class LaunchActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_launch)
		start_button_launch.setOnClickListener { view ->
			startActivity<MainActivity>()
		}
	}
}
