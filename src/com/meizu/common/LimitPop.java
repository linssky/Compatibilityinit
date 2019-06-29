package com.meizu.common;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.meizu.test.common.AutoAllInOneTestCase;

/**
 * Created by wuchaolin on 2016/3/15.
 */
public class LimitPop extends AutoAllInOneTestCase{
    //权限弹出判断
    public boolean limit() throws UiObjectNotFoundException {
        UiObject parent = new UiObject(new UiSelector().resourceId("android:id/parentPanel"));
        UiObject child = parent.getChild(new UiSelector().resourceId("android:id/buttonPanel"));
        if(parent.exists() && child.exists()){
            return true;
        }
        else{
            return false;
        }
    }
}
