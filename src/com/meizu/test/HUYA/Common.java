package com.meizu.test.HUYA;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.Steps;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.ShellUtil;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Created by wuchaolin on 2016-4-21.
 */
public class Common extends AutoAllInOneTestCase {



    @Steps("点击键盘进行搜索或者发送")
    public void send() throws UiObjectNotFoundException {
        UiObject talk=new UiObject(new UiSelector().resourceId("com.duowan.kiwi:id/input_edit"));
        talk.setText(Utf7ImeHelper.e("666"));
        int x= UiDevice.getInstance().getDisplayWidth();
        int y=UiDevice.getInstance().getDisplayHeight();
        ShellUtil.setSystemInput();
        click(x-100,y-100);
    }





}
