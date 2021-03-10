package com.luuuzi.wanandroid.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.IError
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.simplehttp.widget.loader.VCyunLoader

/**
 *@author: Luuuzi
 *@Date: 2021-03-10
 *@description:
 */
class MyViewModel:ViewModel() {
    val logoutData:MutableLiveData<String> = MutableLiveData()
    //退出登录
    fun logout(){
        SimpleHttp.Builder()
            .url("user/logout/json")
            .error(object :IError{
                override fun onError(code: Int, msg: String?) {
                    VCyunLoader.stopLoading()
                }
            })
            .build()
            .get()
            .request(Any::class.java,object :ISuccess<Any>{
                override fun success(t: Any) {
                    logoutData.value="退出成功"
                }

            })
    }
}