package com.software.blogcommon.entity;

import com.software.blogcommon.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.software.blogcommon.enums.CodeEnum.SUCCESS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResult {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;
    private Object data1;

    public static LoginResult ok(String message, Object data, Object data1){
        return new LoginResult(true, SUCCESS.getCode(), message, data, data1);
    }
    public static LoginResult fail(CodeEnum code){
        return new LoginResult(false, code.getCode(), code.getDescipt(), null, null);
    }


}
