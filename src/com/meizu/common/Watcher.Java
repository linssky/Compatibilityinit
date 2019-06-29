package com.meizu.common;

/**
 * Created by wuchaolin
 */
import com.android.uiautomator.core.*;

public class Watcher {
    LimitPop pop = new LimitPop();

    //Ȩ�޵���������Ȩ������Ϊ����
    public void watcherLimitPop() {
        UiDevice.getInstance().registerWatcher("Limit", new UiWatcher() {
            public boolean checkForCondition() {
                try {
                    if ( pop.limit()) {
                        System.out.println("LimitPop was touched off");
                        UiObject limitButton = new UiObject(new UiSelector().text("����"));
                        limitButton.click();
                        return true;
                    }
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}
