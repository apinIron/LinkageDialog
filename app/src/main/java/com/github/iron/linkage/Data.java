package com.github.iron.linkage;

import com.github.iron.library.linkage.LinkageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iron
 *         created at 2018/6/19
 */
public class Data implements LinkageItem {

    private String name;
    private List<LinkageItem> data;

    public Data(String name){
        this.name = name;
        data = new ArrayList<>();
    }

    @Override
    public String getLinkageName() {
        return name;
    }

    @Override
    public String getLinkageId() {
        return name;
    }

    @Override
    public List<LinkageItem> getChild() {
        return data;
    }
}
