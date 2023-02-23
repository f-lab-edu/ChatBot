package com.flab.fire_inform.domains.crawling.exception;

import com.flab.fire_inform.global.exception.error.ErrorCode;

public class ElementParseException extends JobCrawlerException {

    public ElementParseException(ErrorCode errorCode) {
        super(errorCode);
    }
}
