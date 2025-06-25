package org.example.server.upload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/photo-service")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> uploadPhoto(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok().body(photoService.uploadPhotoForCurrentUser(username, file));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-photo/{username}")
    public ResponseEntity<String> deletePhoto(@PathVariable String username, Long photoId) {
        try {
            return ResponseEntity.ok().body(photoService.deleteCurrentUserPhoto(username, photoId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Delete failed: " + e.getMessage());
        }
    }

    @GetMapping("/gallery/{username}")
    public ResponseEntity<?> getUserGallery(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(photoService.findUserGallery(username));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
