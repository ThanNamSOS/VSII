package vn.constants;

import vn.claim.capture.model.JsonObject;

public class Constants {
    public static final String CLAIM_CAPTURE_PWD_SECRET = "CLAIM_CAPTURE_PWD_SECRET";
    public static final String JASYPT_PASS = "claim-capture";
    public static final String JASYPT_ALGORITHM = "PBEWithMD5AndDES";
    public static final String JASYPT_GENERATORCLASSNAME = "org.jasypt.salt.RandomSaltGenerator";
    public static final String JASYPT_TYPE = "base64";
    public static final String JASYPT_NAME_PROVIDER = "SunJCE";
    public static final String SOURCE = "PPX";
    public static final String SOURCE_ = "source";

    public static final String policyNumber = "policyNumber";

    public static final String lifeNo = "lifeNo";

    public static final String clientNumber = "clientNumber";

    public static final String mode = "mode";
    public static final String claimRequestId = "claimRequestId";
    public static final String attachments = "attachments";
    public static final String SUCCESS = "Xử lý yêu cầu thành công";
    public static final String MESSAGE = "success";
    public static final String DATA = "Dữ liệu đã nhập của trường ";
    public static final String invalid = " không hợp lệ";
    public static final String FORM_ID_VALIDATE = "Form ID không được để trống";
    public static final String submissionTimestamp = "submissionTimestamp";
    public static final String startDate = "startDate";
    public static final String endDate = "endDate";
    public static final String description = "description";
    public static final String accountOwnerName = "accountOwnerName";
    public static final String receiverCidIssuePlace = "receiverCidIssuePlace";
    public static final String receiverName = "receiverName";
    public static final String name = "name";
    public static final String benCode = "benCode";
    public static String formId = "formId";
    public static String followUpCode = "followUpCode";

    public static String ownClientId = "clientId";
    public static String ownClientNumber = "clientNumber";
    public static String ownCitizenId = "citizenId";
    public static String diagnosticCode = "code";
    public static String paymentInfoMode = "mode";
    public static String receiverCidIssueDate = "receiverCidIssueDate";
}
