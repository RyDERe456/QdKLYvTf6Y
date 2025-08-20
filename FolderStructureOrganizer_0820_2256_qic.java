// 代码生成时间: 2025-08-20 22:56:20
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileSystemUtils;

@Component
@Order(1)
public class FolderStructureOrganizer {

    private final ResourceLoader resourceLoader;

    public FolderStructureOrganizer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void organize(String sourceFolder, String targetFolder) throws IOException {
        // Check if source and target folders are valid
        if (sourceFolder == null || targetFolder == null) {
            throw new IllegalArgumentException("Source and target folders cannot be null");
        }

        // Create target directory if it does not exist
        Path targetPath = Paths.get(targetFolder);
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        // Copy files from source to target directory
        Path sourcePath = Paths.get(sourceFolder);
        if (Files.exists(sourcePath) && Files.isDirectory(sourcePath)) {
            Files.walk(sourcePath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        Path targetFilePath = targetPath.resolve(sourcePath.relativize(file).toString());
                        Files.copy(file, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException("Error copying file: " + file, e);
                    }
                });
        } else {
            throw new IllegalArgumentException("Source folder does not exist or is not a directory");
        }
    }

    // Helper method to delete a folder and its contents
    private void deleteFolder(Path folderPath) throws IOException {
        if (Files.exists(folderPath)) {
            FileSystemUtils.deleteRecursively(folderPath);
        }
    }
}
