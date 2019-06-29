package com.meizu.test.ThirdPartySanity.impl;

import android.view.KeyEvent;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.test.ThirdPartySanity.testcase.AccountInfo;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016/6/2.
 */
public class AliPay extends Common {


    @Steps("支付宝--点击桌面图标，运行应用")
    @Expectation("支付宝--应用运行成功")
    public void openApp() throws Exception
    {
        //启动支付宝应用
        //STARTAPP(AppInfo.PACKAGE_ALIPAY,AppInfo.VERSION_ALIPAY,AppInfo.APKNAME_ALIPAY,AppInfo.URL_ALIPAY,AppInfo.ACTIVITY_ALIPAY);
        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);//启动支付宝应用
        sleep(5000);
        int i=0;
        while(isExistByText("允许")) {
            if (i > 10) {
                break;
            } else {
                clickByTextContains("允许");
            }
        }
        UiSelector test=new UiSelector().packageName("com.eg.android.AlipayGphone");
        UiObject landingButton=new UiObject(test);
        assertTrue(landingButton.waitForExists(1000 * 4));

    }

    @Steps("支付宝--输入账号进行登陆")
    @Expectation("支付宝--登陆成功")
    public void landing() throws Exception
    {
        clearData(AppInfo.PACKAGE_ALIPAY);//清除登录数据
        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);//启动支付宝应用
        sleep(5000);
        ShellUtil.setUtf7Input();//切换Utf7输入法
        UiSelector test=new UiSelector().resourceId("com.ali.user.mobile.security.ui:id/content");
        UiObject inputAccount=new UiObject(test);
        sleep(2000);
        inputAccount.setText(Utf7ImeHelper.e(AccountInfo.ACCOUNT_ALIPAY));
        ShellUtil.setSystemInput();//切换系统输入法
        click("com.ali.user.mobile.security.ui:id/userPasswordInput");
        sleep(2000);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_1);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_2);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_3);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_4);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_5);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_6);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_T);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_E);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_S);
        UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_T);
        sleep(2000);
        ShellUtil.setUtf7Input();
        UiObject landingButton=new UiObject(new UiSelector().resourceId("com.ali.user.mobile.security.ui:id/loginButtonUnlogin"));
        landingButton.click();
        sleep(2000);
        /*int time=0;
        while(!isExistById("com.alipay.android.phone.openplatform:id/saoyisao_button"))
        {
            if(time>5){
                break;
            }else {
                pressBack();
                time++;
            }
        }
        sleep(2000);
        UiObject testFirstPage=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.openplatform:id/tab_description"));
        assertTrue(testFirstPage.waitForExists(1000 * 4));
        sleep(1000);
        exitApp(AppInfo.PACKAGE_ALIPAY);*/
    }

    @Steps("支付宝--点击扫一扫")
    @Expectation("支付宝--进入扫一扫页面")
    public void Camera() throws Exception
    {

        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);
        sleep(5000);
        //进入支付宝首页
        UiSelector test=new UiSelector().resourceId("com.alipay.android.phone.openplatform:id/tab_description");
        UiObject testFirstPage=new UiObject(test);
        testFirstPage.click();
        sleep(1000*2);
        //点击扫一扫图标
        UiObject scanCamera=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.openplatform:id/saoyisao_layout"));
        assertTrue(scanCamera.waitForExists(1000 * 3));
        scanCamera.click();
        sleep(1000);
        exitApp(AppInfo.PACKAGE_ALIPAY);
    }

    @Steps("支付宝--点击转账")
    @Expectation("支付宝--进入转账页面")
    public void TransMoney() throws Exception
    {
        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);
        sleep(5000);
        //进入支付宝首页
        UiObject testFirstPage=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.openplatform:id/tab_description"));
        testFirstPage.click();
        sleep(1000*2);

        UiObject transMoney=new UiObject(new UiSelector().className("android.widget.TextView").text("转账"));
        assertTrue(transMoney.waitForExists(1000 * 3));
        transMoney.click();
        sleep(1000*5);

        UiObject testBack=new UiObject(new UiSelector().resourceId("com.alipay.mobile.ui:id/title_bar_back_button"));
        testBack.click();
        sleep(2000);
        exitApp(AppInfo.PACKAGE_ALIPAY);
    }

    @Steps("支付宝--点击付款")
    @Expectation("支付宝--进入付款页面")
    public void Pay() throws Exception
    {
        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);
        sleep(5000);
        //进入支付宝首页
        UiObject testFirstPage=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.openplatform:id/tab_description"));
        testFirstPage.click();
        sleep(1000*2);

        UiObject Pay=new UiObject(new UiSelector().className("android.widget.TextView").text("付款"));
        assertTrue(Pay.waitForExists(1000 * 4));
        Pay.click();
        sleep(1000*5);

        pressBack();
        sleep(2000);
        exitApp(AppInfo.PACKAGE_ALIPAY);

    }

    @Steps("支付宝--依次点击底栏上 口碑、朋友和我的")
    @Expectation("支付宝--进入 口碑、朋友和我的页面")
    public void ChangePage() throws Exception
    {
        startApp(AppInfo.PACKAGE_ALIPAY,AppInfo.ACTIVITY_ALIPAY);
        sleep(5000);
        //进入支付宝首页
        /*UiObject test1=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.discovery.o2ohome:id/sigle_tab_bg"));//
        test1.click();
        sleep(1000*15);*/

        UiSelector test2=new UiSelector().resourceId("com.alipay.mobile.socialwidget:id/social_bottom_tab");
        UiObject testFriendsPage=new UiObject(test2);
        testFriendsPage.click();
        sleep(1000*15);

        UiSelector test3=new UiSelector().resourceId("com.alipay.android.phone.wealth.home:id/sigle_tab_bg");
        UiObject testMyPage=new UiObject(test3);
        testMyPage.click();
        sleep(1000*15);

        UiObject testok=new UiObject(new UiSelector().packageName(AppInfo.PACKAGE_ALIPAY));
        assertTrue(testok.waitForExists(1000 * 4));

    }

    @Steps("支付宝--关闭支付宝")
    @Expectation("支付宝--关闭支付宝")
    public void CloseApp() throws Exception
    {
        exitApp(AppInfo.PACKAGE_ALIPAY);
        UiObject isExist=new UiObject(new UiSelector().packageName(AppInfo.PACKAGE_ALIPAY));
        assertTrue(!isExist.waitForExists(1000*2));
        sleep(1000*2);
        //ShellUtil.setSystemInput();
    }

}
