// 代码生成时间: 2025-09-06 12:53:37
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUnzipper {

    private static final String TEMP_DIR = "/tmp/unzipped-files";  // 定义临时目录

    public void unzipFile(MultipartFile file, String targetDirectory) throws IOException, ArchiveException {
        // 确保目标目录存在
        File targetDir = new File(targetDirectory);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // 临时文件存储
        File tempFile = new File(TEMP_DIR, file.getOriginalFilename());
        try (InputStream is = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(tempFile)) {

            IOUtils.copy(is, fos); // 将上传的文件复制到临时目录

            TarArchiveInputStream tais = null;
            try {
                // 创建解压缩流
                tais = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream(
                        ArchiveStreamFactory.TAR, new FileInputStream(tempFile));

                TarArchiveEntry entry;
                while ((entry = tais.getNextTarEntry()) != null) {
                    // 解压缩文件到目标目录
                    File outputFile = new File(targetDir, entry.getName());
                    if (entry.isDirectory()) {
                        outputFile.mkdirs();
                    } else {
                        outputFile.getParentFile().mkdirs(); // 确保父目录存在
                        try (OutputStream out = new FileOutputStream(outputFile)) {
                            IOUtils.copy(tais, out); // 将解压数据写入文件
                        }
                    }
                }
            } finally {
                if (tais != null) {
                    tais.close();
                }
            }
        } finally {
            // 清理临时文件
            Files.deleteIfExists(Paths.get(tempFile.getAbsolutePath()));
        }
    }
}
