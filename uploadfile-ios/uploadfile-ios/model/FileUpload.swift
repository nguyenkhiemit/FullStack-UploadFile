//
//  FileUpload.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/23/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import UIKit

class FileUpload: NSObject {
    var name: String? = nil
    var image: UIImage? = nil
    var progress: Float = 0
    var url: String? = nil

    init(image: UIImage? = nil, name: String, progress: Float) {
        self.image = image
        self.name = name
        self.progress = progress
    }
}
