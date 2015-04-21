/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.ejb.PostService;
import com.cuongnt.qwap.entity.Navigator;
import com.cuongnt.qwap.entity.Post;
import java.io.Serializable;
import java.util.List;
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
public class ViewNavigatorBean implements Serializable {

    private static final long serialVersionUID = -8020356610064049000L;
    private static final Logger logger = LoggerFactory.getLogger(ViewNavigatorBean.class);
    
    private String slug;
    private Navigator currentNav;
    private List<Post> posts;
    private int numberOfPosts = 10;
    
    @EJB
    private NavigatorService navigatorService;
    @EJB
    private PostService postService;

    public ViewNavigatorBean() {
    }

    public Navigator getCurrentNav() {
        if (slug != null && !slug.trim().isEmpty()) {
            try {
                currentNav = navigatorService.findBySlug(slug);
            } catch (Exception e) {
                logger.error("Error when load navigator by slug", e);
            }
        }
        return currentNav;
    }
    
    public boolean isShowDetail() {
        return currentNav != null && currentNav.getType() == Navigator.Type.Single;
    }

    public void setCurrentNav(Navigator currentNav) {
        this.currentNav = currentNav;
    }

    public List<Post> getPosts() {
        try {
            posts = postService.findByCategory(currentNav.getPostCategory(), numberOfPosts);
        } catch (Exception e) {
            logger.error("Error when load post by navigator", e);
        }
       
        return posts;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
    
    public void loadMore() {
        this.numberOfPosts += 10;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    
}
