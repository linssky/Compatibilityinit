package com.meizu.test.IQIYI.testcase;

import com.android.uiautomator.core.*;
import com.meizu.automation.constants.AutoException;
import com.meizu.automation.service.Element;
import com.meizu.common.APPINFO;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import java.util.Random;

/**
 * Created by wuchaolin on 2016-3-24.
 */
public class PubScipt extends AutoAllInOneTestCase {
    public static final String TEXT_SEARCH="画江湖之灵主";
    public static final String TEXT_REPLY="hao!";
    public static final String ID_TITLE="com.qiyi.video:id/episode_info_title";
    public static final String ID_SHARE="com.qiyi.video:id/play_video_share";//播放界面分享图标
    public static final String ID_FAVOUR="com.qiyi.video:id/player_piecemeal_layer_favour";//播放界面右上角收藏图标
    public static final String ID_DeleFavo="com.qiyi.video:id/title_delete";//收藏界面右上角删除图标
    public static final String ID_BACK="com.qiyi.video:id/player_piecemeal_layer_back";
    public static final String ID_SEAECH="com.qiyi.video:id/phoneSearchSubmitLayout";//搜索界面的搜索图标
    public static final String ID_PALYWINDOW="com.qiyi.video:id/video_view";//播放窗口
    public static final String CLASS_LZ="android.widget.RelativeLayout";
    public static final String ID_MAINSEARCH = "com.qiyi.video:id/search";//主界面搜索图标
    public static final String ID_SEARCHFACE = "com.qiyi.video:id/phoneSearchKeyword";//搜索界面搜索栏
    public static final String ID_CLEARTEXT = "com.qiyi.video:id/phoneSearchDeleteButton";//搜索栏清除文本的图标
    public static final String ID_VOIDICON = "com.qiyi.video:id/phoneSearchVoiceSubmit";//搜索栏语音图标
    public static final String ID_VOIDVIEW ="com.qiyi.module.voice:id/baidu_voice_input_image_layout";//语音对话框
    public static final String ID_PLAYNOW ="com.qiyi.video:id/episode_info_play_btn";//搜索界面立即播放图标
    public static final String CLASS_LISTVIEW="android.widget.ListView";//listview的类名
    public static final String ID_COMMENTEDIT="com.qiyi.video:id/comment_edit";//评论栏图标
    public static final String ID_MY="com.qiyi.video:id/naviMy";//主界面“我的”
    public static final String ID_LOCKSCREEN ="com.qiyi.video:id/player_landscape_lock_screen";//全屏播放界面锁屏图标
    public static final String ID_PAUSEBUTTOM="com.qiyi.video:id/player_landscape_pauseBtn";//全屏播放界面开始暂停按钮
    public static final String ID_FULLPWINDOW="com.qiyi.video:id/paonanlayout";//全屏窗口控件
    public static final String PACKAGE_FILE="com.meizu.filemanager";//文件管理包名
    public static final String ACTIVITY_FILE="com.meizu.flyme.filemanager.activity.HomeActivity";//文件管理activity
    public static final String ID_ADD="com.qiyi.video:id/title_plus";//主界面右上角加号
    public static final String ID_MEIZUCAMER="com.meizu.media.camera:id/shutter_btn";//魅族相机拍照控件



    //首次启动app
    public void clearCacheAndStartApp() throws AutoException {
        startApp(APPINFO.PACKAGE_AIQIYI, APPINFO.ACTIVITY_AIQIYI);
        if(isExistById("com.qiyi.video:id/phone_qiyi_guide_dots")){
            pressBack();
            sleep(2000);
            startApp(APPINFO.PACKAGE_AIQIYI, APPINFO.ACTIVITY_AIQIYI);
        }
        while (isExistById("android:id/extractArea") && isExistByText("记住我的选择")){
            clickByText("允许");
        }
        click("com.qiyi.video:id/naviRecom");

    }




    //点击启动app
    public void StartApp(String appTextName,String packageName) throws AutoException, UiObjectNotFoundException {
        int y=UiDevice.getInstance().getDisplayHeight();
        int x=UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().pressHome();
        sleep(1000);
        UiDevice.getInstance().pressHome();
        sleep(500);
        for(int i=0;i<10;i++){
            if( !isExistByText(appTextName)){
                UiDevice.getInstance().swipe(x-50,y/2,50,y/2,30);
        }
        }
        new UiObject(new UiSelector().text(appTextName)).clickAndWaitForNewWindow(5000);
        assertTrue(new UiObject(new UiSelector().packageName(packageName)).waitForExists(5000));
    }

    //进入画江湖的搜索播放界面
    public void intoPlay() throws AutoException, UiObjectNotFoundException {
        sleep(1000);
        Element TEXT_SEARCHFACE = findElement(ID_SEARCHFACE);//获取搜索框对象
        click(ID_MAINSEARCH);
        ShellUtil.setUtf7Input();//切换输入法
        TEXT_SEARCHFACE.setText(Utf7ImeHelper.e(TEXT_SEARCH));
        ShellUtil.setSystemInput();
        if (isExistByText("搜索")) {
            click(ID_SEAECH);
        }
        sleep(2000);
        UiCollection listView=new UiCollection(new UiSelector().className(CLASS_LISTVIEW));//定义组件范围到listView里
        UiObject HJH=listView.getChildByInstance(new UiSelector().resourceId(ID_PLAYNOW).clickable(true),0);//在listView里获取画江湖的可点击控件
        HJH.clickAndWaitForNewWindow(2000);
    }


    //进入全屏播放界面
    public void intoFullPlay() throws Exception {
        String ID_JumpFull="com.qiyi.video:id/btn_tolandscape";//跳至全屏控件
        intoPlay();
        UiObject favour=new UiObject(new UiSelector().resourceId(ID_FAVOUR).selected(false));//获取未点击的收藏图标控件
        UiObject jumpFull=new UiObject(new UiSelector().resourceId(ID_JumpFull));
        favour.waitForExists(90*1000);//等待广告时间结束
        if(!isExistById(ID_JumpFull)){
            click(ID_PALYWINDOW);
        }
        jumpFull.clickAndWaitForNewWindow();
        sleep(8*1000);//8s等待控件出现
    }

    //进入全屏界面判断是否出现了边框的控件
    public void isFullPalySeeView() throws AutoException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        if(!isExistById(ID_PAUSEBUTTOM)){//若控件还未出现
            click(x/2,y/2);
        }
    }

    //进入离线观看页面
    public void intoDownload() throws AutoException, UiObjectNotFoundException {
        intoPlay();
        UiObject download = new UiObject(new UiSelector().resourceId("com.qiyi.video:id/download"));
        download.clickAndWaitForNewWindow(1000);
        UiObject list =new UiObject(new UiSelector().resourceId("com.qiyi.video:id/listview").instance(0));//获取listview对象
        int listSize=list.getChildCount();//获取listview子集数量

        for(int i=1;i<4;i++) {
            int a=random(listSize);//定义一个子集内的随机数
            UiObject HList=list.getChild(new UiSelector().className("android.widget.LinearLayout").index(a));//获取listview子集中的任意横排对象
            int listSize1=HList.getChildCount();//获取该横排子集数量
            int b=random(listSize1);//定义该子集内的随机数
            UiObject randomDown=HList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(b));//获取随机点击对象
            randomDown.clickAndWaitForNewWindow(500);
        }
        click("com.qiyi.video:id/download");//点击“确定缓存”
        sleep(500);
        clickByText("缓存管理");//点击进入缓存管理
        sleep(1000);
    }

    //进入我的收藏页面
    public void  intoMyfavour() throws Exception {
        init();
        click(ID_MY);
        UiCollection list=new UiCollection(new UiSelector().resourceId("com.qiyi.video:id/my_main_root_listview").instance(0));//获取我的listview
        UiObject favours=list.getChild(new UiSelector().className("android.widget.LinearLayout").index(5)).
                getChild(new UiSelector().resourceId("com.qiyi.video:id/my_exposed_item_title").index(0));//获取收藏的标题图标
        favours.clickAndWaitForNewWindow(1000);
    }

    //进入拍摄上传界面
    public void intoShot() throws Exception {
        UiSelector add=new UiSelector().resourceId(ID_ADD);
        click(ID_ADD);
        click("com.qiyi.video:id/popup_capture_upload");//点击拍摄上传
        sleep(1000);
    }

    //进入个人资料界面
    public void intoInformation() throws Exception {
        click(ID_MY);
        sleep(500);
        click("com.qiyi.video:id/phone_avatar_icon");//点击头像
        sleep(500);
        UiObject info=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/vw_self_info_edit_info").text("编辑资料"));
        assertTrue(info.waitForExists(5*1000));
        info.clickAndWaitForNewWindow(3 * 1000);
        new UiObject(new UiSelector().resourceId("com.qiyi.video:id/tv_avatar_title").text("头像")).clickAndWaitForNewWindow(3*1000);
    }

    //离线观看界面删除下载项目
    public void DeleDownload() throws AutoException, UiObjectNotFoundException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        String ID_DeleDownl="com.qiyi.video:id/titleOffLineDelete";
        if(isExistById(ID_DeleDownl)) {
            click(ID_DeleDownl);//点右上角删除
            sleep(1000);
            UiObject downList = new UiObject(new UiSelector().resourceId("com.qiyi.video:id/phone_download_list").instance(0));//获取可删除的列表
            int c = downList.getChildCount();
            for (int i = 0; i < c-1; i++) {//因为listview里面多了一个无用控件，所以要用c-1
                UiObject d = downList.getChild(new UiSelector().resourceId("com.qiyi.video:id/phone_download_delete_checkbox").instance(i));//获取存在的可删除对象
                if (!d.isChecked()) {//判断是否已经选取
                    d.click();
                }
            }
            click(x * 8 / 10, y - 50);//点删除
            sleep(1000);
        }

    }

    //在收藏界面删除已有收藏
    public void DeleFavours() throws AutoException, UiObjectNotFoundException {
        int x=UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        if (isExistById(ID_DeleFavo)) {//如果存在删除图标，则操作，否则不动
            click(ID_DeleFavo);//点右上角删除
            sleep(500);
            UiObject downList = new UiObject(new UiSelector().className("android.widget.ListView").instance(0));//获取可删除的列表
            int c = downList.getChildCount();
            for (int i = 0; i < c; i++) {
                UiObject d = downList.getChild(new UiSelector().resourceId("com.qiyi.video:id/ugc_video_item_check").instance(i));//获取存在的可删除对象
                if (!d.isChecked()) {//判断是否已经选取
                    d.click();
                }
            }
            click(x * 8 / 10, y - 50);//点删除
            sleep(1000);
            new UiObject(new UiSelector().resourceId("android:id/button2").text("确定")).clickAndWaitForNewWindow();
        }

    }

    //初始化应用
    public void init() throws Exception {
        exitApp(APPINFO.PACKAGE_AIQIYI);
        ShellUtil.setSystemInput();
        sleep(500);
        startApp(APPINFO.PACKAGE_AIQIYI, APPINFO.ACTIVITY_AIQIYI);
        sleep(500);
    }

    //调用随机数
    public int random(int num){
        Random a=new Random();
        int b=a.nextInt(num);
        return b;
    }



    //注册监听器
    public void watcher(){
        UiDevice.getInstance().registerWatcher("允许", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject allow=new UiObject(new UiSelector().resourceId("android:id/button1").text("允许").clickable(true));//定义弹出的允许对话框
                if(allow.exists()){
                    System.out.print("弹出权限框");
                    try {
                        allow.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                return false;
            }}
        });
    }






}
