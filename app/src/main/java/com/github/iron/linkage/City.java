package com.github.iron.linkage;

import com.github.iron.library.linkage.LinkageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iron
 *         created at 2018/6/20
 */
public class City implements LinkageItem {

    private String areaId;
    private String areaName;
    private List<CitiesBean> cities;

    public String getAreaId() { return areaId;}

    public void setAreaId(String areaId) { this.areaId = areaId;}

    public String getAreaName() { return areaName;}

    public void setAreaName(String areaName) { this.areaName = areaName;}

    public List<CitiesBean> getCities() { return cities;}

    public void setCities(List<CitiesBean> cities) { this.cities = cities;}

    @Override
    public String getLinkageName() {
        return areaName;
    }

    @Override
    public String getLinkageId() {
        return areaId;
    }

    @Override
    public List<LinkageItem> getChild() {
        List<LinkageItem> item = new ArrayList<>();
        item.addAll(cities);
        return item;
    }

    public static class CitiesBean implements LinkageItem {

        private String areaId;
        private String areaName;
        private List<CountiesBean> counties;

        public String getAreaId() { return areaId;}

        public void setAreaId(String areaId) { this.areaId = areaId;}

        public String getAreaName() { return areaName;}

        public void setAreaName(String areaName) { this.areaName = areaName;}

        public List<CountiesBean> getCounties() { return counties;}

        public void setCounties(List<CountiesBean> counties) { this.counties = counties;}

        @Override
        public String getLinkageName() {
            return areaName;
        }

        @Override
        public String getLinkageId() {
            return areaId;
        }

        @Override
        public List<LinkageItem> getChild() {
            List<LinkageItem> item = new ArrayList<>();
            item.addAll(counties);
            return item;
        }

        public static class CountiesBean implements LinkageItem {

            private String areaId;
            private String areaName;

            public String getAreaId() { return areaId;}

            public void setAreaId(String areaId) { this.areaId = areaId;}

            public String getAreaName() { return areaName;}

            public void setAreaName(String areaName) { this.areaName = areaName;}

            @Override
            public String getLinkageName() {
                return areaName;
            }

            @Override
            public String getLinkageId() {
                return areaId;
            }

            @Override
            public List<LinkageItem> getChild() {
                return null;
            }
        }
    }
}
