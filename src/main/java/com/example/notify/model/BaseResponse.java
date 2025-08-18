package com.example.notify.model;

import com.example.config.enums.PublicError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseResponse<T> {
    private String code;
    private String desc;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(PublicError.SUCCESS.getCode())
                .desc(PublicError.SUCCESS.getDesc())
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> errorData(PublicError error, T data) {
        return BaseResponse.<T> builder()
                .code(error.getCode())
                .desc(error.getDesc())
                .data(data)
                .build();
    }
}
