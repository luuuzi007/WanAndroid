package com.luuuzi.wanandroid.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.simplehttp.util.log.MLog
import com.luuuzi.wanandroid.bean.*

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:
 */
class HomeViewModel : ViewModel() {
    var mBanners: MutableLiveData<List<Data>> = MutableLiveData()
    var mTopAriticles: MutableLiveData<List<AriticleData>> = MutableLiveData()
    val mArticles: MutableLiveData<List<AriticleData>> = MutableLiveData()

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

    /**
     * 获取置顶文章列表
     */
    fun loadTopArticle() {
        MLog.i("获取文章列表")
        SimpleHttp.Builder()
            .url("article/top/json")
            .build()
            .get()
            .request(TopAriticleBean::class.java, object : ISuccess<TopAriticleBean> {
                override fun success(t: TopAriticleBean) {
                    mTopAriticles.value = t.data
                }
            })

    }

    /**
     * 获取文章列表
     */
    fun loadAriticle(page: Int) {
        SimpleHttp.Builder()
            .url("article/list/$page/json")
            .build()
            .get()
            .request(ArticleListBean::class.java, object : ISuccess<ArticleListBean> {
                override fun success(t: ArticleListBean) {
                    mArticles.value = t.data.datas
                }
            })
    }


}