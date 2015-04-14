/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.beanvalidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Pattern.List({
    @Pattern(
            regexp = "^(?:ftp|http|https):\\/\\/(?:[\\w\\.\\-\\+]+:{0,1}[\\w\\.\\-\\+]*@)?(?:[a-z0-9\\-\\.]+)(?::[0-9]+)?(?:\\/|\\/(?:[\\w#!:\\.\\?\\+=&%@!\\-\\/\\(\\)]+)|\\?(?:[\\w#!:\\.\\?\\+=&%@!\\-\\/\\(\\)]+))?$",
            message = "{com.cuongnt.beanvalidation.url}"
    )
})
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {

    String message() default "{com.cuongnt.beanvalidation.url}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Url[] value();
    }
}
