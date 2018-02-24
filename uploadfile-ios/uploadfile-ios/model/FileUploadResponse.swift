//
//  FileUploadData.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/24/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import UIKit
import SwiftyJSON

class FileUploadResponse: NSObject {
    var message: String?

    var data: FileUploadData?

    init(json: JSON) {
        message = json["message"].string
        data = FileUploadData(json: json["data"])
    }
}

class FileUploadArrayResponse: NSObject {
    var message: String?

    var datas: Array<FileUploadData>?

    init(json: JSON) {
        message = json["message"].string
        if let dataJsons = json["data"].array {
            datas = Array()
            for dataJson in dataJsons {
                let data = FileUploadData(json: dataJson)
                datas?.append(data)
            }
        }
    }
}

class FileUploadData: NSObject {
    var originalname: String?

    var uploadname: String?

    init(json: JSON) {
        originalname = json["originalname"].string
        uploadname = json["uploadname"].string
    }
}
