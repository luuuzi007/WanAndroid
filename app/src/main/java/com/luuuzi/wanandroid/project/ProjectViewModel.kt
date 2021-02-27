package com.luuuzi.wanandroid.project

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
 *@description:
 */
class ProjectViewModel : ViewModel() {
    var mClassifyBean: MutableLiveData<List<Classify>> = MutableLiveData()

    /**
     * 获取分类
     */
    fun getClassfly() {
        SimpleHttp.Builder()
            .url(Config.PROJECT_CLASSFLY)
            .build()
            .get()
            .request(ProjectClassifyBean::class.java, object : ISuccess<ProjectClassifyBean> {
                override fun success(t: ProjectClassifyBean) {
                    mClassifyBean.value=t.data
                }

            })
    }
}