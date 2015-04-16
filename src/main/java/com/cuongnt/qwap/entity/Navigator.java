/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Navigator.getAll", 
            query = "SELECT n FROM Navigator n ORDER BY n.orderNumber ASC"), 
    @NamedQuery(name = "Navigator.countAll",
            query = "SELECT COUNT(n) FROM Navigator n")
})
public class Navigator extends BaseEntity {

    private static final long serialVersionUID = 3287800031030186902L;
    
    public enum Type {
        Single, Collection;

        @Override
        public String toString() {
            
            switch (this) {
                case Single:
                    return "single";
                case Collection:
                    return "collection";
                default:
                    return "unknown";
            }
        }
    }
    
    @Min(0)
    @Max(100)
    private int orderNumber;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 11, nullable = false)
    private Type type = Type.Single;
    
    @Lob
    @Basic(optional = false, fetch = FetchType.LAZY)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORYID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostCategory postCategory;

    public Navigator() {
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PostCategory getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
