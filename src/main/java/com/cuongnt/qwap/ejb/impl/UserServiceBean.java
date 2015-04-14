/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.entity.User;
import com.cuongnt.qwap.ejb.UserService;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class UserServiceBean extends AbstractFacadeBean<User> implements UserService {

    private static final long serialVersionUID = -7041394405912029639L;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceBean.class);

    public UserServiceBean() {
        super(User.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
