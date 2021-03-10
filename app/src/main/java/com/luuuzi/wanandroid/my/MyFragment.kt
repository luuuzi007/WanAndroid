package com.luuuzi.wanandroid.my

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jeremyliao.liveeventbus.LiveEventBus
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.simplehttp.util.sharepreference.VCPreference
import com.luuuzi.simplehttp.widget.loader.VCyunLoader
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.article.ArticleActivity
import com.luuuzi.wanandroid.bean.LoginBean
import com.luuuzi.wanandroid.constant.Config
import com.luuuzi.wanandroid.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class MyFragment : BaseFragment(), View.OnClickListener {
    private val viewmodel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(MyViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

    override fun getLayoutId() = R.layout.fragment_my
    override fun initView(rootView: View) {
        iv_avator.setOnClickListener(this)
        tv_my_points.setOnClickListener(this)
        tv_collection.setOnClickListener(this)
        tv_blog.setOnClickListener(this)
        tv_history.setOnClickListener(this)
        tv_jianshu.setOnClickListener(this)
        tv_github.setOnClickListener(this)
        tv_about_me.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
    }

    override fun initData() {
        if (VCPreference.getAppFlag(Config.IS_LOGIN)) {
            val loginBean = VCPreference.get<LoginBean>(Config.USER_INFO)
            loginBean?.let {
                tv_name.text = loginBean.data.username
                btn_logout.visibility = View.VISIBLE
                tv_name.text="请登录"
            }
        }
        listLogin()
        viewmodel.logoutData.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                VCyunLoader.stopLoading()
                VCPreference.clearAppPreferences()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_avator ->
                LoginActivity.actionStart(activity as Context)
            R.id.tv_blog -> ArticleActivity.actionStart(
                activity as Context,
                "我的CSDN",
                Config.CSDN
            )
            R.id.tv_jianshu ->
                ArticleActivity.actionStart(
                    activity as Context,
                    "我的简书",
                    Config.JIANSHU
                )
            R.id.tv_github -> ArticleActivity.actionStart(
                activity as Context,
                "我的Github",
                Config.GITHUB
            )
            R.id.btn_logout -> logout()
        }
    }

    //登录监听
    private fun listLogin() {
        LiveEventBus.get(Config.IS_LOGIN, Boolean::class.java)
            .observe(this, object : Observer<Boolean> {
                override fun onChanged(t: Boolean?) {
                    val loginBean = VCPreference.get<LoginBean>(Config.USER_INFO)
                    tv_name.text = loginBean.data.username
                    btn_logout.visibility = View.VISIBLE
                }
            })
    }

    //退出登录
    private fun logout() {
        AlertDialog.Builder(context).setTitle("退出登录")
            .setMessage("确定要退出吗？")
            .setNegativeButton(
                "取消"
            ) { dialog, _ -> dialog?.dismiss() }
            .setPositiveButton("确定") { dialog, _ ->
                dialog?.dismiss()
                VCyunLoader.showLoading(activity)
                viewmodel.logout()
            }.show()
    }
}