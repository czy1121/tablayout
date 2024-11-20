package me.reezy.cosmo.tablayout.ontabselected

import android.animation.ValueAnimator
import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import me.reezy.cosmo.tablayout.utility.textView


class OnTabTextSize(private val normal: Float, private val active: Float, private val unit: Int = TypedValue.COMPLEX_UNIT_SP) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        tab.textView?.animateTextSize(normal, active)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.textView?.animateTextSize(active, normal)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }

    private fun TextView.animateTextSize(from: Float, to: Float) {
        if (isInEditMode) {
            setTextSize(unit, to)
            return
        }
        val animator = ValueAnimator.ofFloat(from, to)
        animator.addUpdateListener {
            setTextSize(unit, it.animatedValue as Float)
        }
        animator.start()
    }
}