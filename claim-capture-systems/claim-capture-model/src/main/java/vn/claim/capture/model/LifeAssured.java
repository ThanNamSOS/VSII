package vn.claim.capture.model;

import lombok.Data;

import java.util.List;

@Data
public class LifeAssured {
    private String clientId="";
    private String clientNumber="";
    private String lifeNo="";
    private String name="";
    private String citizenId="";
    private List<ClaimBenefit> claimBenefits;
}
//"lifeAssured": {
//        "clientId": "dsf32432432fsdfds3243", // mã tự sinh trên CIV
//        "clientNumber": "10005458", // tương ứng với customers.customerId
//        "lifeNo": "01",
//        "name": "Life Assured 1",
//        "citizenId": "0823-24343-32432432",
//        "claimBenefits": [{
//        "benCode": "BEN01", // mã nhóm quyền lợi - anh Hưng VV tự định nghĩa
//        "startDate": "2022-05-06", // ngày vào viện / ngày khám / ngày điều trị
//        "endDate": "2022-05-18", // ngày kết thúc điều trị
//        // international classification of deseas
//        "icd": {
//        "code": "OTHER", // mã chuẩn đoán
//        "description": "Chuẩn đoán bị lang ben hắc lào" // chi tiết chuẩn đoán
//        },
//        "attachments": [{
//        "name": "file1.pdf", // tên file đính kèm
//        "contentType": "application/pdf", // loại file đính kèm
//        "data": "e343dfr432wew3433", // dữ liệu file đính kèm
//        "formId": "CLM0001", // mã tài liệu do IWS định nghĩa.
//        "localPath": ""
//        },{
//        "name": "file2.pdf",
//        "contentType": "application/pdf",
//        "formId": "CLM0002",
//        "data": "e343dfr432wew3433",
//        "formId": "CLM0002", // mã tài liệu do IWS định nghĩa.
//        "localPath": ""
//        }]
//        },{
//        "benCode": "COVR02",
//        "startDate": "2022-05-06",
//        "endDate": "2022-05-18",
//        "icd": {
//        "code": "OTHER",
//        "description": "Chuẩn đoán bị lang ben hắc lào"
//        },
//        "attachments": [{
//        "name": "file1.pdf",
//        "contentType": "application/pdf",
//        "data": "e343dfr432wew3433",
//        "formId": "CLM0001",
//        "localPath": ""
//        },{
//        "name": "file2.pdf",
//        "contentType": "application/pdf",
//        "data": "e343dfr432wew3433",
//        "formId": "CLM0002", // mã tài liệu do IWS định nghĩa.
//        "localPath": ""
//        }]
//        }]
//        }