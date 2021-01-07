package com.luuuzi.wanandroid.project

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
class ProjectFragment:Fragment() {
    companion object{
        @JvmStatic
        fun newInstance()=ProjectFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project,container,false)
    }
}