package com.jfeat.captcha.sdk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
@Aspect
@Component
public class CaptchaAop {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaAop.class);

    @Resource
    private CaptchaConfig captchaConfig;

    @Pointcut(value = "@annotation(com.jfeat.captcha.sdk.Captcha)")
    private void cutCaptcha() {

    }

    @Around("cutCaptcha()")
    public Object doCaptcha(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Captcha captcha = method.getAnnotation(Captcha.class);
        logger.info("before captcha, " + captcha.enabled());
        if (!captchaConfig.isEnabled() || !captcha.enabled()) {
            return point.proceed();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String uuid = request.getHeader(captchaConfig.getUuidHeaderName());
        String code = request.getHeader(captchaConfig.getCodeHeaderName());
        CaptchaComposer composer = new CaptchaComposer(captchaConfig);
        if (composer.verify(uuid, code)) {
            return point.proceed();
        }

        throw new CaptchaException("Code verify failure.");
    }
}
