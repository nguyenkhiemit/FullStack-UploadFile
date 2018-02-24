//
//  FileUploadViewCell.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/23/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import UIKit

class FileUploadViewCell: UITableViewCell {

    var uploadFile: (() -> ())?

    var openViewDetailImage: (() -> ())?

    @IBOutlet weak var uploadImageView: UIImageView!

    @IBOutlet weak var uploadProgressView: UIProgressView!

    @IBOutlet weak var viewDetailButton: UIButton!

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

    func fillData(fileUpload: FileUpload) {
        uploadImageView.image = fileUpload.image
        uploadProgressView.progress = fileUpload.progress
    }

    func displayViewDetailButton() {
        viewDetailButton.isHidden = false
    }

    func loadProgress(progress: Double) {
        uploadProgressView.progress = Float(progress)
    }

    func loadFullProgress() {
        uploadProgressView.progress = 1
    }

    @IBAction func touchUploadButton(_ sender: Any) {
        uploadFile?()
    }

    @IBAction func touchCancelButton(_ sender: Any) {
    }

    @IBAction func touchViewDetailButton(_ sender: Any) {
        openViewDetailImage?()
    }

}
