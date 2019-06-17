/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittest;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.DemoResource;
import utils.ApiData;

/**
 *
 * @author Claus
 */
public class TestApiData
{
    public TestApiData()
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
    public void apiDataReceived() throws Exception
    {
        // Arrange
        String url = "https://swapi.co/api/people/4";
        String response;
        // Act
        response = ApiData.getData(url);
//        System.out.println("Rsponse from " + url + ": " + response);

        // Assert
        Assert.assertTrue(response != null);
    }    
    
    @Test
    public void testFetchFromApisAsync()
    {
        // Arrange
        DemoResource dr = new DemoResource();

        // Act.
        String result = dr.fetchFromApisAsync();

        // Assert
//        System.out.println("result: " + result);
        Assert.assertTrue(result != "");
    }
    
    @Test
    public void testFetchFromApis()
    {
        // Arrange
        DemoResource dr = new DemoResource();

        // Act.
        String result = dr.fetchFromApis();

        // Assert
//        System.out.println("result: " + result);
        Assert.assertTrue(result != "");
    }
    
    @Test
    public void availablecars()
    {
        // Arrange
        DemoResource dr = new DemoResource();
        
        // Act
        String actual = dr.availablecars(1, "cph-airport");
        
        // Assert
        Assert.assertTrue(actual.length() > 0);
    }
    
    
}
