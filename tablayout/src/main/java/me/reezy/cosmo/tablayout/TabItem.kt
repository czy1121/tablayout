package me.reezy.cosmo.tablayout

import androidx.annotation.Keep

class TabItem(
    @Keep val id: String,
    @Keep val text: String? = null,
    @Keep val iconNormal: String? = null,
    @Keep val iconActive: String? = null,
    @Keep val iconWidth: Int = 0,
    @Keep val iconHeight: Int = 0,
) {
    val hasIcon: Boolean get() = !iconNormal.isNullOrEmpty()
    val hasText: Boolean get() = !text.isNullOrEmpty()

    override fun equals(other: Any?): Boolean {
        return if (other is String) id == other else super.equals(other)
    }
}

