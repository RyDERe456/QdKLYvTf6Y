// 代码生成时间: 2025-09-11 05:34:55
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class ImageResizerService {

    private final int maxWidth;
    private final int maxHeight;

    public ImageResizerService() {
        this.maxWidth = 800; // Default max width
        this.maxHeight = 600; // Default max height
    }

    /**
     * Resizes a list of images in batch.
     *
     * @param images List of MultipartFile to be resized
     * @return List of resized images as MultipartFile
     */
    public List<MultipartFile> resizeImages(List<MultipartFile> images) {
        List<MultipartFile> resizedImages = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                BufferedImage originalImage = ImageIO.read(image.getInputStream());
                Dimension resizedDimension = calculateResizeDimension(originalImage);
                BufferedImage resizedImage = resizeImage(originalImage, resizedDimension);
                resizedImages.add(convertToMultipartFile(resizedImage));
            } catch (IOException e) {
                // Error handling: Log the error and continue with the next image
                System.err.println("Error resizing image: " + e.getMessage());
            }
        }
        return resizedImages;
    }

    /**
     * Calculates the new dimensions to resize the image while maintaining aspect ratio.
     *
     * @param originalImage The original image
     * @return Dimension with new width and height
     */
    private Dimension calculateResizeDimension(BufferedImage originalImage) {
        double width = originalImage.getWidth();
        double height = originalImage.getHeight();
        double aspectRatio = width / height;
        int newWidth = (int) (maxHeight * aspectRatio);
        int newHeight = maxHeight;
        if (newWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) (maxWidth / aspectRatio);
        }
        return new Dimension(newWidth, newHeight);
    }

    /**
     * Resizes the image to the specified dimensions.
     *
     * @param originalImage The original image
     * @param dimension The new dimensions
     * @return A resized BufferedImage
     */
    private BufferedImage resizeImage(BufferedImage originalImage, Dimension dimension) {
        BufferedImage resizedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, dimension.width, dimension.height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * Converts a BufferedImage to a MultipartFile.
     *
     * @param image The image to convert
     * @return A MultipartFile representation of the image
     * @throws IOException If an I/O error occurs during file writing
     */
    private MultipartFile convertToMultipartFile(BufferedImage image) throws IOException {
        File tempFile = File.createTempFile("resizedImage", ".png");
        tempFile.deleteOnExit();
        ImageIO.write(image, "png", tempFile);
        return new MockMultipartFile("file", tempFile.getName(), "image/png", tempFile);
    }
}

/**
 * MockMultipartFile is used to create a MultipartFile from a file for testing purposes.
 * In a real application, consider using a library like Apache Commons FileUpload or Spring's own MultipartFile.
 */
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

class MockMultipartFile implements MultipartFile {
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final File file;

    public MockMultipartFile(String name, String originalFilename, String contentType, File file) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.file = file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return Files.size(file.toPath()) == 0;
    }

    @Override
    public long getSize() {
        try {
            return file.length();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get file size", e);
        }
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(file.toPath());
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toPath());
    }
}