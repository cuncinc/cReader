package com.cc.creader.source;

/**
 * Created by CC on 2019/8/29.
 */

public class BookData
{
    String title;
    String route;
    double size;

    public BookData(){}

    public BookData(String route, String title, double size)
    {
        this.title = title;
        this.route = route;
        this.size = size;
    }

    public double getSize()
    {
        return size;
    }

    public void setSize(double s)
    {
        size = s;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getRoute()
    {
        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }
}
