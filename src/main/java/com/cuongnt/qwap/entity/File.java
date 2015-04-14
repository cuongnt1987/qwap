/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import java.io.InputStream;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author richard
 */
@MappedSuperclass
public class File extends BaseEntity {

    private static final long serialVersionUID = -4276523767622215932L;
    
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
    
}
