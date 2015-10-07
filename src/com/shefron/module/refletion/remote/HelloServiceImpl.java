package com.shefron.module.refletion.remote;

import java.util.Date;

/**
 * Created by Administrator on 2014/11/29.
 */
public class HelloServiceImpl implements HelloService {

    private Date time = new Date();

    @Override
    public String echo(String msg) {
        return "echo:"+msg;
    }

    @Override
    public Date getTime() {
        return time;
    }
}
