/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.PostCategoryService;
import com.cuongnt.qwap.entity.PostCategory;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class PostCategoryServiceBean extends AbstractFacadeBean<PostCategory> implements PostCategoryService {

    private static final long serialVersionUID = -404210787775823466L;
    private static final Logger logger = LoggerFactory.getLogger(PostCategoryServiceBean.class);

    public PostCategoryServiceBean() {
        super(PostCategory.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
