package com.wee0.box.spring.boot;

import com.wee0.box.web.annotation.BoxParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/8/30 7:12
 * @Description 请求参数校验
 * <pre>
 * 补充说明
 * </pre>
 **/
//@Component
public class BoxActionParamsValidator implements ConstraintValidator<BoxParam, Object> {
    @Override
    public boolean isValid(Object val, ConstraintValidatorContext context) {
        System.out.println("val:" + val);
        System.out.println("context:" + context);
        return false;
    }
}
