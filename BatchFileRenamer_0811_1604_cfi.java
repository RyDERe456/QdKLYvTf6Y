// 代码生成时间: 2025-08-11 16:04:30
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.ResourceLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Component
public class BatchFileRenamer implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Autowired
    public BatchFileRenamer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> renameFiles(List<MultipartFile> files, String targetDirectory, String prefix) {
        // Check if the files are not empty
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Files list is empty.");
        }

        // Check if the target directory is not empty
        if (StringUtils.isEmpty(targetDirectory)) {
            throw new IllegalArgumentException("Target directory is empty.");
        }

        // Check if the prefix is not empty
        if (StringUtils.isEmpty(prefix)) {
            throw new IllegalArgumentException("Prefix is empty.");
        }

        // Use a try-with-resources block to ensure files are closed
        try (List<MultipartFile> fileList = List.copyOf(files)) {
            // Get the target path
            Path targetPath = Paths.get(targetDirectory);

            // Check if the target directory exists, if not create it
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);
            }

            // Rename files and collect new file names
            return fileList.stream()
                .map(file -> {
                    try {
                        String originalFileName = file.getOriginalFilename();
                        String newFileName = prefix + System.currentTimeMillis() + getFileExtension(originalFileName);
                        String targetFilePath = targetPath.resolve(newFileName).toString();
                        // Save the file to the target directory
                        file.transferTo(targetFilePath);
                        return newFileName;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to rename file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
