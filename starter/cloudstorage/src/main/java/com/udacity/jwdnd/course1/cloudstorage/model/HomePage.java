package com.udacity.jwdnd.course1.cloudstorage.model;

public class HomePage {

    private String navTab;

    public HomePage(String navTab) {
        this.navTab = navTab;
    }

    public String getNavTab() {
        return navTab;
    }

    public void setNavTab(String navTab) {
        this.navTab = navTab;
    }
}
