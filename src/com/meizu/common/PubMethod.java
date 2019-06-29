//package com.meizu.common;
//
//import com.android.uiautomator.core.*;
//import com.meizu.automation.constants.AutoException;
//import com.meizu.test.common.AutoAllInOneTestCase;
//import com.meizu.test.util.ShellUtil;
//import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;
//
///**
// * Created by zengliang
// */
//public class PubMethod {
//
//	// 每条用例执行前均会运行此方法，可将监控类加入此方法中
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        // 加入监听事件
//		ShellUtil.startApp(APPINFO.PACKAGE_VIDEO);
//        Watcher mywatcher = new Watcher();
//        mywatcher.watcherAlarmClock();   // 闹钟监听
//    }
//
//    /*
//     * 1.用例在运行过程时，也会运行此方法
//     * 2.通过sendResult()方法，将结果反馈至平台
//     */
//    @Override
//    protected void runTest() throws Throwable {
//        try {
//            super.runTest();
//            ShellUtil.sendResult(getName(), true);
//        } catch (Throwable e) {
//            ShellUtil.sendResult(getName(), false);
//            throw new Throwable(e);
//        }
//    }
//
//    /*
//     * 1.每条用例在执行完成后，均会执行此方法
//     * 2.可在此方法加入退出应用的方法
//     */
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        ShellUtil.exitApp(APPINFO.PACKAGE_VIDEO);
//    }
//
//
//
///****************************************************************    自写函数  ***************************************************************************/
//
//    @Steps("1.点击我"
//            + "2.点击设置"
//            + "3.点击清楚缓存")
//    @Expectation("5.添加成功")
//	public void test001(){
//
//	}
//
//
//    AutoAllInOneTestCase auto = new AutoAllInOneTestCase();
//
//    /**
//     * 滚动到特定对象
//     * @param IdScrollRegion    滚动列表
//     * @param IdScrollToTarget  目标
//     * @param MaxTimes          1、若为非空，向上、向下最大滚动次数; 2、若为空，向上、向下最大滚动次数为默认值
//     * @throws UiObjectNotFoundException
//     * Created by zengliang
//     */
//    public void scrollToTargetByID(String IdScrollRegion, String IdScrollToTarget, int ...MaxTimes) throws UiObjectNotFoundException {
//        int maxTimesLength = MaxTimes.length;
//        UiScrollable scrollableList = new UiScrollable(new UiSelector().resourceId(IdScrollRegion));
//        double DeadZone = scrollableList.getSwipeDeadZonePercentage();
//        scrollableList.setSwipeDeadZonePercentage(0.25);
//        if(maxTimesLength == 0){
//            scrollableList.scrollIntoView(new UiSelector().resourceId(IdScrollToTarget));
//        }
//        else {
//            int MaxStep = scrollableList.getMaxSearchSwipes();
//            scrollableList.setMaxSearchSwipes(MaxTimes[0]);
//            scrollableList.scrollIntoView(new UiSelector().resourceId(IdScrollToTarget));
//            scrollableList.setMaxSearchSwipes(MaxStep);
//        }
//        scrollableList.setSwipeDeadZonePercentage(DeadZone);
//    }
//
//    /**
//     * 向结束端滚动指定次数
//     * @param IdScrollRegion 滚动列表
//     * @param Times          最大滚动次数
//     * @throws UiObjectNotFoundException
//     *  Created by zengliang
//     */
//    public void scrollToEndSomeTimes(String IdScrollRegion, int Times) throws UiObjectNotFoundException {
//        UiScrollable scrollableList = new UiScrollable(new UiSelector().resourceId(IdScrollRegion));
//        double DeadZone = scrollableList.getSwipeDeadZonePercentage();
//        scrollableList.setSwipeDeadZonePercentage(0.25);
//        scrollableList.scrollToEnd(Times);
//        scrollableList.setSwipeDeadZonePercentage(DeadZone);
//    }
//
//    /**
//     * 向开始端滚动指定次数
//     * @param IdScrollRegion 滚动列表
//     * @param Times          最大滚动次数
//     * @throws UiObjectNotFoundException
//     *  Created by zengliang
//     */
//    public void scrollToBeginningSomeTimes(String IdScrollRegion, int Times) throws UiObjectNotFoundException {
//        UiScrollable scrollableList = new UiScrollable(new UiSelector().resourceId(IdScrollRegion));
//        double DeadZone = scrollableList.getSwipeDeadZonePercentage();
//        scrollableList.setSwipeDeadZonePercentage(0.25);
//        scrollableList.scrollToBeginning(Times);
//        scrollableList.setSwipeDeadZonePercentage(DeadZone);
//    }
//
//    /**
//     * 从相同组件中获取指定控件或者任一控件
//     * @param IdParentList  父类
//     * @param IdChild       子类集
//     * @param Instance      1、为空时选取任一控件； 2、不为空时，选取指定控件
//     * @return
//     * @throws UiObjectNotFoundException
//     *  Created by zengliang
//     */
//    public UiObject getSameButton(String IdParentList, String IdChild, int ...Instance) throws UiObjectNotFoundException {
//        UiCollection parentList = new UiCollection(new UiSelector().resourceId(IdParentList));
//        int MaxSize= parentList.getChildCount(new UiSelector().resourceId(IdChild)) - 1;
//        UiObject Buttons = null;
//        int instanceLength = Instance.length;
//        if(instanceLength == 0){
//            Buttons = parentList.getChildByInstance(new UiSelector().resourceId(IdChild)
//                    , (int) Math.round(Math.random()*MaxSize));
//        }
//        else {
//            Buttons = parentList.getChildByInstance(new UiSelector().resourceId(IdChild),Instance[0]);
//        }
//        return Buttons;
//    }
//
//
//    //模拟用户输入文字，封装
//
//    /**
//     * 模拟用户使用Utf7输入法输入文字
//     * @param IdTextTarget  输入文字的目标
//     * @param Text          输入的文字
//     * @throws AutoException
//     */
//    public void sendTextById(String IdTextTarget, String Text) throws AutoException {
//        if(auto.isExistById(IdTextTarget)){
//            long setTextDelay = Configurator.getInstance().getKeyInjectionDelay();
//            Configurator.getInstance().setKeyInjectionDelay(40);
//            ShellUtil.setUtf7Input();
//            auto.setTextById(IdTextTarget, Utf7ImeHelper.e(Text));
//            ShellUtil.setSystemInput();
//            Configurator.getInstance().setKeyInjectionDelay( setTextDelay);  //恢复系统默认延时
//        }
//        else {
//            auto.fail("Fail To Find Controls");
//        }
//    }
//
//    /**
//     * 模拟用户使用系统输入法输入文字
//     * @param IdTextTarget  输入文字的目标
//     * @param Text          输入的文字
//     * @throws AutoException
//     */
//    public void sendSystemTextById(String IdTextTarget, String Text) throws UiObjectNotFoundException {
//        if(auto.isExistById(IdTextTarget)) {
//            long setTextDelay = Configurator.getInstance().getKeyInjectionDelay();
//            Configurator.getInstance().setKeyInjectionDelay(40);
//            UiObject textTarget = new UiObject(new UiSelector().resourceId(IdTextTarget));
//            textTarget.setText(Text);
//            Configurator.getInstance().setKeyInjectionDelay(setTextDelay);  //恢复系统默认延时
//        }
//        else {
//            auto.fail("Fail To Find Controls");
//        }
//    }
//
//    /**
//     * 获取当前页面的Package and Activity
//     * @return 以String类型返回当前页面的Package and Activity
//     * @throws IOException
//     * Created by zengliang
//     */
//    public String getPackageAndActivityName() throws IOException {
//        Process process = Runtime.getRuntime().exec(new String[] {"sh", "-c", "dumpsys window w ^|grep mCurrentFocus"});
//        BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder builder = new StringBuilder();
//        String line = null;
//        while ((line = reader.readLine()) != null)
//        {
//            builder.append(line);
//        }
//        String regEx="\\}(.*?)|(.*?)u0";//正则表达式匹配规则匹配以"u0"开头,"}"结尾的的字符串
//        Pattern pattern = Pattern.compile(regEx);
//        Matcher mTarget = pattern.matcher(builder.toString());
//        String packageAndActivityName = mTarget.replaceAll("").trim();
//        return packageAndActivityName;
//    }
//
//
//}
