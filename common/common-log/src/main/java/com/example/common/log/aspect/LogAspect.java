package com.example.common.log.aspect;

//import com.alibaba.fastjson2.JSON;
import com.example.common.log.annotation.Log;
import com.example.common.log.domain.OperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    
    private ThreadLocal<OperLog> currentLog = new ThreadLocal<>();

    @Before("@annotation(log)")
    public void doBefore(JoinPoint joinPoint, Log log) {
        OperLog operLog = new OperLog();
        operLog.setTitle(log.title());
        operLog.setAction(log.action());
        operLog.setOperTime(new Date());
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            operLog.setOperUrl(request.getRequestURI());
            operLog.setRequestMethod(request.getMethod());
            operLog.setOperIp(request.getRemoteAddr());
        }
        
        // 设置请求参数
        if (log.isSaveRequestData()) {
            operLog.setOperParam(JSON.toJSONString(joinPoint.getArgs()));
        }
        
        currentLog.set(operLog);
    }

    @AfterReturning(pointcut = "@annotation(log)", returning = "jsonResult")
    public void doAfterReturning(Log log, Object jsonResult) {
        handleLog(log, null, jsonResult);
    }

    @AfterThrowing(pointcut = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(Log log, Exception e) {
        handleLog(log, e, null);
    }

    protected void handleLog(Log log, Exception e, Object jsonResult) {
        OperLog operLog = currentLog.get();
        if (operLog == null) {
            return;
        }

        // 设置状态
        if (e != null) {
            operLog.setStatus(1);
            operLog.setErrorMsg(e.getMessage());
        } else {
            operLog.setStatus(0);
            if (log.isSaveResponseData()) {
                operLog.setJsonResult(JSON.toJSONString(jsonResult));
            }
        }

        // TODO: 保存日志到数据库
        // operLogService.insertOperLog(operLog);

        currentLog.remove();
    }
} 