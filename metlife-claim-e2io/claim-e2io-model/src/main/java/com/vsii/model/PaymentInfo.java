package com.vsii.model;

import lombok.Data;

@Data
public class PaymentInfo {
    private String mode="";
    private String policyNumber="";
    private String accountOwnerName="";
    private String accountNumber="";
    private String bank="";
    private String receiverName="";
    private String receiverCitizenId="";
    private String receiverCidIssueDate="";
    private String receiverCidIssuePlace="";
    private String receiverBank="";
}
//"mode": "01", // 01 = chuyển đóng phí cho hợp đồng bảo hiểm
//        // 02 = chuyển tiền vào tài khoản tại ngân hàng
//        // 03 = nhận tiền mặt tại quầy
//        "policyNumber": "80008735", // chỉ có với mode = 01. Quy tắc truy vấn ra danh sách hợp đồng: cùng owner ở trên + (trạng thái HĐ?)
//        "accountOwnerName": "Nguyễn Văn A", // với mode = 02
//        "accountNumber": "077324324322145", // với mode = 02
//        "bank": "BIDV",                     // với mode = 02
//        "receiverName": "Nguyễn Văn B",           // với mode = 03
//        "receiverCitizenId": "432432432432432",   // với mode = 03
//        "receiverCidIssueDate": "1998-06-30",     // với mode = 03
//        "receiverCidIssuePlace": "Nơi cấp - HN",  // với mode = 03
//        "receiverBank": "Vietin Bank"             // với mode = 03
