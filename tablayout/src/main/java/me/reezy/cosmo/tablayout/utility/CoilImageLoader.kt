package me.reezy.cosmo.tablayout.utility

import android.content.Context
import android.graphics.drawable.Drawable
import coil.Coil
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Size

class CoilImageLoader : ImageLoader {
    override fun load(context: Context, url: String?, width: Int, height: Int, onSuccess: (drawable: Drawable) -> Unit) {
        Coil.imageLoader(context).enqueue(
            ImageRequest.Builder(context)
                .data(url)
                .size(if (width > 0) Dimension(width) else Dimension.Undefined, if (height > 0) Dimension(height) else Dimension.Undefined)
                .allowHardware(false)
                .target(onSuccess = onSuccess)
                .build()
        )
    }
}