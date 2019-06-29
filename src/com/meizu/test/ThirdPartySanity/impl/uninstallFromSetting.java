package com.meizu.test.ThirdPartySanity.impl;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import com.meizu.test.util.ShellUtil;


/**
 * Created by wuchaolin on 2016/6/6.
 */
public class uninstallFromSetting extends Common {

    @Steps("卸载应用--启动系统自带设置")
    @Expectation("卸载应用--启动系统自带设置")
    public void openSystemSetting() throws Exception
    {
        pressHome();
        sleep(1000*2);
        //ShellUtil.uninstallApp(AppInfo.PACKAGE_SETTING);
        clearData(AppInfo.PACKAGE_SETTING);
        sleep(1000*15);
        //STARTAPP(AppInfo.PACKAGE_SETTING, AppInfo.ACTIVITY_SETTING);
        startApp(AppInfo.PACKAGE_SETTING, AppInfo.ACTIVITY_SETTING);
        sleep(1000*2);
        UiObject findSettingPage=new UiObject(new UiSelector().resourceId("com.android.settings:id/action_bar"));
        assertTrue(findSettingPage.waitForExists(1000 * 4));
        sleep(2000);
        exitApp(AppInfo.PACKAGE_SETTING);
    }

    @Steps("卸载应用--进入应用管理页面")
    @Expectation("卸载应用--进入应用管理页面可进入")
    public void intoAppManagePage() throws Exception
    {
        startApp(AppInfo.PACKAGE_SETTING, AppInfo.ACTIVITY_SETTING);
        sleep(1000*2);
        //滑动列表寻找应用管理
        int i=0;
        while (!isExistByTextContains("应用管理"))
        {
            if(i>10){
                break;
            }else {
                swipeUp(30);
                i++;
            }
        }

        //进入应用管理页面
        UiObject appManageBotton=new UiObject(new UiSelector().text("应用管理"));
        assertTrue(appManageBotton.waitForExists(1000 * 4));
        appManageBotton.click();
        sleep(2000);

    }

    @Steps("卸载应用--已安装页面点击QQ进行卸载")
    @Expectation("卸载应用--完成QQ卸载")
    public void uninstallQQ() throws Exception
    {
        //点击已安装Tab
        clickByTextContains("已安装");

        //滑动列表寻找QQ
        int a=0;
        while (!isExistByTextContains("QQ"))
        {
            if(a>10){
                break;
            }else {
                swipeUp(30);
                a++;
            }
        }

        //点击QQ
        UiObject findQQ=new UiObject(new UiSelector().textContains("QQ"));
        assertTrue(findQQ.waitForExists(1000 * 4));
        findQQ.click();
        sleep(2000);

        //卸载QQ
        clickByTextContains("卸载");
        if (isExistByTextContains("确定"))
        {
            clickByTextContains("确定");
        }

    }

    @Steps("卸载应用--关闭系统设置")
    @Expectation("卸载应用--系统设置关闭成功")
    public void exitSystemSetting() throws Exception
    {
    exitApp(AppInfo.PACKAGE_SETTING);
    }

}
