package com.software.blogserver.exception;

import lombok.Getter;
import com.software.blogcommon.enums.CodeEnum;
import static com.software.blogcommon.enums.CodeEnum.FAIL;

/**
 * 处理业务逻辑异常
 *
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(String message, CodeEnum codeEnum) {
        this.message = message;
        this.code = codeEnum.getCode();
    }

    public BizException(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getDescipt();
    }
}
