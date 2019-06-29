package com.meizu.test.IQIYI.impl;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.meizu.test.IQIYI.imple.IFunctionTraversal;
import com.meizu.test.IQIYI.testcase.PubScipt;

/**
 * Created by wuchaolin on 2016-4-6.
 */
public class FunctionTraversal extends PubScipt implements IFunctionTraversal{

    @Override
    public void recommend() throws Exception {
        UiObject topL=new UiObject(new UiSelector().className("android.widget.LinearLayout").instance(3));
        int a=topL.getChildCount();
        System.out.print("     is      "+  a);
        for(int i=1;i<a;i++){
            topL.getChild(new UiSelector().index(i)).clickAndWaitForNewWindow();
            sleep(1000);
            pressBack();
            sleep(500);
        }
        assertTrue(isExistById("com.qiyi.video:id/qiyi_logo"));//断言是否存在主界面logo
        UiScrollable TV=new UiScrollable(new UiSelector().className("android.widget.RadioGroup"));//推荐、电影、电视那一栏
        int b=TV.getChildCount();
        System.out.print("   is    "+ b);
        for(int i=0;i<b;i++){
            TV.getChild(new UiSelector().index(i)).clickAndWaitForNewWindow();
        }
        assertTrue(isExistById("com.qiyi.video:id/qiyi_logo"));//断言是否存在主界面logo
    }

    @Override
    public void Navigation() throws Exception {
        click("com.qiyi.video:id/naviCate");//点导航
        UiScrollable every=new UiScrollable(new UiSelector().resourceId("com.qiyi.video:id/category_content_recycler_view"));
        int a=every.getChildCount(new UiSelector().className("android.widget.RelativeLayout"));//对应子集的个数
        System.out.print("   is   "+a);
        for(int i=0;i<a;i++){
            UiObject ev=every.getChild(new UiSelector().className("android.widget.RelativeLayout").instance(i));
            ev.clickAndWaitForNewWindow();
            sleep(500);
            pressBack();
            sleep(500);
            assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/phoneTitle").text("导航")).waitForExists(1000));
        }
    }

    @Override
    public void VIP() throws Exception {
        click("com.qiyi.video:id/naviFind");//点VIP
        UiScrollable list=new UiScrollable(new UiSelector().className("android.widget.ListView"));
        int suma=list.getChildCount(new UiSelector().className("android.widget.ImageView"));
        int sumb=random(suma);
        System.out.print("   is    "+suma+"   and    "+ sumb);
        UiObject rand=new UiObject(new UiSelector().className("android.widget.ImageView").instance(sumb));
        list.scrollIntoView(rand);
        rand.clickAndWaitForNewWindow();
        sleep(500);
        pressBack();
        sleep(500);
        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/phoneTitle").text("爱奇艺VIP会员")).waitForExists(1000));
        UiCollection grop=new UiCollection(new UiSelector().className("android.widget.RadioGroup").index(0));//获取顶栏滑动集
        int c=grop.getChildCount();
        System.out.print("   is    "+c);
        for(int i=0;i<c;i++){
            UiObject cli=grop.getChild(new UiSelector().className("android.widget.RadioButton").index(i));
            cli.clickAndWaitForNewWindow();
            sleep(500);
        }
        assertTrue(new UiObject(new UiSelector().resourceId("com.qiyi.video:id/phoneTitle").text("爱奇艺VIP会员")).waitForExists(1000));
    }

    @Override
    public void my() throws Exception {
        click("com.qiyi.video:id/naviMy");//进入我的
        UiCollection list=new UiCollection(new UiSelector().resourceId("com.qiyi.video:id/my_main_root_listview"));//获取listview
        int a=list.getChildCount();
        System.out.print("   is "+a);
        for(int i=0;i<a;i++){
            watcher();
            UiObject cli=list.getChild(new UiSelector().index(i));
            cli.clickAndWaitForNewWindow();
            sleep(2*1000);
            pressBack();
            sleep(1000);
            UiSelector id=new UiSelector().resourceId("com.qiyi.video:id/phoneTitle").text("我的");
            UiObject my=new UiObject(id);
            assertTrue(my.waitForExists(1000));
        }

    }

    @Override
    public void makeFrends() throws Exception {
        UiObject makeF=new UiObject(new UiSelector().resourceId("com.qiyi.video:id/naviVip"));
        makeF.clickAndWaitForNewWindow();
        sleep(2*1000);
        pressBack();
        sleep(2*1000);
        if(makeF.exists()){//点掉第一次进入的水印
            makeF.clickAndWaitForNewWindow();
        }
        UiCollection list1=new UiCollection(new UiSelector().resourceId("com.iqiyi.paopao:id/tabbar_title").index(2));
        int a=list1.getChildCount();
        System.out.print("   is   "+a);
        UiScrollable listview=new UiScrollable(new UiSelector().resourceId("com.iqiyi.paopao:id/pp_pager_main"));
        for(int i=0;i<a;i++){
            UiObject cli=list1.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
            cli.clickAndWaitForNewWindow();
            sleep(500);
            listview.scrollToEnd(3);
            sleep(500);
        }
        assertTrue(new UiObject(new UiSelector().resourceId("com.iqiyi.paopao:id/pp_qiyi_title_tab_name").text("热门")).waitForExists(1000));
    }
}
