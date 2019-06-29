package com.meizu.test.QQLOL;

import com.android.uiautomator.core.*;
import com.meizu.automation.Steps;
import com.meizu.common.APPINFO;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.ShellUtil;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

/**
 * Created by wuchaolin on 2016-4-11.
 */
public class Common extends AutoAllInOneTestCase{



    public void island(){
        if(isExistByPackageName(APPINFO.PACKAGE_QQLOL)) {
            exitApp(APPINFO.PACKAGE_QQLOL);
        }
        ShellUtil.setSystemInput();
        sleep(500);
        startApp(APPINFO.PACKAGE_QQLOL, APPINFO.ACTIVITY_QQLOL);
        sleep(500);

    }
    //调用随机数
    public int random(int num){
        Random a=new Random();
        int b=a.nextInt(num);
        return b;
    }

    //注册监听器
    public void watcher(){
        UiDevice.getInstance().registerWatcher("允许", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject allow=new UiObject(new UiSelector().resourceId("android:id/button1").text("允许").clickable(true));//定义弹出的允许对话框
                if(allow.exists()){
                    System.out.print("弹出权限框");
                    try {
                        allow.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                    return false;
                }}
        });
        UiDevice.getInstance().registerWatcher("快捷方式", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject creat=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/message").text("创建桌面快捷方式，可以快速找到掌盟"));
                UiObject cliCreat=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/positive_text").text("创建"));
                if(creat.waitForExists(3*1000)){
                    try {
                        cliCreat.clickAndWaitForNewWindow(2*1000);
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }


    @Steps("启动响应时间")
    public void starAppTime(String packageName, String endResourceID,String starText) throws UiObjectNotFoundException {
        int x= UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        exitApp(packageName);
        pressHome();
        sleep(500);
        pressHome();
        sleep(500);
        UiObject OB=new UiObject(new UiSelector().resourceId(endResourceID));
        for(int i=0;i<5;i++){
            if(!isExistByText(starText)) {
                swipe(x - 100, y / 2, 300, y / 2, 10);
                sleep(500);
            }
        }
        if(isExistByText(starText)) {
            new UiObject(new UiSelector().text(starText)).clickAndWaitForNewWindow();
            Calendar c=Calendar.getInstance();
            System.currentTimeMillis();
            int starSec = c.get(Calendar.SECOND);
            int starMS=c.get(Calendar.MILLISECOND);
            if(OB.exists()) {
                Calendar E = Calendar.getInstance();
                int endSec = E.get(Calendar.SECOND);
                int endMS = E.get(Calendar.MILLISECOND);
                int Sec, ms, starTime;
                if (endSec < starSec) {
                    Sec = endSec + 60 - starSec;
                } else {
                    Sec = endSec - starSec;
                }
                System.out.print("second is :" + Sec);
                if (endMS < starMS) {
                    ms = endMS + 1000 - starMS;
                } else {
                    ms = endMS - starMS;
                }
                System.out.print("ms is :" + ms);
                starTime = Sec * 1000 + ms;
                System.out.print("time is :" + starTime + "ms");
            }

        }else {
            System.out.print("fail in can't find text");
        }
    }
}
