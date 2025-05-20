package com.demo.app

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

        val items2 = listOf(
            imageItem("1"),
            imageItem("13"),
            imageItem("20"),
            imageItem("36"),
            imageItem("54"),
        )
        binding.tabs2.setup(items2)

        binding.tabs3.setup(listOf(pngItem("0"), pngItem("1"), pngItem("2"), pngItem("3")))


        binding.tabs4.setup(listOf(tabItem("1"), tabItem("13"), tabItem("20"), tabItem("54")))


    }

    private fun imageItem(name: String, key: String = name) = TabItem(
        name,
        iconNormal = "https://picsum.photos/id/$key/225/150?grayscale",
        iconActive = "https://picsum.photos/id/$key/225/150",
    )

    private fun tabItem(name: String, key: String = name) = TabItem(
        name,
        "tab-$key",
        iconNormal = "https://picsum.photos/id/$key/300/300?grayscale",
        iconActive = "https://picsum.photos/id/$key/300/300",
        iconHeight = 24
    )

    private fun pngItem(id: String) = TabItem(id, iconNormal = icon("${id}_0.png"), iconActive = icon("${id}_1.png"))

    private fun icon(key: String) = "file:///android_asset/tabs/$key"
}