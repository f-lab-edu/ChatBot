package com.flab.fire_inform.domains.crawling.exception;

import com.flab.fire_inform.global.exception.error.ErrorCode;

public class JobCrawlerException extends RuntimeException {

    private final ErrorCode errorCode;

    public JobCrawlerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
