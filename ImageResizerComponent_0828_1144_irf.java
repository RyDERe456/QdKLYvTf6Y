// 代码生成时间: 2025-08-28 11:44:03
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ImageResizerComponent {

    private static final String TEMP_DIR = "temp";
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;

    public List<Path> resizeImages(List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                // 保存文件到临时目录
                Path path = saveFile(file);

                // 调整图片尺寸
                BufferedImage image = ImageIO.read(path.toFile());
                BufferedImage resizedImage = resizeImage(image);

                // 保存调整后的图片并返回路径
                return saveResizedImage(resizedImage, path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to resize image", e);
            }
        }).collect(Collectors.toList());
    }

    private Path saveFile(MultipartFile file) throws IOException {
        Path tempDir = Paths.get(TEMP_DIR);
        Files.createDirectories(tempDir); // 确保临时目录存在

        Path path = tempDir.resolve(file.getOriginalFilename());
        file.transferTo(path);
        return path;
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        int width = Math.min(originalImage.getWidth(), MAX_WIDTH);
        int height = Math.min(originalImage.getHeight(), MAX_HEIGHT);
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        return resizedImage;
    }

    private Path saveResizedImage(BufferedImage image, Path originalPath) throws IOException {
        Path resizedPath = originalPath.getParent().resolve("resized_" + originalPath.getFileName());
        ImageIO.write(image, "png", resizedPath.toFile());
        return resizedPath;
    }
}
