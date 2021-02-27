package com.luuuzi.wanandroid.utils

import android.os.Build
import android.text.Html

/**
 *@author: Luuuzi
 *@Date: 2021-02-26
 *@description: 加载html
 */
fun getHtmlText(str: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        str
    }
}