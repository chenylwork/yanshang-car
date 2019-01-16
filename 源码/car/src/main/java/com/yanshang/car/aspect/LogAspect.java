package com.yanshang.car.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;

/*
 * @ClassName LogAspect
 * @Description 日志控制
 * @Author 陈彦磊
 * @Date 2019/1/16- 11:16
 * @Version 1.0
 **/
@Aspect
@Slf4j
@Configuration
public class LogAspect {
//    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };
    /**
     * 设置切入点
     */
    @Pointcut("execution(public * com.yanshang.car.controller.*.*(..))")
    public void job() {}

    @Before("job()")
    public void doBefore(JoinPoint joinPoint) {
        String classType = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
//        Class<?>[] classes = new Class[args.length];
//        for (int k = 0; k < args.length; k++) {
//            if (!args[k].getClass().isPrimitive()) {
//                //获取的是封装类型而不是基础类型
//                String result = args[k].getClass().getName();
//                Class s = map.get(result);
//                classes[k] = s == null ? args[k].getClass() : s;
//            }
//        }
        String[] paramsNames = null;
        log.info("执行方法："+classType+"."+methodName);
        StringBuffer params = new StringBuffer();
//        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
//        try {
//            Method method = Class.forName(classType).getMethod(methodName, classes);
//            paramsNames = parameterNameDiscoverer.getParameterNames(method);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ListIterator<String> paramsIterator = Arrays.asList(paramsNames).listIterator();
        Arrays.asList(args).forEach(data -> {
            // 键
//            params.append(paramsIterator.next()+"：");
            // 值
            if (data instanceof MultipartFile) {
                params.append("文件是否空："+(data == null));
            } else {
                params.append(data);
            }
            params.append(";");
        });
        log.info("请求参数："+ params.toString());
    }
    @After("job()")
    public void doAfter() {}

    @AfterReturning(returning="obj", pointcut = "job()")
    public void doAfterReturnig(Object obj){
        log.info("返回结果：{}", obj);
    }
}
