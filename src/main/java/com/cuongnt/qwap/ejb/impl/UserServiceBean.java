/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.entity.User;
import com.cuongnt.qwap.ejb.UserService;
import com.cuongnt.qwap.util.AppUtil;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
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

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        User user = null;
        q.setParameter("username", username);
        try {
            q.setParameter("password", AppUtil.encodeSHA256(password));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new EJBException("error-when-hash-password");
        }
        try {
            user = q.getSingleResult();
        } catch (Exception e) {
            
        }
        return user;
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        User user = em.find(User.class, id);
        if (user != null) {
            try {
                user.setPassword(AppUtil.encodeSHA256(newPassword));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                throw new EJBException("error-when-hash-password");
            }
            em.merge(user);
        }
    }

    @Override
    public boolean hasAdminUser() {
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(u.id) FROM User u WHERE :group IN (u.groups)", Long.class);
        q.setParameter("group", User.Group.Admin);
        long resutl = q.getSingleResult();
        return resutl > 0;
    }

}
