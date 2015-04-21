/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.checker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author richard
 */
@Named("mobileChecker")
@SessionScoped
public class MobileCheckerImpl implements MobileChecker{

    private static final String MSISDN = "MSISDN";
    private static final String X_FORWARD_FOR = "X-FORWARDED-FOR";
    private static final long serialVersionUID = 1487215206830122170L;
    private UserAgentInfo agentInfo;
    private String phoneNumber;

    @PostConstruct
    public void init() {
        agentInfo = UserAgentInfo.createInstance();
        /**
         * ******************
         * Check phone Number
        ********************
         */
        // detech header
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        phoneNumber = request.getHeader(MSISDN);

        
    }

    @Override
    public boolean isMobile() {
        return agentInfo.isMobileDevice();
    }

    @Override
    public boolean isIos() {
        return agentInfo.detectIos();
    }

    @Override
    public boolean isAndroid() {
        return agentInfo.detectAndroid();
    }

    @Override
    public boolean isWindowPhone() {
        return agentInfo.detectWindowsMobile();
    }

    @Override
    public int getOsCode() {
        if (isAndroid()) {
            return 1;
        } else if (isIos()) {
            return 2;
        } else if (isWindowPhone()) {
            return 4;
        } else if (isMobile()) {
            return 3;
        } else {
            return 5;
        }
    }
}
