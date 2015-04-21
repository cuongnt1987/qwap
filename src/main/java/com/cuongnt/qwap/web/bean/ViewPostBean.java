/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.PostService;
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
public class ViewPostBean implements Serializable {

    private static final long serialVersionUID = -5987815302712636005L;
    private static final Logger logger = LoggerFactory.getLogger(ViewPostBean.class);
    @EJB
    private PostService postService;
    private String slug;
    private Post post;
    private List<Post> relatePosts;

    public ViewPostBean() {
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Post getPost() {
        if (slug != null && !slug.trim().isEmpty()) {
            try {
                post = postService.findBySlug(slug);
            } catch (Exception e) {
                logger.error("Error when load post by slug when view post detail", e);
            }
        }
        return post;
    }

    public List<Post> getRelatePosts() {
        if (relatePosts == null) {
            try {
                relatePosts = postService.getRelatePost(post, 5);
            } catch (Exception e) {
                logger.error("Error when get relate ports", e);
            }
        }
        return relatePosts;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
