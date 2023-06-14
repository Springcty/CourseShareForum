package com.software.blogcommon.entity;

import com.software.blogcommon.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.software.blogcommon.enums.CodeEnum.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public static Result ok(String message, Object data){
        return new Result(true, SUCCESS.getCode(), message, data);
    }
    public static Result ok(String message){
        return new Result(true, SUCCESS.getCode(), message, null);
    }

    public static Result fail(String message, Object data){ return new Result(false, FAIL.getCode(), message, data);}
    public static Result fail(String message){
        return new Result(false, FAIL.getCode(), message, null);
    }
    public static Result fail(String message, Integer code){
        return new Result(false, code, message, null);
    }
    public static Result fail(CodeEnum code){ return new Result(false, code.getCode(), code.getDescipt(), null);}
}
