package com.luuuzi.wanandroid

import android.app.Application
import com.blankj.utilcode.util.ThreadUtils
import com.luuuzi.simplehttp.app.App
import com.luuuzi.simplehttp.app.Configurator
import com.luuuzi.simplehttp.util.log.MLog
import com.luuuzi.wanandroid.constant.Config

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(this)
        initThreadService()
    }

    private fun initThreadService() {
        ThreadUtils.executeByCpu(object : ThreadUtils.SimpleTask<Void>() {
            override fun doInBackground(): Void? {
                Configurator.getInstance()
                    .withApiHost(Config.BASE_URL) //必填：baseurl
//                    .withInterceptor(ResponseDecryptInercept())//可选：自定义拦截器(这里自己添加拦截器去设置head等)
//                    .withNetErrorHandle(ErrorHandle::class.java) //可选：添加错误返回处理
                    .configure()//是否初始化完成
                return null
            }
            override fun onSuccess(result: Void?) {
                MLog.i("初始完成")
            }

            override fun onFail(t: Throwable?) {
                MLog.i("初始化失败")
            }
        })
    }
}