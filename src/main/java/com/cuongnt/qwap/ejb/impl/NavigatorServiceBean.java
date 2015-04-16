/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.entity.Navigator;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class NavigatorServiceBean extends AbstractFacadeBean<Navigator> implements NavigatorService {

    private static final long serialVersionUID = -5043121109735572700L;
    private static final Logger logger = LoggerFactory.getLogger(NavigatorServiceBean.class);

    public NavigatorServiceBean() {
        super(Navigator.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
