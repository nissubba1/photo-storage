package org.example.server.upload;

import org.example.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByFileName(String fileName);

    Optional<Photo> findByUrl(String url);

    Optional<Photo> findByS3Key(String s3Key);

    Optional<List<Photo>> findByUser(User user);
}
