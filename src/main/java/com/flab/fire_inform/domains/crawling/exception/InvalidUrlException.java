package com.flab.fire_inform.domains.crawling.exception;

import com.flab.fire_inform.global.exception.error.ErrorCode;

public class InvalidUrlException extends JobCrawlerException {

    public InvalidUrlException(ErrorCode errorCode) {
        super(errorCode);
    }
}
