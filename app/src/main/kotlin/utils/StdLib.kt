/**
 * Created by ice1000 on 2016/11/1.
 * @author ice1000
 */
package utils

fun unless(condition: Boolean, block: () -> Unit) {
	if (!condition) block()
}

