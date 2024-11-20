package me.reezy.cosmo.tablayout.utility

import android.content.Context
import android.graphics.drawable.Drawable
import coil.Coil
import coil.request.ImageRequest

class CoilImageLoader : ImageLoader {
    override fun load(context: Context, url: String?, onSuccess: (drawable: Drawable) -> Unit) {
        Coil.imageLoader(context).enqueue(ImageRequest.Builder(context).data(url).allowHardware(false).target(onSuccess = onSuccess).build())
    }
}