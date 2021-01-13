package com.luuuzi.wanandroid.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luuuzi.common.view.BaseFragment
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.Data
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *author:
 *Date: 2021-01-06
 *description:
 */
class HomeFragment : BaseFragment() {
    private lateinit var bannerAdapter: ImageAdapter
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
}