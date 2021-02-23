package com.luuuzi.wanandroid.home

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.bean.Data
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class HomeFragment : BaseFragment() {
    private lateinit var bannerAdapter: ImageAdapter

    val articleList = ArrayList<AriticleData>()

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

    override fun initView(rootView: View) {

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
                banner_home.setIndicator(CircleIndicator(context)).start()
                bannerAdapter.notifyDataSetChanged()
            })
        viewModel.loadBanner()
    }

    private fun initArticle() {
        rlv_home.layoutManager = LinearLayoutManager(activity)
        ariticleAdapter = AriticleAdapter(R.layout.item_article, articleList.toMutableList())

        rlv_home.adapter = ariticleAdapter

        refresh_layout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                viewModel.loadAriticle(page)
            }
        })
        refresh_layout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                viewModel.loadAriticle(page)
            }

        })
        viewModel.mTopAriticles.observe(this,
            Observer<List<AriticleData>> { t ->
                ariticleAdapter.setList(t.toMutableList())
            })
        viewModel.mArticles.observe(this, object : Observer<List<AriticleData>> {
            override fun onChanged(t: List<AriticleData>?) {
                if (page == 1) {
                    refresh_layout.finishRefresh()
                    ariticleAdapter.setList(t)
                } else {
                    refresh_layout.finishLoadMore()
                    articleList.addAll(t!!)
                    Log.i("aaa","size:${articleList.size}")
                    ariticleAdapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.loadTopArticle()

    }
}