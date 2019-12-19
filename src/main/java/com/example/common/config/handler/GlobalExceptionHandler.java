package com.example.common.config.handler;

import com.example.common.config.execptions.BussinessException;
import com.example.common.entity.Msg;
import com.example.common.enums.EnError;
import com.example.common.services.ISysErrorLogService;
import com.example.common.utils.threadPool.ThreadPoolExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/12/19 17:11
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ISysErrorLogService sysErrorLogService;

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Msg throwable(HttpServletRequest request, HttpServletResponse response, Throwable e){
        Msg msg = new Msg();
        if (e instanceof BussinessException) {
            msg.setResult(EnError.SYSTEM_ERROR);
            response.addHeader("error-code", EnError.SYSTEM_ERROR.getDescription());
        }

        //TODO
        //插入异常消息至数据库
        ThreadPoolExecutorFactory poolExecutorFactory = new ThreadPoolExecutorFactory();
        poolExecutorFactory.executor(()->{
            sysErrorLogService.insert();
        });
        return msg;
    }
}
