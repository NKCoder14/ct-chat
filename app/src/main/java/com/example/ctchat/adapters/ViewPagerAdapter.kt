package com.example.ctchat.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ctchat.fragments.CallsFragment
import com.example.ctchat.fragments.ChatsFragment
import com.example.ctchat.fragments.StatusFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ChatsFragment()
            1 -> StatusFragment()
            2 -> CallsFragment()
            else -> ChatsFragment()
        }
    }
}
