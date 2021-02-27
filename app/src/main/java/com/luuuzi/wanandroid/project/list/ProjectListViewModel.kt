package com.luuuzi.wanandroid.project.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.bean.ArticleListBean

/**
 *@author: Luuuzi
 *@Date: 2021-02-27
 *@description:文章列表
 */
class ProjectListViewModel : ViewModel() {
    val mArticles: MutableLiveData<List<AriticleData>> = MutableLiveData()
    fun getProjectList(page: Int, cid: Int) {
        SimpleHttp.Builder()
            .url("project/list/$page/json")
            .params("cid", cid)
            .build()
            .get()
            .request(ArticleListBean::class.java, object : ISuccess<ArticleListBean> {
                override fun success(t: ArticleListBean) {
                    mArticles.value=t.data.datas
                }
            })

    }
}