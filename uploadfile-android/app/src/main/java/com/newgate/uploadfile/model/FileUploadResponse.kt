package com.newgate.uploadfile.model

import com.google.gson.annotations.SerializedName

/**
 * Created by apple on 2/21/18.
 */
data class FileUploadResponse(
        @SerializedName("message") var message: String,
        @SerializedName("data") var data: FileUploadData)

data class FileUploadsResponse(
        @SerializedName("message") var message: String,
        @SerializedName("data") var data: ArrayList<FileUploadData>)

data class FileUploadData(
        @SerializedName("originalname") var originalname: String,
        @SerializedName("uploadname") var uploadname: String)

data class FileUpload(
        var name: String,
        var progress: Int,
        var uploadname: String = "",
        var displayViewDetail: Boolean = false,
        var beginUpdate: Boolean = false)