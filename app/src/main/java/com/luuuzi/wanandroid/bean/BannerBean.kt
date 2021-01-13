package com.luuuzi.wanandroid.bean

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:
 */
data class BannerBean(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String,
    val filePath:String

)