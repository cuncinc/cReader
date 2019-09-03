package com.cc.creader.lib;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by CC on 2019/8/31.
 */

public class TxtFileUtil
{
    private String path;
    private File f;
    private int buff_length;    //buff_length必须是3的倍数
    private FileInputStream fin;

    public TxtFileUtil(String path, int buff_length, long skip_length)
    {
        this.path = path;
        this.buff_length = buff_length;
        f = new File(path);
        try
        {
            fin = new FileInputStream(f);
            fin.skip(skip_length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public long size()
    {
        return f.length();
    }

    public void setBuffLength(int length)
    {
        this.buff_length = length;
    }

    public String getPath()
    {
        return path;
    }

    public String loadNextTxt()
    {
        byte[] buff = new byte[buff_length];
        try
        {
            fin.read(buff);
            String encodeing = FileCharsetDetector.getCharset(path);
            String t = new String(buff, encodeing);
            t = t.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");   //删除空行
            return (t);
        }

         catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
