package com.newgate.uploadfile.helper

import com.newgate.uploadfile.model.FileUploadData
import com.newgate.uploadfile.model.FileUploadResponse
import com.newgate.uploadfile.model.FileUploadsResponse
import com.newgate.uploadfile.service.ApiClient
import com.newgate.uploadfile.service.FileUploadService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created by apple on 2/22/18.
 */
class MainActivityHelper {

    var call: Call<FileUploadResponse>? = null
    var calls: Call<FileUploadsResponse>? = null

    interface OnUpdateFileListener {
        fun updateFileSuccess(data: FileUploadData)
        fun updateFileFailure()
    }

    interface OnUpdateAllFileListener {
        fun uploadFilesSuccess(arrayData: ArrayList<FileUploadData>)
        fun updateFileFailure()
    }

    fun uploadFile(filePath: String, listener: OnUpdateFileListener?) {
        var body = prepareFilePart(filePath)
        var name = RequestBody.create(MediaType.parse("text/plain"), "file")

        val service =  ApiClient.getInstance().getClient().create(FileUploadService::class.java)
        call = service.uploadFile(body)
        call?.enqueue(object: Callback<FileUploadResponse> {
            override fun onResponse(call: Call<FileUploadResponse>?, response: Response<FileUploadResponse>?) {
                val fileUploadResponse = response?.body()
                if(fileUploadResponse != null) {
                    listener?.updateFileSuccess(fileUploadResponse.data)
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>?, t: Throwable?) {
                listener?.updateFileFailure()
            }
        })
    }

    fun cancelUploadFile() {
        if(call != null) {
            call!!.cancel()
        }
    }

    fun uploadFiles(filePaths: ArrayList<String>, listener: OnUpdateAllFileListener?) {
        var parts = ArrayList<MultipartBody.Part>()
        for(i in filePaths.indices) {
            parts.add(prepareFilePart(filePaths[i]))
        }
        val service =  ApiClient.getInstance().getClient().create(FileUploadService::class.java)
        calls = service.uploadFilesDynamic(parts)
        calls?.enqueue(object: Callback<FileUploadsResponse> {
            override fun onResponse(call: Call<FileUploadsResponse>?, response: Response<FileUploadsResponse>?) {
                val fileUploadsResponse = response?.body()
                if(fileUploadsResponse != null) {
                    listener?.uploadFilesSuccess(fileUploadsResponse.data)
                }
            }

            override fun onFailure(call: Call<FileUploadsResponse>?, t: Throwable?) {
            }
        })
    }

    fun cancelUploadFiles() {
        if(calls != null) {
            calls!!.cancel()
        }
    }

    fun prepareFilePart(filePath: String) : MultipartBody.Part {
        var file = File(filePath)
        var reqFile = RequestBody.create(MediaType.parse("image*/"), file)
        //name of file in MultipartBody must same name in multer single
        var body = MultipartBody.Part.createFormData("file", file.name, reqFile)
        return body
    }
}