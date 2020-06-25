package com.cijee.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 实体没有找到异常类
 *
 * @author cijee
 * @date 2020/6/25
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractBlogException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
