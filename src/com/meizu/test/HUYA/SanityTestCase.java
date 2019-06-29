package com.meizu.test.HUYA;

import com.android.uiautomator.core.*;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.common.APPINFO;
import com.meizu.common.PubM;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import java.io.BufferedReader;
import java.util.regex.Pattern;

/**
 * Created by wuchaolin on 2016-4-21.
 */
public class SanityTestCase extends Common {
    PubM pub=new PubM();
    UiObject playwindow=new UiObject(new  UiSelector().resourceId("com.duowan.kiwi:id/tab_title_tv").textContains("聊天"));

    @Steps("初始化操作")
    public void setUp() throws UiObjectNotFoundException, AutoException {
        pub.init(APPINFO.PACKAGE_HUYA,APPINFO.ACTIVITY_HUYA);
        pub.watcher();

        UiSelector my=new UiSelector().resourceId("com.duowan.kiwi:id/title").text("我的");
        UiObject My=new UiObject(my);
        My.clickAndWaitForNewWindow();

        UiSelector unLand=new UiSelector().resourceId("com.duowan.kiwi:id/login_button").text("立即登录");
        UiObject UnLand=new UiObject(unLand);
        if(UnLand.waitForExists(2000)){
            UnLand.clickAndWaitForNewWindow();
            click("com.duowan.kiwi:id/login_qq");
            sleep(2000);
            pub.QQLAND();
            sleep(1000);
            UiObject ok=new UiObject(new UiSelector().className("android.widget.TextView").resourceId("com.duowan.kiwi:id/page_green_bean"));
            if(!ok.exists()){
                System.out.print("land fail!");
            }
        }

    }

    @Steps("初始化结束")
    public void tearDown(){
        ShellUtil.setSystemInput();
        exitApp(APPINFO.PACKAGE_HUYA);
    }



    @Steps("登陆")
    public void test01() throws AutoException, UiObjectNotFoundException {
        ShellUtil.clearCache(APPINFO.PACKAGE_HUYA);
        setUp();
        assertTrue("land fail!",new UiObject(new UiSelector().className("android.widget.TextView").resourceId("com.duowan.kiwi:id/page_green_bean")).waitForExists(2000));
    }

    @Steps("主界面随机播放")
    public void test02() throws UiObjectNotFoundException {
        UiObject tv=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/title").textContains("直播"));
        tv.clickAndWaitForNewWindow();
        UiScrollable list=new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiSelector image=new UiSelector().resourceId("com.duowan.kiwi:id/image");
        int a=list.getChildCount(image);
        System.out.print("number is :"+a);
        list.scrollToEnd(3);
        new UiObject(image).clickAndWaitForNewWindow();
        assertTrue("skip play fail!",playwindow.waitForExists(2000));
    }


    @Steps("搜索播放")
    public void test03() throws UiObjectNotFoundException, AutoException {
        int x= UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        UiObject search=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/action_bar_item_left"));
        search.clickAndWaitForNewWindow();
        UiObject editSearch=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/search_content"));
        editSearch.setText(Utf7ImeHelper.e("骚男"));
        ShellUtil.setSystemInput();
        editSearch.clickAndWaitForNewWindow();
        click(x-100,y-100);
        sleep(1000);
        UiObject ID=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/anchor_a"));
        ID.clickAndWaitForNewWindow();
        assertTrue("jump search fail!",search.waitForExists(1000));
        assertTrue("search fail",ID.waitForExists(1000));
        assertTrue("jump play fail!",playwindow.waitForExists(2000));
    }
    @Steps("评论")
    public void test04() throws AutoException, UiObjectNotFoundException {
        test02();
        UiObject talk=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/input_edit"));
        talk.setText(Utf7ImeHelper.e("666"));
        int x= UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        ShellUtil.setSystemInput(); click(x-100,y-100);
        sleep(1000);
        assertTrue("send fail!", playwindow.waitForExists(1000));

    }

    @Steps("播放界面功能遍历")
    public void test05() throws UiObjectNotFoundException {
        test02();
        new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/tab_title_tv").textContains("详情")).clickAndWaitForNewWindow();//点详情
        sleep(500);
        new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/tab_title_tv").textContains("推荐")).clickAndWaitForNewWindow();//点推荐
        sleep(500);
        new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/tab_title_tv").textContains("贵宾")).clickAndWaitForNewWindow();//点贵宾
        sleep(500);
        assertTrue("app is broken",playwindow.waitForExists(1000));
    }

    @Steps("跳转至全屏")
    public void test06() throws UiObjectNotFoundException {
        test02();
        UiObject fullS=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/full_screen"));//全屏按键
        UiObject play=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/media_area"));//播放小窗口
        if(!fullS.waitForExists(1000)){
            play.clickAndWaitForNewWindow();
        }
        fullS.clickAndWaitForNewWindow();
        assertTrue("skip fail!",new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/landscape_info_bar")).waitForExists(1000));
    }

    @Steps("全屏界面调节遍历")
    public void test07() throws UiObjectNotFoundException {
        test06();
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        swipe(x*2/3,y*2/3,x*2/3,y/3,40);//音量加
        swipe(x*2/3,y/3,x*2/3,y*2/3,40);//音量减
        swipe(x/3,y*2/3,x/3,y/3,40);//亮度加
        swipe(x/3,y/3,x/3,y*2/3,40);//亮度减
    }
    @Steps("我要直播")
    public void test08() throws UiObjectNotFoundException {
        UiObject fun=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/title").textContains("娱乐"));
        fun.clickAndWaitForNewWindow();
        UiObject cam=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/mobile_live"));
        cam.clickAndWaitForNewWindow();
        UiSelector ed=new UiSelector().resourceId("com.duowan.kiwi:id/et_topic_content");
        UiObject edit=new UiObject(ed);
        edit.setText(Utf7ImeHelper.e("你从未见过的"));
        sleep(1000);
        UiObject playNow=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/fl_topic_text").textContains("户外直播"));
        playNow.clickAndWaitForNewWindow();
        UiObject begain=new UiObject(new UiSelector().className("android.widget.TextView").textContains("开播"));
        begain.clickAndWaitForNewWindow();
        sleep(1000);
        UiSelector s=new UiSelector().resourceId("com.duowan.kiwi:id/guide_tips").textContains("开启全民连麦，与观众现场连线");
        UiObject shuiyin=new UiObject(s);
        if(shuiyin.waitForExists(1000)){
            shuiyin.clickAndWaitForNewWindow();
        }
        pressBack();
        sleep(1000);
    }

    @Steps("头像")
    public void test09() throws UiObjectNotFoundException {
        UiObject im=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/avatar"));
        im.clickAndWaitForNewWindow();
        BufferedReader in;
        Pattern pattern = Pattern.compile("//(//d{3}//)//s//d{3}-//d{4}");



    }


}
