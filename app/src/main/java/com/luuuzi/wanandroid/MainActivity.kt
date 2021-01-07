package com.luuuzi.wanandroid

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.luuuzi.common.view.BaseActivity
import com.luuuzi.wanandroid.home.HomeFragment
import com.luuuzi.wanandroid.my.MyFragment
import com.luuuzi.wanandroid.project.ProjectFragment
import com.luuuzi.wanandroid.public.ThePublicFragment

class MainActivity : BaseActivity(), View.OnClickListener {
    private var testviews: ArrayList<AppCompatTextView>? = null

    private var mFragments: ArrayList<Fragment>? = null
    private var mFragmentManager: FragmentManager? = null
    private val mHomeFragment: HomeFragment by lazy { HomeFragment.newInstance() }
    private val mProjectFragment: ProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mPublicFragment: ThePublicFragment by lazy { ThePublicFragment.newInstance() }
    private val mMyFragment: MyFragment by lazy { MyFragment.newInstance() }
    private var currentFragment: Fragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        testviews = arrayListOf(
            findViewById(R.id.tv_home),
            findViewById(R.id.tv_project),
            findViewById(R.id.tv_the_public),
            findViewById(R.id.tv_my)
        )
    }

    override fun initData() {
        mFragments = arrayListOf(mHomeFragment, mProjectFragment, mPublicFragment, mMyFragment)
        mFragmentManager = supportFragmentManager
        testviews?.let {
            for (tv in it) {
                tv.setOnClickListener(this)
            }
        }
        switchFragment(0)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_home -> switchFragment(0)
            R.id.tv_project -> switchFragment(1)
            R.id.tv_the_public -> switchFragment(2)
            R.id.tv_my -> switchFragment(3)
        }
    }

    //销毁，避免内存泄漏
    override fun onDestroy() {
        super.onDestroy()
        if (!testviews.isNullOrEmpty()) {
            testviews?.clear()
            testviews = null
        }
    }

    /**
     * fragment的切换 实现底部导航栏的切换
     *
     * @param position 序号
     */
    private fun switchFragment(position: Int) {
        switchTextView(position)
        val targetFg: Fragment = mFragments!![position]
        val transaction = mFragmentManager!!.beginTransaction()
        transaction.apply {
            if (currentFragment != null) {
                hide(currentFragment!!)
            }
            setReorderingAllowed(true)
            if (!targetFg.isAdded) {
                add(R.id.frl_main, targetFg).commit()
            } else {
                show(targetFg).commit()
            }
        }
        currentFragment = targetFg
    }

    //切换textview
    private fun switchTextView(position: Int) {
        for (i in testviews!!.indices) {
            testviews!![i].isSelected = position == i
        }
    }
}