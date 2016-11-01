package org.ice1000.kitiku

import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.gc.materialdesign.widgets.Dialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import utils.Audio
import utils.BaseActivity
import utils.kitikuList
import utils.unless
import kotlin.concurrent.thread

@Suppress("DEPRECATION")
class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar_main)
		recycler_main.layoutManager = GridLayoutManager(this, dm.widthPixels / 256)
		recycler_main.adapter = KitikuAdapter()
		recycler_main.itemAnimator = DefaultItemAnimator()
	}

	inner class KitikuAdapter() : RecyclerView.Adapter<KitikuViewHolder>() {
		override fun getItemCount() = kitikuList.size
		override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
				KitikuViewHolder(LayoutInflater.from(this@MainActivity)
						.inflate(R.layout.kitiku_item, null))

		override fun onBindViewHolder(holder: KitikuViewHolder?, position: Int) {
			holder?.init(position)
		}
	}

	inner class KitikuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val image: AppCompatImageView

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
						buttonCancel?.visibility = View.GONE
						buttonAccept?.text = str(R.string.ok)
						setOnAcceptButtonClickListener { view ->
							dismiss()
//							finish()
						}
						show()
					}
				}
			}
			image.setOnTouchListener { view, event ->
//				Log.v(this.toString(), event.action.toString())
				when (event.action) {
					MotionEvent.ACTION_DOWN,
					MotionEvent.ACTION_MOVE -> {
						image.alpha = 0.5f
					}
					else -> {
						image.alpha = 1f
					}
				}
				return@setOnTouchListener false
			}
//			runMaterial {
//				view.setBackgroundDrawable(resources.getDrawable(R.drawable.ripple_background))
//			}
		}
	}

	val MENU_ID_SETTINGS = 1
	val MENU_ID_SHUFFLE = 2

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menu?.add(Menu.NONE, MENU_ID_SETTINGS, 1, R.string.settings)
		menu?.add(Menu.NONE, MENU_ID_SHUFFLE, 1, R.string.shuffle)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		item?.let { item ->
			when (item.itemId) {
				MENU_ID_SETTINGS -> startActivity<SettingsActivity>()
				MENU_ID_SHUFFLE -> thread {
					var i = 100
					while (i-- > 0) {
						Thread.sleep(50)
						Audio.randomPlay()
					}
				}
				else -> {
				}
			}
		}
		return true
	}
}
