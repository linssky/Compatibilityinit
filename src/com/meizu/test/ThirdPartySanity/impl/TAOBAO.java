package com.meizu.test.ThirdPartySanity.impl;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import com.meizu.automation.By;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.automation.service.Element;
import com.meizu.test.ThirdPartySanity.testcase.AccountInfo;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import java.io.IOException;

/**
 * Created by wuchaolin on 2016-6-6.
 */
public class TAOBAO extends Common{


    @Steps("淘宝：输入账户密码")
    @Expectation("可正常输入")
    public void Login() throws AutoException, InterruptedException, UiObjectNotFoundException, IOException {
        /*exitApp(AppInfo.PACKAGE_WECHAT);
        sleep(1000*2);
        pressHome();
        sleep(1000*2);
        ShellUtil.uninstallApp(AppInfo.PACKAGE_TAOBAO);
        sleep(1000*15);
        //STARTAPP(AppInfo.PACKAGE_TAOBAO,AppInfo.ACTIVITY_TAOBAO);*/

        UiSelector test=new UiSelector().text("51153546");
        UiSelector test1=new UiSelector().text("51153546");
        findElement(By.byDescription("我的淘宝")).click();
        sleep(1000*3);
        Element account = findElementById("com.taobao.taobao:id/accountCompleteTextView");//手机号/会员名/邮箱
        Element content = findElementById("com.taobao.taobao:id/content");//密码
        if(account.exists()) {
            ShellUtil.setUtf7Input();
            account.setText(Utf7ImeHelper.e(AccountInfo.ACCOUNT_TAOBAO));
            sleep(1000);
            content.setText(Utf7ImeHelper.e(AccountInfo.PASSWORD_TAOBAO));

            ShellUtil.setSystemInput();
        }
        assertTrue(new UiObject(new UiSelector().packageName(AppInfo.PACKAGE_TAOBAO)).waitForExists(1000*10));
    }

    @Steps("淘宝：扫一扫")
    @Expectation("进入页面正常")
    public void sweep() throws AutoException, InterruptedException, UiObjectNotFoundException {
        //STARTAPP(AppInfo.PACKAGE_TAOBAO,AppInfo.ACTIVITY_TAOBAO);
        startApp(AppInfo.PACKAGE_TAOBAO,AppInfo.ACTIVITY_TAOBAO);
        sleep(1000*6);
        Element sao=findElementById("com.taobao.taobao:id/bar_search");
        sao.click();
        sleep(1000*3);
        if(isExistByText("相机需要联网, 定位2项权限"))
        {
            clickByText("允许");
        }
        assertTrue(findElementByText("将条码 / 二维码放入框内").waitForExists(1000*5));
        exitApp(AppInfo.PACKAGE_TAOBAO);
    }

    @Steps("淘宝：首页点击搜索框，输入宝贝名称（衣服）搜索")
    @Expectation("正常搜索出结果")
    public void editSearch() throws AutoException, InterruptedException, UiObjectNotFoundException {
        //STARTAPP(AppInfo.PACKAGE_TAOBAO, AppInfo.ACTIVITY_TAOBAO);
        startApp(AppInfo.PACKAGE_TAOBAO, AppInfo.ACTIVITY_TAOBAO);
        sleep(1000*6);
        Element edit = findElementById("com.taobao.taobao:id/home_searchedit");
        edit.click();
        sleep(1000 * 5);
        UiObject closed=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/closeBtn").text("퀺"));
        if(closed.waitForExists(1000*3)){
            closed.clickAndWaitForNewWindow();
        }
        ShellUtil.setUtf7Input();
        UiObject editText = new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/searchEdit"));
        editText.setText(Utf7ImeHelper.e("衣服"));
        click("com.taobao.taobao:id/searchbtn");
        assertTrue(findElement("com.taobao.taobao:id/button_overflow").waitForExists(1000 * 5));
        UiObject exit = new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/closeBtn"));

        if(exit.waitForExists(1000*3)){
        exit.clickAndWaitForNewWindow();
       }
    }

    @Steps("淘宝：滑动搜索结果列表")
    @Expectation("正常滑动无闪退等")
    public void scorllList() throws UiObjectNotFoundException {
        UiScrollable searchList=new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView").resourceId("com.taobao.taobao:id/search_listview"));//搜索界面的整个滑动列表
        searchList.scrollToEnd(3);
        sleep(1000);
        searchList.scrollToEnd(3);
        searchList.scrollToBeginning(4);
        sleep(1000*3);
        UiObject mark=new UiObject(new UiSelector().packageName("com.taobao.taobao"));//搜索列表的综合排序控件，用来判断是否还在搜索界面
        assertTrue("app broken!",mark.waitForExists(1000*5));
        exitApp(AppInfo.PACKAGE_TAOBAO);
    }

    @Steps("淘宝：点击底栏切换各页面")
    @Expectation("正常滑动无闪退等")
    public void tabBottom() throws InterruptedException, UiObjectNotFoundException, AutoException {
        //STARTAPP(AppInfo.PACKAGE_TAOBAO, AppInfo.ACTIVITY_TAOBAO);
        startApp(AppInfo.PACKAGE_TAOBAO, AppInfo.ACTIVITY_TAOBAO);
        sleep(1000*6);
        UiSelector aa=new UiSelector().description("微淘");
        UiObject first=new UiObject(new UiSelector().description("首页"));
        UiSelector bb=new UiSelector().description("微淘");
        UiObject second=new UiObject(new UiSelector().description("微淘"));
        UiSelector bc=new UiSelector().description("微淘");
        UiObject third=new UiObject(new UiSelector().description("问大家"));
//        UiObject fourth=new UiObject(new UiSelector().description("购物车"));
        second.clickAndWaitForNewWindow();
        sleep(1000*3);
        third.clickAndWaitForNewWindow();
        sleep(1000*3);
//        fourth.clickAndWaitForNewWindow();
//        sleep(1000);
        UiObject first1=new UiObject(new UiSelector().description("首页"));//触发监听器
        first.clickAndWaitForNewWindow();
        sleep(1000*3);
        UiObject mark=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/home_searchedit"));
        assertTrue("app broken!",mark.waitForExists(1000*5));
        exitApp(AppInfo.PACKAGE_TAOBAO);
        sleep(1000*2);

    }

}
