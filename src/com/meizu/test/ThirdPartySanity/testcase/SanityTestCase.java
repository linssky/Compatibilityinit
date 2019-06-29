package com.meizu.test.ThirdPartySanity.testcase;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.test.ThirdPartySanity.impl.*;
import com.meizu.test.util.ShellUtil;
import java.io.IOException;

/**
 * Created by wuchaolin on 2016-6-2.
 */
public class SanityTestCase extends Common {

    /*
      * 声明各类对象
      * */
    WeChat wechat = new WeChat();
    TAOBAO taobao = new TAOBAO();
    Weibo weibo = new Weibo();
    GoogleInstaller testGoogle = new GoogleInstaller();
    AliPay testAliPay = new AliPay();
    QQ testQQ = new QQ();
    uninstallFromSetting testUninstall = new uninstallFromSetting();

    public void setUp() throws Exception {
        watcher();
        super.setUp();
    }


    @Steps("微信：1.若未安装微信，先在应用中心安装微信"
            + "2.进入微信——登录——使用其他方式登录——输入账号密码——登录")
    @Expectation("正常登录")
    public void test001WeChat() throws Exception {
//        closeSecurityLimits();
//        testAliPay.openApp();
        STARTAPP(AppInfo.PACKAGE_WECHAT,AppInfo.VERSION_WECHAT,AppInfo.APKNAME_WECHAT,AppInfo.URL_WEICHAT,AppInfo.ACTIVITY_WECHAT);
        wechat.Login();//判断是否登录，未登录则去登录

    }

    @Steps("微信：进入通讯录——搜索联系人'test'——发送hello world给他")
    @Expectation("信息发送正常")
    public void test002WeChat() throws Exception {
        wechat.sendMessage();
    }

    @Steps("微信：和test的聊天界面——右下角加号——发送图片——拍照——发送")
    @Expectation("图片发送正常")
    public void test003WeChat() throws Exception {
        wechat.takePhotoAndSend();
    }

    @Steps("微信：和test的聊天界面——切换到语音——长按语音——发送")
    @Expectation("语音发送正常")
    public void test004WeChat() throws Exception {
        wechat.sendVoice();
    }

    @Steps("微信：和test的聊天界面——右下角加号——视频聊天")
    @Expectation("正常跳转")
    public void test005WeChat() throws Exception {
        wechat.videoChat();
        ShellUtil.setSystemInput();
        exitApp(AppInfo.PACKAGE_WECHAT);
    }
    @Steps("淘宝：输入账户密码登录")
    @Expectation("登录成功")
    public void test011TaoBao() throws InterruptedException, UiObjectNotFoundException, AutoException, IOException {
        STARTAPP(AppInfo.PACKAGE_TAOBAO,AppInfo.VERSION_TAOBAO,AppInfo.APKNAME_TAOBAO,AppInfo.URL_TAOBAO,AppInfo.ACTIVITY_TAOBAO);
        taobao.Login();
        exitApp(AppInfo.PACKAGE_TAOBAO);
    }

    @Steps("淘宝：扫一扫")
    @Expectation("进入页面正常")
    public void test012TaoBao() throws InterruptedException, UiObjectNotFoundException, AutoException {
        taobao.sweep();
    }

    @Steps("淘宝：首页点击搜索框，输入宝贝名称（衣服）搜索")
    @Expectation("正常搜索出结果")
    public void test013TaoBao() throws InterruptedException, UiObjectNotFoundException, AutoException {
        taobao.editSearch();
    }

    @Steps("淘宝：滑动搜索结果列表")
    @Expectation("正常滑动无闪退等")
    public void test014TaoBao() throws UiObjectNotFoundException {
        taobao.scorllList();
    }

    @Steps("淘宝：点击底栏切换各页面")
    @Expectation("正常滑动无闪退等")
    public void test015TaoBao() throws InterruptedException, UiObjectNotFoundException, AutoException {
        taobao.tabBottom();
    }

    @Steps("新浪微博：1.进入微博首页"
            + "2.点击登录"
            + "3.输入账号密码，登录")
    @Expectation("3.登录成功")
    public void test021Weibo() throws InterruptedException, UiObjectNotFoundException, AutoException, IOException {
        STARTAPP(AppInfo.PACKAGE_WEIBO,AppInfo.VERSION_WEIBO,AppInfo.APKNAME_WEIBO,AppInfo.URL_WEIBO,AppInfo.ACTIVITY_WEIBO);
        weibo.weiboLogin();
    }

    @Steps("新浪微博：1.进入微博"
            + "2.点击“+”"
            + "3.点击摄像头图标，进入微博相机"
            + "4.后置摄像头，点击拍照按钮"
            + "5.下一步，回到发微博界面")
    @Expectation("正常调用相机，回到微博发送界面")
    public void test022Weibo() throws AutoException, UiObjectNotFoundException, InterruptedException, IOException {
        weibo.sendPhotoWeibo();
    }

    @Steps("新浪微博：1.进入微博"
            + "2.点击“+”"
            + "3.点击摄像头图标，进入照片视频"
            + "4.选择任意照片，下一步"
            + "5.下一步，回到发微博界面"
            + "6.发送，发送图片微博")
    @Expectation("正常发送图片微博")
    public void test023Weibo() throws UiObjectNotFoundException, InterruptedException {
        weibo.sendPicWeibo();
    }

    @Steps("新浪微博："
            + "1.进入微博"
            + "2.点击“+”"
            + "3.点击文字，进入文字微博发送界面"
            + "5.选择位置，"
            + "6.输入Test Address，发送微博"
            + "7.删除所发微博")
    @Expectation("正常选择位置，正常发送微博")
    public void test024Weibo() throws UiObjectNotFoundException, InterruptedException {
        weibo.address();
    }

    @Steps("新浪微博："
            + "1.进入微博"
            + "2.在主界面滑动微博")
    @Expectation("正常滑动无闪退等")
    public void test025Weibo() throws UiObjectNotFoundException, InterruptedException {
        weibo.scorllList();
    }


    @Steps("谷歌安装器--1.在桌面上找到应用" +
            "谷歌安装器--2.点击运行应用")
    @Expectation("谷歌安装器--1.在桌面找到应用" +
            "谷歌安装器--2.应用运行成功")
    public void test031GoogleInstaller() throws Exception {
        //STARTAPP(AppInfo.PACKAGE_GOOGLEINSTALLER,AppInfo.VERSION_TGOOGLE,AppInfo.APKNAME_GOOGLE,AppInfo.URL_GOOGLE,AppInfo.ACTIVITY_GOOGLEINSTALLER);
        if(isInstalled(AppInfo.PACKAGE_GOOGLEINSTALLER)) {
            uninstallApp(AppInfo.PACKAGE_GOOGLEINSTALLER);//先卸载谷歌安装器
            sleep(1000 * 2);
        }
        downLoad(AppInfo.URL_GOOGLE,AppInfo.APKNAME_GOOGLE);//从测试平台上下载apk到手机
        System.out.println("--------下载完成---------");
        installApk(AppInfo.APKNAME_GOOGLE);
        testGoogle.OpenApp();
    }


    @Steps("谷歌安装器--安装谷歌服务框架")
    @Expectation("谷歌安装器--谷歌服务框架安装成功")
    public void test032GoogleInstaller() throws Exception {
        testGoogle.InstallService();
        sleep(1000*5*60);
    }

    @Steps("谷歌安装器--卸载谷歌服务框架")
    @Expectation("谷歌安装器--服务框架卸载成功")
    public void test033GoogleInstaller() throws Exception {
        testGoogle.UninstallService();
    }

    @Steps("谷歌安装器--退出应用")
    @Expectation("谷歌安装器--退出应用成功")
    public void test034GoogleInstaller() throws Exception {
        testGoogle.CloseApp();
    }

    @Steps("支付宝--点击桌面图标，运行应用")
    @Expectation("支付宝--应用运行成功")
    public void test041AliPay() throws Exception {
        testAliPay.openApp();
    }

  /*@Steps("支付宝--登录支付宝")
    @Expectation("支付宝--登录成功")
    public void test042AliPay() throws Exception {
        testAliPay.landing();
    }*/
    @Steps("支付宝--点击扫一扫")
    @Expectation("支付宝--进入扫一扫页面")
    public void test043AliPay() throws Exception {
        testAliPay.Camera();
    }

    @Steps("支付宝--点击转账")
    @Expectation("支付宝--进入转账页面")
    public void test044AliPay() throws Exception {
        testAliPay.TransMoney();
    }

    @Steps("支付宝--点击付款")
    @Expectation("支付宝--进入付款页面")
    public void test045AliPay() throws Exception {
        testAliPay.Pay();
    }

    @Steps("支付宝--依次点击底栏上 口碑、朋友和我的")
    @Expectation("支付宝--进入 口碑、朋友和我的页面")
    public void test046AliPay() throws Exception {
        testAliPay.ChangePage();
    }

    @Steps("支付宝--关闭支付宝")
    @Expectation("支付宝--关闭支付宝")
    public void test047AliPay() throws Exception {
        testAliPay.CloseApp();
    }

    @Steps("QQ--打开应用")
    @Expectation("QQ--应用打开成功")
    public void test051QQ() throws Exception {
        testQQ.OpenApp();
    }

    @Steps("QQ--登陆")
    @Expectation("QQ--登陆成功")
    public void test052QQ() throws Exception {
        testQQ.Landing();
    }

    @Steps("QQ--给好友发送文字信息")
    @Expectation("QQ--给好友发送文字信息")
    public void test053QQ() throws Exception {
        testQQ.SendMessage();
    }

    @Steps("QQ--拍照发给好友")
    @Expectation("QQ--照片发送成功")
    public void test054QQ() throws Exception {
        testQQ.SendPicture();
    }

    @Steps("QQ--发语音消息给好友")
    @Expectation("QQ--语音消息发送成功")
    public void test055QQ() throws Exception {
        testQQ.SendVoiceMessage();
    }

    @Steps("QQ--给好友拨打语音电话")
    @Expectation("QQ--语音电话呼出成功")
    public void test056QQ() throws Exception {
        testQQ.VoiceTalk();
    }

    @Steps("QQ--给好友发送视频聊天邀请")
    @Expectation("QQ--视频聊天邀请发送成功")
    public void test057QQ() throws Exception {
        testQQ.VideoTalk();
    }

    @Steps("QQ--给好友发送文件")
    @Expectation("QQ--文件发送成功")
    public void test058QQ() throws Exception {
        testQQ.SendFile();
    }

    @Steps("QQ--关闭QQ")
    @Expectation("QQ--关闭QQ")
    public void test059QQ() throws Exception {
        testQQ.CloseApp();
    }

    @Steps("卸载应用--启动系统自带设置")
    @Expectation("卸载应用--启动系统自带设置")
    public void test061uninstallFromSetting() throws Exception {
        testUninstall.openSystemSetting();
    }

    @Steps("卸载应用--进入应用管理页面")
    @Expectation("卸载应用--进入应用管理页面可进入")
    public void test062uninstallFromSetting() throws Exception {
        testUninstall.intoAppManagePage();
    }

    @Steps("卸载应用--已安装页面点击QQ进行卸载")
    @Expectation("卸载应用--完成QQ卸载")
    public void test063uninstallFromSetting() throws Exception {
        testUninstall.uninstallQQ();
    }

    @Steps("卸载应用--关闭系统设置")
    @Expectation("卸载应用--系统设置关闭成功")
    public void test064uninstallFromSetting() throws Exception {
        testUninstall.exitSystemSetting();
        sleep(1000 * 2);
        ShellUtil.setSystemInput();
    }

    @Steps("清除QQ、微信、微博的登录信息")
    @Expectation("成功清除对应的登录信息")
    public void test071clear() throws Exception {
        sleep(1000 * 5);
        pressHome();
        sleep(1000 * 3);
        clearData(AppInfo.PACKAGE_WEIBO);
//        ShellUtil.clearCache(AppInfo.PACKAGE_WEIBO);
        sleep(1000 * 5);
        clearData(AppInfo.PACKAGE_WECHAT);
//        ShellUtil.clearCache(AppInfo.PACKAGE_WECHAT);
        sleep(1000 * 5);
        clearData(AppInfo.PACKAGE_QQ);
//        ShellUtil.clearCache(AppInfo.PACKAGE_QQ);
        sleep(1000 * 5);
    }
    /*public void test077() throws UiObjectNotFoundException {
        long startTime=System.currentTimeMillis();
        //找到控件并点击
        UiObject search=new UiObject(new UiSelector().description("搜索"));
        search.click();
        sleep(1000);
        //输入文本
        UiObject editText=new UiObject(new UiSelector().resourceId("com.tencent.mm:id/fo"));
        editText.setText("rdgztest_69000");
        long endTime=System.currentTimeMillis();
        System.out.println("U1执行时间为："+(endTime-startTime));
    }*/
    public void test077()
    {
        clearData(AppInfo.PACKAGE_GOOGLEINSTALLER);
    }
}
