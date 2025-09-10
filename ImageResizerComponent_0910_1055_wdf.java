// 代码生成时间: 2025-09-10 10:55:25
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.ImageObserver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageResizerComponent {
    
    // 通过Spring自动注入的方式设置图片的最大宽度和高度
    @Value("{image.maxWidth}")
    private int maxWidth;
    
    @Value("{image.maxHeight}")
    private int maxHeight;

    // 批量调整图片尺寸的方法
    public Response<List<String>> resizeImages(List<MultipartFile> files) {
        try {
            List<String> resizedImagePaths = new ArrayList<>();
            for (MultipartFile file : files) {
                String resizedImagePath = resizeImage(file);
                resizedImagePaths.add(resizedImagePath);
            }
            return new Response<>(resizedImagePaths);
        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理
            return new Response<>(null, "Error resizing images: " + e.getMessage());
        }
    }

    private String resizeImage(MultipartFile file) throws IOException {
        // 读取图片
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, (ImageObserver) null);
        g2d.dispose();
        // 保存调整后的图片
        String resizedImagePath = "resized_" + file.getOriginalFilename();
        File resizedImageFile = new File(resizedImagePath);
        ImageIO.write(resizedImage, "jpg", resizedImageFile);
        return resizedImagePath;
    }

    // 用于包装方法返回值的类
    public static class Response<T> {
        private T data;
        private String message;

        public Response(T data, String message) {
            this.data = data;
            this.message = message;
        }

        // Getter and Setter
        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
