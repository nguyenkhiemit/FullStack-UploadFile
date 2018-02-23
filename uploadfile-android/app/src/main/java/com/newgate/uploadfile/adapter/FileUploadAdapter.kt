package com.newgate.uploadfile.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newgate.uploadfile.R
import com.newgate.uploadfile.activity.DetailActivity
import com.newgate.uploadfile.helper.MainActivityHelper
import com.newgate.uploadfile.model.FileUpload
import kotlinx.android.synthetic.main.activity_detail_main.view.*
import kotlinx.android.synthetic.main.layout_item_load.view.*

/**
 * Created by apple on 2/21/18.
 */
class FileUploadAdapter(private val context: Context, private var arrayUploadFile: ArrayList<FileUpload>, val listener: SelectedFileListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_load, parent, false)
        return UploadViewHoler(view)
    }

    override fun getItemCount(): Int {
        return arrayUploadFile.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is UploadViewHoler) {
            holder.fillData(position)
        }
    }

    inner class UploadViewHoler(var view: View): RecyclerView.ViewHolder(view) {

        fun fillData(position: Int) {
            if(arrayUploadFile[position].beginUpdate) {
                view.uploadFileProgress.loadData(0.0f, 30.0f)
                arrayUploadFile[position].beginUpdate = false
            }
            if(arrayUploadFile[position].displayViewDetail) {
                view.viewDetailButton.visibility = View.VISIBLE
                view.uploadFileProgress.loadFullData()
            }
            view.nameFileTextView.text = arrayUploadFile[position].name
            view.uploadFileProgress.progress = arrayUploadFile[position].progress
            view.uploadButton.setOnClickListener {
                if(arrayUploadFile[position].progress < 100) {
                    listener?.selectedFile(position)
                    view.uploadFileProgress.loadData(0.0f, 30.0f)
                }
            }
            view.viewDetailButton.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("url_image", arrayUploadFile[position].uploadname)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
            view.cancelButton.setOnClickListener {
                listener?.cancelUploadFile(position)
            }
        }
    }

    interface SelectedFileListener {
        fun selectedFile(position: Int)
        fun cancelUploadFile(position: Int)
    }
}