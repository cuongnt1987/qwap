/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@NamedQueries({
        @NamedQuery(name = "Post.findBySlug", query = "SELECT p FROM Post p WHERE p.slug = :slug"),
        @NamedQuery(name = "Post.countBySlug", query = "SELECT COUNT(p.id) FROM Post p WHERE p.slug = :slug")
})
@NamedEntityGraphs({
    @NamedEntityGraph(name="Post.showListEntityGraph", attributeNodes={
        @NamedAttributeNode("title"),
        @NamedAttributeNode("slug"),
        @NamedAttributeNode("summary")
    }),
    @NamedEntityGraph(name="Post.showDetailEntityGraph", attributeNodes={
        @NamedAttributeNode("title"),
        @NamedAttributeNode("slug"),
        @NamedAttributeNode("content"),
        @NamedAttributeNode("summary")
    })
})
@XmlRootElement
public class Post extends WebContent {

    private static final long serialVersionUID = -4248779070644646067L;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "THUMBFILEID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageFile thumbnail = new ImageFile();
    
    @Lob
    @NotNull
    @Basic(optional = false, fetch = FetchType.LAZY)
    private String content;
    
    @NotNull
    @Basic(optional = false)
    private String summary;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORYID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostCategory category;
    
    public Post() {
    }

    public ImageFile getThumbnail() {
        if (thumbnail == null) 
            thumbnail = new ImageFile();
        return thumbnail;
    }

    public void setThumbnail(ImageFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }
    
    //    @PrePersist
    //    @PreUpdate
    //    public void checkThumbnail() {
    //        thumbnail = (ImageFile) checkFile(thumbnail);
    //    }
    
}
