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

    @Override
    public int getPosOnePage() {
        if (page > 3) {
            return page - 3;
        }
        return 1;
    }

    @Override
    public int getPosTwoPage() {
        if (page > 3) {
            return page - 2;
        }
        return 2;
    }

    @Override
    public int getPosThreePage() {
        if (page > 3) {
            return page - 3;
        }
        return 3;
    }

    @Override
    public int getPosFourPage() {
        if (page > 3) {
            return page + 1;
        }
        return 4;
    }

    @Override
    public int getPosFivePage() {
        if (page > 3) {
            return page + 3;
        }
        return 5;
    }

    @Override
    public void one() {
        if (page > 3) {
            page = page - 3;
        } else {
            page = 1;
        }
    }

    @Override
    public void two() {
        if (page > 3) {
            page = page - 2;
        } else {
            page = 2;
        }
    }

    @Override
    public void three() {
        if (page < 3) {
            page = 3;
        }
    }

    @Override
    public void four() {
        if (page > 3) {
            page = page + 1;
        } else {
            page = 4;
        }
    }

    @Override
    public void five() {
        if (page > 3) {
            page = page + 2;
        } else {
            page = 5;
        }
    }

    @Override
    public boolean isHasOne() {
        return getPageCount() >= 1;
    }

    @Override
    public boolean isHasTwo() {
        return getPageCount() >= 2;
    }

    @Override
    public boolean isHasThree() {
        return getPageCount() >= 3;
    }

    @Override
    public boolean isHasFour() {
        return getPageCount() >= 4;
    }

    @Override
    public boolean isHasFive() {
        return getPageCount() >= 5;
    }

    public abstract List<T> load(int start, int range);

    public abstract int count();
}
