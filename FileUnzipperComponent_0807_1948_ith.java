// 代码生成时间: 2025-08-07 19:48:32
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A Spring Boot component for unzipping compressed files.
 */
@Component
public class FileUnzipperComponent {

    private static final String TEMP_DIRECTORY = "/tmp/unzipped_files/";

    /**
     * Unzips a given zip file to a specified directory.
     * @param zipFile The zip file to be unzipped.
     * @param destinationDirectory The directory where the file will be unzipped.
     * @return A message indicating the status of the operation.
     * @throws IOException If an I/O error occurs.
     */
    public String unzipFile(MultipartFile zipFile, String destinationDirectory) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(Paths.get(destinationDirectory + File.separator + fileName));
                } else {
                    File newFile = newFile(destinationDirectory + File.separator + fileName);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }

                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            // Handle the exception, log the error, and return an error message.
            // Depending on the application's requirements, you may choose to rethrow the exception or return a custom error message.
            return "Error occurred while unzipping the file: " + e.getMessage();
        }
        return "File has been successfully unzipped to the destination directory.";
    }

    /**
     * Creates a new File instance.
     * @param directoryPath The path where the file will be created.
     * @return A new File instance.
     */
    private File newFile(String directoryPath) {
        try {
            return new File(directoryPath);
        } catch (Exception e) {
            // Handle exceptions, log the error, and return null or throw an exception as needed.
            return null;
        }
    }
}
