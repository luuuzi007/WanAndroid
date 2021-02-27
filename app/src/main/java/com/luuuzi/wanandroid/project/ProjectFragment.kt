package com.luuuzi.wanandroid.project

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.Classify
import com.luuuzi.wanandroid.project.list.ProjectListFragment
import kotlinx.android.synthetic.main.fragment_project.*

/**
 *author:
 *Date: 2021-01-06
 *description:项目模块
 */
class ProjectFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(ProjectViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {
        val titles = mutableListOf<String>()
        val fragments = mutableListOf<Fragment>()
        viewModel.mClassifyBean.observe(this, object : Observer<List<Classify>> {
            override fun onChanged(t: List<Classify>?) {
                t?.forEach {
                    titles.add(it.name)
                    fragments.add(ProjectListFragment.newInstance(it.id))

                }
                val fragmentAdapter = FragmentAdapter(fragmentManager)
                fragmentAdapter.reset(fragments)
                fragmentAdapter.reset(titles.toTypedArray())

                view_pager.adapter = fragmentAdapter
                tablayout.setViewPager(view_pager)
            }
        })
        viewModel.getClassfly()
    }
}