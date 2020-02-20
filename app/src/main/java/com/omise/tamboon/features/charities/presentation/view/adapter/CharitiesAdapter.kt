package com.omise.tamboon.features.charities.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omise.tamboon.R
import com.omise.tamboon.core.extensions.getInflatedView
import com.omise.tamboon.core.extensions.loadImage
import com.omise.tamboon.core.misc.OneParamFunction
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import kotlinx.android.synthetic.main.item_charity.view.*

class CharitiesAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var charitiesList = mutableListOf<CharityEntity>()
    var onDonateClick :OneParamFunction<CharityEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return   CharitiesViewHolder(parent.getInflatedView(R.layout.item_charity))
    }

    override fun getItemCount(): Int {
        return charitiesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is CharitiesViewHolder){
           holder.bind(charitiesList[position])
       }
    }

    inner class CharitiesViewHolder (private var charityItemView :View): RecyclerView.ViewHolder(charityItemView){
        fun bind(charityEntity: CharityEntity) {

            with(charityItemView){
                imgCharity.loadImage(charityEntity.logoUrl)
                tvCharityName.text = charityEntity.name
                btnDonate.setOnClickListener {
                    onDonateClick?.invoke(charityEntity)
                }

            }
        }
    }


    fun addItems(charitiesList :MutableList<CharityEntity>){
        val lastIndex = itemCount
        this.charitiesList.addAll(charitiesList)
        notifyItemRangeInserted(lastIndex, charitiesList.size)
    }
}