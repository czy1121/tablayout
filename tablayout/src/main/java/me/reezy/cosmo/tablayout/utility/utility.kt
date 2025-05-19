@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.tablayout.utility

import android.widget.TextView
import com.google.android.material.tabs.TabLayout


val TabLayout.Tab.textView: TextView?
    get() = customView?.findViewById(android.R.id.text1)
