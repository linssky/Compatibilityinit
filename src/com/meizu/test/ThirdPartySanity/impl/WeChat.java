package com.meizu.test.ThirdPartySanity.impl;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016-6-2.
 */
public class WeChat extends Common{


    @Steps("1.若未安装微信，先在应用中心安装微信"
            +"2.进入微信——登录——使用其他方式登录——输入账号密码——登录"    )
    @Expectation("正常登录")
    public void Login() throws Exception {
        sleep(1000);
        isLand();
        UiObject add = new UiObject(new UiSelector().description("更多功能按钮"));//微信界面右上角加号
        assertTrue("landing fail!", add.waitForExists(1000 * 5));
    }

    @Steps("进入通讯录——搜索联系人'test'——发送hello world给他")
    @Expectation("信息发送正常")
    public void sendMessage() throws Exception {
        exitApp(AppInfo.PACKAGE_WECHAT);
        sleep(1000*2);
        pressHome();
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_WECHAT,AppInfo.ACTIVITY_WECHAT);//启动微信
        sleep(1000*5);
        isLand();
        enterWeiChat();//搜索微信联系人并进入微信聊天界面
        UiObject setsend=new UiObject(new UiSelector().description("切换到键盘"));
        if(setsend.exists()){
            setsend.clickAndWaitForNewWindow();
        }
        UiObject sendText=new UiObject(new UiSelector().className("android.widget.EditText"));//聊天界面的发送框
        sendText.setText(Utf7ImeHelper.e("Hello World"));
        UiObject send=new UiObject(new UiSelector().className("android.widget.Button").text("发送"));
        send.clickAndWaitForNewWindow();
        sleep(1000);
        UiObject textH=new UiObject(new UiSelector().className("android.widget.TextView").text("Hello World"));//发出信息后的控件
        assertTrue("send message fail",textH.waitForExists(1000*2));
        deleteWcContext();//删除微信聊天记录

    }

    @Steps("和test的聊天界面——右下角加号——发送图片——拍照——发送")
    @Expectation("图片发送正常")
    public void takePhotoAndSend() throws Exception {
        exitApp(AppInfo.PACKAGE_WECHAT);
        sleep(1000*2);
        pressHome();
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_WECHAT,AppInfo.ACTIVITY_WECHAT);//启动微信
        sleep(1000*5);
        isLand();
        enterWeiChat();//搜索微信联系人并进入微信聊天界面
        UiObject more=new UiObject(new UiSelector().description("更多功能按钮，已折叠"));
        more.clickAndWaitForNewWindow();
        UiObject pic=new UiObject(new UiSelector().className("android.widget.TextView").text("图片"));
        pic.clickAndWaitForNewWindow();
        UiSelector  tp=new UiSelector().className("android.widget.TextView").text("拍摄照片");
        UiObject takePic=new UiObject(new UiSelector().className("android.widget.TextView").text("拍摄照片"));//微信界面拍摄
        takePic.clickAndWaitForNewWindow();
        UiSelector takep=new UiSelector().resourceId("com.meizu.media.camera:id/shutter_btn");//系统界面拍摄
        sleep(1000);
        UiObject TakePic=new UiObject(takep);
        TakePic.clickAndWaitForNewWindow(1000*2);
        UiObject ok=new UiObject(new UiSelector().resourceId("com.meizu.media.camera:id/btn_done"));
        ok.clickAndWaitForNewWindow();
        sleep(1000);
        UiObject sendPic=new UiObject(new UiSelector().className("android.widget.TextView").text("发送"));
        sendPic.clickAndWaitForNewWindow();
        sleep(1000);
        UiObject okPic=new UiObject(new UiSelector().description("图片"));//发出去的图片
        assertTrue("Picture send fail",okPic.waitForExists(1000*5));
        deleteWcContext();//删除微信聊天记录


    }

    @Steps("和test的聊天界面——切换到语音——长按语音——发送")
    @Expectation("语音发送正常")
    public void sendVoice() throws Exception {
        exitApp(AppInfo.PACKAGE_WECHAT);
        sleep(1000*2);
        pressHome();
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_WECHAT,AppInfo.ACTIVITY_WECHAT);//启动微信
        sleep(1000*5);
        enterWeiChat();//搜索微信联系人并进入微信聊天界面
        UiObject press=new UiObject(new UiSelector().description("切换到按住说话"));//切换到按住说话的图标
        if(press.waitForExists(1000*2)){
            press.clickAndWaitForNewWindow();
        }
        UiObject pTalk=new UiObject(new UiSelector().className("android.widget.Button").description("按住说话"));
        int x=(pTalk.getBounds().right-pTalk.getBounds().left)/2+pTalk.getBounds().left;
        int y=(pTalk.getBounds().bottom-pTalk.getBounds().top)/2+pTalk.getBounds().top;
        swipe(x,y,x,y,100);
        sleep(1000*2);
        UiObject voice=new UiObject(new UiSelector().resourceId("com.tencent.mm:id/a6s"));
        assertTrue("send voice fail",voice.waitForExists(1000*5));
        deleteWcContext();//删除微信聊天记录

    }

    @Steps("和test的聊天界面——右下角加号——视频聊天")
    @Expectation("正常跳转")
    public void videoChat() throws Exception {
        exitApp(AppInfo.PACKAGE_WECHAT);
        sleep(1000*2);
        pressHome();
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_WECHAT,AppInfo.ACTIVITY_WECHAT);//启动微信
        sleep(1000*5);
        enterWeiChat();//搜索微信联系人并进入微信聊天界面
        UiObject more=new UiObject(new UiSelector().description("更多功能按钮，已折叠"));
        more.clickAndWaitForNewWindow();
        UiObject video=new UiObject(new UiSelector().className("android.widget.TextView").text("视频聊天"));
        video.clickAndWaitForNewWindow();
        sleep(1000);
        UiObject videoText=new UiObject(new UiSelector().className("android.widget.TextView").text("视频聊天"));
        videoText.clickAndWaitForNewWindow();
        sleep(1000);
        UiObject canle=new UiObject(new UiSelector().text("取消"));
        assertTrue("returen fail",canle.waitForExists(1000*3));

    }


}
