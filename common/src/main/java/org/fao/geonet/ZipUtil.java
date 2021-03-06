package org.fao.geonet;

import org.fao.geonet.utils.IO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

/**
 * Zip or unzip files
 */
public class ZipUtil {
    public static void extract(Path zipFile, Path toDir) throws IOException, URISyntaxException {
        try (FileSystem fs = openZipFs(zipFile)) {
            extract(fs, toDir);
        }
    }

    /**
     * Extracts a zip file to a specified directory.
     *
     * @param zipFile the zip file to extract
     * @param toDir   the target directory
     * @throws java.io.IOException
     */
    public static void extract(FileSystem zipFile, Path toDir) throws IOException {
        Files.createDirectories(toDir);

        for (Path root : zipFile.getRootDirectories()) {
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(root)) {
                for (Path path : paths) {
                    final Path fileName = path.getFileName();
                    if (fileName != null) {
                        IO.copyDirectoryOrFile(path, toDir.resolve(fileName.toString()), false);
                    }
                }
            }
        }
    }

    /**
     * FileSystem must be closed when done.  This method should always be called in a try (resource) {} block
     */
    public static FileSystem openZipFs(Path path) throws IOException, URISyntaxException {
        try {
            URI uri = new URI("jar:" + path.toUri().toString());
            return FileSystems.newFileSystem(uri, Collections.singletonMap("create", String.valueOf(false)));
        } catch (Throwable e) {
            try {
                URI uri = new URI("jar:" + URLDecoder.decode(path.toUri().toString(), Constants.ENCODING));
                return FileSystems.newFileSystem(uri, Collections.singletonMap("create", String.valueOf(false)));
            } catch (Throwable e2) {
                URI uri = new URI("jar:" + URLEncoder.encode(path.toUri().toString(), Constants.ENCODING));
                return FileSystems.newFileSystem(uri, Collections.singletonMap("create", String.valueOf(false)));
            }
        }
    }

    /**
     * Delete path if it exists and create a new zip at the location.
     * <p/>
     * FileSystem must be closed when done.
     * <p/>
     * This method should always be called in a try (resource) {} block
     */
    public static FileSystem createZipFs(Path path) throws IOException, URISyntaxException {
        Files.deleteIfExists(path);

        URI uri = new URI("jar:" + path.toUri());
        return FileSystems.newFileSystem(uri, Collections.singletonMap("create", String.valueOf(true)));
    }
}
