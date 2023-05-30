package com.github.mengweijin.vitality.framework.minio;

import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.MinioServiceException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Slf4j
public class MinioService {
    @Autowired
    private MinioProperties minioProperties;

    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        this.minioClient = MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                .build();
    }

    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public void makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public String upload(MultipartFile file) {
        String bucket = minioProperties.getBucket();
        String filePath = DateUtil.format(LocalDateTime.now(), "yyyy/MM/dd") + Const.SLASH + IdUtil.simpleUUID() + Const.SLASH + file.getOriginalFilename();
        try {
            if(!this.bucketExists(bucket)) {
                this.makeBucket(bucket);
            }
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            //文件名称相同会覆盖
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(objectArgs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
        return filePath;
    }

    public String preview(String filePath) {
        try {
            // 查看文件地址
            GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(filePath)
                    .method(Method.GET)
                    .build();
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public GetObjectResponse download(String filePath) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(filePath).build();
        try {
            return minioClient.getObject(objectArgs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }

    public void remove(String filePath){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(filePath).build());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new MinioServiceException(e);
        }
    }
}
