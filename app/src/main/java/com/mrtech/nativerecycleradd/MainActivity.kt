package com.mrtech.nativerecycleradd

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.mrtech.nativerecycleradd.adapter.NativeAdapter
import com.mrtech.nativerecycleradd.decoration.RecyclerDecoration
import com.mrtech.nativerecycleradd.model.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    companion object {
        // The number of native ads to load.
        const val NO_OF_ADD = 5
    }

    // The AdLoader used to load ads.
    private var adLoader: AdLoader? = null

    // List of MenuItems and native ads that populate the RecyclerView.
    private var mRecyclerViewItems: MutableList<Any> = ArrayList()

    // List of native ads that have been successfully loaded.
    private val mNativeAds: MutableList<UnifiedNativeAd> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) { }

        // RecyclerView Design.
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = GridLayoutManager(this,1)
        recyclerView.addItemDecoration(
            RecyclerDecoration(
                10, true
            )
        )
        val adapter = NativeAdapter(this, recyclerItems())
        recyclerView.adapter = adapter

        // Don't forget to add this line otherwise add will not display
        adapter.notifyDataSetChanged()

    }

    private fun recyclerItems(): List<Any> {
        // insert menuItem
        menuItem()

        val builder = AdLoader.Builder(this,resources.getString(R.string.native_ad_unit))
        adLoader = builder.forUnifiedNativeAd(
            UnifiedNativeAd.OnUnifiedNativeAdLoadedListener { unifiedNativeAd ->
                if(isDestroyed){
                    unifiedNativeAd.destroy()
                    return@OnUnifiedNativeAdLoadedListener
                }
                mNativeAds.add(unifiedNativeAd)
                if (!adLoader!!.isLoading) {
                    try {
                        if (mRecyclerViewItems.size >= 6) {
                            if (mNativeAds.size <= 0) {
                                return@OnUnifiedNativeAdLoadedListener
                            }
                            val offset: Int =
                                mRecyclerViewItems.size / mNativeAds.size + 1
                            var index = 6
                            for (ad in mNativeAds) {
                                mRecyclerViewItems.add(index, ad)
                                index = index.plus(offset)
                            }
                        } else {
                            val offset: Int =
                                mRecyclerViewItems.size / mNativeAds.size + 1
                            var index = 0
                            for (ad in mNativeAds) {
                                mRecyclerViewItems.add(index, ad)
                                index += offset
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }).withAdListener(
            object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {}
            }).build()

        // Load the Native ads.
        adLoader!!.loadAds(AdRequest.Builder().build(),NO_OF_ADD)

        return mRecyclerViewItems
    }

    private fun menuItem() {
        try {
            val jsonDataString = readJsonDataFromFile()
            val menuItemsJsonArray = JSONArray(jsonDataString)
            for (i in 0 until menuItemsJsonArray.length()) {
                val menuItemObject = menuItemsJsonArray.getJSONObject(i)
                val menuItemName = menuItemObject.getString("name")
                val menuItemDescription = menuItemObject.getString("description")
                val menuItemPrice = menuItemObject.getString("price")
                val menuItemCategory = menuItemObject.getString("category")
                val menuItemImageName = menuItemObject.getString("photo")
                val menuItem = MenuItem(
                    menuItemName, menuItemDescription, menuItemPrice,
                    menuItemCategory, menuItemImageName
                )
                mRecyclerViewItems.add(menuItem)
            }
        } catch (exception: IOException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }

    @Throws(IOException::class)
    private fun readJsonDataFromFile(): String? {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonDataString: String?
            inputStream = this.resources.assets.open("menu.json")
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonDataString = it } != null) {
                builder.append(jsonDataString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }
}
