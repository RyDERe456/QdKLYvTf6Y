// 代码生成时间: 2025-09-02 04:31:59
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchFileRenamer {

    /**
     * Handles a POST request to rename files.
     * @param files List of files to rename.
     * @return A list of renamed file names.
     */
    @RequestMapping(value = "/rename-files", method = RequestMethod.POST)
    @ResponseBody
    public List<String> renameFiles(@RequestParam("files") List<MultipartFile> files) {
        List<String> renamedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                if (file.isEmpty()) {
                    throw new IOException("File is empty");
                }

                // Create a unique file name
                String fileName = "renamed_" + System.currentTimeMillis() + file.getOriginalFilename();
                String destPath = "/path/to/destination/" + fileName;

                // Save the file with a new name
                Files.copy(file.getInputStream(), Paths.get(destPath));

                renamedFiles.add(fileName);
            } catch (IOException e) {
                // Handle the error and add the error message to the list
                renamedFiles.add("Error: " + e.getMessage());
            }
        }
        return renamedFiles;
    }
}
