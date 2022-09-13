package vn.claim.capture.model;

import lombok.Data;

@Data
public class ClaimModel {
    private String application;
    private String policyNumber;

    @Override
    public String toString() {
        return "ClaimModel{" +
                "application='" + application + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                '}';
    }
}
