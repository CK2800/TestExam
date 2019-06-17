package rest;

import com.google.gson.Gson;
import entity.History;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.ApiData;
import utils.ApiDataCallable;
import utils.PuSelector;
import utils.URLReader;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource
{
    /**
     * file name of file containing api urls.
     */
    public static String fileName = "/META-INF/externalApis.properties";
//    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;
    
    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll()
    {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers()
    {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        try
        {
            List<User> users = em.createQuery("select user from User user").getResultList();

            return "[" + users.size() + "]";

        } finally
        {
            em.close();
        }

    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser()
    {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin()
    {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    
    /**
     * Anonymous users can only fetch data synchronously.
     * 
     * @return 
     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("fetch")
    public String fetchFromApis()
    {
        // Create collection to hold result.
        List<String> result = new ArrayList();
        // Read in urls from external resource.
        ArrayList<String> urls = URLReader.readUrls(DemoResource.fileName);
        long end, total = 0, start = System.currentTimeMillis();
        // Iterate through urls, and call each url in sequence.
        for(String url : urls)
        {           
            try
            {
                result.add(ApiData.getData(url));
            }
            catch(Exception e)
            {
                // Q&D each exception must be properly handled,
                // but due to lack of time, we push the message into the result.
                result.add(e.getMessage());
            }            
            finally
            {
                end = System.currentTimeMillis();
                total += (end - start);
            }            
        }
//        System.out.println("Execution time sequence: " + total);
        result.add(0, "{\"msg\": \"Fetch time sequential: " + total + " ms.\"}");
        if (result.size() > 0)
            return result.toString(); //return gson.toJson(result);
        else
            return "";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("history")
    //@RolesAllowed({"user", "admin"})
    public String history()
    {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        List<History> histories = new ArrayList();
        try
        {
            // Q&D should be returned as list of HistoryDTOs.
            histories = em.createQuery("SELECT h FROM History h").getResultList();
            
        }
        finally
        {
            em.close();
        }
        
        
        return new Gson().toJson(histories);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("availablecars/{week}/{address}")
    public String availablecars(@PathParam("week") int week, @PathParam("address") String address)
    {
        SaveArguments(week, address);
        // Create executor service.
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // Create collection to hold futures.
        List<Future<String>> futures = new ArrayList();
        // Create collection to hold results.
        List<String> results = new ArrayList();
        // Q&D create collection of urls here.
        List<String> urls = new ArrayList();
        urls.add("http://localhost:3333/availableCars?week=$1&comp=avis&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=hertz&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=europcar&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=budget&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=alamo&addr=$2");
        // loop through urls to replace in week.
        for(String url:urls)
        {
            url = url.replace("$1", Integer.toString(week)).replace("$2", address);
           
            // Add callables to executorservice.
            // Since executorService.submit returns a future, adding the future
            // to the collection is trivial.
            futures.add(executorService.submit(new ApiDataCallable(url)));
        }
        
        try
        {
            // futures.get is blocking, this is ok, since we are awaiting completion.
            for(Future<String> future: futures)
            {
                // Add result of future to collection of results.                
                results.add(future.get());
            }
        }
        catch(Exception e)
        {
            // Q&D Add error to the results collection. 
            results.add(e.getMessage());
        }
        finally
        {
            // close down executorservice.
            executorService.shutdown();
            
            
        }
        return results.toString();
        
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("fetch")
    @RolesAllowed({"user", "admin"})
    public String fetch()
    {
        // admins fetch asynchronously, users sequentially.
        return securityContext.isUserInRole("admin") ? fetchFromApisAsync() : fetchFromApis(); 
    }
    /**
     * Fetches data asynchronously from the urls specified.
     *      
     * @return JSON encoded string.
     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("fetch2") 
    
    @RolesAllowed({"user", "admin"})
    public String fetchFromApisAsync()
    {
        // Create executor service
        ExecutorService executorService = Executors.newCachedThreadPool();
        // Create collection to hold futures.
        List<Future<String>> futures = new ArrayList();
        // Create collection to hold result.
        List<String> result = new ArrayList();
        
        long start, end;
        
        ArrayList<String> urls = URLReader.readUrls(DemoResource.fileName);
        
        // Time the parallel loading of urls.
        start = System.currentTimeMillis();
        
        // Iterate through urls, create anonymous callable, add it to executorservice and futures.
        for(String url : urls)
        {           
            futures.add((Future<String>) executorService.submit(new ApiDataCallable(url)));            
        }

        try
        {
            // Iterate through futures to assemble results.
            // NOTE: future.get() is blocking, since it waits for the future to resolve. 
            // It is ok to wait here, since every callable is running in parallel at this point.            
            for(Future<String> future : futures)            
            {
                result.add(future.get());
            }
        }
        catch(Exception e)
        {
            // Q&D Store exception in result.             
            result.add(e.getMessage());
            e.printStackTrace();
        }
        finally
        {        
            end = System.currentTimeMillis();
//            System.out.println("Execution time parallel: " + (end - start));
            result.add(0, "{\"msg\": \"Fetch time async: " + (end-start) + " ms.\"}");            
        }
        
        
//        return new Gson().toJson(result);
            if (result.size() > 0)
                return result.toString(); //return gson.toJson(result);
            else
                return ""; // or null

    }

    private void SaveArguments(int week, String address)
    {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        History history = new History(week, address);
        try
        {
            em.getTransaction().begin();
            em.persist(history);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }        
    }
}
