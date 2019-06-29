package com.meizu.common;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ͼƬʶ����ط���
 * Created by wuchaolin
 */
public class BitMap{
    public static final int IWIDTH = UiDevice.getInstance().getDisplayWidth();//��ȡ��Ļ���
    public static final int IHEIGHT = UiDevice.getInstance().getDisplayHeight();//��ȡ��Ļ�߶�
    //mkdir���������˳���·������ָ����Ŀ¼����ֻ�ܴ���һ����Ŀ¼������Ҫ���ڸ�Ŀ¼��
    //mkdirs���������˳���·��ָ����Ŀ¼���������б��뵫�����ڵĸ�Ŀ¼���������Դ����༶Ŀ¼�������Ƿ���ڸ�Ŀ¼��

    /**
     * ��ȡͼƬ��������ָ��·�����磺String path = "/sdcard/BitMapTest/";
     * @param IdTargetRegion   ��ȡ�����ѡ��
     * @param args 		ImageName��path��ͼƬ���ơ�ͼƬ����·��
     * @throws IOException
     * Created by zengliang
     */
    public String newImageFile(String IdTargetRegion,String ...args) throws IOException, UiObjectNotFoundException {//String ImageName, String path
        int argsLength = args.length;
        String path = null;
        //ѡȡ�洢·��
        if(argsLength != 2){
            path = "/sdcard/BitMap/";
        }
        else{
            path = args[1];
        }
        newFolder(path);
        String deletePath = path + "delete.png";
        File file = new File(deletePath);
        UiDevice.getInstance().takeScreenshot(file);//ͼƬ��ʽӦΪPNG
        String savePath = null;
        //ѡȡ��ͼ������·��
        if(argsLength == 0){
            savePath = path + "cutImage.png";
        }
        else{
            savePath = path + args[0] + ".png";
        }
        //ѡȡ��ȡ����
        Rect rect = null;
        if(!IdTargetRegion.equals("null")){
            UiObject object = new UiObject(new UiSelector().resourceId(IdTargetRegion));

            rect = object.getBounds();
        }
        else{
            rect = new Rect(Math.round(IWIDTH / 4), Math.round(IHEIGHT / 4),
                    Math.round(IWIDTH * 3 / 4), Math.round(IHEIGHT * 3 / 4));
        }
        Bitmap saveBitMap = this.cutTargetRegionById(rect, deletePath);

        this.saveImage(saveBitMap, savePath);
        deleteFile(file);
        return savePath;
    }


    /**
     * ����ͼƬ
     * @param bitmap    ��ȡԭͼ��BitMap��ʽ
     * @param savePath  ��ͼ����·��
     * @throws IOException
	 * Created by zengliang
     */
    public void saveImage(Bitmap bitmap, String savePath) throws IOException {
        FileOutputStream out = null;
        out = new FileOutputStream(savePath);
        if (out != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        }
    }

    /**
     * ͨ��rect��ȡָ�������ͼƬ
     * @param rect            ��ȡ����
     * @param sourcePath      ��ȡ����ԭͼ
     * @return              ��ȡ������BitMap��ʽ
     * @throws UiObjectNotFoundException
     * @throws IOException
	 * Created by zengliang
     */
    public Bitmap cutTargetRegionById(Rect rect, String sourcePath) throws UiObjectNotFoundException, IOException {
        Bitmap m = BitmapFactory.decodeFile(sourcePath);
        m = m.createBitmap(m, rect.left, rect.top, rect.width(), rect.height());
        return m;
    }

    /**
     * �����ļ���
     * @param path  �����ļ��е�·��
	 * Created by zengliang
     */
    public void newFolder(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * ɾ��ָ��·�����ļ�
     * @param file 		Ŀ��ɾ���ļ�
     * Created by zengliang
     */
    public void deleteFile(File file) {
        if (file.exists()) { // �ж��ļ��Ƿ����
            if (file.isFile()) { // �ж��Ƿ����ļ�
                file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
            }
            else if (file.isDirectory()) { // �����������һ��Ŀ¼
                File[] files = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
                for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
                    this.deleteFile(files[i]); // ��ÿ���ļ� ������������е���
                }
            }
            file.delete();
        }
        else {
            System.out.println("File isn't exists");
        }
    }

    /**
     * ͼƬ�Ա�,���ƶȴﵽҪ��true
     * @param targetImagePath 	Ŀ��ͼƬ����׼ͼ��
     * @param compareImagePath	�Ա�ͼƬ
     * @param SimilarPercent	ͼƬ���Ƴ̶�
     * Created by zengliang
     */
    public boolean ImageCompare(String targetImagePath, String compareImagePath, double SimilarPercent){
        Bitmap target = BitmapFactory.decodeFile(targetImagePath);
        Bitmap compare = BitmapFactory.decodeFile(compareImagePath);

        int width = target.getWidth();
        int height = target.getHeight();
        int differentPixels = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (compare.getPixel(x, y) != target.getPixel(x, y)) {
                    differentPixels++;
                }
            }
        }
        double totalPicels =  width * height ;
        double differentPercent = differentPixels / totalPicels;

        return SimilarPercent <= 1.0 - differentPercent;
    }
}
