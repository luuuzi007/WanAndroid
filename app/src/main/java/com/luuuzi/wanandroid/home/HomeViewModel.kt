package com.luuuzi.wanandroid.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.wanandroid.bean.BannerBean
import com.luuuzi.wanandroid.bean.Data

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:
 */
class HomeViewModel : ViewModel() {
    var mBanners: MutableLiveData<List<Data>> = MutableLiveData()

    /**
     * 获取banner
     */
    fun loadBanner() {
        SimpleHttp
            .Builder()
            .url("banner/json")
            .build()
            .get()
            .request(BannerBean::class.java, object : ISuccess<BannerBean> {
                override fun success(t: BannerBean) {

                    mBanners.value = t.data
                }
            })

    }


}