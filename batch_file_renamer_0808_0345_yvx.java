// 代码生成时间: 2025-08-08 03:45:00
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BatchFileRenamer {

    private final ResourceLoader resourceLoader;
    private final String directoryPath;

    @Autowired
    public BatchFileRenamer(ResourceLoader resourceLoader, @Value("${file.directory.path}") String directoryPath) {
        this.resourceLoader = resourceLoader;
        this.directoryPath = directoryPath;
    }

    // 重命名文件的方法
    public List<String> renameFiles(String oldNamePattern, String newNamePattern) {
        List<String> renamedFiles = new ArrayList<>();
        try {
            // 加载目录资源
            Resource[] resources = resourceLoader.getResources(directoryPath + "*");

            // 遍历目录下所有文件
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename.matches(oldNamePattern)) {
                    // 构建新文件名
                    String newFilename = filename.replaceAll(oldNamePattern, newNamePattern);

                    // 文件重命名逻辑
                    Path oldPath = Paths.get(resource.getURI());
                    Path newPath = oldPath.resolveSibling(newFilename);
                    Files.move(oldPath, newPath);

                    // 添加重命名的文件到列表
                    renamedFiles.add(newFilename);
                }
            }

            return renamedFiles;
        } catch (IOException e) {
            throw new RuntimeException("Error renaming files", e);
        }
    }

    // 获取目录下所有文件的方法
    private List<String> listFiles() {
        try {
            Resource[] resources = resourceLoader.getResources(directoryPath + "*");
            return Stream.of(resources)
                    .map(Resource::getFilename)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error listing files", e);
        }
    }
}
