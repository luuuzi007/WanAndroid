package com.luuuzi.wanandroid.bean

import java.io.Serializable

/**
 *@author: Luuuzi
 *@Date: 2021-03-04
 *@description:
 */
data class LoginBean(val `data`: UserBean) : BaseBean()
data class UserBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) : Serializable {
    override fun toString(): String {
        return "admin:$admin,email:$email," +
                "icon:$icon,nickname:$nickname,publicName:$publicName,token:$token,username:$username"
    }
}