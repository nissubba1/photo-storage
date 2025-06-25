package org.example.server.upload;

import jakarta.transaction.Transactional;
import org.example.server.aws.AwsS3Config;
import org.example.server.user.User;
import org.example.server.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final S3Client s3Client;
    private final String bucketName;
    private final S3Presigner s3Presigner;

    public PhotoService(PhotoRepository photoRepository, UserService userService, S3Client s3Client, AwsS3Config awsS3Config, S3Presigner s3Presigner) {
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.s3Client = s3Client;
        this.bucketName = awsS3Config.getBucketName();
        this.s3Presigner = s3Presigner;
    }

    @Transactional(rollbackOn = Exception.class)
    public Photo uploadPhotoForCurrentUser(String username, MultipartFile file) {
//        User user = userService.getCurrentAuthenticatedUser();
        User user = userService.findUserUsingUsername(username);
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("File name is null");
        }
        String newFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8);
        String s3Key = "user_" + user.getUserId() + "/" + UUID.randomUUID() + "-" + newFileName;

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

//            String url = "https://" + bucketName + ".s3.amazonaws.com/" + s3Key;
            String url = generatePresignedUrl(s3Key);

            Photo photo = new Photo();
            photo.setFileName(originalFileName);
            photo.setS3Key(s3Key);
            photo.setUrl(url);
            photo.setUser(user);
            return photoRepository.save(photo);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload photo for current user", e);
        }
    }

    public String generatePresignedUrl(String s3Key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
