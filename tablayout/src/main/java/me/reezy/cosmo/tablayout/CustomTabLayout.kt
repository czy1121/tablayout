package me.reezy.cosmo.tablayout

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.reezy.cosmo.R
import me.reezy.cosmo.tablayout.ontabselected.OnTabTextFont
import me.reezy.cosmo.tablayout.ontabselected.OnTabTextSize
import me.reezy.cosmo.tablayout.utility.CoilImageLoader
import me.reezy.cosmo.tablayout.utility.DrawableWrapper
import me.reezy.cosmo.tablayout.utility.ImageLoader
import kotlin.math.max

class CustomTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TabLayout(context, attrs, defStyleAttr) {

    companion object {
        var imageLoader: ImageLoader = CoilImageLoader()

        val ICON_VIEW_FACTORY_FILL: (TabLayout) -> ImageView = {
            AppCompatImageView(it.context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
    }

    private var tabTextSize: Float = 0f
    private var tabTextTypeface: Typeface? = null

    private var tabSelectedTextSize: Float = 0f
    private var tabSelectedTextTypeface: Typeface? = null

    var textViewFactory: (TabLayout) -> TextView = {
        AppCompatTextView(context).apply {
            gravity = Gravity.CENTER
        }
    }

    var iconViewFactory: (TabLayout) -> ImageView = {
        AppCompatImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }
    }

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout)
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
            addTab(newTab().setTag(item).setText(item.text).setCustomView(createCustomView(item)))
        }
    }


    fun setup(tabs: List<TabItem>, pager: ViewPager2) {
        TabLayoutMediator(this, pager) { tab, position ->
            val item = tabs[position]
            tab.setTag(item).setText(item.text).customView = createCustomView(item)
        }.attach()
    }


    private fun createCustomView(item: TabItem): View? {
        val view = LinearLayoutCompat(context)
        view.orientation = LinearLayoutCompat.VERTICAL
        view.gravity = Gravity.CENTER
        view.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        if (item.hasIcon) {
            view.addView(iconViewFactory.invoke(this).apply {
                id = android.R.id.icon1
                if (item.iconResId > 0) {
                    setImageResource(item.iconResId)
                } else {
                    setImageDrawable(createItemIcon(item, this::requestLayout))
                }
            })
        }

        if (item.hasText) {
            view.addView(textViewFactory.invoke(this).apply {
                id = android.R.id.text1
                setTextColor(tabTextColors)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize)
                typeface = tabTextTypeface
            })
        }
        return view
    }


    private fun createItemIcon(item: TabItem, onLoaded: () -> Unit): Drawable? {
        if (item.iconNormal.isNullOrEmpty()) return null

        val width = (max(item.iconWidth, 0) * resources.displayMetrics.density).toInt()
        val height = (max(item.iconHeight, 0) * resources.displayMetrics.density).toInt()

        if (item.iconActive.isNullOrEmpty()) return createDrawable(item.iconNormal, width, height, onLoaded)

        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), createDrawable(item.iconActive, width, height, onLoaded))
            addState(intArrayOf(), createDrawable(item.iconNormal, width, height, onLoaded))
        }
    }

    private fun createDrawable(url: String?, width: Int, height: Int, onLoaded: () -> Unit = {}): Drawable {

        val wrapper = DrawableWrapper(ColorDrawable())
        imageLoader.load(context.applicationContext, url) {
            wrapper.wrappedDrawable = it
            if (width > 0 && height > 0) {
                wrapper.setBounds(0, 0, width, height)
            } else if (width > 0) {
                wrapper.setBounds(0, 0, width, (width * it.intrinsicHeight / it.intrinsicWidth.toFloat()).toInt())
            } else if (height > 0) {
                wrapper.setBounds(0, 0, (height * it.intrinsicWidth / it.intrinsicHeight.toFloat()).toInt(), height)
            } else {
                wrapper.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            }
            wrapper.invalidateSelf()
            onLoaded()
        }
        return wrapper
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
}