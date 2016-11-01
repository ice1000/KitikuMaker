package utils

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.util.*

/**
 * Created by ice1000 on 2016/11/1.
 *
 * @author ice1000
 */

object Audio {
    var loadedData: List<Int> = emptyList()
    var soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 1)
    fun init(context: Context) {
        val list = ArrayList<Int>(50)
        kitikuList.forEach { p -> list.add(soundPool.load(context, p.second, 1)) }
    }

    fun playSound(soundId: Int): Boolean {
        if (soundId < loadedData.size)
            soundPool.play(loadedData[soundId], 1f, 1f, 0, 0, 1f)
        Log.d("233", "playing $soundId")
        return soundId < loadedData.size
    }

    operator fun get(int: Int) = loadedData[int]
}
