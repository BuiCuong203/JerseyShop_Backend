package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.constant.DefinedUpLoadDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get(DefinedUpLoadDir.UPLOAD_DIR).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Không thể tạo thư mục lưu trữ các tập tin đã tải lên.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Tạo tên file duy nhất
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
            // Kiểm tra tên file có chứa ký tự không hợp lệ
            if(fileName.contains("..")) {
                throw new RuntimeException("Tên tệp chứa chuỗi đường dẫn không hợp lệ " + fileName);
            }

            // Copy file vào thư mục đích
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn tương đối để lưu vào DB
            return DefinedUpLoadDir.UPLOAD_DIR + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu trữ tập tin " + fileName + ". Vui lòng thử lại", ex);
        }
    }
}
