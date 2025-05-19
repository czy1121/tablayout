package me.reezy.cosmo.tablayout.utility

import android.content.Context
import android.graphics.drawable.Drawable

interface ImageLoader {
    fun load(context: Context, url: String?, width: Int, height: Int, onSuccess: (drawable: Drawable) -> Unit)
}