import { Component } from '@angular/core';
import {FileUploader} from "ng2-file-upload";

const url = 'http://localhost:3000/file/upload';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  uploader: FileUploader = new FileUploader({url: url});

  attachmentList: any = [];

  constructor() {
    this.uploader.onBeforeUploadItem = (item) => {
      item.withCredentials = false;
    };
    this.uploader.onCompleteItem = (item:any, response: any, status: any, headers: any) => {
      console.log(response);
      this.attachmentList.push(JSON.parse(response));
    }
  }
}
