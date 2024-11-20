package me.reezy.cosmo.tablayout.utility

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat

@Suppress("DEPRECATION")
class DrawableWrapper(drawable: Drawable) : Drawable(), Drawable.Callback {

    var wrappedDrawable: Drawable = drawable
        set(value) {
            field.callback = null
            field = value
            field.callback = this
        }
    
    init {
        wrappedDrawable.callback = this
    }


    override fun draw(canvas: Canvas) {
        try {
            wrappedDrawable.draw(canvas)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        wrappedDrawable.bounds = bounds
    }

    override fun setChangingConfigurations(configs: Int) {
        wrappedDrawable.changingConfigurations = configs
    }

    override fun getChangingConfigurations(): Int {
        return wrappedDrawable.changingConfigurations
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun setDither(dither: Boolean) {
        wrappedDrawable.setDither(dither)
    }

    override fun setFilterBitmap(filter: Boolean) {
        wrappedDrawable.isFilterBitmap = filter
    }

    override fun setAlpha(alpha: Int) {
        wrappedDrawable.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        wrappedDrawable.colorFilter = cf
    }

    override fun isStateful(): Boolean {
        return wrappedDrawable.isStateful
    }

    override fun setState(stateSet: IntArray): Boolean {
        return wrappedDrawable.setState(stateSet)
    }

    override fun getState(): IntArray {
        return wrappedDrawable.state
    }

    override fun jumpToCurrentState() {
        wrappedDrawable.jumpToCurrentState()
    }

    override fun getCurrent(): Drawable {
        return wrappedDrawable.current
    }

    override fun setVisible(visible: Boolean, restart: Boolean): Boolean {
        return super.setVisible(visible, restart) || wrappedDrawable.setVisible(visible, restart)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getOpacity(): Int {
        return wrappedDrawable.opacity
    }

    override fun getTransparentRegion(): Region? {
        return wrappedDrawable.transparentRegion
    }

    override fun getIntrinsicWidth(): Int {
        return wrappedDrawable.intrinsicWidth
    }

    override fun getIntrinsicHeight(): Int {
        return wrappedDrawable.intrinsicHeight
    }

    override fun getMinimumWidth(): Int {
        return wrappedDrawable.minimumWidth
    }

    override fun getMinimumHeight(): Int {
        return wrappedDrawable.minimumHeight
    }

    override fun getPadding(padding: Rect): Boolean {
        return wrappedDrawable.getPadding(padding)
    }

    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        scheduleSelf(what, `when`)
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        unscheduleSelf(what)
    }

    override fun onLevelChange(level: Int): Boolean {
        return wrappedDrawable.setLevel(level)
    }

    override fun setAutoMirrored(mirrored: Boolean) {
        DrawableCompat.setAutoMirrored(wrappedDrawable, mirrored)
    }

    override fun isAutoMirrored(): Boolean {
        return DrawableCompat.isAutoMirrored(wrappedDrawable)
    }

    override fun setTint(tint: Int) {
        DrawableCompat.setTint(wrappedDrawable, tint)
    }

    override fun setTintList(tint: ColorStateList?) {
        DrawableCompat.setTintList(wrappedDrawable, tint)
    }

    override fun setTintMode(tintMode: PorterDuff.Mode?) {
        DrawableCompat.setTintMode(wrappedDrawable, tintMode!!)
    }

    override fun setHotspot(x: Float, y: Float) {
        DrawableCompat.setHotspot(wrappedDrawable, x, y)
    }

    override fun setHotspotBounds(left: Int, top: Int, right: Int, bottom: Int) {
        DrawableCompat.setHotspotBounds(wrappedDrawable, left, top, right, bottom)
    }
}