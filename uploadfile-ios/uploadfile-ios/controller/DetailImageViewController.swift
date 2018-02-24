//
//  DetailImageViewController.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/24/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import UIKit
import Kingfisher

class DetailImageViewController: UIViewController {

    @IBOutlet weak var detailImageView: UIImageView!

    var url: String? = nil

    override func viewDidLoad() {
        super.viewDidLoad()
        if let url = url {
            let url = URL(string: URLs.main + url)
            detailImageView.kf.setImage(with: url)
        }
    }

    @IBAction func touchBackButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }


}
