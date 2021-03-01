package com.luuuzi.common.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.githang.statusbar.StatusBarCompat

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //沉浸式
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FF8C00"))
        initView()
        initData()
    }

    open fun initView() {

    }

    open fun initData() {

    }

    abstract fun getLayoutId(): Int
}