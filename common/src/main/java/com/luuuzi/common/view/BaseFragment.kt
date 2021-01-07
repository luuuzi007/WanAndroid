package com.luuuzi.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *author:
 *Date: 2021-01-07
 *description:
 */
abstract class BaseFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), container, false)
        return rootView
    }
    open fun initView(rootView:View){

    }
    open fun initData(){

    }
    abstract fun getLayoutId():Int
}