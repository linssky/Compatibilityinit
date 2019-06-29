package com.meizu.test.ThirdPartySanity.impl;

import com.android.uiautomator.core.*;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.test.ThirdPartySanity.testcase.AccountInfo;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import java.io.IOException;

import static com.meizu.test.ThirdPartySanity.testcase.AppInfo.PACKAGE_WEIBO;

/**
 * Created by wuchaolin on 2016-6-6.
 */
public class Weibo extends Common {
    @Steps("新浪微博：1.进入微博首页"
            + "2.点击登录"
            + "3.输入账号密码，登录")
    @Expectation("3.登录成功")
    // TODO: 2016/3/24  登录验证码问题
    public void weiboLogin() throws AutoException, IOException, InterruptedException, UiObjectNotFoundException {
        UiSelector test=new UiSelector().text("2154565");//触发监听器
        UiObject enterWeibo = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/iv_navigater_clickable"));
        if (enterWeibo.exists()) {
            enterWeibo.clickAndWaitForNewWindow();
        }
        sleep(1000*2);
        UiObject notk=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/btn_3").text("不了，谢谢"));//弹出的“给我评分”窗口
        if(notk.exists()){
            notk.clickAndWaitForNewWindow();
        }
        sleep(1000*2);
        UiObject my = new UiObject(new UiSelector().description("我的资料"));
        my.clickAndWaitForNewWindow();
        sleep(1000);

        UiObject land = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/btn_login").text("登录"));
        if (!land.waitForExists(1000 * 3)) {
            System.out.print("already landed.");
        }

            try {
                land.clickAndWaitForNewWindow(1000 * 2);
                sleep(1000);
                UiObject accont = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/etLoginUsername"));
                UiObject password = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/etPwd"));
                ShellUtil.setUtf7Input();//设置输入法
               // Configurator configurator=Configurator.getInstance();
                //configurator.setKeyInjectionDelay(500);
                accont.setText(Utf7ImeHelper.e(AccountInfo.ACCOUNT_WEIBO));
                password.setText(Utf7ImeHelper.e(AccountInfo.PASSWORD_WEIBO));
                sleep(1000 * 2);
                // UiObject landed = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/bnLogin").text("登录"));
                UiObject landed = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/rlLogin"));
                landed.clickAndWaitForNewWindow();
                sleep(1000*3);
               /* int time=0;
                 while(time<100)
                 {
                     if(landed.exists())
                     {
                         landed.clickAndWaitForNewWindow();
                         sleep(1000*2);
                         time++;
                     }
                     else
                         break;
                 }*/
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }

        sleep(1000*3);
        //UiObject open=new UiObject(new UiSelector().description("打开发布面板"));
        UiObject landed = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/rlLogin"));//点击登录
        assertTrue("fail to login",!landed.waitForExists(1000*2));
    }

    @Steps("新浪微博：1.进入微博"
            + "2.点击“+”"
            + "3.点击摄像头图标，进入微博相机"
            + "4.后置摄像头，点击拍照按钮"
            + "5.下一步，回到发微博界面")
    @Expectation("正常调用相机，回到微博发送界面")
    public void sendPhotoWeibo() throws AutoException, UiObjectNotFoundException, IOException, InterruptedException {//true错
        exitApp(AppInfo.PACKAGE_WEIBO);
        sleep(1000);
        pressHome();
        sleep(1000*2);
        //STARTAPP(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        startApp(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        sleep(1000*5);
        UiSelector object=new UiSelector().description("打开发布面板");
        UiObject moreF = new UiObject(new UiSelector().description("打开发布面板"));
        assertTrue(moreF.waitForExists(1000 * 3));
            moreF.clickAndWaitForNewWindow();
            sleep(1000*3);
            UiObject takePic = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/composer_item_text").instance(1));//展开加号后的“照片/视频”页面
            takePic.clickAndWaitForNewWindow();
            sleep(1000 * 5);
            UiObject carme = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/photo_album_grideview_camera_image"));
            carme.clickAndWaitForNewWindow();
            sleep(1000 * 5);
            UiObject takeit = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/camera_bottom_middle"));//相机拍照按钮
            assertTrue("open carme fail",takeit.waitForExists(1000*3));
            takeit.clickAndWaitForNewWindow();
            sleep(1000*5);
            UiObject next = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/photoalbum_tool_bar_right_btn"));
            assertTrue("take photo fail",next.waitForExists(1000*3));
            next.clickAndWaitForNewWindow();
            sleep(1000*3);
            UiObject send= new UiObject(new UiSelector().resourceId("com.sina.weibo:id/titleSave").text("发送"));//微博界面的发送按钮
            assertTrue("use carme fail",send.waitForExists(1000 * 4));

    }

    @Steps("新浪微博："
            +"1.进入相机，拍摄一张图片"
            + "2.进入微博"
            + "3.点击“+”"
            + "4.点击摄像头图标，进入照片视频"
            + "5.选择第一张照片，下一步"
            + "6.下一步，回到发微博界面"
    +"发送图片微博")
    @Expectation("正常发送图片微博")
    public void sendPicWeibo() throws InterruptedException, UiObjectNotFoundException {
        exitApp(PACKAGE_WEIBO);
        sleep(1000*2);
        startApp("com.meizu.media.camera",".CameraLauncher");
        sleep(1000*2);
        UiSelector Take=new UiSelector().description("拍照");
        UiObject take=new UiObject(Take);
        assertTrue("System camera is broken",take.waitForExists(1000*3));
        take.clickAndWaitForNewWindow();
        exitApp("com.meizu.media.camera");
        sleep(1000*2);
        pressHome();
        sleep(1000);
        startApp(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        sleep(1000*5);
        UiObject moreF = new UiObject(new UiSelector().description("打开发布面板"));
        assertTrue(moreF.waitForExists(1000 * 3));
            moreF.clickAndWaitForNewWindow();
            sleep(1000*3);
            UiObject takePic = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/composer_item_text").instance(1));
            takePic.clickAndWaitForNewWindow();
            sleep(1000 * 2);
            UiObject pic=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/photo_album_grideview_item_select").instance(0));//第一张图片右上角的选中圈圈
            pic.clickAndWaitForNewWindow();
            sleep(1000*2);
            UiObject next = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/photoalbum_tool_bar_right_btn").text("下一步(1)"));
            next.clickAndWaitForNewWindow();
            sleep(1000*2);
            UiObject next1=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/photoalbum_tool_bar_right_btn").text("下一步(1)"));
            assertTrue("trun fail",next1.waitForExists(1000*3));
            next1.clickAndWaitForNewWindow();
            sleep(1000*2);
            UiObject setText=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/edit_view").text("分享新鲜事..."));//输入字符
            UiObject send= new UiObject(new UiSelector().resourceId("com.sina.weibo:id/titleSave").text("发送"));//微博界面的发送按钮
            setText.setText(Utf7ImeHelper.e("Hello World"));
            sleep(1000);
            send.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject my = new UiObject(new UiSelector().description("我的资料"));
            my.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject myweibo=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/cabWeibo"));//“我”的界面的微博
            myweibo.clickAndWaitForNewWindow();
            sleep(1000*3);

            UiObject HW=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/tvItemContent").text("Hello World"));//发出的微博
            assertTrue("send weibo fail",HW.waitForExists(1000*5));
            HW.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject more=new UiObject(new UiSelector().description("更多操作按钮"));
            more.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject del=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/tv_dialog_item").text("删除"));
            del.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject sure=new UiObject(new UiSelector().className("android.widget.TextView").text("确定"));//删除所发微博
            sure.clickAndWaitForNewWindow();
            sleep(1000*2);

    }

    @Steps("新浪微博："
            + "1.进入微博"
            + "2.点击“+”"
            + "3.点击文字，进入文字微博发送界面"
            + "5.选择位置，"
            + "6.输入Test Address，发送微博"
            +"7.删除所发微博")
    @Expectation("正常选择位置，正常发送微博")
    public void address() throws InterruptedException, UiObjectNotFoundException {
       // STARTAPP(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        exitApp(PACKAGE_WEIBO);
        sleep(1000*2);
        startApp(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        sleep(1000*6);
        UiSelector f=new UiSelector().description("打开发布面板");
        UiObject moreF = new UiObject(f);
        sleep(1000*3);
        assertTrue("send message failed",moreF.exists());
            moreF.clickAndWaitForNewWindow();
            UiObject address = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/composer_item_text").text("文字"));
            address.clickAndWaitForNewWindow();
            sleep(1000*3);
            UiObject add=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/tv_location_imageivew").instance(0));//显示位置的控件
            add.clickAndWaitForNewWindow();
            sleep(1000*2);
            UiCollection list=new UiCollection(new UiSelector().resourceId("com.sina.weibo:id/lvUser"));
            UiObject addre=list.getChild(new UiSelector().className("android.widget.LinearLayout").index(3));//第三个地址
            addre.clickAndWaitForNewWindow();
            sleep(1000*2);
            UiObject setText=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/edit_view").text("分享新鲜事..."));//输入字符
            UiObject send= new UiObject(new UiSelector().resourceId("com.sina.weibo:id/titleSave").text("发送"));//微博界面的发送按钮
            ShellUtil.setUtf7Input();
            setText.setText(Utf7ImeHelper.e("Test Address"));
            sleep(1000);
            send.clickAndWaitForNewWindow();
            sleep(1000*2);

            UiObject my = new UiObject(new UiSelector().description("我的资料"));
            my.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject myweibo=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/cabWeibo"));//“我”的界面的微博
            myweibo.clickAndWaitForNewWindow();
            sleep(1000*3);

            UiObject AW=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/tvItemContent").text("Test Address"));//发出的微博
            assertTrue("send weibo fail",AW.waitForExists(1000*5));
            AW.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject more=new UiObject(new UiSelector().description("更多操作按钮"));
            more.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject del=new UiObject(new UiSelector().resourceId("com.sina.weibo:id/tv_dialog_item").text("删除"));
            del.clickAndWaitForNewWindow();
            sleep(1000);
            UiObject sure=new UiObject(new UiSelector().className("android.widget.TextView").text("确定"));//删除所发微博
            sure.clickAndWaitForNewWindow();
            sleep(1000*2);
    }

    @Steps("新浪微博："
            + "1.进入微博"
            + "2.在主界面滑动微博")
    @Expectation("正常滑动无闪退等")
    public void scorllList() throws InterruptedException, UiObjectNotFoundException {
        exitApp(PACKAGE_WEIBO);
        sleep(1000*2);
        startApp(PACKAGE_WEIBO, AppInfo.ACTIVITY_WEIBO);
        sleep(1000*5);
        UiObject home=new UiObject(new UiSelector().description("首页"));
        home.clickAndWaitForNewWindow();
        sleep(1000*3);
        sleep(1000*3);
        UiScrollable list=new UiScrollable(new UiSelector().className("android.widget.ListView").resourceId("com.sina.weibo:id/lvUser"));
        list.scrollToEnd(3);
        sleep(1000*3);
        list.scrollToEnd(3);
        list.scrollToBeginning(3);
        UiObject mark=new UiObject(new UiSelector().description("找朋友"));
        assertTrue("app is broken",mark.waitForExists(1000*5));
        exitApp(AppInfo.PACKAGE_WEIBO);
    }


}
