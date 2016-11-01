package org.ice1000.kitiku

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gc.materialdesign.widgets.Dialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.find
import utils.Audio
import utils.BaseActivity
import utils.kitikuList
import utils.unless

class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar_main)
		recycler_main.layoutManager = GridLayoutManager(this, dm.widthPixels / 400)
		recycler_main.adapter = KitikuAdapter()
		recycler_main.itemAnimator = DefaultItemAnimator()
	}

	inner class KitikuAdapter() : RecyclerView.Adapter<KitikuViewHolder>() {
		override fun getItemCount() = kitikuList.size
		override fun onBindViewHolder(holder: KitikuViewHolder?, position: Int) {
			holder?.init(position)
		}

		override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = KitikuViewHolder(
				LayoutInflater.from(this@MainActivity).inflate(0, parent)
		)
	}

	inner class KitikuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val image: ImageView

		init {
			image = view.find(R.id.image_recycler_main)
		}

		fun init(position: Int) {
			image.setImageResource(kitikuList[position].first)
			image.setOnClickListener { view ->
				unless(Audio.playSound(position)) {
					Dialog(this@MainActivity,
							str(R.string.oops),
							str(R.string.crash_message)).apply {
						buttonCancel.visibility = View.GONE
						buttonAccept.text = str(R.string.ok)
						setOnAcceptButtonClickListener { view ->
							dismiss()
//							finish()
						}
						show()
					}
				}
			}
		}
	}
}
