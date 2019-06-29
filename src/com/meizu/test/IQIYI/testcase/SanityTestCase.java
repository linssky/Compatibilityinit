package com.meizu.test.IQIYI.testcase;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Expectation;
import com.meizu.automation.Priority;
import com.meizu.automation.Steps;
import com.meizu.common.APPINFO;
import com.meizu.test.IQIYI.impl.Compatible;
import com.meizu.test.IQIYI.impl.CoreFunctions;
import com.meizu.test.IQIYI.impl.FunctionTraversal;
import com.meizu.test.IQIYI.impl.Land;
import com.meizu.test.IQIYI.imple.ICompatible;
import com.meizu.test.IQIYI.imple.ICoreFuntions;
import com.meizu.test.IQIYI.imple.IFunctionTraversal;
import com.meizu.test.IQIYI.imple.ILand;
import com.meizu.test.util.ShellUtil;


/**
 * Created by wuchaolin on 2016-3-24.
 */
public class SanityTestCase extends PubScipt{

    //-----------------------------实例化------------------------------------------------
    ILand land=new Land();
    ICoreFuntions coreFuntions=new CoreFunctions();
    ICompatible compatible=new Compatible();
    IFunctionTraversal functionTraversal=new FunctionTraversal();


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
            try {
                throw e;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void setUp() throws Exception {
        if(isExistByPackageName(APPINFO.PACKAGE_AIQIYI)) {
            exitApp(APPINFO.PACKAGE_AIQIYI);
        }
        ShellUtil.setSystemInput();
        sleep(500);
        startApp(APPINFO.PACKAGE_AIQIYI, APPINFO.ACTIVITY_AIQIYI);
        sleep(500);
        UiObject shuiyin=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/card_guide_center_view"));
        if(shuiyin.exists()){
            shuiyin.clickAndWaitForNewWindow();
            exitApp(APPINFO.PACKAGE_AIQIYI);
            sleep(500);
            startApp(APPINFO.PACKAGE_AIQIYI,APPINFO.ACTIVITY_AIQIYI);
            sleep(500);
        }
        watcher();
        super.setUp();
    }

    public void tearDown() throws Exception {
        ShellUtil.setSystemInput();
        exitApp(APPINFO.PACKAGE_AIQIYI);
        super.tearDown();
    }

    @Steps("登陆")
    @Expectation("正常登陆")
    @Priority
    public void test001() throws Exception {
//        StartApp("爱奇艺",PACKAGE_AIQIYI);
        land.registerAccount();
    }

    @Steps("主界面-下滑-随机播放")
    @Expectation("可正常播放")
    @Priority
    public void test002() throws Exception {
        coreFuntions.randomPlay();
    }

    @Steps("搜索-语音图标")
    @Expectation("正常搜索")
    @Priority
    public void test003() throws Exception {
        coreFuntions.voiceSearch();
    }

    @Steps("搜索-中文输入“画江湖之灵主”-输入法上点搜索-点击视频播放")
    @Expectation("正常搜索")
    @Priority
    public void test004() throws Exception {
        coreFuntions.textSearch();
    }

    @Steps("下滑-点击“我来评论”-中文输入“还不错哦”-发表")
    @Expectation("正常发表评论")
    @Priority
    public void test005() throws Exception {
        land.registerAccount();
        coreFuntions.comment();
    }

    @Steps("点缓存-勾选任意集数-确定缓存-缓存管理")
    @Expectation("正常下载")
    @Priority
    public void test006() throws Exception {
        coreFuntions.download();
    }

    @Steps("点“正在缓存”-全部暂停-右上角删除-勾选随机下载项-点左下角取消全选-左下角全选-删除")
    @Expectation("正常删除")
    @Priority
    public void test007() throws Exception {
        coreFuntions.deleteDownload();
    }

    @Steps("点Home键-下拉通知栏-点爱奇艺缓存通知")
    @Expectation("正常跳转")
    @Priority
    public void test008() throws Exception {
        coreFuntions.jump();
    }

    @Steps("分享-微信好友-搜索选择自己-返回爱奇艺")
    @Expectation("正常分享")
    @Priority
    public void test009() throws Exception {
        coreFuntions.share();
    }

    @Steps("点视频右上角收藏图标-两次back回到主界面-我的-收藏-画江湖之灵主")
    @Expectation("正常收藏")
    @Priority
    public void test010() throws Exception{
        land.registerAccount();
        init();
        coreFuntions.Collection();
    }

    @Steps("跳转至全屏")
    @Expectation("正常跳转")
    @Priority
    public void test011() throws Exception {
        coreFuntions.jumpFullScreen();
    }

    @Steps("全屏功能遍历")
    @Expectation("无闪退等")
    @Priority
    public void test012() throws Exception {
        coreFuntions.fullScreenFunctionTraversal();
    }

    @Steps("点锁定屏幕-点back")
    @Expectation("不会退出，无闪退，无“无响应”提示")
    @Priority
    public void test013() throws Exception {
        coreFuntions.screenLock();
    }

    @Steps("弹幕-信息图标-输入“矮油不错”-发射")
    @Expectation("输入弹幕时视频暂停，输入后继续播放")
    @Priority
    public void test014() throws Exception {
        land.registerAccount();
        init();
        coreFuntions.barrage();
    }

    @Steps("文件管理-视频-任意视频-爱奇艺")
    @Expectation("跳至爱奇艺播放界面，无闪退，无“无响应”提示")
    @Priority
    public void test015() throws Exception {
        coreFuntions.localPlay();
    }

    @Steps("拍摄上传功能遍历")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public  void test016() throws Exception {
        coreFuntions.shotUploadFunctionTraversal();
    }

    @Steps("点录像3s后-下一步-下一步（等待导出完成跳至发布页面）-点击添加描述-输入“矮油这个不错哦”-点完成-点发布")
    @Expectation("跳转至我的视频")
    @Priority
    public void test017() throws Exception {
        land.registerAccount();
        init();
        coreFuntions.shot();
    }

    @Steps("我的-扫一扫图标")
    @Expectation("正常跳转无闪退，无“无响应”提示")
    @Priority
    public void test018() throws Exception {
        compatible.Sweep();
    }

    @Steps("我的-个人资料-编辑资料-头像-拍照")
    @Expectation("跳转至相机界面，正常跳转无闪退，无“无响应”提示")
    @Priority
    public void test019() throws Exception {
        land.registerAccount();
        setUp();
        compatible.shotphoto();
    }

    @Steps("播放记录/订阅/电视剧/电影/滑动/进入任意视频")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public void test020() throws Exception {
        functionTraversal.recommend();
    }

    @Steps("娱乐/资讯/片花/微电影/动漫/滑动")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public void test021() throws Exception {
        functionTraversal.Navigation();
    }

    @Steps("滑动/开通会员/微信支付")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public void test022() throws Exception {
        functionTraversal.VIP();
    }

    @Steps("消息/播放记录/我的设备/设置/滑动")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public void test023() throws Exception {
        functionTraversal.my();
    }

    @Steps("订阅/滑动/进入任意视频/热门/泡泡/查看圈子/加入")
    @Expectation("无闪退，无“无响应”提示")
    @Priority
    public void test024() throws Exception {
        functionTraversal.makeFrends();
    }





}
