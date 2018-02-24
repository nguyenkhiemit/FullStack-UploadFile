//
//  ViewControllerHelper.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/23/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import Foundation
import UIKit
import Alamofire
import SwiftyJSON

class ViewControllerHelper {
    func upload(fileUpload: FileUpload, completion: @escaping (_ error: Error?, _ success: Bool, _ data: FileUploadData?)->Void, progressLoad: @escaping (_ progress: Double)->Void) {
        let url = URLs.upload
        Alamofire.upload(multipartFormData: { multipartFormData in
            if let imageData = UIImageJPEGRepresentation(fileUpload.image!, 0.5) {
                multipartFormData.append(imageData, withName: "file", fileName: fileUpload.name!, mimeType: "image/jpg")
            }
        }, to: url) { encodingResult in
            switch encodingResult {
            case .failure(let error):
                completion(error, false, nil)
            case .success(request: let upload, streamingFromDisk: _, streamFileURL: _):
                upload.uploadProgress(closure: { progress in
                    progressLoad(progress.fractionCompleted)
                }).responseJSON(completionHandler: { response in
                    switch response.result {
                    case .failure(let error):
                        completion(error, false, nil)
                    case .success(let value):
                        let json = JSON(value)
                        let dataResponse = FileUploadResponse(json: json)
                        if let message = dataResponse.message, message == "success"  {
                            completion(nil, true, dataResponse.data)
                        } else {
                            completion(nil, false, nil)
                        }
                    }
                })
            }
        }
    }

    func uploadAll(fileUploads: Array<FileUpload>, completion: @escaping (_ error: Error?, _ success: Bool, _ data: Array<FileUploadData>?)->Void, progressLoad: @escaping (_ progress: Double)->Void) {
        let url = URLs.uploads
        Alamofire.upload(multipartFormData: { multipartFormData in
            for fileUpload in fileUploads {
                if let imageData = UIImageJPEGRepresentation(fileUpload.image!, 0.5) {
                    multipartFormData.append(imageData, withName: "file", fileName: fileUpload.name!, mimeType: "image/jpg")
                }
            }
        }, to: url) { encodingResult in
            switch encodingResult {
            case .failure(let error):
                completion(error, false, nil)
            case .success(request: let upload, streamingFromDisk: _, streamFileURL: _):
                upload.uploadProgress(closure: { progress in
                    progressLoad(progress.fractionCompleted)
                }).responseJSON(completionHandler: { response in
                    switch response.result {
                    case .failure(let error):
                        completion(error, false, nil)
                    case .success(let value):
                        let json = JSON(value)
                        let dataResponse = FileUploadArrayResponse(json: json)
                        if let message = dataResponse.message, message == "success" {
                            completion(nil, true, dataResponse.datas)
                        } else {
                            completion(nil, false, nil)
                        }
                    }
                })
            }
        }
    }
}

