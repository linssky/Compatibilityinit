package com.meizu.common;

import com.android.uiautomator.core.*;
import com.meizu.automation.Steps;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.ShellUtil;

import java.util.Random;

/**
 * Created by wuchaolin on 2016-4-21.
 */
public class PubM extends AutoAllInOneTestCase{

    @Steps("调用随机函数")
    public int random(int num){
        Random a=new Random();
        int b=a.nextInt(num);
        return b;
    }

    @Steps("初始化启动app")
    public void init(String PackageName,String launchActivity){
        if(isExistByPackageName(PackageName)) {
            exitApp(PackageName);
        }
        sleep(500);
        startApp(PackageName,launchActivity);
        sleep(500);
        ShellUtil.setUtf7Input();
        sleep(500);
    }

    @Steps("QQ登陆界面的点击")
    public void QQLAND() throws UiObjectNotFoundException {
        UiSelector qland=new UiSelector().className("android.widget.Button").resourceId("com.tencent.mobileqq:id/name");
        UiObject qqland=new UiObject(qland);
        qqland.clickAndWaitForNewWindow(5*1000);
    }

    @Steps("监听器注册")
    public void watcher() {
        UiDevice.getInstance().registerWatcher("允许", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject allow = new UiObject(new UiSelector().resourceId("android:id/button1").text("允许").clickable(true));//定义弹出的允许对话框
                if (allow.exists()) {
                    System.out.print("弹出权限框");
                    try {
                        allow.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("快捷方式", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject creat = new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/message").textContains("创建桌面快捷方式"));
                UiObject cliCreat = new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/positive_text").text("创建"));
                if (creat.waitForExists(3 * 1000)) {
                    try {
                        cliCreat.clickAndWaitForNewWindow(2 * 1000);
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("程序无响应", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject broken=new UiObject(new UiSelector().textContains("要将其关闭吗"));
                if (broken.waitForExists(2000)){
                    System.out.print("the app is broken");
                }
                return false;
            }
        });
    }


}
