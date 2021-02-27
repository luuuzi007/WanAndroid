package com.luuuzi.wanandroid.article

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.*
import android.widget.ProgressBar
import com.blankj.utilcode.util.LogUtils
import com.luuuzi.simplehttp.util.toast.ToastUtil
import com.luuuzi.wanandroid.R

/**
 *@author: Luuuzi
 *@Date: 2021-02-26
 *@description:自定义webview
 */
class ArticleWebView : WebView {
    private var processBar: ProgressBar? = null

    private val client: WebViewClient = object : WebViewClient() {
        //当页面加载完成回调
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            val endCookie = cookieManager.getCookie(url)
            LogUtils.i("onPageStarted:endCookie=$endCookie")
            CookieManager.getInstance().flush()
            super.onPageStarted(view, url, favicon)
        }

        //返回值是true的时候控制去WebView打开，
        // 为false调用系统浏览器或第三方浏览器
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            LogUtils.i("url:$url")
            return if (url!!.startsWith("http") || url!!.startsWith("https") || url!!.startsWith("ftp")) {
                false
            } else {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(url)
                    view!!.context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    LogUtils.i("没有安装支持打开此网页的应用")
                    ToastUtil.showMessage("没有安装支持打开此网页的应用")
                }
                true
            }
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
            val reUrl = view!!.url + ""
            val urlList: MutableList<String> = ArrayList()
            urlList.add(reUrl)
            val newList: MutableList<String> = ArrayList()
//            if (cd in urlList){
//                if (!newList.contains(cd)) {
//                    newList.add(cd)
//                }
//            }
        }
    }
    private val chromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            if (TextUtils.isEmpty(title)) {
                return
            }
        }

        //监听进度
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            processBar!!.progress = newProgress
            if (processBar != null && newProgress != 100) {

                //Webview加载没有完成 就显示我们自定义的加载图
                processBar!!.visibility = VISIBLE
            } else if (processBar != null) {

                //Webview加载完成 就隐藏进度条,显示Webview
                processBar!!.visibility = GONE
                //imageView.setVisibility(GONE);
            }
        }
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        processBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        processBar!!.max = 100
        processBar!!.progressDrawable = resources.getDrawable(R.drawable.color_progressbar)
        addView(processBar, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6))
        initWebViewStting()
    }

    private fun initWebViewStting() {
        setBackgroundColor(resources.getColor(android.R.color.white))
        webChromeClient = chromeClient
        webViewClient = client
        isClickable = true
        setOnTouchListener { v, event -> false }
        val webSetting = settings
        webSetting.javaScriptEnabled = true
        webSetting.builtInZoomControls = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.domStorageEnabled = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
//android 默认是可以打开_bank的，是因为它默认设置了WebSettings.setSupportMultipleWindows(false)
        //在false状态下，_bank也会在当前页面打开……
        //而x5浏览器，默认开启了WebSettings.setSupportMultipleWindows(true)，
        // 所以打不开……主动设置成false就可以打开了
        //需要支持多窗体还需要重写WebChromeClient.onCreateWindow
        webSetting.setSupportMultipleWindows(false)
        //        webSetting.setCacheMode(WebSettings.LOAD_NORMAL);
//        getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack() // goBack()表示返回WebView的上一页面
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}