package com.shefron.module.refletion.remote;

import java.util.Date;

/**
 * Created by Administrator on 2014/11/29.
 */
public interface HelloService {
    public String echo(String msg) throws Exception;
    public Date getTime() throws Exception;

}
