@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.tablayout.utility

import android.widget.TextView
import com.google.android.material.tabs.TabLayout


val TabLayout.Tab.textView: TextView?
    get() = customView?.findViewById(android.R.id.text1)

//@SuppressLint("UnsafeOptInUsageError")
//fun TabLayout.Tab.updateBadge(block: BadgeDrawable.() -> Unit) {
//    customView?.doOnNextLayout {
//        var badge = it.getTag(R.id.tab_item_badge_id) as? BadgeDrawable
//        if (badge == null) {
//            badge = BadgeDrawable.create(it.context)
//            it.setTag(R.id.tab_item_badge_id, badge)
//        }
//        badge.block()
//
//        val view = it.findViewById(android.R.id.icon1) ?: it.findViewById(android.R.id.text1) ?: it
//        BadgeUtils.attachBadgeDrawable(badge, view)
//    }
//}
//
//fun TabLayout.Tab.setBadgeNumber(value: Int) {
//    updateBadge {
//        number = value
//        isVisible = true
//    }
//}
//
//fun TabLayout.Tab.setBadgeVisible(value: Boolean) {
//    updateBadge {
//        clearNumber()
//        isVisible = value
//    }
//}
