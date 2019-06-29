package com.meizu.test.ThirdPartySanity.testcase;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.RemoteException;
import com.android.uiautomator.core.*;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wuchaolin on 2016-6-2.
 */
public class Common extends AutoAllInOneTestCase {
    public  static Context context;


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
                }
            }
        });
        UiDevice.getInstance().registerWatcher("确定", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject ok=new UiObject(new UiSelector().resourceId("android:id/button1").text("确定").clickable(true));//定义弹出的允许对话框
                if(ok.exists()){
                    System.out.print("Flyme6相机弹出权限框");
                    try {
                        ok.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("淘宝", new UiWatcher() {
            @Override
            public boolean checkForCondition() {

                UiObject dialog=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/ocean_home_double11_dialog_pic"));
                UiObject close=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/ocean_home_double11_dialog_close"));
                if(dialog.exists())
                {
                    try {
                       close.click();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.print("弹出权限框");
                    return true;
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("微博评分弹窗", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject notk = new UiObject(new UiSelector().resourceId("com.sina.weibo:id/btn_3").text("不了，谢谢"));//弹出的“给我评分”窗口
                if (notk.exists()) {
                    try {
                        notk.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;
                }
            }

        });
        UiDevice.getInstance().registerWatcher("淘宝神秘礼包", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject bag=new UiObject(new UiSelector().packageName("com.taobao.taobao").description("main.html?spm=a1z8m.7980655.0"));
                if(bag.exists()){
                try {
                    bag.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                    return true;
                } else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("淘宝见面礼", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject closed=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/homepage_overlay_close"));
                if(closed.exists()){
                    try {
                        closed.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("微淘引导界面", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject weitao=new UiObject(new UiSelector().description("引导界面"));
                if(weitao.exists()){
                    try {
                        weitao.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("微博同意", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject agree=new UiObject(new UiSelector().packageName("com.sina.weibo").className("android.widget.TextView").text("同意"));
                if(agree.exists()){
                try {
                    if(agree.exists()&agree.isClickable()){
                        agree.clickAndWaitForNewWindow();
                    }
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                    return true;
                } else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("微博确定", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject agree=new UiObject(new UiSelector().packageName("com.sina.weibo").className("android.widget.TextView").text("确定"));
               if(agree.exists()){
                try {
                    if(agree.exists()&agree.isClickable()){
                        agree.clickAndWaitForNewWindow();
                    }
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                return false;
            }
            }
        });
        UiDevice.getInstance().registerWatcher("进入微博", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject enterWeibo=new UiObject(new UiSelector().packageName("com.sina.weibo").resourceId("com.sina.weibo:id/iv_navigater_clickable"));
               if(enterWeibo.exists()) {
                   try {
                       if (enterWeibo.exists() & enterWeibo.isClickable()) {
                           enterWeibo.clickAndWaitForNewWindow();
                       }
                   } catch (UiObjectNotFoundException e) {
                       e.printStackTrace();
                   }
                   return true;
               }else {
                   return false;
               }
            }
        });
        UiDevice.getInstance().registerWatcher("微博更新提示", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject RightNow=new UiObject(new UiSelector().text("立即更新"));
                UiObject later=new UiObject(new UiSelector().text("以后再说"));
                if(RightNow.exists()) {
                    try {
                        later.click();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("淘宝更新提示", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject now=new UiObject(new UiSelector().text("立即下载"));
                UiObject cancel=new UiObject(new UiSelector().text("取消"));
                if(now.exists()) {
                    try {
                        cancel.click();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("淘宝双十一弹框", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject dialog=new UiObject(new UiSelector().text("我要发问"));
                UiObject cancel=new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/ocean_home_double11_dialog_close"));
                if(dialog.exists()) {
                    try {
                        cancel.click();
                        sleep(1000*2);
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });
        UiDevice.getInstance().registerWatcher("微博版本更新", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject gengxin=new UiObject(new UiSelector().className("android.widget.TextView").text("以后再说"));
                if(gengxin.exists()){
                    try {
                        gengxin.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("低版本覆盖安装", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject info=new UiObject(new UiSelector().textContains("降级"));
                UiObject bt=new UiObject(new UiSelector().text("继续"));
                if(info.exists()){
                    try {
                       bt.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("高版本覆盖安装", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject info=new UiObject(new UiSelector().textContains("升级"));
                UiObject bt=new UiObject(new UiSelector().text("继续"));
                if(info.exists()){
                    try {
                        bt.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("支付宝弹窗", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject closed=new UiObject(new UiSelector().resourceId("com.alipay.android.phone.discovery.o2ohome:id/image_close"));
                if(closed.exists()){
                    try {
                        closed.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("QQ挂件广告", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject retrun=new UiObject(new UiSelector().packageName("com.tencent.mobileqq").className("android.widget.Button").text("跳过").clickable(true));
                if(retrun.exists()){
                    try {
                        retrun.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }
    protected String exec(String commandLine)
    {//TODO 执行shell命令
        StringBuilder result = new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(commandLine);//创建一个线程执行shell指令
//            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));//创建输入文件流
            String e=null;
            while((e = reader.readLine()) != null) {
                result.append(e);//将执行结果追加到result中
            }

            reader.close();//关闭输入流
            process.destroy();//销毁进程
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return result.toString();
    }
    public  void clearData(String packageName) {
        exec("pm clear " + packageName);
    }
    public boolean isInstalled(String packageName)
    {//TODO 判断应用是否安装
        String str=exec("pm list packages "+packageName);//执行shell指令并取得返回值
        if(str!=null&&str.contains(packageName))
            return  true;
        else
            return  false;
    }


    public boolean isSameVersion(String packageName,String versionName)
    {//TODO 若安装判断版本号是否与指定app相同
//        String lineStr = null;
//        String version = null;
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec("pm dump " + packageName);
//            InputStreamReader e = new InputStreamReader(process.getInputStream());//文件输入流
//            BufferedReader input = new BufferedReader(e);//文件输出流
//            //一行一行地去读取
//            while((lineStr = input.readLine()) != null) {
//                if (lineStr.contains("versionName=")) {
//                    version = lineStr.split("versionName=")[1].toString();//获得版本名称
//                    System.out.println("------VERSIONNAME="+version);
//                    //System.out.println("------VERSIONNAME="+version);
//                    if (version.equals(versionName))
//                        return true;
//                    else
//                        return false;
//                }
//            }
//            e.close();
//            input.close();
//        } catch (IOException var9) {
//            var9.printStackTrace();
//        } finally {
//            process.destroy();
//        }
//
//        return false;
    String getVersion=exec("pm dump " + packageName);
        System.out.println("00000000000000000"+versionName);
        if(getVersion.contains(versionName)){
            System.out.println("---------版本一样sameV--------");
            return true;
        }else {
            System.out.println("---------版本不一样diffV--------");
            return false;
        }
    }
    public void installApk(String apkName)
    {
        String path=Environment.getExternalStorageDirectory().getPath()+"/"+apkName;
        System.out.println("------安装路径为：-------"+path);
        exec("pm install -d -r "+path);//安装下载的apk（允许降级和覆盖安装）
        System.out.println("-----安装成功-----");

    }
public void install(String packageName,String versionName,String apkName,String  url) throws IOException
{
    //TODO 判断应用是否安装，未安装则去下载安装；已安装则去判断版本号是否与指定应用版本号相同，不同则去安装；相同则直接清除数据即可
    //TODO 参数：packageName：应用包名 versionName：指定应用版本号 apkName：下载apk命名
    if(!isInstalled(packageName))
    {//没有安装应用
        System.out.println("————没有安装！noInst————");
        downLoad(url,apkName);//从测试平台上下载apk到手机
        System.out.println("--------下载完成---------"+url);
        installApk(apkName);
    }
    else
    {//安装了应用则去判断版本号是否与指定应用相同
        if(isSameVersion(packageName,versionName))
        {//版本号相同
            System.out.println("--------same版本号相同，开始测试------------");
            clearData(packageName);//清除应用数据
        }
        else
        {//版本号不同
            downLoad(url,apkName);//从测试平台上下载apk到手机
            System.out.println("------diff版本号不同------"+url);
            installApk(apkName);
        }

    }
}
    public String getVersionName(String packageName)
    {//TODO 获得应用版本名称
        String lineStr = null;
        String version = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("pm dump " + packageName);
            InputStreamReader e = new InputStreamReader(process.getInputStream());//文件输入流
            BufferedReader input = new BufferedReader(e);//文件输出流
            while((lineStr = input.readLine()) != null) {//一行一行地去读取
                if(lineStr.contains("versionName=")) {
                    version = lineStr.split("versionName=")[1].toString();//获得版本名称
                    break;
                }
            }

            e.close();
            input.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        } finally {
            process.destroy();
        }
        System.out.println(version);
        return version;

    }
    public boolean isInstalled1(String packagename)
    {
        //TODO 根据包名判断是否安装应用2016/10/11
        PackageManager pm = context.getPackageManager();//获得包管理器
        try {
            PackageInfo packageinfo = pm.getPackageInfo(packagename, 0);//获得包信息
            if (packageinfo != null)//应用存在
                return true;
            else
                return false;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return false;
    }
    public boolean isInstalledApk1(String packagename)
    {
        //TODO 判断是否已经安装指定应用2016/10/11
        PackageManager pm = context.getPackageManager();//获得包管理器
        try {
            PackageInfo packageinfo = pm.getPackageInfo(packagename, 0);
            String version=packageinfo.versionName;//获取版本号
            System.out.println("获取到的应用版本号是：   "+version);
            if (packageinfo != null&&version.equals(AppInfo.VERSION_WECHAT))//应用存在且版本号相同
                return true;
            else
                return false;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return false;
    }
    public void downLoad(String uri,String apkName) throws IOException {
        //TODO 从网址上下载apk到手机上2016/10/11
        InputStream inputStream = null;
        OutputStream os = null;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String chrome = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31";
        connection.setRequestProperty("User-Agent", chrome);//修改
        connection.setConnectTimeout(300 * 1000); //超时时间
        connection.connect();  //连接
        String path= Environment.getExternalStorageDirectory().getPath()+"/"+apkName;
        File file = new File(path);
        file.createNewFile();
        //Log.v(connection.getResponseCode() + "", "code");
        System.out.println(connection.getResponseCode());
        if (connection.getResponseCode()==200)
            try {
                inputStream = connection.getInputStream();
                os = new FileOutputStream(file);
                int len;
                // 1K的数据缓冲
                byte[] bs = new byte[1024 * 10];
                while ((len = inputStream.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {// 完毕，关闭所有链接
                try {
                    if (null != os) os.close();

                    if (null != inputStream) inputStream.close();
                } catch (IOException e) {
                }
            }
    }
    public void deleteContext() throws UiObjectNotFoundException {//TODO 删除QQ聊天信息
        UiObject icon=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/ivTitleBtnRightImage"));
        icon.clickAndWaitForNewWindow();//点击聊天设置图标
        UiObject context=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/chatHistory"));
        context.clickAndWaitForNewWindow();//点击聊天记录
        UiObject delete=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
        delete.click();//点击删除图标
        UiObject deleteContext=new UiObject(new UiSelector().text("删除漫游聊天记录"));
        delete.click();//点击删除漫游聊天记录
        UiObject ok=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/dialogRightBtn"));//点击确定
    }
   /* public void installApk(String str) throws IOException
    {
        //TODO 手机指定路径下的apk2016/10/11
        String path=Environment.getExternalStorageDirectory().getPath()+"/";
        File file=new File(path+str);
       Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //设置intent的data和type属性
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
    }*/
    /*public void installAndReset() throws IOException {
        //TODO 判断是否安装了指定应用，没有安装则去下载安装；安装了则清除数据
        if(!isInstalledApk(AppInfo.PACKAGE_WECHAT))
        {
            //没有安装指定应用
            downLoad();//去服务器下载到手机目录
            installApk(AppInfo.APKNAME_WECHAT);//安装apk
            int i=0;
            while(!isInstalledApk(AppInfo.PACKAGE_WECHAT)&&i<10)
            {//每10s判断一次是否安装好了指定应用，没有则提示正在安装，安转好了则退出
                sleep(10*1000);
                System.out.println("--------正在安装--------");
                i++;
            }
            System.out.println("--------安装成功--------");
        }
        else
        {//已经安装了微信应用
            clearCache(AppInfo.PACKAGE_WECHAT);//清除应用数据

        }

    }*/
    public static String execCommd(String commandLine) {
        //TODO 执行cmd命令
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(commandLine);
            process.waitFor();
            process.destroy();
        } catch (IOException var4) {
            var4.printStackTrace();
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        }

        return result.toString();
    }
    public static void clearCache(String packageName) {
        execCommd("pm clear " + packageName);
    }
    //判断是否登录的方法,未登录的话就重新登录
    public  void isLand() throws UiObjectNotFoundException {
        UiSelector land=new UiSelector().text("登录");
        UiObject Land=new UiObject(land);//主界面登录按钮
        UiObject isL= new UiObject(new UiSelector().className("android.widget.RelativeLayout").descriptionContains("更多功能按钮"));//微信界面右上角加号
        UiObject more=new UiObject(new UiSelector().text("更多"));//有登录记录时的更多按钮
        if(!isL.exists()) {
            if (Land.waitForExists(1000 * 5)) {
                Land.clickAndWaitForNewWindow();
            }
            if(more.exists()){
                more.clickAndWaitForNewWindow();
                sleep(1000);
                UiObject tab=new UiObject(new UiSelector().text("切换帐号"));
                tab.clickAndWaitForNewWindow();
                sleep(1000*2);
            }
                UiObject otherLand = new UiObject(new UiSelector().text("使用其他方式登录"));//使用其他方式登录按钮
                otherLand.clickAndWaitForNewWindow();
                UiObject accounts = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));//账号输入框
                UiObject passWord = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));//密码输入框
                ShellUtil.setUtf7Input();
                accounts.setText(Utf7ImeHelper.e(AccountInfo.ACCOUNT_WECHAT));
                passWord.setText(Utf7ImeHelper.e(AccountInfo.PASSWORD_WECHAT));
                UiObject load = new UiObject(new UiSelector().text("登录"));//输入后的登录按钮
                load.clickAndWaitForNewWindow();
                sleep(1000 * 15);
        }
    }


    @Steps("重写启动应用，加入判断应用是否已安装，若没有安装则到应用商店下载安装")
    @Expectation("判断正常，安装运行正常")
    public void STARTAPP1(String PackageName, String ActivityName) throws UiObjectNotFoundException, InterruptedException {
        startApp(PackageName, ActivityName);
        sleep(2000);
        ShellUtil.setUtf7Input();
        UiSelector pack =new UiSelector().packageName("com.meizu.flyme.launcher");//桌面
        UiObject Pack = new UiObject(pack);
        if (Pack.waitForExists(2000))
        {
            startApp(AppInfo.PACKAGE_MSTORE, AppInfo.ACTIVITY_MSTORE);//启动应用商店
            sleep(1000*3);
            UiSelector intoStore = new UiSelector().resourceId("com.meizu.mstore:id/btnSkip").text("进入市场");
            UiObject IStore = new UiObject(intoStore);   //早期进入应用中心的控件

            UiSelector intoStore1=new UiSelector().resourceId("com.meizu.mstore:id/btnSkip").text("进入应用商店");
            UiObject IStore1=new UiObject(intoStore1);//2016年9月8日修改，进入应用商店控件
            if (IStore.waitForExists(2000))//如果有进入市场的提示，则点击进入
            {
                IStore.clickAndWaitForNewWindow(2000);
            }
            if(IStore1.waitForExists(1000*2))//如果有进入应用商店的提示，则点击进入
            {
                IStore1.clickAndWaitForNewWindow(1000*2);
            }
            sleep(1000*3);
            UiSelector search=new UiSelector().resourceId("com.meizu.mstore:id/search_menu");
            UiObject Search=new UiObject(search);//主界面右上角的搜索图标

            UiSelector search1=new UiSelector().resourceId("com.meizu.mstore:id/mc_search_edit");
            UiObject  Search1=new UiObject(search1);//2016年9月8日修改，应用商店的搜索控件
            if(Search.exists()){
                Search.clickAndWaitForNewWindow(1000 * 2);
            }
//            else {
//                ShellUtil.clearCache(AppInfo.PACKAGE_MSTORE);
//                sleep(1000*2);
//                STARTAPP(PackageName, ActivityName);
//                sleep(1000*2);
//            }
            if(Search1.exists()){
                Search1.clickAndWaitForNewWindow(1000 * 2);
            }
//            else {
//                ShellUtil.clearCache(AppInfo.PACKAGE_MSTORE);
//                sleep(1000*2);
//                STARTAPP(PackageName, ActivityName);
//                sleep(1000*2);
//            }
                UiObject searchApp=new UiObject(new UiSelector().resourceId("com.meizu.mstore:id/mc_search_edit"));//搜索应用界面
            UiObject trueButtom=new UiObject(new UiSelector().resourceId("com.meizu.mstore:id/mc_search_textView"));//确定搜索按钮

            if(PackageName==AppInfo.PACKAGE_WEIBO)
            {
                searchApp.setText(Utf7ImeHelper.e("微博"));
            }
            if(PackageName==AppInfo.PACKAGE_WECHAT)
            {
                searchApp.setText(Utf7ImeHelper.e("微信"));
            }
            if(PackageName==AppInfo.PACKAGE_QQ)
            {
                searchApp.setText(Utf7ImeHelper.e("QQ"));
            }
            if(PackageName==AppInfo.PACKAGE_TAOBAO)
            {
                searchApp.setText(Utf7ImeHelper.e("手机淘宝"));
            }
            if(PackageName==AppInfo.PACKAGE_ALIPAY)
            {
                searchApp.setText(Utf7ImeHelper.e("支付宝"));
            }
            if(PackageName==AppInfo.PACKAGE_GOOGLEINSTALLER)
            {
                searchApp.setText(Utf7ImeHelper.e("谷歌安装器"));
            }
            if(PackageName==AppInfo.PACKAGE_ANTUTU)
            {
                searchApp.setText(Utf7ImeHelper.e("安兔兔"));
            }
            if(PackageName==AppInfo.PACKAGE_ANTUTU3D)
            {
                searchApp.setText(Utf7ImeHelper.e("安兔兔3D"));
            }
            if(PackageName==AppInfo.PACKAGE_GFXBENCH)
            {
                searchApp.setText(Utf7ImeHelper.e("GFXbench"));
            }

            trueButtom.clickAndWaitForNewWindow(1000*3);
            UiCollection searchList=new UiCollection(new UiSelector().className("flyme.support.v7.widget.RecyclerView").resourceId("com.meizu.mstore:id/recyclerView"));
            UiObject install=searchList.getChildByInstance(new UiSelector().resourceId("com.meizu.mstore:id/btnInstall"),0);
            UiObject aa=install.getChild(new UiSelector().resourceId("com.meizu.mstore:id/circular_button").text("打开"));
            if(!aa.exists()){
                install.clickAndWaitForNewWindow();
            }
            sleep(1000*5);
//            UiObject first= searchList.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
//            UiObject child=first.getChild(new UiSelector().className("android.widget.FrameLayout").index(2));
//            UiObject child1=child.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
//            UiObject open=child1.getChild(new UiSelector().resourceId("com.meizu.mstore:id/circular_button").text("打开"));//此四个控件是逐层定位到搜索列表的第一个“打开”按钮
            int time=0;
            while(!aa.exists()){//判断是否下载完成
                if(time>19){
                    System.out.print("Download timeout!");
                    break;
                }else {
                    trueButtom.clickAndWaitForNewWindow();
                    sleep(1000*15);
                    time++;
                }
            }
            exitApp(AppInfo.PACKAGE_MSTORE);
            sleep(1000*2);
            ShellUtil.clearCache(AppInfo.PACKAGE_MSTORE);
            sleep(1000*2);
            startApp(PackageName,ActivityName);
            sleep(1000*2);
            UiSelector boom=new UiSelector().text("sdfsfs");//触发监听器
            sleep(1000);
            UiSelector boom1=new UiSelector().text("sdfsfs");//触发监听器
            sleep(1000);
            UiSelector boom2=new UiSelector().text("sdfsfs");//触发监听器
        }
    }

    public  void STARTAPP(String packageName,String versionName,String apkName,String  url,String  activityName) throws IOException {
        install(packageName,versionName,apkName,url);
        startApp(packageName,activityName);//启动应用
        sleep(5*1000);
    }
    public void enterChat() throws UiObjectNotFoundException, AutoException {
        //TODO 搜索联系人并进入聊天页面
        //使用搜索框搜索好友
        UiObject intoSearchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        intoSearchFriend.click();
        UiObject searchFriend=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/et_search_keyword"));
        searchFriend.setText("t");
        sleep(3000);
        clickByTextContains("Test");
        sleep(2000);

    }
    public void deleteQqContext() throws UiObjectNotFoundException {//TODO 删除QQ聊天记录
        //删除漫游聊天记录
        UiObject set=new UiObject(new UiSelector().description("聊天设置"));
        set.clickAndWaitForNewWindow();
        sleep(1000*3);
        UiObject history=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/chatHistory"));
        history.clickAndWaitForNewWindow();
        sleep(1000*5);
        UiCollection comTitle=new UiCollection(new UiSelector().resourceId("com.tencent.mobileqq:id/rlCommenTitle"));
        UiObject list=comTitle.getChild(new UiSelector().resourceId("com.tencent.mobileqq:id/name").instance(0));
        UiObject dele=list.getChild(new UiSelector().index(0));
        dele.clickAndWaitForNewWindow();
        sleep(1000*3);
        UiObject deleHis=new UiObject(new UiSelector().text("删除漫游聊天记录"));
        deleHis.clickAndWaitForNewWindow();
        sleep(1000*3);
        UiObject OK=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/dialogRightBtn").text("确定"));
        OK.clickAndWaitForNewWindow();
        sleep(1000*3);

    }
    public void deleteWcContext() throws UiObjectNotFoundException {
        //TODO 删除QQ聊天记录
        UiObject record=new UiObject(new UiSelector().description("聊天信息"));//右上角信息
        record.clickAndWaitForNewWindow();
        UiObject remove=new UiObject(new UiSelector().text("清空聊天记录"));
        remove.clickAndWaitForNewWindow();
        UiObject clear=new UiObject(new UiSelector().text("清空"));//弹出框的清空选项
        clear.clickAndWaitForNewWindow();
        sleep(1000*3);
    }
    public  void enterWeiChat() throws UiObjectNotFoundException {
        //TODO 进入微信聊天界面
        UiObject address=new UiObject(new UiSelector().className("android.widget.TextView").text("通讯录"));//进入通讯录
        address.clickAndWaitForNewWindow();
        UiObject searchInAd=new UiObject(new UiSelector().description("搜索"));//通讯录界面搜索
        searchInAd.clickAndWaitForNewWindow();
        UiObject setText=new UiObject(new UiSelector().className("android.widget.EditText").text("搜索"));//搜索输入栏控件
        setText.setText(Utf7ImeHelper.e("rdgztest_69000"));
        UiObject test=new UiObject(new UiSelector().className("android.widget.TextView").text("rdgztest_69000"));//搜索出的第一个联系人的头像
        test.clickAndWaitForNewWindow();
        sleep(1000*2);

    }
    public void uninstallApp(String packageName)
    {
        //卸载应用
        exec("pm uninstall "+packageName);

    }
    /*@Steps("清扫功能，如果已安装应用："+
            "1.进入系统设置"+
            "2.清楚目标应用的数据"+
            "3.设置中卸载应用")
    @Expectation("1.应用数据被清除"+
            "2.应用被卸载")
    public void UninstallApp(String PackageName) throws Exception
    {
        String appName=null;
        STARTAPP(AppInfo.PACKAGE_SETTING, AppInfo.ACTIVITY_SETTING);
        sleep(1500);


        int x=0;
        //滑动列表寻找应用管理
        while (!isExistByTextContains("应用管理"))
        {
            if(x>20){
                break;
            }else {
                swipeUp(30);
            }
        }

        //进入应用管理页面
        UiObject appManageBotton=new UiObject(new UiSelector().text("应用管理"));
        appManageBotton.click();
        sleep(1000);

        //点击已安装Tab
        clickByTextContains("已安装");

        //根据包名确定应用的名称
        if(PackageName=="com.tencent.mobileqq")
        {
            appName=AppInfo.NAME_QQ;
        }
        if(PackageName=="com.tencent.mm")
        {
            appName=AppInfo.NAME_WECHAT;
        }
        if(PackageName=="com.sina.weibo")
        {
            appName=AppInfo.NAME_WEIBO;
        }
        if(PackageName=="com.taobao.taobao")
        {
            appName=AppInfo.NAME_TAOBAO;
        }
        if(PackageName=="com.eg.android.AlipayGphone")
        {
            appName=AppInfo.NAME_ALIPAY;
        }


//滑动列表寻找应用
        for(int i=0;i<=20;i++) {
            if (!isExistByText(appName)) {
                swipeUp(23);
            }
            else break;
        }
        //点击应用
        if (isExistByText(appName)) {
            UiObject findApp = new UiObject(new UiSelector().text(appName));
            findApp.click();
            sleep(1500);

            //清楚应用数据
            try{
                clickByTextContains("清除数据");
                sleep(1000);
                clickByTextContains("确定");
            }catch (Exception e){}


            //卸载应用
            clickByTextContains("卸载");
            sleep(1000);
            clickByTextContains("确定");
            sleep(2000);

        }
        exitApp(AppInfo.PACKAGE_SETTING);

    }
*/
    /**
     * 检查是否熄屏，若熄屏，则打开
     */
    public void isLock() throws RemoteException {
        if(!UiDevice.getInstance().isScreenOn()){
            UiDevice.getInstance().wakeUp();
            sleep(1000);
        }

        int x=UiDevice.getInstance().getDisplayHeight();
        int y=UiDevice.getInstance().getDisplayWidth();
        swipe(x/2,y-100,x/2,y/4,50);
        sleep(1000);
        pressHome();
        sleep(1000);
    }

    public void closeSecurityLimits(){
        Object hipsManager =
                context.getSystemService("hips_service");
        if (null == hipsManager) {
            return;
        }
        try {
            Method usbInstallMethod =
                    hipsManager.getClass().getMethod("setUsbInstallSwitchState", boolean.class);
            Method securityMarginMethod =
                    hipsManager.getClass().getMethod("setSecurityMarginSwitchState", boolean.class);

            if (null != usbInstallMethod) {//关闭usb管理接口
                usbInstallMethod.setAccessible(true);
                usbInstallMethod.invoke(hipsManager, false);//选择USB打开或者关闭，true为打开
            }
            if (null != securityMarginMethod){//关闭支付保护接口
                securityMarginMethod.setAccessible(true);
                securityMarginMethod.invoke(hipsManager, false);//选择支付安全保护打开或者关闭，true为打开
            }
        } catch (Exception e) {
//            Log.w("closeSecurityLimits", "Exception : " + e.toString() + " - Cause: " + e.getCause());
        }
    }
}
