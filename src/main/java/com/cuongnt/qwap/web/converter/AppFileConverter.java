/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.AppFileService;
import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.entity.AppFile;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter(forClass = AppFile.class, value = "appFileConverter")
public class AppFileConverter extends AbstractEntityConverter<AppFile>{
    
    @EJB
    private AppFileService service;

    @Override
    protected BaseService<AppFile> getBaseService() {
        return service;
    }

    @Override
    protected Class<AppFile> getEntityClass() {
        return AppFile.class;
    }
}
