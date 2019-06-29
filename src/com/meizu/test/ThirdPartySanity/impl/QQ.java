package com.meizu.test.ThirdPartySanity.impl;

import com.android.uiautomator.core.*;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.test.ThirdPartySanity.testcase.AccountInfo;
import com.meizu.test.ThirdPartySanity.testcase.AppInfo;
import com.meizu.test.ThirdPartySanity.testcase.Common;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016/6/3.
 */
public class QQ extends Common {


    @Steps("QQ--打开应用")
    @Expectation("QQ--应用打开成功")
    public void OpenApp() throws Exception
    {
        /*sleep(1000*3);
        pressHome();
        sleep(1000*3);
        ShellUtil.clearCache(AppInfo.PACKAGE_QQ);
        sleep(1000*3);
        ShellUtil.uninstallApp(AppInfo.PACKAGE_QQ);
        sleep(1000*15);
        STARTAPP(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);*/
        STARTAPP(AppInfo.PACKAGE_QQ,AppInfo.VERSION_QQ,AppInfo.APKNAME_QQ,AppInfo.URL_QQ,AppInfo.ACTIVITY_QQ);
        //sleep(1000*10);
        sleep(1000*3);
        UiSelector selectIntoLanding2=new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login");
        sleep(1000);
        UiSelector selectIntoLanding3=new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login");
        sleep(1000);
        UiSelector selectIntoLanding4=new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login");
        sleep(1000);
        try {//flyme6的QQ弹框太多，且登录界面有广告，监听器经常会出现点不到情况，所以做了下面的处理
            int bk=0;
            while (!new UiObject(new UiSelector().packageName("com.tencent.mobileqq").className("android.widget.Button").text("跳过").clickable(true)).exists()&&bk<15){
                if(new UiObject(new UiSelector().resourceId("android:id/button1").text("允许").clickable(true)).exists()){
                    new UiObject(new UiSelector().resourceId("android:id/button1").text("允许").clickable(true)).clickAndWaitForNewWindow();
                }
                sleep(1000);
                bk++;
            }
            new UiObject(new UiSelector().packageName("com.tencent.mobileqq").className("android.widget.Button").text("跳过").clickable(true)).clickAndWaitForNewWindow();

        }catch (Exception e){

        }

        UiSelector selectIntoLanding=new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login");
        sleep(1000);
        UiObject IntoLandingPage=new UiObject(selectIntoLanding);
        IntoLandingPage.click();
        sleep(2000);
        UiObject intoLandingPage=new UiObject(new UiSelector().packageName(AppInfo.PACKAGE_QQ));
        assertTrue(intoLandingPage.waitForExists(1000 * 4));
        exitApp(AppInfo.PACKAGE_QQ);

    }

    @Steps("QQ--登陆")
    @Expectation("QQ--登陆成功")
    public void Landing() throws Exception
    {
        //STARTAPP(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        UiObject island=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        if(!island.exists())
        {
        UiSelector selectIntoLanding=new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login");
        UiObject IntoLandingPage=new UiObject(selectIntoLanding);
        IntoLandingPage.click();
        sleep(2000);
        ShellUtil.setUtf7Input();//切换Utf7输入法
        UiObject inputAccount=new UiObject(new UiSelector().className("android.widget.EditText").textContains("QQ号"));
        inputAccount.setText(Utf7ImeHelper.e(AccountInfo.ACCOUNT_QQ));
        sleep(1000);

        UiObject inputPassWard=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/password"));
//        inputPassWard.setText(Utf7ImeHelper.e(AccountInfo.PASSWORD_QQ));
            inputPassWard.setText(AccountInfo.PASSWORD_QQ);
        sleep(1000);

        UiObject landingBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/login"));
        landingBotton.click();
//        ShellUtil.setSystemInput();//切换系统输入法
        sleep(1000*10);
        if(isExistByTextContains("绑定手机号码"))
        {
          click("com.tencent.mobileqq:id/ivTitleBtnLeft");
        }
        sleep(1000*3);
        }
        UiObject intoSearchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/name"));//联系人控件，搜索的控件是：“com.tencent.mobileqq:id/et_search_keyword”
        assertTrue("use carme fail",intoSearchFriend.waitForExists(1000 * 4) );

    }

    @Steps("QQ--给好友发送文字信息")
    @Expectation("QQ--给好友发送文字信息")
    public void SendMessage() throws Exception
    {
        exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        enterChat();//搜索联系人并进入聊天界面, 保证用例进入的是初始界面
        //进入对话信息页面后切换输入法
        ShellUtil.setUtf7Input();//切换Utf7输入法
        UiObject inputMessage=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/input"));
        inputMessage.setText(Utf7ImeHelper.e("test"));
        sleep(1000*2);
        UiObject sendBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/fun_btn"));
        sendBotton.click();
        //ShellUtil.setSystemInput();//切换系统输入法
        //返回对话列表，若出现搜索框，则证明发送成功也没有删除
       /*UiObject backBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/ivTitleBtnLeft"));
        backBotton.click();
        sleep(2000);
        assertTrue("use carme fail",intoSearchFriend.waitForExists(1000 * 4));*/
       //通过判断聊天页面有没有该文字信息，有则说明用例执行成功，没有则说明用例失败
        UiObject text=new UiObject(new UiSelector().text("test"));
        assertTrue("sendMessage fail",text.waitForExists(1000*4));
        deleteQqContext();//删除QQ聊天记录
        //exitApp(AppInfo.PACKAGE_QQ);
    }

    @Steps("QQ--拍照发给好友")
    @Expectation("QQ--照片发送成功")
    public void SendPicture() throws Exception
    {   exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        enterChat();//搜索联系人并进入聊天界面
        //点击拍照按键
        click("com.tencent.mobileqq:id/input");
        sleep(1000);
        pressBack();
        sleep(1000);
        UiObject father=new UiObject(new UiSelector().className("android.widget.LinearLayout").instance(2));
        UiObject paizhao=father.getChild(new UiSelector().index(3));//QQ6.6.1后改了布局
        paizhao.clickAndWaitForNewWindow();

        //点击按键拍照
        sleep(1000*3);
        UiSelector findTakePictureBotton=new UiSelector().resourceId("com.meizu.media.camera:id/shutter_btn");

        UiSelector QQTakePic=new UiSelector().description("拍照按钮，点击拍摄照片");//QQ新版控件

        UiSelector QQTaT=new UiSelector().description("点击拍照，长按录制");//QQ6.6.0的新控件
        sleep(1000*5);
        try {
            UiObject takePictureBotton=new UiObject(findTakePictureBotton);//第一次会弹出权限弹框，多次获取控件
            if(new UiObject(new UiSelector().text("允许")).exists()){
                new UiObject(new UiSelector().text("允许")).clickAndWaitForNewWindow();
            }
            if(new UiObject(new UiSelector().text("允许")).exists()){
                new UiObject(new UiSelector().text("允许")).clickAndWaitForNewWindow();
            }
            if(new UiObject(new UiSelector().text("允许")).exists()){
                new UiObject(new UiSelector().text("允许")).clickAndWaitForNewWindow();
            }

            UiSelector QQTT=new UiSelector().description("点击拍照，长按录制");//QQ6.6.0的新控件
            UiSelector QQTakePica=new UiSelector().description("拍照按钮，点击拍摄照片");//QQ新版控件
            UiObject takePic=new UiObject(QQTakePic);//QQ新版控件
            UiObject takett=new UiObject(QQTT);//QQ6.6.1的新控件
            if(takett.exists()){
                takett.clickAndWaitForNewWindow();//QQ6.6.1的新控件
            }
            if (takePictureBotton.exists()) {
                takePictureBotton.click();
            }
            if (takePic.exists()){
                takePic.clickAndWaitForNewWindow();//QQ新版控件
            }
        }catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        sleep(1000*5);
        //确定完成拍照
        UiObject downWithTakePicture=new UiObject(new UiSelector().text("发送"));
        downWithTakePicture.click();
        sleep(1000*5);

//        //发送拍照给好友
//        UiObject sendPictureBotton=new UiObject(new UiSelector().resourceId("com.tencent.photoplus:id/crop_confirm"));
//        sendPictureBotton.click();
//        sleep(1000*2);

        //返回对话列表
        UiObject pic =new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/pic"));
        assertTrue(pic.waitForExists(1000 * 4));
        sleep(1000*5);
       deleteQqContext();//删除QQ聊天记录
        //exitApp(AppInfo.PACKAGE_QQ);
    }


    @Steps("QQ--发语音消息给好友")
    @Expectation("QQ--语音消息发送成功")
    public void SendVoiceMessage() throws  Exception
    {
        exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        enterChat();//搜索联系人并进入聊天界面
        //点击语音消息按键
        click("com.tencent.mobileqq:id/input");
        sleep(1000);
        pressBack();
        sleep(1000);
        int x= UiDevice.getInstance().getDisplayWidth();
        int y =UiDevice.getInstance().getDisplayHeight();
        click(110*x/1440,2470*y/2560);
        //该控件只能通过坐标点击，所以先确定键盘退下来，然后根据坐标点击按键
        sleep(1000*2);
        //长按录制语音消息并发送
        UiSelector findLongPressBotton=new UiSelector().resourceId("com.tencent.mobileqq:id/press_to_speak_iv");
        UiObject longPressToSaveVoice=new UiObject(findLongPressBotton);
        longPressToSaveVoice.dragTo(700,1683,500);//慢速拖拽得到长按的效果
        sleep(2000);
        longPressToSaveVoice.dragTo(700,1683,500);//慢速拖拽得到长按的效果
        sleep(2000);
        UiObject sound=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/name"));
       // assertTrue(intoSearchFriend.waitForExists(1000 * 4));
        assertTrue("sendSound",sound.waitForExists(1000 * 4));
        deleteQqContext();//删除QQ聊天记录

    }


    @Steps("QQ--给好友拨打语音电话")
    @Expectation("QQ--语音电话呼出成功")
    public void VoiceTalk() throws Exception

    {
        exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        enterChat();//搜索联系人并进入聊天界面
        //点击更多按键
        click("com.tencent.mobileqq:id/input");
        sleep(1000);
        pressBack();
        sleep(1000);
        int x= UiDevice.getInstance().getDisplayWidth();
        int y =UiDevice.getInstance().getDisplayHeight();
        click(980*x/1080,1850*y/1920);
        sleep(1000);

        //点击语音聊天按键
        UiObject voiceTalkBotton=new UiObject(new UiSelector().descriptionContains("QQ电话按钮"));
        voiceTalkBotton.click();
        sleep(5000);

        //结束语音聊天
        UiObject stopVoiceTalkBotton=new UiObject(new UiSelector().descriptionContains("结束QQ电话"));
        stopVoiceTalkBotton.click();
        sleep(2000);

        //返回对话列表
        UiObject backBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/ivTitleBtnLeft"));
        backBotton.click();
        sleep(2000);
        UiObject intoSearchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        intoSearchFriend.click();
        assertTrue(intoSearchFriend.waitForExists(1000 * 4));
    }


    @Steps("QQ--给好友发送视频聊天邀请")
    @Expectation("QQ--视频聊天邀请发送成功")
    public void VideoTalk() throws Exception
    {
        exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
       startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        UiObject intoSearchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        intoSearchFriend.click();
        UiObject searchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        searchFriend.setText("t");
        sleep(3000);
        clickByTextContains("Test");
        sleep(2000);

        //点击更多按键
        click("com.tencent.mobileqq:id/input");
        sleep(1000);
        pressBack();
        sleep(1000);
        int x= UiDevice.getInstance().getDisplayWidth();
        int y =UiDevice.getInstance().getDisplayHeight();
        click(980*x/1080,1850*y/1920);
        sleep(1000);

        //点击视频聊天按键
        UiObject videoTalkBotton=new UiObject(new UiSelector().descriptionContains("视频电话按钮"));
        videoTalkBotton.click();
        sleep(1000);

        int time=0;
        while(isExistByTextContains("确定"))
        {
            if(time>10){
                break;
            }else {
                clickByTextContains("确定");
                sleep(1000 * 2);
                time++;
            }
        }
        sleep(3000);

            int time1=0;
        while(isExistByDesc("发起视频"))
        {
            if(time1>10){
                break;
            }else {
                clickByDesc("发起视频");
            sleep(1000*2);
                time1++;
            }
        }
        sleep(2000);

        //中断视频聊天
        UiSelector findStopVideoTalk=new UiSelector().descriptionContains("结束视频电话");
        UiObject stopVideoTalkBotton=new UiObject(findStopVideoTalk);
        stopVideoTalkBotton.click();
        sleep(1000*4);

        //返回对话列表
        UiObject backBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/title"));//标题控件
        assertTrue(backBotton.waitForExists(1000 * 4));

    }

    @Steps("QQ--给好友发送文件")
    @Expectation("QQ--文件发送成功")
    public void SendFile() throws Exception
    {
        exitApp(AppInfo.PACKAGE_QQ);
        sleep(1000*2);
        startApp(AppInfo.PACKAGE_QQ,AppInfo.ACTIVITY_QQ);
        sleep(1000*4);
        UiObject intoSearchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        intoSearchFriend.click();
        UiObject searchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        searchFriend.setText("t");
        sleep(3000);
        clickByTextContains("Test");
        sleep(2000);

        //点击更多按键
        click("com.tencent.mobileqq:id/input");
        sleep(1000);
        pressBack();
        sleep(1000);
        int x= UiDevice.getInstance().getDisplayWidth();
        int y =UiDevice.getInstance().getDisplayHeight();
        click(980*x/1080,1850*y/1920);
        sleep(1000);

        //点击发送文件按键
        UiObject sendFileBotton=new UiObject(new UiSelector().descriptionContains("文件按钮"));
        sendFileBotton.click();
        sleep(2000);

        //进入图片目录
        UiObject pictureFileBotton=new UiObject(new UiSelector().descriptionContains("图片按钮"));
        pictureFileBotton.click();

        //点击选择一张图片
        UiObject choosePicture=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/image").index(0));
        choosePicture.click();
        sleep(1000);

        //点击发送按键
        UiObject sendBotton=new UiObject(new UiSelector().className("android.widget.Button").instance(1));
        sendBotton.click();
        sleep(3000);

        //返回对话列表
        UiObject backBotton=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/ivTitleBtnLeft"));
        backBotton.click();
        sleep(2000);
        assertTrue(intoSearchFriend.waitForExists(1000 * 4));
    }

    @Steps("QQ--关闭QQ")
    @Expectation("QQ--关闭QQ")
    public void CloseApp() throws Exception
    {
        exitApp(AppInfo.PACKAGE_QQ);
    }

}
