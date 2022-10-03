package com.hunter.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//有参
@NoArgsConstructor//无参
public class ConsumeException extends RuntimeException {
    private Integer code;
    private String msg;
}
