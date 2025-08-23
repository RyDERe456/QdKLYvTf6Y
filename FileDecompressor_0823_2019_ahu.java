// 代码生成时间: 2025-08-23 20:19:02
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class FileDecompressor {

    // 解压ZIP文件
    public void unzipFile(MultipartFile file, String destinationFolder) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        // 获取文件的字节流
        InputStream inputStream = file.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        // 创建目标文件夹
        File destDir = new File(destinationFolder);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                newFile.mkdirs();
            } else {
                newFile.createNewFile();
                copyInputStream(zipInputStream, newFile);
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    // 辅助方法：根据ZIP条目创建文件
    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("ZIP文件包含无效的路径");
        }

        return destFile;
    }

    // 辅助方法：复制输入流到文件
    private void copyInputStream(InputStream inputStream, File destFile) throws IOException {
        OutputStream output = new FileOutputStream(destFile);
        try {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            output.close();
        }
    }
}
