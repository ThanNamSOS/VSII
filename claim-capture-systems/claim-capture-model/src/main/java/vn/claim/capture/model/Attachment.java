package vn.claim.capture.model;

import lombok.Data;

@Data
public class Attachment {
    private String name;
    private String contentType="";
    private String data="";
    private String formId;
    private String localParth="";
}
//"name": "file1.pdf", // tên file đính kèm
//        "contentType": "application/pdf", // loại file đính kèm
//        "data": "e343dfr432wew3433", // dữ liệu file đính kèm
//        "formId": "CLM0001", // mã tài liệu do IWS định nghĩa.
//        "localPath": ""