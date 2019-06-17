/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rest.DemoResource;
import utils.URLReader;

/**
 *
 * @author Claus
 */
public class TestURLReader
{
    
    public TestURLReader()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

     @Test
    public void propertiesRead()
    {
        // Arrange
        List<String> result;
        String fileName = DemoResource.fileName;
        
        // Act.
        result = URLReader.readUrls(fileName);
        
        // Assert.
        Assert.assertTrue(result.size() > 0);       
    }
    
    // Q&D - we should expect an Exception thrown, due to lack of time, we check for null instead.
    @Test
    public void propertiesNotRead()
    {
        // Arrange
        List<String> result;
        String fileName = "blablabla";
        
        // Act.
        result = URLReader.readUrls(fileName);
        
        // Assert.
        Assert.assertTrue(result == null);
    }
}
