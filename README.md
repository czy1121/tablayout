# CustomTabLayout

简化 TabLayout 使用，包含文本和图标的 customView 代替 TabLayout.TabView


- 支持远程动态加载图标
- 支持在 xml 中设置多个文本属性，支持在 AS 中预览
  - 大小 tabTextSize/tabSelectedTextSize
  - 字体 tabTextFont/tabSelectedTextFont
  - 样式 tabTextStyle/tabSelectedTextStyle


## Gradle

``` groovy
repositories {
    maven { url "https://gitee.com/ezy/repo/raw/cosmo/"}
}
dependencies {
    implementation "me.reezy.cosmo:tablayout:0.10.8"
}
```

## TabLayout 的一些问题

### TabLayout 无法动态设置字体大小

TabLayout.TabView 的字体大小只在 app:tabTextAppearance 中设置，就算获取到TabView里的TextView也无法改变字体大小

想要动态改变字体大小只能使用 customView

使用 customView 后 `tabTextAppearance, tabTextColor, tabSelectedTextColor` 都失效了
使用 customView 后 `badge` 失效


### TabLayout 的 tabPadding

- `tabPadding*` 不是添加在 `customView` 上的边距，对手动在 `customView` 添加的 `badge` 有影响
- `tabPadding` 只能设置垂直方向的边距，对水平方向的空白无效
- `tabPaddingStart, tabPaddingEnd` 可设置水平方向边距，有默认值

###  TabLayout 无法为指示器动态设置透明

```
// 这行代码是无效的，还是之前的颜色
tabs.setSelectedTabIndicatorColor(Color.TRANSPARENT)
```

### 代码中设置指示器图形时需要触发一个点击事件才生效

## LICENSE

The Component is open-sourced software licensed under the [Apache license](LICENSE).