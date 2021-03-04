package com.luuuzi.wanandroid.my

import android.content.Context
import android.view.View
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.article.ArticleActivity
import com.luuuzi.wanandroid.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class MyFragment : BaseFragment(), View.OnClickListener {
    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

    override fun getLayoutId() = R.layout.fragment_my
    override fun initData() {
        iv_avator.setOnClickListener(this)
        tv_my_points.setOnClickListener(this)
        tv_collection.setOnClickListener(this)
        tv_blog.setOnClickListener(this)
        tv_history.setOnClickListener(this)
        tv_jianshu.setOnClickListener(this)
        tv_github.setOnClickListener(this)
        tv_about_me.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_avator ->
                LoginActivity.actionStart(activity as Context)
            R.id.tv_blog -> ArticleActivity.actionStart(
                activity as Context,
                "我的CSDN",
                "https://blog.csdn.net/qq_36366618?spm=1011.2124.3001.5343"
            )
            R.id.tv_jianshu ->
                ArticleActivity.actionStart(
                    activity as Context,
                    "我的简书",
                    "https://www.jianshu.com/u/1234cb001f0f"
                )
            R.id.tv_github -> ArticleActivity.actionStart(
                activity as Context,
                "我的Github",
                "https://github.com/luuuzi007"
            )
        }
    }
}