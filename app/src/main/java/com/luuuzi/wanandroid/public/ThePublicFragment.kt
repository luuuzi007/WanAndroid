package com.luuuzi.wanandroid.public

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.Classify
import com.luuuzi.wanandroid.public.list.PublicListFragment
import kotlinx.android.synthetic.main.fragment_the_public.*

/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class ThePublicFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        ).get(ThePublicViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThePublicFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_the_public
    }

    override fun initData() {
        LogUtils.i("公众号initData")
        val titles = mutableListOf<String>()
        val fragments = mutableListOf<Fragment>()
        viewModel.mClassifyBean.observe(this,
            Observer<List<Classify>> { t ->
                t?.forEach {
                    titles.add(it.name)
                    fragments.add(PublicListFragment.newInstance(it.id))
                }
                val fragmentAdapter = FragmentAdapter2(fragmentManager)
                fragmentAdapter.reset(fragments)
                fragmentAdapter.reset(titles.toTypedArray())
                view_pager.adapter = fragmentAdapter
                tablayout.setViewPager(view_pager)
            })
        viewModel.getChapterClassfly()
    }
}