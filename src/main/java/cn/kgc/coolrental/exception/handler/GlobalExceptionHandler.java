package cn.kgc.coolrental.exception.handler;

import cn.kgc.coolrental.dto.ResponseMsg;
import cn.kgc.coolrental.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenException.class)
    public ResponseMsg handler(TokenException tokenException){
        log.error("Token异常");
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg("Token异常");
        responseMsg.setResponseStatus(cn.kgc.coolrental.constant.ResponseStatus.NOT_AUTHORITY);
        return responseMsg;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseMsg handler(IllegalArgumentException argumentException){
        log.error(argumentException.getMessage());
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg(argumentException.getMessage());
        responseMsg.setCode(cn.kgc.coolrental.constant.ResponseStatus.ARGUMENT_EXCEPT.getCode());
        responseMsg.setResponseStatus(cn.kgc.coolrental.constant.ResponseStatus.ARGUMENT_EXCEPT);
        return responseMsg;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseMsg handler(HttpMessageNotReadableException messageNot){
        log.error(messageNot.getMessage());
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg("未提交必须参数");
        responseMsg.setCode(cn.kgc.coolrental.constant.ResponseStatus.ARGUMENT_EXCEPT.getCode());
        responseMsg.setResponseStatus(cn.kgc.coolrental.constant.ResponseStatus.ARGUMENT_EXCEPT);
        return responseMsg;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseMsg handler(HttpRequestMethodNotSupportedException methodNotSupported){
        log.error(methodNotSupported.getMessage());
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsg("请求方式不支持,请使用"+ Arrays.toString(methodNotSupported.getSupportedMethods())+"方式进行请求");
        responseMsg.setCode(HttpStatus.BAD_REQUEST.value());
        return responseMsg;
    }
}
