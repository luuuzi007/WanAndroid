package com.luuuzi.wanandroid.public

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.wanandroid.bean.Classify
import com.luuuzi.wanandroid.bean.ProjectClassifyBean
import com.luuuzi.wanandroid.constant.Config

/**
 *@author: Luuuzi
 *@Date: 2021-02-27
 *@description:公众号
 */
class ThePublicViewModel : ViewModel() {
    var mClassifyBean: MutableLiveData<List<Classify>> = MutableLiveData()

    /**
     * 获取公众号分类
     */
    fun getChapterClassfly() {
        SimpleHttp.Builder()
            .url(Config.CHAPTERS_CLASSFLY)
            .build()
            .get()
            .request(ProjectClassifyBean::class.java, object : ISuccess<ProjectClassifyBean> {
                override fun success(t: ProjectClassifyBean) {
                    mClassifyBean.value=t.data
                }
            })
    }
}