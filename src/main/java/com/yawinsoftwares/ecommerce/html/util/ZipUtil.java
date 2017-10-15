package com.yawinsoftwares.ecommerce.html.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    /**
     * This method zips the directory
     * @param dir
     * @param zipDirName
     */
    public static void zipDirectory(String dirName, String zipDirName) {
    	FileOutputStream fos = null;
    	ZipOutputStream zos = null;
        try {
        	File dir = new File(dirName);
        	List<String> filesListInDir = populateFiles(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            fos = new FileOutputStream(zipDirName);
            zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                //System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                //read the file and write to ZipOutputStream
                FileInputStream fis = null;
                try {
                    zos.putNextEntry(ze);
	                fis = new FileInputStream(filePath);
	                byte[] buffer = new byte[1024];
	                int len;
	                while ((len = fis.read(buffer)) > 0) {
	                    zos.write(buffer, 0, len);
	                }
                }catch(Exception e) {
                	 e.printStackTrace();
                } finally {
                	try {fis.close();}catch(Exception e) {}
                	try {zos.closeEntry();}catch(Exception e) {}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {zos.close();}catch(Exception e) {}
        	try {fos.close();}catch(Exception e) {}
        }
    }
    
    
    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    public static List<String> populateFiles(File dir) throws IOException {
    	List<String> filesListInDir = new ArrayList<String>();
    	populateFilesList(filesListInDir,dir);
    	return filesListInDir;
    }
    
    /**
     * This method populates all the files in a directory to a List
     * @param filesListInDir
     * @param dir
     * @throws IOException
     */
    private static void populateFilesList(List<String> filesListInDir,File dir) throws IOException {
    	
        File[] files = dir.listFiles();
        if(files==null) return;
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(filesListInDir,file);
        }
    }

    /**
     * This method compresses the single file to zip format
     * @param file
     * @param zipFileName
     */
    public static void zipSingleFile(File file, String zipFileName) {
        try {
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            
            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
