/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.pagging;

import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author richard
 * @param <T>
 */
public abstract class PaginationHelper<T> implements Pagination<T> {

    /**
     * 1,2,3,4,5 ....
     */
    private int page = 1;
    private int pageSize = 20;
    private int count;


    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public int getPageCount() {
        if (pageSize > 0) {
            if (count % pageSize > 0) {
                return count / pageSize + 1;
            } else {
                return count / pageSize;
            }
        }
        return 0;
    }

    @Override
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public DataModel<T> getDataModel() {
        int start = (page - 1) * pageSize;
        this.count = count();
        return new ListDataModel<>(load(start, getPageSize()));
    }

    @Override
    public boolean hasNext1() {
        return page < getPageCount();
    }

    @Override
    public boolean hasNext2() {
        return page < (getPageCount() -1);
    }

    @Override
    public boolean hasPreview1() {
        return page > 1;
    }

    @Override
    public boolean hasPreview2() {
        return page > 2;
    }

    @Override
    public void next1() {
        if (hasNext1()) {
            page = page + 1;
        } else {
            goToEnd();
        }
    }

    @Override
    public void next2() {
        if (hasNext2()) {
            page = page + 2;
        } else {
            goToEnd();
        }
    }

    @Override
    public void preview1() {
        if (hasPreview1()) {
            page = page - 1;
        } else {
            goToStart();
        }
    }

    @Override
    public void preview2() {
        if (hasPreview2()) {
            page = page -2;
        } else {
            goToStart();
        }
        
    }

    @Override
    public void goToStart() {
        this.page = 1;
    }

    @Override
    public void goToEnd() {
        this.page = getPageCount();
    }

    @Override
    public boolean isStart() {
        return this.page == 1;
    }

    @Override
    public boolean isEnd() {
        return this.page == getPageCount();
    }
    
    public abstract List<T> load(int start, int range);
    public abstract int count();
}
