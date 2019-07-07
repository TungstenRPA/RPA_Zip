/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webzip;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Robert.Birkenheuer
 */
public class CustomApplication extends ResourceConfig
{
    public CustomApplication()
    {
        packages("webzip.resources");
 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
        //register(WebzipResponseFilter.class);
    }
}
