package vn.claim.capture.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseObject {
    private Integer code;
    private String message;
    private String description;
    private Object data;
}
