package com.luuuzi.wanandroid.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:
 */
class ParameterizedTypeUtils {
    companion object {
        fun getType(raw: Class<*>, vararg args: Type) = object : ParameterizedType {
            override fun getRawType(): Type = raw
            override fun getActualTypeArguments(): Array<out Type> = args
            override fun getOwnerType(): Type? = null
        }
    }
}