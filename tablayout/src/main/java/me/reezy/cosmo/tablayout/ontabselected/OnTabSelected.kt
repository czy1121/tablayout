package me.reezy.cosmo.tablayout.ontabselected

import com.google.android.material.tabs.TabLayout

class OnTabSelected(
    private val onUnselected: ((TabLayout.Tab) -> Unit)? = null,
    private val onReselected: ((TabLayout.Tab) -> Unit)? = null,
    private val onSelected: ((TabLayout.Tab) -> Unit)? = null
) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        onSelected?.invoke(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        onUnselected?.invoke(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        onReselected?.invoke(tab)
    }
}