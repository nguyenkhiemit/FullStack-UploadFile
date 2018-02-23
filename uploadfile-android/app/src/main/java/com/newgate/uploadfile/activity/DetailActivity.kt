package com.newgate.uploadfile.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.newgate.uploadfile.R
import com.newgate.uploadfile.service.ApiClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_main.*

/**
 * Created by apple on 2/22/18.
 */
class DetailActivity: AppCompatActivity() {
    var urlImage: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_main)
        val bundle = intent.extras
        if(bundle.containsKey("url_image")) {
            urlImage = bundle.getString("url_image")
        }
        Log.e("XDetail_image", "urlImage = " + ApiClient.getInstance().BASE_URL + urlImage)
        Picasso.with(this).load(ApiClient.getInstance().BASE_URL + urlImage).into(detailImageView)
    }
}