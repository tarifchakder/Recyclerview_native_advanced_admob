/*
 * Created by Tarif Chakder at 6/12/20, 8:52 AM
 * Copyright © MR Tech 2020
 */

package com.mrtech.nativerecycleradd.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.mrtech.nativerecycleradd.R


class AddViewHolder internal constructor(view: View) : ViewHolder(view) {

    val adView: UnifiedNativeAdView = view.findViewById<View>(R.id.ad_view) as UnifiedNativeAdView

    init {
        // The MediaView will display a video asset if one is present in the ad, and the
        // first image asset otherwise.
        adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView

        // Register the view used for each individual asset.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
    }
}