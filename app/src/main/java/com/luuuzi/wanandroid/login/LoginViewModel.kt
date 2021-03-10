package com.luuuzi.wanandroid.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luuuzi.simplehttp.net.callback.IError
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.SimpleHttp
import com.luuuzi.simplehttp.widget.loader.VCyunLoader
import com.luuuzi.wanandroid.bean.LoginBean

/**
 *@author: Luuuzi
 *@Date: 2021-03-04
 *@description:
 */
class LoginViewModel:ViewModel() {
    val loginResult:MutableLiveData<LoginBean> = MutableLiveData()
    fun login(userName:String,password:String){
        SimpleHttp.Builder()
            .url("user/login")
            .params("username",userName)
            .params("password",password)
            .error(object :IError{
                override fun onError(code: Int, msg: String?) {
                    VCyunLoader.stopLoading()
                }
            })
            .build()
            .postQuery()
            .request(LoginBean::class.java,object :ISuccess<LoginBean>{
                override fun success(t: LoginBean) {
                    VCyunLoader.stopLoading()
                    loginResult.value=t
                }
            })
    }

    fun register(userName: String,password: String,repassword:String){
        SimpleHttp.Builder()
            .url("user/register")
            .params("username",userName)
            .params("password",password)
            .params("repassword",repassword)
            .error(object :IError{
                override fun onError(code: Int, msg: String?) {
                    VCyunLoader.stopLoading()
                }
            })
            .build()
            .postQuery()
            .request(LoginBean::class.java,object :ISuccess<LoginBean>{
                override fun success(t: LoginBean) {
                    VCyunLoader.stopLoading()
                    loginResult.value=t
                }

            })
    }
}