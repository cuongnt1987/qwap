/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.UserService;
import com.cuongnt.qwap.entity.User;
import com.cuongnt.qwap.web.util.MessageUtil;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@ViewScoped
public class UserBean extends AbstractManagedBean<User> {

    private static final long serialVersionUID = 9174631341378859822L;
    private static final Logger logger = LoggerFactory.getLogger(UserBean.class);
    
    private SelectItem[] userGroupItems;
    
    @EJB
    private UserService userService;

    @Override
    protected User initEntity() {
        return new User();
    }

    @Override
    protected BaseService<User> getBaseService() {
        return userService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
    
    public SelectItem[] getUserGroupItems() {
        if (userGroupItems == null) {
            userGroupItems = new SelectItem[User.Group.values().length];
            for (int i = 0; i < User.Group.values().length; i++) {
                User.Group group = User.Group.values()[i];
                userGroupItems[i] = new SelectItem(group, MessageUtil.getBundleMessage(group.toString()));
            }
        }
        return userGroupItems;
    }

}
