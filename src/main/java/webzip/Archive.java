/*
 * https://www.baeldung.com/java-compress-and-uncompress
 */
package webzip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;

/**
 *
 * @author Robert.Birkenheuer
 */
public class Archive {
    
    private static String temp_directory;
    private static boolean overwrite_files = false;
    private final ServletContext servletContext;
    private final HttpServletRequest request;
    private static final int BUFFER_SIZE = 4096;

    public Archive(ServletContext servletContext, HttpServletRequest request ) {
        this.servletContext = servletContext;
        this.request = request;
        setTempDirectory();
    }
    
    /**
     * Read temp directory path from webzip.properties file
     */
    private void setTempDirectory() {
        if (temp_directory == null) {
            try {
                String appConfigPath = servletContext.getRealPath("/META-INF/webzip.properties");
                Properties appProps = new Properties();
                appProps.load(new FileInputStream(appConfigPath));
                temp_directory = appProps.getProperty("tempfolder") + File.separator ;
                String overwrite = appProps.getProperty("overwritefiles");
                overwrite_files = overwrite.equalsIgnoreCase("true");

                File uploadDir = new File(temp_directory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    /**
     * Format path to temp directory containing ZIP archives and folders
     * @param location
     * @return 
     */
    private String getTempDirectory(String location) {

        String token = request.getHeader( HTTPHeaderNames.SERVICE_KEY );
        String username = Authenticator.getInstance().getServiceUser(token);
        return temp_directory + username + File.separator + location;

    }
    
    private boolean doOverwrite() {
        return overwrite_files;
    }
    
    /**
     * Write ZIP file (big chunk of bytes)
     * @param content
     * @param archivename
     * @throws IOException 
     */
    public void writeArchive(byte[] content, String archivename) throws IOException {
        
        File file = new File(getTempDirectory(archivename));

        if (!doOverwrite()) {
            if (file.exists()) {
                throw new IOException("Archive already exists: " + archivename);
            }
        }
        
        file.createNewFile();

        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
        Logger.getLogger(Archive.class.getName()).log(Level.INFO, "Written: " + archivename);
    }
    
    /**
     * Write ZIP file (buffered streaming)
     * @param fileInputStream
     * @param archivename
     * @throws IOException 
     */
    public void writeArchive(InputStream fileInputStream, String archivename) throws IOException {
        OutputStream out = null;
        File file = new File(getTempDirectory(archivename));
        
        if (!doOverwrite()) {
            if (file.exists()) {
                throw new IOException("Archive already exists: " + archivename);
            }
        }

        file.createNewFile();
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                out.close();
                fileInputStream.close();
            } catch (IOException e) {
                Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        Logger.getLogger(Archive.class.getName()).log(Level.INFO, "Written: " + archivename);
    }
    
    /**
     * Create new empty archive
     * @param archivename
     * @throws IOException 
     */
    public void createArchive(String archivename) throws IOException {
        if (doOverwrite()) {
            deleteZipDirectory(archivename);
        } else {
            File file = new File(getTempDirectory(archivename));
            if (file.exists()) {
                throw new IOException("Archive already exists: " + archivename);
            }
        }
        createZipDirectory(archivename);
        zipDirectoryToArchive(archivename);
    }
    
    /**
     * Delete archive
     * @param archivename
     * @throws IOException 
     */
    public void deleteArchive(String archivename) throws IOException {
        File file = new File(getTempDirectory(archivename));
        if (!file.exists()) {
            throw new IOException("Archive does not exist: " + archivename);
        } else {
            deleteZipDirectory(archivename);
            file.delete();
        }
    }
    
    /**
     * Get ZIP file for download. If it doesn't exist, return empty file.
     * @param archivename
     * @return
     * @throws IOException 
     */
    public File getArchive(String archivename) throws IOException {
        
        zipDirectoryToArchive(archivename);
        File file = new File(getTempDirectory(archivename));
        if (!file.exists()) {
            throw new IOException("Archive does not exist: " + archivename);
        }
        return file;
    }
    
    /**
     * Get content file for download
     * @param archivename
     * @param filename
     * @return 
     */
    public File getFile(String archivename, String filename) throws IOException {

        String destination = archivename.substring(0, archivename.length()-4);
        File file = new File(getTempDirectory(destination) + File.separator + filename);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + filename);
        }
        return file; 
    }
    
    /**
     * List archive files
     * @return 
     */
    public String listArchives() {
        JSONArray arr = new JSONArray();
        File folder = new File(getTempDirectory(""));
        for (File f : folder.listFiles()) {
            if (f.getName().toLowerCase().endsWith(".zip")) {
                JSONObject jsonfile = new JSONObject();
                jsonfile.put("name", f.getName());
                jsonfile.put("length", f.length());
                jsonfile.put("lastModified", new Date(f.lastModified())).toString();
                arr.put(jsonfile);                
            }
        }
        JSONObject jsonarchives = new JSONObject();
        jsonarchives.put("archives", arr);
        //System.out.println(jsonarchives.toString());
        return jsonarchives.toString();
    }
    
    /**
     * Extract ZIP Archive
     * @param archivename
     * @throws IOException 
     */
    public void extractArchive(String archivename) throws IOException {
        
        deleteZipDirectory(archivename);
        File destDir = getZipDirectory(archivename);

        ZipInputStream zis = new ZipInputStream(new FileInputStream(getTempDirectory(archivename)));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            
            File newFile = newFile(destDir, zipEntry);
            File destinationParent = newFile.getParentFile();
            // create the parent directory structure if needed
            destinationParent.mkdirs();
            
            if (!zipEntry.isDirectory()) {
                extractFile(zis, newFile); 
                // preserve lastModified
                newFile.setLastModified(zipEntry.getTime());
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }
    
    /**
     * Create file within archive
     * @param fileInputStream
     * @param archivename
     * @param filename
     * @throws IOException 
     */
    public void writeFile(InputStream fileInputStream, String archivename, String filename) throws IOException {
        File zipfile = new File(getTempDirectory(archivename));
        if (zipfile.exists()) {
            OutputStream out = null;
            File archivefolder = getZipDirectory(archivename);
            File file = new File(archivefolder.getPath() + File.separator + filename);
            
            if (!doOverwrite()) {
                if (file.exists()) {
                    throw new IOException("File already exists: " + file.getPath());
                }
            }
        
            //overwrites existing files
            File destinationParent = file.getParentFile();
            // create the parent directory structure if needed
            destinationParent.mkdirs();
            file.createNewFile();
            try {
                out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fileInputStream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    out.close();
                    fileInputStream.close();
                } catch (IOException e) {
                    Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            Logger.getLogger(Archive.class.getName()).log(Level.INFO, "Written: " + archivefolder.getPath() + File.separator + filename);
        } else {
            throw new IOException("Archive does not exist: " + archivename);
        }
    }
    
    /**
     * Create directory within archive
     * @param archivename
     * @param directory
     * @throws IOException 
     */
    public void createDirectory(String archivename, String directory) throws IOException {

        File zipfile = new File(getTempDirectory(archivename));
        if (zipfile.exists()) {
            File archivefolder = getZipDirectory(archivename);
            File file = new File(archivefolder.getPath() + File.separator + directory);
            
            if (!doOverwrite()) {
                if (file.exists()) {
                    throw new IOException("Directory already exists: " + file.getPath());
                }
            }
            
            file.mkdirs();
            Logger.getLogger(Archive.class.getName()).log(Level.INFO, "Created: " + archivefolder.getPath() + File.separator + directory);
        } else {
            throw new IOException("Archive does not exist: " + archivename);
        }
    }
    
    /**
     * Delete file or folder (recursively)
     * @param archivename
     * @param filename
     * @throws IOException 
     */
    public void deleteFile(String archivename, String filename) throws IOException {

        File zipfile = new File(getTempDirectory(archivename));
        if (zipfile.exists()) {
            File archivefolder = getZipDirectory(archivename);
            File file = new File(archivefolder.getPath() + File.separator + filename);
            if (file.exists()) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            } else {
                throw new IOException("File does not exist: " + filename);
            }
        } else {
            throw new IOException("Archive does not exist: " + archivename);
        }
    }
    
    /**
     * Lists all entries of a ZIP file
     * @param archivename
     * @return
     * @throws IOException 
     */
    public String listZipEntries(String archivename) throws IOException {
        if (archivename.toLowerCase().endsWith(".zip")) {

            JSONArray arr = new JSONArray();
            ZipInputStream zis = new ZipInputStream(new FileInputStream(getTempDirectory(archivename)));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                JSONObject jsonfile = new JSONObject();
                jsonfile.put("name", zipEntry.getName());
                //jsonfile.put("size", zipEntry.getSize());
                //jsonfile.put("compressed", zipEntry.getCompressedSize())
                //jsonfile.put("created", zipEntry.getCreationTime());
                jsonfile.put("modified", zipEntry.getLastModifiedTime());
                arr.put(jsonfile);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            JSONObject jsonarchives = new JSONObject();
            jsonarchives.put("archive", archivename);
            jsonarchives.put("content", arr);
            return jsonarchives.toString();
        } else {
            throw new IOException("File is not a ZIP archive: " + archivename);
        }
    }
    
    /**
     * Lists extracted files from a target ZIP folder. If the target doesn't exist ZIP archive will be extracted.
     * @param archivename
     * @return
     * @throws IOException 
     */
    public String listFiles(String archivename) throws IOException {
        if (archivename.toLowerCase().endsWith(".zip")) {
            File file = new File(getTempDirectory(archivename));
            if (file.exists()) {
                String destination = archivename.substring(0, archivename.length()-4);
                File destDir = new File(getTempDirectory(destination));
                if (! destDir.exists()) {
                    extractArchive(archivename);
                }
                
                JSONArray arr = new JSONArray();
                File folder = getZipDirectory(archivename);
                for (File f : folder.listFiles()) {

                    if (f.isDirectory()) {
                        JSONObject jsonfile = new JSONObject();
                        jsonfile.put("name", f.getName());
                        jsonfile.put("path", "");
                        jsonfile.put("length", "0");
                        jsonfile.put("lastModified", new Date(f.lastModified())).toString();
                        //jsonfile.put("get", "");
                        jsonfile.put("type", "folder");
                        arr.put(jsonfile); 
                        listSubFolders(f.getName(), f, arr);
                    } else {
                        JSONObject jsonfile = new JSONObject();
                        jsonfile.put("name", f.getName());
                        jsonfile.put("path", "");
                        jsonfile.put("length", f.length());
                        jsonfile.put("lastModified", new Date(f.lastModified())).toString();
                        //jsonfile.put("get", request.getRequestURL() + "/content?file=" + URLEncoder.encode(f.getName(), StandardCharsets.UTF_8.toString()));
                        jsonfile.put("type", "file");
                        arr.put(jsonfile); 
                    }
                }
                JSONObject jsonarchives = new JSONObject();
                jsonarchives.put("archive", archivename);
                jsonarchives.put("content", arr);

                return jsonarchives.toString();
                
            } else {
                throw new IOException("File does not exist: " + archivename);
            }
        } else {
            throw new IOException("File is not a ZIP archive: " + archivename);
        } 
    }
    
    /**
     * helper function for listFiles
     * @param path
     * @param folder
     * @param arr
     * @throws UnsupportedEncodingException 
     */
    private void listSubFolders(String path, File folder, JSONArray arr) throws UnsupportedEncodingException {
        for (File f : folder.listFiles()) {
            
            if (f.isDirectory()) {
                JSONObject jsonfile = new JSONObject();
                jsonfile.put("name", f.getName());
                jsonfile.put("path", path + "/");
                jsonfile.put("length", "0");
                jsonfile.put("lastModified", new Date(f.lastModified())).toString();
                //jsonfile.put("get", "");
                jsonfile.put("type", "folder");
                arr.put(jsonfile); 
                listSubFolders(path + "/" + f.getName(), f, arr);
            } else {
                JSONObject jsonfile = new JSONObject();
                jsonfile.put("name", f.getName());
                jsonfile.put("path", path + "/");
                jsonfile.put("length", f.length()); 
                jsonfile.put("lastModified", new Date(f.lastModified())).toString();
                //jsonfile.put("get", request.getRequestURL() + "/content?file=" + URLEncoder.encode(path + "/" + f.getName(), StandardCharsets.UTF_8.toString()));
                jsonfile.put("type", "file");
                arr.put(jsonfile);
            }
        }
    }
    
    /**
     * Prevent ZIP slip volnurability
     * @param destinationDir
     * @param zipEntry
     * @return
     * @throws IOException 
     * Another key point can be seen in the newFile() method. These method guards against writing files to the file system outside of the target folder. This vulnerability is called Zip Slip
     */
    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
    
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, File newFile) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    /**
     * Returns or created target directory for ZIP extraction
     * @param archivename
     * @return
     * @throws IOException 
     */
    private File getZipDirectory(String archivename) throws IOException {
       if (archivename.toLowerCase().endsWith(".zip")) {
            String destination = archivename.substring(0, archivename.length()-4);
            File destDir = new File(getTempDirectory(destination));
            if (! destDir.exists()) {
                destDir.mkdir();
            }
            return destDir;
       } else {
            throw new IOException("File is not a ZIP archive: " + archivename);
       } 
    }
    
    /**
     * Delete target ZIP directory
     * @param archivename
     * @throws IOException 
     */
    private void deleteZipDirectory(String archivename) throws IOException {
        if (archivename.toLowerCase().endsWith(".zip")) {
            String destination = archivename.substring(0, archivename.length()-4);
            File destDir = new File(getTempDirectory(destination));
            if (destDir.exists()) {
                //destDir.delete();
                deleteDirectory(destDir);
            }
       } else {
            throw new IOException("File is not a ZIP archive: " + archivename);
       } 
    }
    
    /**
     * helper function for deleteZipDirectory. Recursively delete contents of a directory
     * @param directoryToBeDeleted
     * @return 
     */
    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    
    /**
     * Create target ZIP directory
     * @param archivename
     * @throws IOException 
     */
    private void createZipDirectory(String archivename) throws IOException {
        if (archivename.toLowerCase().endsWith(".zip")) {
            String destination = archivename.substring(0, archivename.length()-4);
            File destDir = new File(getTempDirectory(destination));
            destDir.mkdir();
       } else {
            throw new IOException("File is not a ZIP archive: " + archivename);
       } 
    }
    
    /**
     * Load contents of directory to ZIP archive
     * @param archivename
     * @throws IOException 
     */
    private void zipDirectoryToArchive(String archivename) throws IOException {

        FileOutputStream fos = new FileOutputStream(getTempDirectory(archivename));
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = getZipDirectory(archivename);
        File[] children = fileToZip.listFiles();
        for (File childFile : children) {
            zipFileToArchive(childFile, childFile.getName(), zipOut);
        }
        zipOut.close();
        fos.close();   
    }
    
    /**
     * helper function for zipDirectoryToArchive
     * @param fileToZip
     * @param fileName
     * @param zipOut
     * @throws IOException 
     */
    private void zipFileToArchive(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFileToArchive(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
