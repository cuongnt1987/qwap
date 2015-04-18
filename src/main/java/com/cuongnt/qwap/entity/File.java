/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import com.cuongnt.qwap.exception.AppException;
import com.cuongnt.qwap.util.AppConfig;
import com.cuongnt.qwap.util.AppUtil;
import java.io.IOException;
import java.io.InputStream;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@MappedSuperclass
public class File extends BaseEntity {

    private static final long serialVersionUID = -4276523767622215932L;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(File.class);

    @Size(max = 75)
    @Column(length = 75)
    protected String contentType;

    @Min(0)
    protected long fileSize;

    @Size(max = 300)
    @Column(length = 300)
    protected String filePath;

    @Size(max = 300)
    @Column(length = 300)
    protected String url;

    @Transient
    @XmlTransient
    protected InputStream inputStream;

    @Transient
    @XmlTransient
    protected Part part;
    
    @Transient
    @XmlTransient
    protected boolean selected;

    public File() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (part != null) {
            // delete old file
            if (title != null) {
                FileUtils.deleteQuietly(new java.io.File(AppConfig.getFileStorePath()
                        + this.id + java.io.File.separator + this.title));
            }

            this.contentType = part.getContentType();
            this.fileSize = part.getSize();
            this.title = AppUtil.toSlug(part.getSubmittedFileName());
        }
    }

    @PostUpdate
    @PostPersist
    public void postPersist() {
        if (part != null) {
            try {
                FileUtils.copyInputStreamToFile(part.getInputStream(), new java.io.File(AppConfig.getFileStorePath() + id + java.io.File.separator + title));
            } catch (IOException ex) {
                throw new AppException(null, "Cannot save file", ex);
            }

        }
    }

    @PreRemove
    public void preRemove() {
        if (title != null) {
            FileUtils.deleteQuietly(new java.io.File(AppConfig.getFileStorePath()
                    + this.id + java.io.File.separator + this.title));
        }
    }

}
