package org.evgem.android.drachukeugenesapp

object AppConfig {
    enum class Theme {
        LIGHT, DARK
    }
    enum class Layout (val portraitIconAmount: Int, val landscapeIconAmount: Int){
        STANDARD(4, 6), TIGHT(5, 7)
    }

    var appTheme: Theme = Theme.LIGHT
    var layout: Layout = Layout.STANDARD
}