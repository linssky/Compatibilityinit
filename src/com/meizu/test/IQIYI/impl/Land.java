package com.meizu.test.IQIYI.impl;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.service.Element;
import com.meizu.test.IQIYI.imple.ILand;
import com.meizu.test.IQIYI.testcase.PubScipt;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016-3-25.
 */
public class Land extends PubScipt implements ILand{
//    String TEXT_NOTLOGIN ="未登录";
    String ID_NOTLOGIN="com.qiyi.video:id/ugc_login_for_service";
    Element notLogin=findElement(ID_NOTLOGIN);

    @Override
    public void registerAccount() throws Exception {
        Element edtUserName = findElement("com.qiyi.video:id/phoneMyAccountEmail");//手机号或邮箱
        Element edtPassword = findElement("com.qiyi.video:id/phoneMyAccountPwd");//密码
        Element clearPhone = findElement("com.qiyi.video:id/iv_clear_phone");//删除记录的账号或密码

        init();
        sleep(1000);
        UiSelector MI=new UiSelector().resourceId(ID_MY).text("我的");
        UiObject my=new UiObject(MI);
        my.waitForExists(3000);
        my.clickAndWaitForNewWindow(2000);

        if (isExistById(ID_NOTLOGIN)) {//“登陆后可享用更多云服务”控件存在，则执行登陆操作
            notLogin.clickAndWaitForNewWindow(2000);
            if (isExistById("com.qiyi.video:id/iv_clear_phone")) {//如果有记录账号显示，清空记录账号
                clearPhone.click();
            }

            if (edtUserName.exists() && edtPassword.exists()) {
                ShellUtil.setUtf7Input();
                edtUserName.click();
                edtUserName.setText(Utf7ImeHelper.e(USER_NAME));
                sleep(1000);
                edtPassword.click();
                edtPassword.setText(Utf7ImeHelper.e(URER_PSD));
            }
            click("com.qiyi.video:id/phoneMyAccountLogin");

            assertTrue(findElementByText("186****7067_m1046").waitForExists(TIMEOUT));
        }
    }







}
