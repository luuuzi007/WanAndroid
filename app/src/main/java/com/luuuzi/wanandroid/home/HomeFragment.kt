package com.luuuzi.wanandroid.home

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.simplehttp.util.toast.ToastUtil
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.article.ArticleActivity
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.bean.Data
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class HomeFragment : BaseFragment() {
    private lateinit var bannerAdapter: ImageAdapter

    val articleList: MutableList<AriticleData> = ArrayList()

    private lateinit var ariticleAdapter: AriticleAdapter

    private var page: Int = 1
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(HomeViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        initBanner()
        initArticle()
    }

    override fun onResume() {
        super.onResume()
        banner_home.start()
    }

    override fun onStop() {
        super.onStop()
        banner_home.stop()
    }

    private fun initBanner() {
        viewModel.mBanners.observe(this,
            Observer<List<Data>> {
                bannerAdapter = ImageAdapter(context!!, viewModel.mBanners.value!!)
                banner_home.adapter = bannerAdapter
                banner_home.setIndicator(CircleIndicator(context))
                    .setOnBannerListener(object : OnBannerListener<Data> {
                        override fun OnBannerClick(data: Data?, position: Int) {
                            if (data != null) {
                                ArticleActivity.actionStart(
                                    activity as Context,
                                    data.title,
                                    data.url
                                )
                            }
                        }
                    }).start()
                bannerAdapter.notifyDataSetChanged()
            })

        viewModel.loadBanner()
    }

    private fun initArticle() {
        rlv_home.layoutManager = LinearLayoutManager(activity)
        ariticleAdapter = AriticleAdapter(R.layout.item_article, articleList)

        ariticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                ArticleActivity.actionStart(activity as Context, ariticleAdapter.data[position])
            }
        })
        ariticleAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.iv_collect->ToastUtil.showMessage("收藏")
            }
        }
        rlv_home.adapter = ariticleAdapter

        refresh_layout.setOnRefreshListener {
            page = 1
            viewModel.loadTopArticle()
        }
        refresh_layout.setOnLoadMoreListener {
            page++
            viewModel.loadAriticle(page)
        }
        viewModel.mTopAriticles.observe(this,
            Observer<List<AriticleData>> { t ->
                refresh_layout.finishRefresh()
                ariticleAdapter.setList(t.toMutableList())
            })
        viewModel.mArticles.observe(this,
            Observer<List<AriticleData>> { t ->
                if (page == 1) {
                    refresh_layout.finishRefresh()
                    ariticleAdapter.setList(t)
                } else {
                    refresh_layout.finishLoadMore()
                    ariticleAdapter.addData(t!!)
                    ariticleAdapter.notifyDataSetChanged()
                }
            })
        viewModel.loadTopArticle()

    }
}