package com.luuuzi.wanandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luuuzi.wanandroid.R

/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class HomeFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}