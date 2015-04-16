/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.AppFileService;
import com.cuongnt.qwap.entity.AppFile;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class AppFileServiceBean extends AbstractFacadeBean<AppFile> implements AppFileService {

    private static final long serialVersionUID = 7528236588452872960L;
    private static final Logger logger = LoggerFactory.getLogger(AppFileServiceBean.class);

    public AppFileServiceBean() {
        super(AppFile.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
