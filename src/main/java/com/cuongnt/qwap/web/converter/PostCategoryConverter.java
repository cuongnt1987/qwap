/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.PostCategoryService;
import com.cuongnt.qwap.entity.PostCategory;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter("postCategoryConverter")
public class PostCategoryConverter extends AbstractEntityConverter<PostCategory> {

    @EJB
    private PostCategoryService service;

    @Override
    protected BaseService<PostCategory> getBaseService() {
        return service;
    }

    @Override
    protected Class<PostCategory> getEntityClass() {
        return PostCategory.class;
    }

}
