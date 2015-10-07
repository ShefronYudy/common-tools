package com.shefron.module.rmi;

import java.io.IOException;

/**
 * Created by Administrator on 2014/11/30.
 */
public interface INewsService {

    /**
     * 发布新闻
     * @param msg
     * @throws IOException
     */
    public void publish(String msg) throws IOException;

    /**
     * 订阅新闻
     * @param id
     * @throws Exception
     */
    public void order(String id) throws Exception;
}
