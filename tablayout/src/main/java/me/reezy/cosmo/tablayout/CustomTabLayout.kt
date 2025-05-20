package me.reezy.cosmo.tablayout

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.reezy.cosmo.R
import me.reezy.cosmo.tablayout.ontabselected.OnTabTextFont
import me.reezy.cosmo.tablayout.ontabselected.OnTabTextSize
import me.reezy.cosmo.tablayout.utility.CoilImageLoader
import me.reezy.cosmo.tablayout.utility.ImageLoader
import me.reezy.cosmo.tablayout.utility.textView
import kotlin.math.max

class CustomTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TabLayout(context, attrs, defStyleAttr) {

    companion object {
        var imageLoader: ImageLoader = CoilImageLoader()
    }

    private var tabTextSize: Float = 0f
    private var tabTextTypeface: Typeface? = null

    private var tabSelectedTextSize: Float = 0f
    private var tabSelectedTextTypeface: Typeface? = null

    private var tabLayoutId: Int = 0


    private var tabIconScaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER
    private var tabIconAdjustViewBounds: Boolean = false

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout)

        tabLayoutId = a.getResourceId(R.styleable.CustomTabLayout_tabLayoutId, R.layout.tablayout_tab_icon_text)
        tabIconScaleType = ImageView.ScaleType.values()[a.getInt(R.styleable.CustomTabLayout_tabIconScaleType, ImageView.ScaleType.FIT_CENTER.ordinal)]
        tabIconAdjustViewBounds = a.getBoolean(R.styleable.CustomTabLayout_tabIconAdjustViewBounds, false)

        tabTextSize = a.getDimension(R.styleable.CustomTabLayout_tabTextSize, 14f * context.resources.displayMetrics.density)
        tabTextTypeface = a.getTypeface(R.styleable.CustomTabLayout_tabTextFont, R.styleable.CustomTabLayout_tabTextStyle)

        tabSelectedTextSize = a.getDimension(R.styleable.CustomTabLayout_tabSelectedTextSize, 0f)
        tabSelectedTextTypeface = a.getTypeface(R.styleable.CustomTabLayout_tabSelectedTextFont, R.styleable.CustomTabLayout_tabSelectedTextStyle)
        a.recycle()

        if (tabSelectedTextSize > 0) {
            addOnTabSelectedListener(OnTabTextSize(tabTextSize, tabSelectedTextSize, TypedValue.COMPLEX_UNIT_PX))
        }

        if (tabSelectedTextTypeface != null) {
            addOnTabSelectedListener(OnTabTextFont(tabTextTypeface, tabSelectedTextTypeface))
        }


        if (isInEditMode) {
            val items = listOf(
                TabItem("1", "Tab1"),
                TabItem("2", "Tab2"),
                TabItem("3", "Tab3"),
            )
            setup(items)
        }


    }

    override fun setTabTextColors(textColor: ColorStateList?) {
        super.setTabTextColors(textColor)
        if (tabTextColors != textColor) {
            (0 until tabCount).forEach {
                getTabAt(it)?.textView?.setTextColor(textColor)
            }
        }
    }

    fun getTab(name: String): Tab? {
        if (name.isEmpty()) return null
        (0 until tabCount).forEach { i ->
            getTabAt(i)?.let {
                if (it.tag?.equals(name) == true) {
                    return it
                }
            }
        }
        return null
    }

    fun select(name: String): Tab? {
        return getTab(name)?.apply { select() }
    }

    fun select(index: Int): Tab? {
        return getTabAt(index)?.apply { select() }
    }

    fun setup(tabs: List<TabItem>) {
        removeAllTabs()
        for (item in tabs) {
            addTab(newTab().setItem(item))
        }
    }


    fun setup(tabs: List<TabItem>, pager: ViewPager2) {
        TabLayoutMediator(this, pager) { tab, position ->
            tab.setItem(tabs[position])
        }.attach()
    }

    fun setItem(index: Int, item: TabItem) {
        getTabAt(index)?.setItem(item)
    }


    private fun Tab.setItem(item: TabItem): Tab {
        tag = item
        text = item.text
        customView = createCustomView(this, item)
        return this
    }


    private fun createCustomView(tab: Tab, item: TabItem): View {
        val custom = LayoutInflater.from(context).inflate(tabLayoutId, tab.view, false)

        custom.findViewById<ImageView>(android.R.id.icon1)?.apply {
            if (item.hasIcon) {
                scaleType = tabIconScaleType
                adjustViewBounds = tabIconAdjustViewBounds
                loadTabIcon(item, max(item.iconWidth.dp, 0), max(item.iconHeight.dp, 0), ::setImageDrawable)
            } else {
                visibility = GONE
            }
        }
        custom.findViewById<TextView>(android.R.id.text1)?.apply {
            if (item.hasText) {
                setTextColor(tabTextColors)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize)
                typeface = tabTextTypeface
            } else {
                visibility = GONE
            }
        }
        return custom
    }

    private fun loadTabIcon(item: TabItem, width: Int, height: Int, onLoaded: (Drawable) -> Unit) {
        if (item.iconNormal.isNullOrEmpty()) return

        if (item.iconActive.isNullOrEmpty()) {
            imageLoader.load(context.applicationContext, item.iconNormal, width, height) {
                onLoaded(it)
            }
        } else {
            var normal: Drawable? = null
            var active: Drawable? = null

            imageLoader.load(context.applicationContext, item.iconNormal, width, height) {
                if (active != null) {
                    onLoaded(createStateListDrawable(it, active!!))
                } else {
                    normal = it
                }
            }
            imageLoader.load(context.applicationContext, item.iconActive, width, height) {
                if (normal != null) {
                    onLoaded(createStateListDrawable(normal!!, it))
                } else {
                    active = it
                }
            }
        }
    }



    private fun TypedArray.getTypeface(fontFamilyIndex: Int, textStyleIndex: Int): Typeface? {
        val font = getFont(fontFamilyIndex)
        if (font != null) {
            if (hasValue(textStyleIndex)) {
                return Typeface.create(font, getInt(textStyleIndex, Typeface.NORMAL))
            }
            return font
        }
        if (hasValue(textStyleIndex)) {
            return Typeface.create(Typeface.DEFAULT, getInt(textStyleIndex, Typeface.NORMAL))
        }
        return null
    }

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()

    private fun createStateListDrawable(normal: Drawable, active: Drawable) = StateListDrawable().apply {
        addState(intArrayOf(android.R.attr.state_selected), active)
        addState(intArrayOf(), normal)
    }
}