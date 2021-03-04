package com.luuuzi.wanandroid.login

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.OvershootInterpolator
import com.luuuzi.common.view.BaseActivity
import com.luuuzi.wanandroid.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 *@author: Luuuzi
 *@Date: 2021-03-04
 *@description:登录注册模块
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    private var mIsLogin = true

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_login
    override fun initData() {
        tv_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register -> flipAnimatorXVieShow(ll_login)
        }
    }

    private fun flipAnimatorXVieShow(view: View) {
        //从登录到注册
        val ofFloat1 = ObjectAnimator.ofFloat(view, "rotationY", 0f, if (mIsLogin) 90f else -90f)
        //从注册到登录
        val ofFloat2 = ObjectAnimator.ofFloat(view, "rotationY", if (mIsLogin) -90f else 90f, 0f)
        ofFloat2.interpolator = OvershootInterpolator(2.0f)
        ofFloat1.setDuration(700).start()
        ofFloat1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                ofFloat2.setDuration(700).start()
                updateState()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    private fun updateState() {
        tv_register.text = if (mIsLogin) "返回登录" else "注册账号"
        btn_login.text = if (mIsLogin) "立即注册" else "登录"
        mIsLogin = !mIsLogin
    }
}