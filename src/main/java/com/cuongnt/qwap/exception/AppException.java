/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.exception;

/**
 *
 * @author Cuong
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -4808588009136179451L;
    protected ErrorInfo errorInfo;

    public AppException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo, String message) {
        super(message);
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo, String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}