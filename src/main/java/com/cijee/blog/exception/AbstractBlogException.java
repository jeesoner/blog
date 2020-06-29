package com.cijee.blog.exception;

import org.springframework.http.HttpStatus;

/**
 * @author cijee
 * @date 2020/6/25
 */
public abstract class AbstractBlogException extends RuntimeException{

    /**
     * 错误数据
     */
    private Object errorData;

    public AbstractBlogException(String message) {
        super(message);
    }

    public AbstractBlogException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http status code
     *
     * @return {@link HttpStatus}
     */
    public abstract HttpStatus getHttpStatus();

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    public Object getErrorData() {
        return errorData;
    }
}
