package com.flab.fire_inform.global.exception;


import com.flab.fire_inform.domains.crawling.exception.JobCrawlerException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import com.flab.fire_inform.global.exception.error.ErrorResponse;
import com.flab.fire_inform.global.mail.MailSendManager;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${admin.email}")
    private String adminEmail;

    private final MailSendManager mailSendManager;

    public GlobalExceptionHandler(MailSendManager mailSender) {
        this.mailSendManager = mailSender;
    }

    // 서버 에러
    @ExceptionHandler(value = {ConstraintViolationException.class,
        DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleServerError(Exception e) {
        log.error("handleData Exception throw Exception : {}", ErrorCode.SERVER_ERROR);
        return ErrorResponse.toResponseEntity(ErrorCode.SERVER_ERROR);
    }

    // 사용자정의 에러
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {JobCrawlerException.class})
    protected ResponseEntity<ErrorResponse> handleJobCrawlerException(JobCrawlerException e)
        throws MessagingException {
        log.error("JobCrawlerException : {}", e.getErrorCode());
        mailSendManager.sendMail(adminEmail, "Job Crawler RuntimeException 발생", String.format(
            "exception name : %s\nerror code : %s\nerror message : %s", e.getClass().toString(),
            e.getErrorCode(), e.getErrorCode().getDetail()));
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
