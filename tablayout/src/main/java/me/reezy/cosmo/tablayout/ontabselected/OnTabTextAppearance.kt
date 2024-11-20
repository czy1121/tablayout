package me.reezy.cosmo.tablayout.ontabselected

import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.google.android.material.tabs.TabLayout
import me.reezy.cosmo.tablayout.utility.textView

class OnTabTextAppearance(@StyleRes private val normal: Int, @StyleRes private val active: Int) : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        tab.textView?.let {
            TextViewCompat.setTextAppearance(it, active)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        tab.textView?.let {
            TextViewCompat.setTextAppearance(it, normal)
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }
}