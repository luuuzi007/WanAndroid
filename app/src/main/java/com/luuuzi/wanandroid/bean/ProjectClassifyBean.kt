package com.luuuzi.wanandroid.bean

/**
 *@author: Luuuzi
 *@Date: 2021-02-27
 *@description:
 */
data class ProjectClassifyBean(
    val `data`: List<Classify>
):BaseBean()

data class Classify(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)