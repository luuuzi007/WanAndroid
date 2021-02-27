package com.luuuzi.wanandroid.article

import android.content.Context
import android.content.Intent
import android.view.View
import com.luuuzi.common.view.BaseActivity
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.utils.getHtmlText
import kotlinx.android.synthetic.main.activity_article.*

/**
 *@author: Luuuzi
 *@Date: 2021-02-25
 *@description:查看文章内容
 */
const val PAGE_NAME = "PAGE_NAME"
const val PAGE_URL = "PAGE_URL"
const val PAGE_ID = "PAGE_ID"
const val ORIGIN_ID = "ORIGIN_ID"
const val USER_ID = "USER_ID"
const val IS_COLLECTION = "IS_COLLECTION"

class ArticleActivity : BaseActivity(), View.OnClickListener {
    private var pageName = ""
    private var pageUrl = ""
    private var pageId = -1
    private var originId = ""
    private var userId = -1
    private var isCollection = -1
    override fun getLayoutId(): Int = R.layout.activity_article

    override fun initData() {
        iv_back.setOnClickListener(this)
        pageName = intent.getStringExtra(PAGE_NAME) ?: ""
        pageUrl = intent.getStringExtra(PAGE_URL) ?: ""
        pageId = intent.getIntExtra(PAGE_ID, -1)
        isCollection = intent.getIntExtra(IS_COLLECTION, -1)
        originId = intent.getStringExtra(ORIGIN_ID) ?: ""
        userId = intent.getIntExtra(USER_ID, -1)
        tv_Title.text = getHtmlText(pageName)
        web_view.loadUrl(pageUrl)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                if (web_view.canGoBack()) {
                    //返回上个页面
                    web_view.goBack()
                    return
                } else {
                    finish()
                }
            }
            R.id.iv_right -> {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        web_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        web_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        web_view.destroy()
    }
    companion object {
        fun actionStart(context: Context, data: AriticleData) {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(PAGE_NAME, data.title)
                putExtra(PAGE_URL, data.link)
                putExtra(PAGE_ID, data.id)
                putExtra(IS_COLLECTION, 1)
                putExtra(ORIGIN_ID, data.origin)
                putExtra(USER_ID, data.userId)
            }
            context.startActivity(intent)
        }
        fun actionStart(
            context: Context,
            pageName: String,
            pageUrl: String
        ) {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(PAGE_NAME, pageName)
                putExtra(PAGE_URL, pageUrl)
            }
            context.startActivity(intent)
        }
    }


}