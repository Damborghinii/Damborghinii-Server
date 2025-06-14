package org.dongguk.dambo.infrastructure.api;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class GcsClient {
    private static final String IMAGE_DIR = "image";
    private static final String FILE_DIR = "file";
    @Value("${cloud.storage.bucket}")
    private String BUCKET_NAME;
    private final Storage gcsStorage;

    public String uploadImage(Long userId, Long copyrightId, String imageName, MultipartFile file) {
        return upload(userId, copyrightId, IMAGE_DIR, imageName, file);
    }

    public String uploadFile(Long userId, Long copyrightId, String fileName, MultipartFile file) {
        return upload(userId, copyrightId, FILE_DIR, fileName, file);
    }

    public void deleteByUrl(String url) {
        String objectName = extractObjectNameFromUrl(url);

        boolean deleted = gcsStorage.delete(BUCKET_NAME, objectName);
        if (!deleted) {
            throw new RuntimeException("GCS 파일 삭제 실패: " + objectName);
        }
    }

    private String upload(Long userId, Long copyrightId, String typeDir, String objectName, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String objectPath = String.format("user%d/copyright%d/%s/%s", userId, copyrightId, typeDir, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, objectPath)
                    .setContentType(file.getContentType())
                    .build();
            gcsStorage.create(blobInfo, bytes);

            return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, objectPath);
        } catch (Exception e) {
            throw new RuntimeException("GCS 파일 업로드 실패", e);
        }
    }

    private String extractObjectNameFromUrl(String url) {
        String prefix = String.format("https://storage.googleapis.com/%s/", BUCKET_NAME);

        if (!url.startsWith(prefix)) {
            throw new RuntimeException("올바르지 않은 GCS URL입니다: " + url);
        }
        return url.substring(prefix.length());
    }
}
