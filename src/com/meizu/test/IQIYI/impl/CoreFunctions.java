package com.meizu.test.IQIYI.impl;

import com.android.uiautomator.core.*;
import com.meizu.test.IQIYI.imple.ICoreFuntions;
import com.meizu.test.IQIYI.testcase.PubScipt;
import com.meizu.common.APPINFO;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016-3-25.
 */
public class CoreFunctions extends PubScipt implements ICoreFuntions{



    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ShellUtil.exitApp(APPINFO.PACKAGE_AIQIYI);
        ShellUtil.setSystemInput();
    }




    @Override
    public void randomPlay() throws Exception {
        UiScrollable control=new UiScrollable(new UiSelector().className("android.widget.ListView")); //获取list父类
        control.flingToEnd(random(5));
        UiObject video=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/poster_layout_2").clickable(true));//获取可播放的layout_2的对象
        video.clickAndWaitForNewWindow(2000);
        assertTrue(new UiObject(new UiSelector().resourceId(ID_PALYWINDOW)).waitForExists(2000));//断言是否出现视频播放框组件
    }


    @Override
    public void voiceSearch() throws Exception {
        UiObject voidIcon= new UiObject(new UiSelector().resourceId(ID_VOIDICON));//获取语音图标对象
        click(ID_MAINSEARCH);//点击主界面搜索
        sleep(1000);
        click(ID_SEARCHFACE);//点击搜索栏
        sleep(500);
        if(isExistById(ID_CLEARTEXT)){//判断是否存在已有记录
            click(ID_CLEARTEXT);
            sleep(500);
        }
        voidIcon.clickAndWaitForNewWindow(4000);
        UiSelector voi=new UiSelector().resourceId(ID_VOIDVIEW);//出发监听器
        assertTrue(findElementById(ID_VOIDVIEW).waitForExists(5000));//断言是否存在语音对话框组件
    }

    @Override
    public void textSearch() throws Exception {
        intoPlay();
        assertTrue(new UiObject(new UiSelector().resourceId(ID_PALYWINDOW)).waitForExists(2*1000));//断言是否出现视频播放框组件
    }

    @Override
    public void comment() throws Exception {
        intoPlay();
        UiScrollable listView=new UiScrollable(new UiSelector().className(CLASS_LISTVIEW));//定义组件范围到listView里
        UiObject commentEdit= new UiObject(new UiSelector().resourceId(ID_COMMENTEDIT));//获取编辑评论对象
        listView.scrollIntoView(commentEdit);//滚动到评论编辑框
        sleep(1000);
        commentEdit.clickAndWaitForNewWindow(1000);//点击评论等待新窗口
        ShellUtil.setUtf7Input();
        UiObject content=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/commentContent"));//获取新窗口中评论框的对象
       sleep(2*1000);
        content.setText(Utf7ImeHelper.e(TEXT_REPLY));
        sleep(2*1000);
        new UiObject(new UiSelector().resourceId("com.qiyi.video:id/commentSend")).clickAndWaitForNewWindow(1000);//点击发送
        UiObject userName=new UiObject(new UiSelector().text("186****7067_m1046"));
        listView.scrollIntoView(userName);//滚动寻找用户名
        assertTrue(userName.waitForExists(2*1000));//断言是否默认用户名出现

    }



    @Override
    public void download() throws Exception {
        intoDownload();
        assertTrue(new UiObject(new UiSelector().text("正在缓存")).waitForExists(2*1000));//断言是否存在
        DeleDownload();

        }


    @Override
    public void jump() throws Exception {
        intoDownload();
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        pressHome();
        sleep(1000);
        UiDevice.getInstance().swipe(x-300,50,x-300,y-200,20);//下拉通知栏
        sleep(1000);
        clickByText("正在缓存");
        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/titleOffLineDelete")).waitForExists(2*1000));//断言是否存在
        DeleDownload();


    }

    @Override
    public void deleteDownload() throws Exception {
        intoDownload();
        DeleDownload();
        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/phone_download_no_item")).waitForExists(2*1000));//断言是否存在
    }

    @Override
    public void share() throws Exception {
        intoPlay();
        UiObject share=new UiObject(new UiSelector().resourceId(ID_SHARE).text("分享"));//获取分享控件
        share.clickAndWaitForNewWindow(2*1000);
        UiObject shareWechat=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/tv_share_item").text("微信好友"));//分享窗口获取微信好友控件
        shareWechat.clickAndWaitForNewWindow();
        assertTrue(new UiObject(new UiSelector().resourceId("android:id/text1").text("登录微信")).waitForExists(2000));//断言是否出现登录微信的控件出现
        click("com.tencent.mm:id/cd1");
    }

    @Override
    public void Collection() throws Exception {
        intoMyfavour();//进入我的收藏
        DeleFavours();//删除已有收藏
        init();
        intoPlay();
        UiObject favour=new UiObject(new UiSelector().resourceId(ID_FAVOUR).selected(false));//获取未点击的收藏图标控件
        favour.waitForExists(90*1000);
        if(isExistById(ID_FAVOUR)){
            favour.clickAndWaitForNewWindow();
        }
        intoMyfavour();
        assertTrue(new UiObject(new UiSelector().resourceId(ID_DeleFavo)).waitForExists(1000));//断言右上角是否存在删除图标
        DeleFavours();
    }

    @Override
    public void jumpFullScreen() throws Exception {
        intoFullPlay();
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().resourceId(ID_LOCKSCREEN)).waitForExists(1000));//断言是否出现锁定屏幕图标
    }


    @Override
    public void fullScreenFunctionTraversal() throws Exception {
        intoFullPlay();
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
       isFullPalySeeView();
        click(ID_PAUSEBUTTOM);//暂停播放
        String ID_FPSHARE   ="com.qiyi.video:id/player_landscape_btn_share";//分享
        String ID_FPDOWN="com.qiyi.video:id/player_landscape_btn_download";//下载
        String ID_FPLIST="com.qiyi.video:id/player_landscape_btn_setting";//菜单
        String ID_FPTX="com.qiyi.video:id/player_landscape_coderateTx";//清晰度
        String ID_FPETX="com.qiyi.video:id/player_landscape_selected_layout";//选集
        String ID_FPNEXT="com.qiyi.video:id/player_landscape_nextBtn";//下一集

        click(ID_FPSHARE);
        sleep(1000);
        pressBack();
        isFullPalySeeView();
        click(ID_FPDOWN);
        sleep(1000);
        pressBack();
        isFullPalySeeView();
        click(ID_FPLIST);
        sleep(1000);
        pressBack();
        isFullPalySeeView();
        click(ID_FPTX);
        sleep(1000);
        pressBack();
        isFullPalySeeView();
        click(ID_FPETX);
        sleep(1000);
        pressBack();
        isFullPalySeeView();
        swipe(x/3,y/2,x*2/3,y/2,40);//快进
        swipe(x*2/3,y/2,x/3,y/2,40);//快退
        swipe(x*2/3,y*2/3,x*2/3,y/3,40);//音量加
        swipe(x*2/3,y/3,x*2/3,y*2/3,40);//音量减
        swipe(x/3,y*2/3,x/3,y/3,40);//亮度加
        swipe(x/3,y/3,x/3,y*2/3,40);//亮度减
        isFullPalySeeView();
        click(ID_FPNEXT);
        sleep(1000);


        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/skipads_pre_ad").text("会员免广告")).waitForExists(2*1000));//遍历以后断言是否出现下一集的广告界面
    }

    @Override
    public void screenLock() throws Exception {
        intoFullPlay();
        UiObject Lock=new UiObject(new UiSelector().resourceId(ID_LOCKSCREEN));//锁屏控件
        if(!Lock.exists()){
            click(ID_FULLPWINDOW);
        }
        Lock.clickAndWaitForNewWindow();
        pressBack();
        assertFalse(new UiObject(new UiSelector().resourceId(ID_SHARE)).waitForExists(2000));//断言若回到半屏播放界面则失败
    }

    @Override
    public void barrage() throws Exception {
        intoFullPlay();
        String bar="com.qiyi.video:id/player_landscape_btn_toggle_spitslot";//弹幕控件id
        String mess="com.qiyi.video:id/player_landscape_spitslot_send";//发送消息控件
        UiObject barrage=new UiObject(new UiSelector().resourceId(bar));//弹幕控件
        for(int i=0;i<6;i++){//点掉屏幕水印
         click(ID_FULLPWINDOW);
        sleep(1000);
        }
        if(!barrage.exists()){//若弹幕控件不存在
            click(ID_FULLPWINDOW);
        }
        if(!isExistById(mess)){
            barrage.clickAndWaitForNewWindow(2000);
        }
        if(!isExistById(mess)){//若弹幕发送的控件不存在
            click(ID_FULLPWINDOW);
        }
        click(mess);
        sleep(1000);
        ShellUtil.setUtf7Input();
        UiObject edit=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/player_module_spitslot_et"));//获得信息编辑框控件
        edit.setText(Utf7ImeHelper.e("矮油不错哦"));
        click("com.qiyi.video:id/player_module_spitslot_send_tx");//点击发送组件
        assertFalse(edit.waitForExists(1000));
    }

    @Override
    public void localPlay() throws Exception {
        exitApp(APPINFO.PACKAGE_AIQIYI);
        exitApp(PACKAGE_FILE);
        startApp(PACKAGE_FILE,ACTIVITY_FILE);
        UiObject vedio=new UiObject(new UiSelector().resourceId("com.meizu.filemanager:id/gridView_text").text("视频"));//找到视频控件
        vedio.clickAndWaitForNewWindow(1000);//点击进入
        UiCollection list=new UiCollection(new UiSelector().resourceId("com.meizu.filemanager:id/file_list").instance(0));//获取list集合
        int a=list.getChildCount();
        UiObject rand=list.getChildByInstance(new UiSelector().resourceId("com.meizu.filemanager:id/mc_item_container"),random(a));//获取随机的可点击视频
        rand.clickAndWaitForNewWindow(1000);
        UiObject aiqiyi=new UiObject(new UiSelector().className("android.widget.TextView").text("爱奇艺"));//获取爱奇艺的点击控件
        aiqiyi.clickAndWaitForNewWindow(1000);
        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/playRootLayout")).waitForExists(3*1000));
        exitApp(APPINFO.PACKAGE_AIQIYI);
        exitApp(PACKAGE_FILE);
    }

    @Override
    public void shotUploadFunctionTraversal() throws Exception {
        intoShot();//进入拍摄上传
        UiObject tool0=new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/layout_camera_tools"));//拍摄界面上面三个图标
        int a=tool0.getChildCount();
        System.out.print("    is     "+ a);
        for (int i=0;i<a;i++){//顶上三个按键的遍历
            tool0.getChild(new UiSelector().index(i)).clickAndWaitForNewWindow();
            sleep(500);
            tool0.getChild(new UiSelector().index(i)).clickAndWaitForNewWindow();
            sleep(500);
        }
        new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/tv_beauty_mode")).clickAndWaitForNewWindow();//切换到美颜
        assertFalse(findElement("com.iqiyi.paopao:id/iv_camera_flash").waitForExists(2000));//闪光灯图标出现则false
    }

    @Override
    public void shot() throws Exception {
        intoShot();
        UiSelector cam=new UiSelector().resourceId("com.iqiyi.paopao:id/circle_inner");//触发监听器
        click("com.iqiyi.paopao:id/circle_inner");//点击录像
        sleep(4*1000);//等待四秒
        new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/iv_finish").text("下一步")).clickAndWaitForNewWindow(2*1000);//录制界面下一面
        new UiObject(new UiSelector().resourceId("com.iqiyi.share.sdk.videoedit:id/vw_edit_title_next_text")).clickAndWaitForNewWindow(30*1000);//预览界面点确定
        sleep(30*1000);
        UiObject text0=new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/edt_video_des").text("点击添加描述(必填)"));//获取添加描述控件
        if(text0.exists()){
            text0.clickAndWaitForNewWindow();
            ShellUtil.setUtf7Input();
            text0.setText(Utf7ImeHelper.e("矮油这个不错哦"));
            new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/privacy_auth_state_text").text("公开")).clickAndWaitForNewWindow(1000);//进入权限设置控件
            clickByText("仅自己可见");
            sleep(500);
            new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/tv_publish").text("发布")).clickAndWaitForNewWindow(8*1000);
        }
        assertTrue(isExistByText("我的视频"));
        click("com.qiyi.video:id/phoneButton");//点击删除按键
        click("com.qiyi.video:id/ugc_video_item_check");//选择删除项
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        sleep(500);
        click(x * 8 / 10, y - 50);//点删除
    }


   }
