package com.example.common.config.execptions;

import com.example.common.enums.EnError;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/12/19 17:30
 */
public class BussinessException extends RuntimeException {
    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    public BussinessException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BussinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = EnError.EXECUTE_FAILED.getCode();
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
