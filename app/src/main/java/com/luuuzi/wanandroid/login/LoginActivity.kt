package com.luuuzi.wanandroid.login

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.luuuzi.common.view.BaseActivity
import com.luuuzi.simplehttp.util.sharepreference.VCPreference
import com.luuuzi.simplehttp.util.toast.ToastUtil
import com.luuuzi.simplehttp.widget.loader.VCyunLoader
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.LoginBean
import com.luuuzi.wanandroid.constant.Config
import kotlinx.android.synthetic.main.activity_login.*

/**
 *@author: Luuuzi
 *@Date: 2021-03-04
 *@description:登录注册模块
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    private var mIsLogin = true
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application)).get(
            LoginViewModel::class.java
        )
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_login
    override fun initData() {
        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        viewModel.loginResult.observe(this, object : Observer<LoginBean> {
            override fun onChanged(t: LoginBean?) {
                LogUtils.i("code:${t?.errorCode}")
                LogUtils.i("code:${t?.errorMsg}")
                if (t?.errorCode == 0) {//成功
                    if (mIsLogin) {
                        ToastUtil.showMessage("登录成功")
                        VCPreference.put(Config.USER_INFO, t)
                        LiveEventBus
                            .get(Config.IS_LOGIN)
                            .post(true)
                        finish()
                    } else {
                        ToastUtil.showMessage("注册成功")
                    }
                } else {
                    ToastUtil.showMessage(t?.errorMsg)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register -> flipAnimatorXVieShow(ll_login)
            R.id.btn_login -> login()
        }
    }

    private fun login() {
        LogUtils.i("login")
        if (checkInfo()) {
            VCyunLoader.showLoading(this)
            if (mIsLogin) {
                viewModel.login(
                    tv_account.text.toString().trim(),
                    tv_password.text.toString().trim()
                )
            } else {
                viewModel.register(
                    tv_account.text.toString().trim(),
                    tv_password.text.toString().trim(),
                    tv_repassword.text.toString().trim()
                )
            }
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
        tv_repassword.visibility = if (mIsLogin) View.VISIBLE else View.GONE
        mIsLogin = !mIsLogin
    }

    private fun checkInfo(): Boolean {
        val account: String? = tv_account.text.toString().trim()
        val password: String? = tv_password.text.toString().trim()
        val repassword: String? = tv_repassword.text.toString().trim()
        if (account.isNullOrEmpty() || account.length < 6) {
            tv_account.error = "请输入正确的用户名格式"
            return false
        }
        if (password.isNullOrEmpty() || account.length < 6) {
            tv_password.error = "请输入正确的密码格式"
            return false
        }
        if (!mIsLogin && (repassword.isNullOrEmpty() || password != repassword)) {
            tv_register.error = "两次密码输入不一致"
            return false
        }
        return true
    }
}