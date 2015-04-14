/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.pagging;

import javax.faces.model.DataModel;

/**
 *
 * @author richard
 * @param <T>
 */
public interface Pagination<T> {
    public int getCount();
    public int getPageCount();
    public int getPage();
    public int getPageSize();
    public void setPageSize(int pageSize);
    public DataModel<T> getDataModel();
    public boolean hasNext1();
    public boolean hasNext2();
    public boolean hasPreview1();
    public boolean hasPreview2();
    public void next1();
    public void next2();
    public void preview1();
    public void preview2();
    public void goToStart();
    public void goToEnd();
    public boolean isStart();
    public boolean isEnd();
}
