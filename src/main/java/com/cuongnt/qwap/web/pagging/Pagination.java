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
    public void goToStart();
    public void goToEnd();
    public boolean isStart();
    public boolean isEnd();
    public int getPosOnePage();
    public int getPosTwoPage();
    public int getPosThreePage();
    public int getPosFourPage();
    public int getPosFivePage();
    public void one();
    public void two();
    public void three();
    public void four();
    public void five();
    public boolean isHasOne();
    public boolean isHasTwo();
    public boolean isHasThree();
    public boolean isHasFour();
    public boolean isHasFive();
}
