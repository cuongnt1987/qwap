/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.PostCategoryService;
import com.cuongnt.qwap.entity.PostCategory;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@ViewScoped
public class PostCategoryBean extends AbstractManagedBean<PostCategory> {

    private static final long serialVersionUID = -4094237595912557283L;
    private static final Logger logger = LoggerFactory.getLogger(PostCategoryBean.class);

    @EJB
    private PostCategoryService categoryService;

    @Override
    protected PostCategory initEntity() {
        return new PostCategory();
    }

    @Override
    protected BaseService<PostCategory> getBaseService() {
        return categoryService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
