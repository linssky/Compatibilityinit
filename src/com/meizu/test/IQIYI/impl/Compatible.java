package com.meizu.test.IQIYI.impl;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.test.IQIYI.imple.ICompatible;
import com.meizu.test.IQIYI.testcase.PubScipt;

/**
 * Created by wuchaolin on 2016-4-5.
 */
public class Compatible extends PubScipt implements ICompatible{

    @Override
    public void Sweep() throws Exception {
        UiSelector selector3=new UiSelector().resourceId(ID_ADD);//获取主界面右上角加号控件
        UiObject add=new UiObject(selector3);
        add.clickAndWaitForNewWindow(2*1000);
        UiSelector selector=new UiSelector().resourceId("com.qiyi.video:id/popup_scan");//扫一扫selector
        UiObject sweep=new UiObject(selector);
        sweep.clickAndWaitForNewWindow(2*1000);
        UiSelector selector1=new UiSelector().resourceId("com.qiyi.video:id/wb_title").text("扫一扫帮助");//扫一扫帮助控件
        UiSelector selector2=new UiSelector().resourceId("com.qiyi.video:id/textview_title").text("扫一扫");//扫一扫控件
        UiObject help=new UiObject(selector1);//扫一扫帮助
        UiObject scan=new UiObject(selector2);//扫一扫
        assertTrue(help.waitForExists(4*1000) || scan.waitForExists(4*1000));
    }

    @Override
    public void shotphoto() throws Exception {
        intoInformation();
        sleep(500);
        click("com.qiyi.video:id/birth_layout");//此处有风险，无法抓取弹框的控件，只能点原来的控件
        UiSelector selector=new UiSelector().resourceId(ID_MEIZUCAMER);
        UiObject MZCamer=new UiObject(selector);
        assertTrue(MZCamer.waitForExists(5*1000));
    }

}
