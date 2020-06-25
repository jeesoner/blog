package com.cijee.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    @NonNull
    public abstract HttpStatus getHttpStatus();

    public void setErrorData(@NonNull Object errorData) {
        this.errorData = errorData;
    }

    @Nullable
    public Object getErrorData() {
        return errorData;
    }
}
