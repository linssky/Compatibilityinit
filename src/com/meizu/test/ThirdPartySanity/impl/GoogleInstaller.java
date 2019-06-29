package com.meizu.test.ThirdPartySanity.impl;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;

/**
 * Created by wuchaolin on 2016/6/1.
 */

public class GoogleInstaller extends Common
{

@Steps("谷歌安装器--1.在桌面上找到应用" +
        "谷歌安装器--2.点击运行应用" )
@Expectation("应用运行成功" )
    public void OpenApp() throws Exception
{
    startApp(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.ACTIVITY_GOOGLEINSTALLER);
    sleep(1000*5);
    UiSelector a=new UiSelector().packageName(AppInfo.PACKAGE_GOOGLEINSTALLER);
    UiObject start=new UiObject(a);
    assertTrue(start.waitForExists(1000*5));
    exitApp(AppInfo.PACKAGE_GOOGLEINSTALLER);
}

    @Steps("谷歌安装器--安装谷歌服务框架" )
    @Expectation("谷歌服务框架安装成功")
    public void InstallService() throws Exception
    {
        startApp(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.ACTIVITY_GOOGLEINSTALLER);
        sleep(1000*5);
        UiSelector insta=new UiSelector().resourceId("com.howie.gserverinstall:id/btn_install");
        sleep(1000);
        UiObject installButton = new UiObject(new UiSelector().resourceId("com.howie.gserverinstall:id/btn_install"));
        if(installButton.exists()) {
            installButton.click();
            if (isExistByTextContains("确定")) {
                clickByTextContains("确定");
            }

            int time = 0;
            while (!isExistByText("重启")) {
                if (time > 100) {
                    break;
                }
                    sleep(1000 * 6);
                    time++;


            }
        }
        UiObject uninstallbig=new UiObject(new UiSelector().text("重启"));
        assertTrue(uninstallbig.waitForExists(1000*3));//
        uninstallbig.clickAndWaitForNewWindow();

    }


    @Steps("谷歌安装器--卸载谷歌服务框架")
    @Expectation("服务框架卸载成功")
    public void UninstallService() throws Exception
    {
        //sleep(1000*50);//安装完成后需要重启手机，等待重启
        isLock();
        //exitApp(AppInfo.PACKAGE_GOOGLEINSTALLER);
        UiSelector chufajianting1=new UiSelector().resourceId("fsdf");
        UiSelector chufajianting2=new UiSelector().resourceId("fsdf");
        UiSelector chufajianting3=new UiSelector().resourceId("fsdf");
        sleep(1000*5);
        //启动谷歌安装器
        startApp(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.ACTIVITY_GOOGLEINSTALLER);
        sleep(4000);
        UiSelector uninstall=new UiSelector().resourceId("com.howie.gserverinstall:id/btn_install");
        sleep(1000);
        if(!new UiObject(uninstall).exists()){
            startApp(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.ACTIVITY_GOOGLEINSTALLER);
            sleep(1000);
        }
        UiObject uninstallButton=new UiObject(uninstall);
        sleep(1000);
        uninstallButton.clickAndWaitForNewWindow();
        sleep(2000);
        UiObject unInstall=new UiObject(new UiSelector().resourceId("android:id/button1"));
        unInstall.click();//点击卸载
        sleep(1000*10);
        //若出现安装则说明卸载
        UiObject isUninstall=new UiObject(new UiSelector().resourceId("com.howie.gserverinstall:id/btn_install"));
        assertTrue(isUninstall.waitForExists(1000*5));
    }

    @Steps("谷歌安装器--退出应用")
    @Expectation("退出应用成功")
    public void CloseApp() throws Exception
    {
        pressHome();
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.ACTIVITY_GOOGLEINSTALLER);
        sleep(1000 * 3);
        pressBack();
        sleep(1000*2);
        UiObject installButton = new UiObject(new UiSelector().resourceId("com.howie.gserverinstall:id/btnInstall"));
        assertTrue(!installButton.waitForExists(1000 * 4));
        exitApp(AppInfo.PACKAGE_GOOGLEINSTALLER);
    }


}

