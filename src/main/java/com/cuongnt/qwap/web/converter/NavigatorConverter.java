/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.entity.Navigator;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter("navigatorConverter")
public class NavigatorConverter  extends AbstractEntityConverter<Navigator>{
    
    @EJB
    private NavigatorService service;

    @Override
    protected BaseService<Navigator> getBaseService() {
        return service;
    }

    @Override
    protected Class<Navigator> getEntityClass() {
        return Navigator.class;
    }
    
}
