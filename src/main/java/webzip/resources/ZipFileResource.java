/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webzip.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.json.JSONObject;
import webzip.Archive;

@Path("/zipfile")
public class ZipFileResource {
    
    @Context
    private ServletContext servletContext;
    @Context
    private HttpServletRequest httpServletRequest;
    
    private Archive archive;    
    
    @PostConstruct
    public void init() {
      // init instance
      archive = new Archive(servletContext, httpServletRequest);
    }
    

}
