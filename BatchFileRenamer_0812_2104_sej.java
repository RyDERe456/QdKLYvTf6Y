// 代码生成时间: 2025-08-12 21:04:12
import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Component
public class BatchFileRenamer {
    
    /**
     * Renames a batch of files based on the provided list of names.
     * @param directoryPath The directory path where files are located.
     * @param newNames A list of new names for the files.
     * @return A list of renamed files' paths.
     * @throws Exception If an error occurs during renaming.
     */
    public List<String> renameFiles(String directoryPath, List<String> newNames) throws Exception {
        // Check if directory exists
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Directory does not exist or is not a directory: " + directoryPath);
        }
        
        // Check if the list of new names is not empty and not null
        if (newNames == null || newNames.isEmpty()) {
            throw new IllegalArgumentException("List of new names cannot be null or empty");
        }
        
        // Get the list of files in the directory
        File[] files = directory.listFiles();
        if (files == null) {
            throw new Exception("Error retrieving files from directory: " + directoryPath);
        }
        
        // Rename files based on the new names list
        return IntStream.range(0, files.length)
            .mapToObj(i -> {
                try {
                    if (i < newNames.size()) {
                        File file = files[i];
                        String newFileName = newNames.get(i);
                        if (StringUtils.hasText(newFileName)) {
                            File renamedFile = new File(directoryPath + File.separator + newFileName);
                            Files.move(file.toPath(), renamedFile.toPath());
                            return renamedFile.getAbsolutePath();
                        } else {
                            throw new IllegalArgumentException("Invalid new file name at index " + i);
                        }
                    } else {
                        throw new Exception("More files than new names provided");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error renaming file at index " + i, e);
                }
            })
            .collect(Collectors.toList());
    }
}
