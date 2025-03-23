package com.example.filestorage;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class FileStorageService {

    private static final Logger LOG = Logger.getLogger(FileStorageService.class);

    // Pfad zum lokalen Upload-Verzeichnis (z.B. /tmp/uploads)
    private final Path uploadDir;

    public FileStorageService() {
        // Beispielhaft: /tmp/uploads
        this.uploadDir = Paths.get(System.getProperty("java.io.tmpdir"), "uploads");
        try {
            Files.createDirectories(uploadDir);
            LOG.info("Upload-Verzeichnis erstellt: " + uploadDir.toAbsolutePath());
        } catch (Exception e) {
            LOG.error("Konnte Upload-Verzeichnis nicht erstellen", e);
        }
    }

    /**
     * Speichert die Datei im lokalen Verzeichnis.
     *
     * @param fileData   Byte-Array des hochgeladenen Inhalts
     * @param fileName   Gewünschter Dateiname
     * @return           Tatsächlich gespeicherter Dateiname (ggf. mit Suffix oder generiertem Namen)
     */
    public String storeFile(byte[] fileData, String fileName) throws Exception {
        // Aus Sicherheitsgründen ggf. Dateinamen validieren, Sonderzeichen filtern etc.
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

        Path targetFile = uploadDir.resolve(safeFileName).toAbsolutePath();

        try (FileOutputStream fos = new FileOutputStream(targetFile.toFile())) {
            fos.write(fileData);
        }
        LOG.infof("Datei gespeichert: %s", targetFile);
        return safeFileName;
    }

    /**
     * Liefert eine Datei aus dem Upload-Verzeichnis zurück.
     * @param filename Name der gesuchten Datei
     * @return File-Objekt oder null falls nicht vorhanden
     */
    public File getFile(String filename) {
        File file = uploadDir.resolve(filename).toFile();
        return file.exists() ? file : null;
    }
}
