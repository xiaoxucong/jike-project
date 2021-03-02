package com.spring.mvc;

import com.spring.aop.AopHelper;
import com.spring.ioc.helper.BeanHelper;
import com.spring.ioc.helper.ClassHelper;
import com.spring.ioc.helper.IocHelper;
import com.spring.utils.ClassUtil;

public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                AopHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
