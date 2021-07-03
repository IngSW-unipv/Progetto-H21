package it.skinjobs.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jessica Vecchia
 */
@ConfigurationProperties(prefix = "file")    //file.uploadDr 
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
    
}
