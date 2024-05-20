package vibe.EZsesh.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.ImageData;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByAppUser(AppUser appUser);
    @Transactional
    @Modifying
    @Query("update ImageData i set i.name = ?1, i.type = ?2, i.imageData = ?3 where i.appUser = ?4")
    void updateImageDataById(String name, String type, byte[] imageData, AppUser app_user_id);
}
