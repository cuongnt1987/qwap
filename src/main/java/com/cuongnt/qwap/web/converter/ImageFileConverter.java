/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.entity.ImageFile;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter(forClass = ImageFile.class, value = "imageFileConverter")
public class ImageFileConverter extends AbstractEntityConverter<ImageFile>{
    
    @EJB
    private ImageFileService service;

    @Override
    protected BaseService<ImageFile> getBaseService() {
        return service;
    }

    @Override
    protected Class<ImageFile> getEntityClass() {
        return ImageFile.class;
    }
    
}
