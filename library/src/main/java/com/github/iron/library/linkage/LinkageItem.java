package com.github.iron.library.linkage;

import java.util.List;

/**
 * @author iron
 *         created at 2018/6/19
 *         联动条目
 */
public interface LinkageItem {

    String getLinkageName();

    String getLinkageId();

    List<LinkageItem> getChild();
}
