package com.meizu.test.QQLOL;

import android.graphics.Rect;
import com.android.uiautomator.core.*;
import com.meizu.automation.Expectation;
import com.meizu.automation.Priority;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.common.APPINFO;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016-4-11.
 */
public class SanityTestCase extends Common{
    String back="com.tencent.qt.qtl:id/nav_left_button";//左上角返回resousid

    @Override
    protected void runTest() throws Throwable {
        try
        {
            super.runTest();
            ShellUtil.sendResult(getName(), true);
        }
        catch (Throwable e)
        {
            ShellUtil.sendResult(getName(), false);
            throw new Throwable(e);

            }
        }


    @Override
    public void setUp() throws AutoException, UiObjectNotFoundException {
        if(isExistByPackageName(APPINFO.PACKAGE_QQLOL)) {
            exitApp(APPINFO.PACKAGE_QQLOL);
        }
        ShellUtil.setUtf7Input();
        sleep(500);
        startApp(APPINFO.PACKAGE_QQLOL, APPINFO.ACTIVITY_QQLOL);
        sleep(500);
        watcher();
        UiSelector phoneQQ=new UiSelector().resourceId("com.tencent.qt.qtl:id/login_quick");
        UiObject PhoneQQ=new UiObject(phoneQQ);
        if(PhoneQQ.waitForExists(5*1000)){
            PhoneQQ.clickAndWaitForNewWindow(3*1000);
            UiObject land=new UiObject(new UiSelector().className("android.widget.Button").resourceId("com.tencent.mobileqq:id/name"));
            land.clickAndWaitForNewWindow(5*1000);
            sleep(3*1000);
            if(isExistById("com.tencent.qt.qtl:id/notice_close_pic")){//若广告弹窗存在
                click("com.tencent.qt.qtl:id/notice_close_pic");
                sleep(500);
            }
        }
    }


    @Override
    public void tearDown() throws Exception {
        ShellUtil.setSystemInput();
        exitApp(APPINFO.PACKAGE_QQLOL);
        super.tearDown();
    }

    //用于断言的两个控件
    UiSelector news=new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_news").text("资讯");//主界面的资讯栏
    UiObject NEWS=new UiObject(news);




    @Steps("登陆")
    @Expectation("能正常登录")
    @Priority
    public void test001() throws UiObjectNotFoundException, AutoException {
        ShellUtil.clearCache(APPINFO.PACKAGE_QQLOL);
        setUp();
        assertTrue(NEWS.waitForExists(3*1000));
    }

    @Steps("查看资讯详情")
    @Expectation("进入资讯详情页面")
    @Priority
    public void test002() throws UiObjectNotFoundException {
        UiScrollable list=new UiScrollable(new UiSelector().resourceId("android:id/list"));//资讯内容的滚动控件
        int numLong=list.getChildCount();
        int numA=random(numLong);
        list.scrollToEnd(3);
        UiObject any=list.getChildByInstance(new UiSelector().resourceId("com.tencent.qt.qtl:id/news_item_root"),numA);
        any.clickAndWaitForNewWindow(2*1000);//是否正常进入资讯页面
        assertTrue(new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/nav_title").text("资讯详情")).waitForExists(3*1000));
        pressBack();
        sleep(500);
        assertTrue(NEWS.waitForExists(3*1000));//是否正常退至主界面
    }

    @Steps("进入视频页面播放视频然后返回到主界面")
    @Expectation("成功回到主界面")
    @Priority
    public void test003() throws UiObjectNotFoundException, AutoException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        UiScrollable top=new UiScrollable(new UiSelector().className("android.widget.LinearLayout").instance(4));//顶栏最新、赛事选项控件
        UiObject video=top.getChild(new UiSelector().className("android.widget.RelativeLayout").index(3));
        video.clickAndWaitForNewWindow(2*1000);
        UiObject videoPlay=new UiObject(new UiSelector().className("android.webkit.WebView"));
        assertTrue(videoPlay.waitForExists(3*1000));//判断视频播放栏控件是否出现

        Rect Point=videoPlay.getBounds();
        int topPoint=Point.top;
        int leftPoint=Point.left;
        click(leftPoint+200,topPoint+200);//无法获取播放列表控件，只能坐标点击
        sleep(2*1000);
        click(x/2,y/3);//同样为web控件无法抓取
        sleep(2*1000);
        UiObject palywind=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/player"));
        UiObject other=new UiObject(new UiSelector().className("android.widget.TextView").resourceId("android:id/message"));//播放失败的控件，为服务器问题
        assertTrue(palywind.waitForExists(2*1000)|other.waitForExists(2*1000));//判断是否出现全屏播放窗口

        if(other.waitForExists(1000)){//若错误提示出现，先back一次
            pressBack();
            sleep(500);
        }
        UiObject backPlay=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/img_back"));//全屏播放界面返回
        if(!backPlay.waitForExists(1000)){
            click(x/2,y/2);
        }
        backPlay.clickAndWaitForNewWindow(1000);//点击左上角返回
        UiObject backWind =new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));//资讯界面返回
        backWind.clickAndWaitForNewWindow();
        assertTrue(NEWS.waitForExists(3*1000));//是否正常退至主界面
    }

    @Steps("搜索点击热词")
    @Expectation("屏幕显示该关键字相关的搜索结果")
    @Priority
    public void test004() throws AutoException, UiObjectNotFoundException {
        click("com.tencent.qt.qtl:id/search_input");//点搜索
        sleep(1000);
        UiCollection hot=new UiCollection(new UiSelector().resourceId("com.tencent.qt.qtl:id/search_popular_container"));
        int numLong=hot.getChildCount();
        int a=random(numLong);
        UiObject any=hot.getChild(new UiSelector().className("android.widget.LinearLayout").index(a));//点随机热搜
        any.clickAndWaitForNewWindow();
        sleep(2*1000);
        assertTrue(isExistById("android:id/list"));//判断是否出现list列表
    }

    @Steps("输入'电竞'进行搜索")
    @Expectation("显示关键字相关的搜索结果")
    @Priority
    public void test005() throws AutoException, UiObjectNotFoundException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        click("com.tencent.qt.qtl:id/search_input");//点搜索
        sleep(1000);
        UiObject edit=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/search_input"));
        edit.setText(Utf7ImeHelper.e("电竞"));
        ShellUtil.setSystemInput();
        edit.clickAndWaitForNewWindow();
        click(x-100,y-100);
        sleep(2*1000);
        UiObject list=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/news_item_root"));
        assertTrue(list.waitForExists(2*1000));//判断是否出现list列表
        sleep(1000);
        UiObject cannel=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/btn_cancel").text("取消"));
        cannel.clickAndWaitForNewWindow();
        assertTrue(NEWS.waitForExists(3*1000));//是否正常退至主界面
    }


    @Steps("好友栏遍历")//没玩过游戏的账号好友栏是空
    @Expectation("正常遍历无报错")
    @Priority
    public void test006() throws UiObjectNotFoundException, AutoException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        UiObject friend = new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_friend"));
        friend.clickAndWaitForNewWindow();
        UiObject gfriend = new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tv_friend_group_name").text("掌盟好友"));
        UiObject zfriend=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tv_friend_group_name").text("游戏好友"));
        if (gfriend.waitForExists(2 * 1000)) {
            UiObject edit=new UiObject(new UiSelector().className("android.widget.EditText").resourceId("com.tencent.qt.qtl:id/search_input"));
            edit.setText(Utf7ImeHelper.e("电竞"));ShellUtil.setSystemInput();
            edit.clickAndWaitForNewWindow();
            click(x-100,y-100);
            sleep(2*1000);
            UiObject canel=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/btn_cancel").text("取消"));
            canel.clickAndWaitForNewWindow();
            assertTrue(gfriend.waitForExists(3 * 1000));

            gfriend.clickAndWaitForNewWindow();
            sleep(500);
            zfriend.clickAndWaitForNewWindow();
            sleep(500);
            click("com.tencent.qt.qtl:id/indicator_conversation");
            sleep(500);
            click("com.tencent.qt.qtl:id/indicator_friend");
            assertTrue(gfriend.waitForExists(3 * 1000));
        }else {
            System.out.print("NOT PLAY LOL");
        }
    }

    @Steps("发现-粉丝俱乐部遍历")//没玩过游戏的账号发现栏是空
    @Expectation("正常遍历无报错")
    @Priority
    public void test007() throws UiObjectNotFoundException, AutoException {
        new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_discovery").text("发现")).clickAndWaitForNewWindow();
        UiObject fans=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/entry_clubs").text("粉丝俱乐部"));
        if(fans.waitForExists(3*1000)){
            fans.clickAndWaitForNewWindow();
            UiObject image=new UiObject(new UiSelector().className("android.widget.ImageView").clickable(true));
            if(image.waitForExists(3*1000)){//如果存在广告页面，点掉
                image.clickAndWaitForNewWindow();
            }
            UiObject skip=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/dismiss").text("跳过"));
            if(skip.waitForExists(3*1000)){//如果存在跳转页，点掉
                skip.clickAndWaitForNewWindow();
            }
            UiObject all=new UiObject(new UiSelector().className("android.widget.TextView").text("全部战队"));
            all.waitForExists(3*1000);
            all.clickAndWaitForNewWindow();
            sleep(1000);
            click(back);
            sleep(1000);
            click(back);//俱乐部界面左上角返回
            assertTrue(fans.waitForExists(3*1000));
        }else {
            System.out.print("NOT PLAY LOL");
        }
    }

    @Steps("发现-英雄资料遍历")//没玩过游戏的账号发现栏是空
    @Expectation("正常遍历无报错")
    @Priority
    public void test008() throws UiObjectNotFoundException, AutoException {
        new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_discovery").text("发现")).clickAndWaitForNewWindow();
        UiObject hero=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/top_one").text("英雄资料"));
        if(hero.waitForExists(3*1000)){
            hero.clickAndWaitForNewWindow();
            sleep(1000);
            UiCollection list=new UiCollection(new UiSelector().resourceId("com.tencent.qt.qtl:id/gridview"));
            int listLong=list.getChildCount();
            int a=random(listLong);
            UiObject cli=list.getChild(new UiSelector().index(a));
            cli.clickAndWaitForNewWindow();
            UiObject skill=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tv_tab_skill").text("技能"));
            assertTrue("skip to hero fail",skill.waitForExists(3*1000));//以技能栏为判断
            UiObject line=new UiObject(new UiSelector().className("android.widget.LinearLayout").instance(2));
            UiObject lineo=line.getChild(new UiSelector().className("android.widget.LinearLayout").index(1));
            int lineoLong=lineo.getChildCount();
            System.out.print("   is     "+lineoLong);
            for(int i=0; i<lineoLong;i++){
                UiObject clic=line.getChild(new UiSelector().className("android.widget.FrameLayout").index(i));
                clic.clickAndWaitForNewWindow(1000);
                sleep(500);
            }
            assertTrue(skill.waitForExists(3*1000));//以技能栏为判断
            click(back);
            sleep(1000);
            click(back);
            sleep(1000);
            assertTrue(hero.waitForExists(3*1000));
        }else {
            System.out.print("NOT PLAY LOL");
        }
    }

    @Steps("我-功能遍历")
    @Expectation("正常遍历无报错")
    @Priority
    public void test009() throws UiObjectNotFoundException {
        new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_me").text("我")).clickAndWaitForNewWindow();
        UiObject abli=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tv_tab_ability").text("能力"));
        UiObject asset=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tv_tab_asset").text("资产"));
        if (abli.waitForExists(3*1000)){
            abli.clickAndWaitForNewWindow();
            sleep(500);
            asset.clickAndWaitForNewWindow();
            sleep(500);
            assertTrue(abli.waitForExists(3*1000));
        }else {
            System.out.print("NOT PLAY LOL");
        }

    }

    @Steps("我-设置退出")
    @Expectation("正常退出")
    @Priority
    public void test010() throws UiObjectNotFoundException {
        new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/tab_me").text("我")).clickAndWaitForNewWindow();
        UiObject set=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
        if (set.waitForExists(3*1000)){
            set.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject exit=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/preference_imagebutton"));
            assertTrue(exit.waitForExists(3*1000));
            exit.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject sure=new UiObject(new UiSelector().resourceId("android:id/button1").text("确定"));
            sure.clickAndWaitForNewWindow();
            UiObject PhoneQQ=new UiObject(new UiSelector().resourceId("com.tencent.qt.qtl:id/login_quick"));
            assertTrue(PhoneQQ.waitForExists(3*1000));
        }else {
            System.out.print("NOT PLAY LOL");
        }
    }


}
