// 代码生成时间: 2025-09-23 09:38:04
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileSystemUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BulkFileRenamer {

    @Value("{file.directory}")
    private String directory;

    private final ResourceLoader resourceLoader;

    public BulkFileRenamer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Renames files in the specified directory based on a naming pattern.
     * 
     * @param oldNames List of old file names.
     * @param newNameFormat Format string for new file names, e.g., "file-%d.ext".
     * @param extension The file extension to be used for renaming.
     * @return A list of renamed files with their new names.
     * @throws IOException If any file operation fails.
     */
    public List<String> renameFiles(List<String> oldNames, String newNameFormat, String extension) throws IOException {
        Path path = Paths.get(directory);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IOException("The specified directory does not exist or is not a directory.");
        }

        List<String> renamedFiles = oldNames.stream()
                .map(oldName -> {
                    try {
                        Path oldFilePath = path.resolve(oldName);
                        if (!Files.exists(oldFilePath)) {
                            throw new IOException("File not found: " + oldName);
                        }

                        String newName = String.format(newNameFormat, oldNames.indexOf(oldName) + 1) + "." + extension;
                        Path newFilePath = path.resolve(newName);
                        Files.move(oldFilePath, newFilePath);
                        return newName;
                    } catch (IOException e) {
                        throw new RuntimeException("Error renaming file: " + oldName, e);
                    }
                })
                .collect(Collectors.toList());

        return renamedFiles;
    }
}
