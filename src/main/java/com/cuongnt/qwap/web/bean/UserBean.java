/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.UserService;
import com.cuongnt.qwap.entity.User;
import com.cuongnt.qwap.util.AppUtil;
import com.cuongnt.qwap.web.util.JsfUtil;
import com.cuongnt.qwap.web.util.MessageUtil;
import com.sun.tools.javac.util.List;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

    @EJB
    private UserService userService;

    @PostConstruct
    public void init() {
        String id = JsfUtil.getRequestParameter("id");
        if (id != null && !id.trim().isEmpty()) {
            try {
                current = userService.find(Long.parseLong(id));
            } catch (Exception e) {
                logger.warn("Use not found with id = {}", id);
            }
        }
    }

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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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

    public void persisAdminUser() {
        JsfUtil.processAction(u -> {
            onBeforePersist();
            u.setGroups(List.of(User.Group.Admin));
            userService.persist(u);
            onPersistSuccess();
        }, current, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
    }

    public void changePassword() {
        try {
            if (!current.getPassword().equals(AppUtil.encodeSHA256(currentPassword))) {
                MessageUtil.addGlobalErrorMessage("current-password-is-not-correct");
            } else {
                current.setPassword(AppUtil.encodeSHA256(newPassword));
                JsfUtil.processAction(u -> {
                    userService.update(u);
                    currentPassword = null;
                    newPassword = null;
                }, current, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            MessageUtil.addGlobalErrorMessage("hash-password-error");
        }
    }
}
