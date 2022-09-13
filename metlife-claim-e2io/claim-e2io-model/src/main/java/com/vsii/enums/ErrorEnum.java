package com.vsii.enums;

public enum ErrorEnum {
    FOLDER_ERROR("001", "Folder Lỗi"), FILE_ERROR("002", "File lỗi"), FILENET_ERROR("003", "Filenet lỗi"),
    INTERNAL_ERROR("500", ""), APPLICATION_NO_NOT_FOUND("004", "Không tồn tại hồ sơ trên hệ thống"),
    STATUS_NOT_MATCH("005", "Status không phù hợp để thêm tài liệu"),
    INVALID_FOLDER_NAME("01", "Tên folder không đúng định dạng"),
    INVALID_FILE_EXTENSION("02", "File có extension chưa được khai báo"),
    INVALID_FORM_ID("03", "Form ID không tồn tại"),
    INVALID_FOLLOW_UP("04", "followupCode không tồn tại"),
    CLAIM_ID("05", "ClaimId không tồn tại"),
    BRANCH_NOT_FOUND("06", "Branch Code không tồn tại"),
    INVALID_FIELD_LENGTH("07", "Độ dài của trường không quá 64 ký tự"),
    INVALID_DATA("08", "Dữ liệu không phù hợp");
    private String errorCode;
    private String errorDesc;

    ErrorEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public static ErrorEnum fromValue(String errorCode) {
        try {
            for (ErrorEnum item : ErrorEnum.values()) {
                if (item.getErrorCode().equals(errorCode))
                    return item;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
