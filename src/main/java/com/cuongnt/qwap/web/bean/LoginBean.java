/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.UserService;
import com.cuongnt.qwap.entity.User;
import com.cuongnt.qwap.web.util.MessageUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);
    private static final long serialVersionUID = 1847717292185072052L;

    @NotNull
    private String username;
    @NotNull
    private String password;
    private String originalURL;
    private User currentUser;

    @EJB
    private UserService service;

    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/admin/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            User user = service.findByUsernameAndPassword(username, password);
            externalContext.getSessionMap().put("currentUser", user);
            currentUser = user;
            request.login(username, password);
            externalContext.redirect(originalURL);
        } catch (ServletException | IOException e) {
            logger.error("Error when login to system with username = {}, password = {}, and errorMessage : {}",
                    username,
                    password,
                    e.toString());
            MessageUtil.addGlobalErrorMessage("username-or-password-incorrect");
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/admin/login.xhtml");
    }

    /* Getters and Setters */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Produces
    public User getCurrentUser() {
        return currentUser;
    }
}
