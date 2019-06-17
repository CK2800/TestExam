/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Small helper class to read API URLS from properties file.
 * @author Claus
 */
public class URLReader
{   
    /**
     * Returns the API URLs from the specified filename.
     * @return ArrayList object that contains the urls or is null if no urls was read.
     */
    public static ArrayList<String> readUrls(String fileName)            
    {        
        Properties properties = new Properties();
        ArrayList result = null;
        try
        {
            properties.load(URLReader.class.getResourceAsStream(fileName));            
            // Get values for each key in the loaded properties, add value to collection.
            for(Object key : properties.keySet())
            {
                if (result == null)
                    result = new ArrayList();
                result.add( (String)properties.getProperty( (String) key) );
            }
        }
        catch(Exception e)
        {
            // Q&D
            System.out.println("No API URLS was read from " + fileName);
        }
        return result;
        
    }
    
}
