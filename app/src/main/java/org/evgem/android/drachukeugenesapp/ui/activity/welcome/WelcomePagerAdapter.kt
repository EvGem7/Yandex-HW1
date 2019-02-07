package org.evgem.android.drachukeugenesapp.ui.activity.welcome

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.evgem.android.drachukeugenesapp.ui.fragment.DescriptionFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.WelcomeFragment

class WelcomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    companion object {
        val fragments = listOf(
            WelcomeFragment(),
            DescriptionFragment()
        )
    }

    override fun getItem(pos: Int): Fragment {
        return fragments[pos]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}