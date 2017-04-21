package com.guifa.doubanproject.beans;

import com.flyco.tablayout.listener.CustomTabEntity;

import java.io.Serializable;

/**
 * Created by GuiFa on 2017/4/21
 */

public class HomeBottomBean implements Serializable, CustomTabEntity {

    public String title; // 标题
    public int selectedIcon; // 选中时的图标
    public int unSelectedIcon; // 未选中时的图标

    public HomeBottomBean(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
