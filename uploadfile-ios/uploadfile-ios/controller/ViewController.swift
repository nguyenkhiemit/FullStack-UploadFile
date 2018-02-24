//
//  ViewController.swift
//  uploadfile-ios
//
//  Created by Nguyen on 2/23/18.
//  Copyright © 2018 Apple. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var arrayFileUpload: Array<FileUpload> = Array()

    let helper = ViewControllerHelper()

    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.delegate = self
        self.tableView.dataSource = self
        let nib = UINib(nibName: "FileUploadViewCell", bundle: nil)
        self.tableView.register(nib, forCellReuseIdentifier: "FileUploadViewCell")
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    @IBAction func openPhotoLibrary(_ sender: Any) {
        if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
            let pickerController = UIImagePickerController()
            pickerController.delegate = self
            pickerController.sourceType = .photoLibrary
            pickerController.allowsEditing = false
            self.present(pickerController, animated: true, completion: nil)
        }
    }

    @IBAction func touchUploadAllButton(_ sender: Any) {
        var arrayUpload: Array<FileUpload> = Array()
        for fileUpload in arrayFileUpload {
            if fileUpload.url == nil {
                arrayUpload.append(fileUpload)
            }
        }
        helper.uploadAll(fileUploads: arrayUpload, completion: {
            [weak self]
            error, result, arrayData in
            if let arrayData = arrayData {
                for i in 0 ..< (self?.arrayFileUpload.count)! {
                    for j in 0 ..< arrayData.count {
                        if self?.arrayFileUpload[i].name! == arrayData[j].originalname! {
                            self?.arrayFileUpload[i].url = arrayData[j].uploadname
                        }
                    }
                }

            }
            self?.tableView.reloadData()
        }, progressLoad: {
            progress in
        })
    }
}
extension ViewController: UITableViewDataSource, UITableViewDelegate {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayFileUpload.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FileUploadViewCell", for: indexPath) as! FileUploadViewCell
        cell.fillData(fileUpload: arrayFileUpload[indexPath.item])
        if arrayFileUpload[indexPath.item].url != nil {
            cell.displayViewDetailButton()
            cell.loadFullProgress()
        }
        // click button upload image
        cell.uploadFile = {
            [weak self] in
            self?.helper.upload(fileUpload: (self?.arrayFileUpload[indexPath.item])!, completion: {
                error, result, data in
                if(data != nil) {
                    self?.arrayFileUpload[indexPath.item].url = data?.uploadname
                    cell.displayViewDetailButton()
                }
            }, progressLoad: {
                progress in
                cell.loadProgress(progress: progress)
            })
        }
        // click button view detail image from url
        cell.openViewDetailImage = {
            [weak self] in
            let viewController = UIStoryboard.init(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "DetailImageViewController") as! DetailImageViewController
            viewController.url = self?.arrayFileUpload[indexPath.item].url
            self?.present(viewController, animated: true, completion: nil)
        }
        return cell
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 108
    }
}
extension ViewController: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        if let image = info[UIImagePickerControllerOriginalImage] as? UIImage {
            arrayFileUpload.append(FileUpload(image: image, name: "\(Date().timeIntervalSince1970)image.jpg", progress: 0.0))
            tableView.reloadData()
        }
        self.dismiss(animated: true, completion: nil)
    }

    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        self.dismiss(animated: true, completion: nil)
    }
}

