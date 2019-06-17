/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.concurrent.Callable;

/**
 * Wrapper class that wraps ApiData functionality to be able to hand the
 * functionality over to an ExecutorService, thus making ApiData functionality
 * asynchronous.
 *
 * @author Claus
 */
public class ApiDataCallable implements Callable<String>
{
    private String url;
    
    public ApiDataCallable(String url)
    {
        this.url = url;
    }
    
    @Override
    public String call() throws Exception
    {
        return ApiData.getData(url);
    }    
}
