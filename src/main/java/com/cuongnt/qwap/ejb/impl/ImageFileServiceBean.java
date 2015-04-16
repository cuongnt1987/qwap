/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.entity.ImageFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
public class ImageFileServiceBean extends AbstractFacadeBean<ImageFile> implements ImageFileService {

    private static final long serialVersionUID = -6238795633281162196L;
    private static final Logger logger = LoggerFactory.getLogger(ImageFileServiceBean.class);

    public ImageFileServiceBean() {
        super(ImageFile.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
