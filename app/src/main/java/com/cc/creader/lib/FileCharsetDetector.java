package com.cc.creader.lib;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Created by CC on 2019/9/1.
 */


public class FileCharsetDetector
{
    private boolean found = false;
    private String encoding = null;

    public static String getCharset(File file)
    {
        String charset = "UTF-8";
        byte[] first3Bytes = new byte[3];
        try
        {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE)
            {
                charset = "UTF-16LE";
                checked = true;
            }
            else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF)
            {
                charset = "UTF-16BE";
                checked = true;
            }
            else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF)
            {
                charset = "UTF-8";
                checked = true;
            }
            if (!checked)
            {
                if (!TextUtils.isEmpty(charset))
                {
                    if (charset.equals("Big5"))
                    {
                        charset = "GBK";
                    }
                }
            }
            bis.reset();
            bis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return charset;
    }
}

