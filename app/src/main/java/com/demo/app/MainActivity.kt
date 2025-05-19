package com.demo.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.demo.app.databinding.ActivityMainBinding
import me.reezy.cosmo.tablayout.TabItem

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            TabItem("1", "Tab1"),
            TabItem("2", "Tab2"),
            TabItem("3", "Tab3"),
            TabItem("4", "Tab4"),
        )
        binding.tabs.setup(items)
//        binding.tabs.getTab("2")?.setBadgeVisible(true)

        val items2 = listOf(
            imageItem("1"),
            imageItem("13"),
            imageItem("20"),
            imageItem("54"),
        )
        binding.tabs2.setup(items2)

        val items3 = listOf(
            tabItem("1"),
            tabItem("13"),
            tabItem("20"),
            tabItem("54"),
        )
        binding.tabs3.setup(items3)


    }

    private fun imageItem(name: String, key: String = name): TabItem {
        return TabItem(
            name,
            iconNormal = "https://picsum.photos/id/$key/270/150?grayscale",
            iconActive = "https://picsum.photos/id/$key/270/150",
        )
    }

    private fun tabItem(name: String, key: String = name): TabItem {
        return TabItem(
            name,
            "tab-$key",
            iconNormal = "https://picsum.photos/id/$key/300/300?grayscale",
            iconActive = "https://picsum.photos/id/$key/300/300",
            iconHeight = 24
        )
    }
}