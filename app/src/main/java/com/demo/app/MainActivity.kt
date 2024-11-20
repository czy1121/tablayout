package com.demo.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.demo.app.databinding.ActivityMainBinding
import me.reezy.cosmo.tablayout.TabItem
import me.reezy.cosmo.tablayout.utility.setBadgeNumber
import me.reezy.cosmo.tablayout.utility.setBadgeVisible

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            TabItem("1", "Tab1"),
            TabItem("2", "Tab2"),
            TabItem(
                "3", //"TAB3",
                iconNormal = "https://loremflickr.com/72/72/cat",
                iconActive = "https://loremflickr.com/72/72/dog",
//                iconSize = 24
            ),
            TabItem("4", "Tab4"),
        )
        binding.tabs.setup(items)
        binding.tabs.getTab("2")?.setBadgeVisible(true)

        binding.tabs2.iconViewFactory = {
            AppCompatImageView(it.context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val items2 = listOf(
            imageItem("1", "cat"),
            imageItem("2", "dog"),
            imageItem("3", "duck"),
            imageItem("4", "fish"),
        )
        binding.tabs2.setup(items2)


    }

    private fun imageItem(name: String, key: String): TabItem {
        return TabItem(
            name,
            iconNormal = "https://loremflickr.com/72/72/$key?1",
            iconActive = "https://loremflickr.com/72/72/$key?2",
        )
    }
}