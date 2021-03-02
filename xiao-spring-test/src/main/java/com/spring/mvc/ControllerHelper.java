package com.spring.mvc;

import com.spring.ioc.interfaces.RequestMapping;
import com.spring.ioc.helper.ClassHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    /**
     * REQUEST_MAP为 "请求-处理器" 的映射
     */
    private static final Map<Request, Handler> REQUEST_MAP = new HashMap<>();

    static {
        //遍历所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                //暴力反射获取所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                //遍历方法
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        //判断是否带RequestMapping注解
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            //请求路径
                            String requestPath = requestMapping.value();
                            //请求方法
                            String requestMethod = requestMapping.method().name();

                            //封装请求和处理器
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            REQUEST_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_MAP.get(request);
    }
}
