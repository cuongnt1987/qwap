/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.UserService;
import com.cuongnt.qwap.entity.User;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter(forClass = User.class, value = "userConverter")
public class UserConverter extends AbstractEntityConverter<User> {

    @EJB
    private UserService userService;

    @Override
    protected BaseService<User> getBaseService() {
        return userService;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

}
