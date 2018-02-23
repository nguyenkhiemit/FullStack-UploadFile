package com.newgate.uploadfile.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.newgate.uploadfile.adapter.FileUploadAdapter
import com.newgate.uploadfile.R
import com.newgate.uploadfile.helper.FileHelper
import com.newgate.uploadfile.helper.MainActivityHelper
import com.newgate.uploadfile.model.FileUpload
import com.newgate.uploadfile.model.FileUploadData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val PICK_IMAGE = 100
    }

    lateinit var adapter: FileUploadAdapter

    //array display on listview
    var arrayUploadFile: ArrayList<FileUpload> = arrayListOf()

    //array string to upload file
    var arrayFilePath: ArrayList<String> = arrayListOf()

    val helper: MainActivityHelper by lazy {
        MainActivityHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = FileUploadAdapter(this, arrayUploadFile, object : FileUploadAdapter.SelectedFileListener {
            override fun cancelUploadFile(position: Int) {
                helper.cancelUploadFile()
            }

            override fun selectedFile(position: Int) {
                helper.uploadFile(arrayUploadFile[position].name, object: MainActivityHelper.OnUpdateFileListener {
                    override fun updateFileSuccess(data: FileUploadData) {
                        arrayFilePath.remove(arrayUploadFile[position].name)
                        arrayUploadFile[position].displayViewDetail = true
                        arrayUploadFile[position].progress = 100
                        arrayUploadFile[position].uploadname = data.uploadname
                        adapter.notifyDataSetChanged()
                    }

                    override fun updateFileFailure() {
                    }

                })
            }
        })
        uploadFileRecyclerView.layoutManager = LinearLayoutManager(this)
        uploadFileRecyclerView.adapter = adapter

        chooseFileButton.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
        }

        uploadAllButton.setOnClickListener {

            for(i in arrayUploadFile.indices) {
                arrayUploadFile[i].beginUpdate = true
                adapter.notifyDataSetChanged()
            }
            helper.uploadFiles(arrayFilePath, object: MainActivityHelper.OnUpdateAllFileListener {
                override fun updateFileFailure() {
                }

                override fun uploadFilesSuccess(arrayData: ArrayList<FileUploadData>) {
                    for(i in arrayUploadFile.indices) {
                        for(j in arrayData.indices) {
                            if(arrayUploadFile[i].name.contains(arrayData[j].originalname)) {
                                arrayUploadFile[i].displayViewDetail = true
                                arrayUploadFile[i].progress = 100
                                arrayUploadFile[i].uploadname = arrayData[j].uploadname
                            }
                        }
                    }
                    arrayFilePath.clear()
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val fileUri = data.data
            val filePath: String? = FileHelper.getInstance().getRealPath(this, fileUri)
            if(!TextUtils.isEmpty(filePath) && !arrayFilePath.contains(filePath)) {
                arrayFilePath.add(filePath!!)
                arrayUploadFile.add(FileUpload(filePath!!, 0))
                adapter.notifyDataSetChanged()
            }
        }
    }
}
