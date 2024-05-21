package vibe.EZsesh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vibe.EZsesh.EntryPoint;
import vibe.EZsesh.entities.AppUser;
import vibe.EZsesh.entities.ImageData;
import vibe.EZsesh.repositories.StorageRepository;
import vibe.EZsesh.utils.ImageUtils;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        Optional<ImageData> dbImageData = storageRepository.findByAppUser(AppUser.builder()
                .id(EntryPoint.currentUser.getId())
                .username(EntryPoint.currentUser.getUsername())
                .email(EntryPoint.currentUser.getEmail())
                .password(EntryPoint.currentUser.getPassword())
                .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                .build());

        if (dbImageData.isEmpty()) {
            ImageData imageData = storageRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes()))
                    .appUser(AppUser.builder()
                            .id(EntryPoint.currentUser.getId())
                            .username(EntryPoint.currentUser.getUsername())
                            .email(EntryPoint.currentUser.getEmail())
                            .password(EntryPoint.currentUser.getPassword())
                            .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                            .role(EntryPoint.currentUser.getRole())
                            .build())
                    .build());
            if (imageData != null) {
                return "Файл успешно загружен : " + file.getOriginalFilename();
            }
            return null;
        }

        try {
            storageRepository.updateImageDataById(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    ImageUtils.compressImage(file.getBytes()),
                    AppUser.builder()
                            .id(EntryPoint.currentUser.getId())
                            .username(EntryPoint.currentUser.getUsername())
                            .email(EntryPoint.currentUser.getEmail())
                            .password(EntryPoint.currentUser.getPassword())
                            .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                            .role(EntryPoint.currentUser.getRole())
                            .build()
            );
            return "Файл успешно обновлён : " + file.getOriginalFilename();
        }
        catch (Exception e) {
            return "При обновлении файла возникла ошибка: " + e.getMessage();
        }
    }



    public byte[] downloadImage() {
        Optional<ImageData> dbImageData = storageRepository.findByAppUser(AppUser.builder()
                .id(EntryPoint.currentUser.getId())
                .username(EntryPoint.currentUser.getUsername())
                .email(EntryPoint.currentUser.getEmail())
                .password(EntryPoint.currentUser.getPassword())
                .resetPasswordCodeWord(EntryPoint.currentUser.getResetPasswordCodeWord())
                .build());
        byte[] images;
        try {
            images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        }
        catch (Exception e) {
            images = null;
        }
        return images;
    }

}
