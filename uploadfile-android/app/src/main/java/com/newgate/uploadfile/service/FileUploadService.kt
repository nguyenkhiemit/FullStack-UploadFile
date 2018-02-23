package com.newgate.uploadfile.service

import com.newgate.uploadfile.model.FileUpload
import com.newgate.uploadfile.model.FileUploadResponse
import com.newgate.uploadfile.model.FileUploadsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by apple on 2/21/18.
 */
interface FileUploadService {
    @Multipart
    @POST("file/upload") fun uploadFile(/*@Part("description") description: RequestBody,*/ @Part file: MultipartBody.Part): Call<FileUploadResponse>

    @Multipart
    @POST("file/uploads") fun uploadFilesDynamic(@Part files: ArrayList<MultipartBody.Part>): Call<FileUploadsResponse>
}