/*
 * https://www.javacodegeeks.com/2018/04/jax-rs-tutorial-with-jersey-for-restful-web-services.html
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/servlet-resources.html
 * http://tobias.blickle.online/using-tomcat-java-rest-server
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/using-rest-services-to-manage-download-and-upload-of-files
 */
package webzip.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import webzip.Archive;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.json.JSONObject;
import webzip.HTTPHeaderNames;
import webzip.Secured;

@Secured
@Path("/archive")
public class ArchiveResource {
    
    @Context
    private ServletContext servletContext;
    @Context
    private HttpServletRequest httpServletRequest;
    
    private Archive archive;    
    
    @PostConstruct
    public void init() {
      // init instance
      //todo: convert Archive class to singleton?
      archive = new Archive(servletContext, httpServletRequest);
    }
    
    /**
     * List ZIP archives
     * @return JSON
     */
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_JSON})
    public String getArchives() {
        return archive.listArchives();
    }
    
    /**
     * Download ZIP archive
     * @param name filename e.g.: backup.zip
     * @return bytes
     */
    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getArchive(@PathParam("name") String archivename) {
        
        File fileDownload = null;
        Response.ResponseBuilder response = null;
        try {
            fileDownload = archive.getArchive(archivename);
            response = Response.ok((Object) fileDownload);
            response.header("Content-Disposition", "attachment;filename=" + archivename);
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            response = Response.status(404); // file not found
            response.header(HTTPHeaderNames.WEBZIP_ERROR, ex.getMessage());
            return response.build();
        } finally {
            return response.build();
        } 
    }
    
    /**
     * Upload ZIP archive
     * @param name filename e.g.: backup.zip
     * @param fileInputStream
     * @return JSON
     */
    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public String uploadArchive(@PathParam("name") String archivename, InputStream fileInputStream) {
        JSONObject jsonObject = new JSONObject();
        try {
            archive.writeArchive(fileInputStream, archivename);
            archive.extractArchive(archivename);
            jsonObject.put("status", "ok");
            jsonObject.put("filename", archivename);
            return jsonObject.toString();            
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }
    
    /**
     * Create new archive
     * @param name filename e.g.: new.zip
     * @return 
     */
    @PUT
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String createArchive(@PathParam("name") String archivename) {
        JSONObject jsonObject = new JSONObject();
        try {
            archive.createArchive(archivename);
            jsonObject.put("status", "ok");
            jsonObject.put("filename", archivename);
            return jsonObject.toString(); 
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }
    
    /**
     * Delete archive
     * @param name filename e.g.: backup.zip
     * @return 
     */
    @DELETE
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteArchive(@PathParam("name") String archivename) {
        JSONObject jsonObject = new JSONObject();
        try {
            archive.deleteArchive(archivename);
            jsonObject.put("status", "ok");
            jsonObject.put("filename", archivename);
            return jsonObject.toString(); 
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }

    /**
     * List all folder/files of an archive
     * @param archivename
     * @return 
     */
    @GET
    @Path("/{name}/content")
    @Produces({MediaType.APPLICATION_JSON})
    public String getFiles(@PathParam("name") String archivename) {
        try {
            return archive.listFiles(archivename);
            //return archive.listZipEntries(archivename);
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }
    
    /**
     * Download file from an archive
     * @param archivename
     * @param filename full path
     * @return 
     */
    @GET
    @Path("/{name}/content")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getFile(@PathParam("name") String archivename, @QueryParam("file") String filename) {
        
        File fileDownload = null;
        Response.ResponseBuilder response = null;
        if (filename.isEmpty()) {
            response = Response.status(404); // file not found
            response.header(HTTPHeaderNames.WEBZIP_ERROR, "Parameter file can not be empty!");
            return response.build();
        }
        
        try {
            fileDownload = archive.getFile(archivename, filename);
            response = Response.ok((Object) fileDownload);
            response.header("Content-Disposition", "attachment;filename=" + filename);
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            response = Response.status(404); // file not found
            response.header(HTTPHeaderNames.WEBZIP_ERROR, ex.getMessage());
            return response.build();
        } finally {
            return response.build();
        }     
    }
    
    /**
     * Upload file into archive
     * @param archivename
     * @param filename full path
     * @param fileInputStream
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}/content")
    public String uploadFile(@PathParam("name") String archivename, @QueryParam("file") String filename, InputStream fileInputStream) {

        JSONObject jsonObject = new JSONObject();
        if (filename.isEmpty()) {
            jsonObject.put("status", "error");
            jsonObject.put("message", "Parameter file can not be empty!");
            return jsonObject.toString();
        } 
        
        try {
            archive.writeFile(fileInputStream, archivename, filename);
            jsonObject.put("status", "ok");
            jsonObject.put("filename", filename);
            return jsonObject.toString();            
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }
    
    /**
     * Create folder in archive
     * @param archivename
     * @param filename full path
     * @param fileInputStream
     * @return 
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}/content")
    public String makeDirectory(@PathParam("name") String archivename, @QueryParam("path") String directory) {
        JSONObject jsonObject = new JSONObject();
        if (directory.isEmpty()) {
            jsonObject.put("status", "error");
            jsonObject.put("message", "Parameter path can not be empty!");
            return jsonObject.toString();
        } 
        try {
            archive.createDirectory(archivename, directory);
            jsonObject.put("status", "ok");
            jsonObject.put("directory", directory);
            return jsonObject.toString();            
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }
    }
    
    /**
     * Delete file or folder from archive.
     * @param archivename
     * @param filename
     * @return 
     */
    @DELETE
    @Path("/{name}/content")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteFile(@PathParam("name") String archivename, @QueryParam("file") String filename) {
        JSONObject jsonObject = new JSONObject();
        if (filename.isEmpty()) {
            jsonObject.put("status", "error");
            jsonObject.put("message", "Parameter file can not be empty!");
            return jsonObject.toString();
        } 
        
        try {
            archive.deleteFile(archivename, filename);
            jsonObject.put("status", "ok");
            jsonObject.put("filename", filename);
            return jsonObject.toString();            
        } catch (IOException ex) {
            Logger.getLogger(ArchiveResource.class.getName()).log(Level.SEVERE, null, ex);
            jsonObject.put("status", "error");
            jsonObject.put("message", ex);
            return jsonObject.toString();
        }   
    }
    
}
