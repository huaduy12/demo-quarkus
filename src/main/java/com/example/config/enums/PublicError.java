package com.example.config.enums;

import lombok.Getter;

@Getter
public enum PublicError {
    SUCCESS("000","Success"),
    DATA_NOT_FOUND("001","Data not found"),
    DATA_ACCESS_ERROR("002","Data access error"),
    NULL_DATA("003","Null data"),
    BAD_REQUEST_DATA("004","Bad request data"),
    RECORD_ALREADY_EXISTS("005","Record already exists"),
    UNEXPECTED_ERROR("006","Unexpected error"),
    UNAUTHORIZED("007","Unauthorized"),
    FORBIDDEN("008","Forbidden"),
    ;

    private final String code;
    private final String desc;


    PublicError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PublicError fromCode(String code) {
        for (PublicError error : PublicError.values()) {
            if (error.code.equals(code)) {
                return error;
            }
        }
        return null;
    }
}
