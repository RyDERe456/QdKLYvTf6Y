// 代码生成时间: 2025-09-19 06:17:39
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
# 添加错误处理
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageReadParam;
# TODO: 优化性能
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 声明 Service 组件，用于图片尺寸批量调整
@Service
# 扩展功能模块
public class ImageResizerService {

    private static final Logger logger = LoggerFactory.getLogger(ImageResizerService.class);
# 改进用户体验

    // 调整图片尺寸的方法
    public File[] resizeImages(MultipartFile[] files, int targetWidth, int targetHeight) {
        File[] resizedFiles = new File[files.length];
        int fileIndex = 0;
# 改进用户体验

        for (MultipartFile file : files) {
            try {
                // 保存文件到临时路径
# FIXME: 处理边界情况
                Path tempFile = Files.createTempFile("resize", file.getOriginalFilename());
                file.transferTo(tempFile.toFile());

                // 读取图片文件
                BufferedImage sourceImage = ImageIO.read(tempFile.toFile());

                // 创建目标图片缓冲区
                BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, sourceImage.getType());
# 扩展功能模块

                // 绘制调整尺寸后的图片
                resizedImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);

                // 保存调整后的图片
                ImageIO.write(resizedImage, "png", tempFile.toFile());

                resizedFiles[fileIndex++] = tempFile.toFile();
            } catch (IOException e) {
                logger.error("Error resizing image: " + e.getMessage(), e);
                throw new RuntimeException("Error resizing image", e);
            }
        }
        return resizedFiles;
    }

    // 清理临时文件的方法
    public void cleanupTempFiles(File[] files) {
# 添加错误处理
        for (File file : files) {
            try {
                Files.deleteIfExists(file.toPath());
# 增强安全性
            } catch (IOException e) {
                logger.error("Error cleaning up temp file: " + e.getMessage(), e);
            }
        }
    }
}
