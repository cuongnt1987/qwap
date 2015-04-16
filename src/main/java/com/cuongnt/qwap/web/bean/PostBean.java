/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.PostService;
import com.cuongnt.qwap.entity.Post;
import com.cuongnt.qwap.util.AppUtil;
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
public class PostBean extends AbstractManagedBean<Post> {

    private static final long serialVersionUID = -8161741625597024831L;
    private static final Logger logger = LoggerFactory.getLogger(PostBean.class);
    
    @EJB
    private PostService postService;

    @Override
    protected Post initEntity() {
        return new Post();
    }

    @Override
    protected BaseService<Post> getBaseService() {
        return postService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
    
    public void onChangeTitle() {
        if (current.getTitle() != null && !getCurrent().getTitle().trim().isEmpty()) {
            String slug = AppUtil.toSlug(getCurrent().getTitle().trim());
            if (postService.countBySlug(slug) > 0) {
                slug += "-1";
            }
            getCurrent().setSlug(slug);
        }
    }

}
