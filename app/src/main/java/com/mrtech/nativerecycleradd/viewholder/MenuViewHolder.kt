/*
 * Created by Tarif Chakder at 6/12/20, 9:20 AM
 * Copyright © MR Tech 2020
 */

package com.mrtech.nativerecycleradd.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrtech.nativerecycleradd.R

/**
 * The {@link MenuViewHolder} class.
 * Provides a reference to each view in the menu item view.
 */
class MenuViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val menu_item_name = view.findViewById<TextView>(R.id.menu_item_name)
    val menu_item_price = view.findViewById<TextView>(R.id.menu_item_price)
    val menu_item_category = view.findViewById<TextView>(R.id.menu_item_category)
    val menu_item_description = view.findViewById<TextView>(R.id.menu_item_description)
}