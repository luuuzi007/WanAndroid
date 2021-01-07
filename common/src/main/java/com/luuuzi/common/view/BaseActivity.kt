package com.luuuzi.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }
    open fun initView(){

    }
    open fun initData(){

    }
    abstract fun getLayoutId():Int
}