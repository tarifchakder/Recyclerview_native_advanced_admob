/*
 * Created by Tarif Chakder at 6/12/20, 8:49 AM
 * Copyright © MR Tech 2020
 */

package com.mrtech.nativerecycleradd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.mrtech.nativerecycleradd.R
import com.mrtech.nativerecycleradd.model.MenuItem
import com.mrtech.nativerecycleradd.viewholder.AddViewHolder
import com.mrtech.nativerecycleradd.viewholder.MenuViewHolder


/**
 * The {@link NativeAdapter} class.
 * <p>The adapter provides access to the items in the {@link MenuItemViewHolder}
 */
class NativeAdapter (
    private val context : Context,
    private val recyclerList : List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MENU_ITEM = 1
        const val ADD_VIEW = 0
    }

    /**
     * Determines the view type for the given position.
     */
    override fun getItemViewType(position: Int): Int {
        val viewType = recyclerList[position]
        if (viewType is UnifiedNativeAd) {
            return ADD_VIEW
        }
        return MENU_ITEM
    }

    /**
     * Creates a new view for a menu item view or a Native ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ADD_VIEW -> {
                val adLayout: View = LayoutInflater.from(context
                ).inflate(
                    R.layout.model_adview,
                    parent, false
                )
                AddViewHolder(adLayout)
            }
            MENU_ITEM -> {
                val menuLayout: View =
                    LayoutInflater.from(context).inflate(
                        R.layout.model_men_item, parent, false
                    )
                MenuViewHolder(menuLayout)
            }
            else -> {
                val menuLayout: View =
                    LayoutInflater.from(context).inflate(
                        R.layout.model_men_item, parent, false
                    )
                MenuViewHolder(menuLayout)
            }
        }
    }

    override fun getItemCount(): Int = recyclerList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ADD_VIEW -> {
                val nativeAd = recyclerList[position] as UnifiedNativeAd
                populateNativeAdView(nativeAd, (holder as AddViewHolder).adView)
            }
            MENU_ITEM -> {
                val menuItemHolder: MenuViewHolder = holder as MenuViewHolder
                val menuItem: MenuItem = recyclerList[position] as MenuItem
                // Add the menu item details to the menu item view
                menuItemHolder.menu_item_name.text = menuItem.name
                menuItemHolder.menu_item_price.text = menuItem.price
                menuItemHolder.menu_item_category.text = menuItem.category
                menuItemHolder.menu_item_description.text = menuItem.description
            }
            else -> {
                val menuItemHolder: MenuViewHolder = holder as MenuViewHolder
                val menuItem: MenuItem = recyclerList[position] as MenuItem
                // Add the menu item details to the menu item view
                menuItemHolder.menu_item_name.text = menuItem.name
                menuItemHolder.menu_item_price.text = menuItem.price
                menuItemHolder.menu_item_category.text = menuItem.category
                menuItemHolder.menu_item_description.text = menuItem.description
            }
        }
    }

    private fun populateNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.bodyView as TextView).text = nativeAd.body
        (adView.callToActionView as Button).text = nativeAd.callToAction

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        val icon = nativeAd.icon
        if (icon == null) {
            adView.iconView.visibility = View.INVISIBLE
        } else {
            (adView.iconView as ImageView).setImageDrawable(icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }
        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }
        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }
        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }
        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd)
    }

}