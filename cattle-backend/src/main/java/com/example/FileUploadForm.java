package com.example.filestorage;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import jakarta.ws.rs.FormParam;

/**
 * POJO für Multipart-Upload
 */
public class FileUploadForm {

    // Teil: die eigentliche Datei (Byte-Array)
    @FormParam("file")
    @PartType("application/octet-stream")
    private byte[] file;

    // Teil: der Dateiname
    @FormParam("fileName")
    @PartType("text/plain")
    private String fileName;

    public FileUploadForm() {
    }

    public byte[] file() {
        return file;
    }

    public String fileName() {
        return fileName;
    }
}
