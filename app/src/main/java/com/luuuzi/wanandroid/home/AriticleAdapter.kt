package com.luuuzi.wanandroid.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.luuuzi.wanandroid.R
import com.luuuzi.wanandroid.bean.AriticleData

/**
 *author: Luuuzi
 *Date: 2021-01-14
 *description:文章列表adapter
 */
class AriticleAdapter(layoutId: Int, data: MutableList<AriticleData>) :
    BaseQuickAdapter<AriticleData, BaseViewHolder>(layoutId, data) {

    override fun convert(holder: BaseViewHolder, item: AriticleData) {
        holder.setText(R.id.tv_author, if (item.author.isEmpty()) item.shareUser else item.author)
            .setText(R.id.tv_time, item.niceShareDate)
            .setText(R.id.tv_title, item.title)
            .setText(R.id.tv_chapter_name, item.chapterName)
        addChildClickViewIds(R.id.iv_collect)
        holder.setVisible(R.id.tv_new, item.fresh)
        holder.setVisible(R.id.tv_top, item.type > 0)

        holder.setImageResource(
            R.id.iv_collect,
            if (item.collect) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
        )

    }
}