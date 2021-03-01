package com.luuuzi.wanandroid.public.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.article.ArticleActivity
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.home.AriticleAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_project_list.*

/**
 *@author: Luuuzi
 *@Date: 2021-02-27
 *@description: 公众号列表
 */

const val ID = "id"
const val TYPE = "type"

class PublicListFragment : BaseFragment() {
    private var articleList: MutableList<AriticleData> = ArrayList()
    private var page: Int = 0
    private var cId: Int = 0

    companion object {
        fun newInstance(id: Int): PublicListFragment {
            val args = Bundle()
            args.putInt(ID, id)
            val fragment = PublicListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(PublicListViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.fragment_project_list
    override fun initView(rootView: View) {
        LogUtils.i("公众号initView：$cId")
    }

    override fun initData() {
//        LogUtils.i("公众号")
        cId = arguments!!.getInt(ID)
        rlv_project.layoutManager = LinearLayoutManager(activity)
        val ariticleAdapter = AriticleAdapter(R.layout.item_article, articleList)
        ariticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                ArticleActivity.actionStart(activity as Context, ariticleAdapter.data[position])
            }
        })
        refresh_layout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 0
                viewModel.getProjectList(page, cId)
            }

        })
        refresh_layout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                viewModel.getProjectList(page, cId)
            }
        })

        viewModel.mArticles.observe(this, object : Observer<List<AriticleData>> {
            override fun onChanged(t: List<AriticleData>?) {
                if (page == 0) {
                    ariticleAdapter.setList(t)
                    rlv_project.adapter = ariticleAdapter
                } else {
                    ariticleAdapter.addData(t!!)
                    ariticleAdapter.notifyDataSetChanged()
                }
            }
        })
//        LogUtils.i("cid:$cId")
        viewModel.getProjectList(page, cId)
    }
}