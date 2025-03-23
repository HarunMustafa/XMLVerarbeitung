package com.example.filestorage;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.io.File;
import java.nio.file.Files;

@Path("/files")
public class FileStorageResource {

    private static final Logger LOG = Logger.getLogger(FileStorageResource.class);

    @Inject
    FileStorageService fileStorageService;

    /**
     * Dateiupload via Multipart (POST /files/upload)
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(FileUploadForm form) {
        if (form == null || form.fileName() == null || form.file() == null) {
            LOG.warn("Fehlerhafter Upload: form, fileName oder file ist null");
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Upload-Formulardaten fehlerhaft").build();
        }
        try {
            String storedFilename = fileStorageService.storeFile(form.file(), form.fileName());
            return Response.ok("{\"filename\":\"" + storedFilename + "\"}").build();
        } catch (Exception e) {
            LOG.error("Fehler beim Speichern der Datei", e);
            return Response.serverError().entity("Fehler beim Speichern").build();
        }
    }

    /**
     * Download einer Datei (GET /files/{filename})
     */
    @GET
    @Path("/{filename}")
    public Response downloadFile(@PathParam("filename") String filename) {
        File file = fileStorageService.getFile(filename);
        if (file == null || !file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Datei nicht gefunden: " + filename).build();
        }
        try {
            return Response.ok(Files.readAllBytes(file.toPath()))
                           .type(MediaType.APPLICATION_OCTET_STREAM)
                           // Optional: als Attachment servieren
                           .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                           .build();
        } catch (Exception e) {
            LOG.error("Fehler beim Lesen der Datei", e);
            return Response.serverError().entity("Fehler beim Datei-Download").build();
        }
    }
}
