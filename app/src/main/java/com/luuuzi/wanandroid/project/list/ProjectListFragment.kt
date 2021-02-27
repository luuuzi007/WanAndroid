package com.luuuzi.wanandroid.project.list

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.AriticleData
import com.luuuzi.wanandroid.home.AriticleAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_project_list.*

/**
 *@author: Luuuzi
 *@Date: 2021-02-27
 *@description:
 */
const val ID="id"
class ProjectListFragment : BaseFragment() {
    private var articleList: MutableList<AriticleData> = ArrayList()
    companion object{

        fun newInstance(id: Int): ProjectListFragment{
            val args = Bundle()
            args.putInt(ID,id)
            val fragment = ProjectListFragment()
            fragment.arguments = args
            return fragment
        }
    }
    val viewModel: ProjectListViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(ProjectListViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.fragment_project_list

    override fun initData() {
        rlv_project.layoutManager = LinearLayoutManager(activity)
        AriticleAdapter(R.layout.item_article, articleList)
        refresh_layout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {

            }

        })
        refresh_layout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {

            }
        })

        viewModel.mArticles.observe(this, object : Observer<List<AriticleData>> {
            override fun onChanged(t: List<AriticleData>?) {

            }

        })

    }
}