package com.luuuzi.wanandroid.home

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luuuzi.wanandroid.bean.Data
import com.youth.banner.adapter.BannerAdapter

/**
 *author: Luuuzi
 *Date: 2021-01-12
 *description:banner适配器
 */
class ImageAdapter(private val mContext: Context, mData: List<Data>) :
    BannerAdapter<Data, ImageAdapter.BannerViewHolder>(mData) {
    inner class BannerViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {
        public var imageView: ImageView = itemView
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: Data?, position: Int, size: Int) {
        Glide.with(mContext).load(if (data?.filePath == null) data?.imagePath else data.filePath)
            .into(holder!!.imageView)
    }
}