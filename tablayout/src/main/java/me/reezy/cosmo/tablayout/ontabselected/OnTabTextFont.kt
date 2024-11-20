package me.reezy.cosmo.tablayout.ontabselected

import android.graphics.Typeface
import com.google.android.material.tabs.TabLayout
import me.reezy.cosmo.tablayout.utility.textView


class OnTabTextFont(private val normal: Typeface?, private val active: Typeface?) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        tab.textView?.typeface = active
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.textView?.typeface = normal
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }
}