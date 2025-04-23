package com.llanerito.manu.intrastructure.cloudinary;


import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryAdapter {
   private final Cloudinary cloudinary;

    public CloudinaryAdapter() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dt9wxgpzu",
            "api_key", "516288349395745",
            "api_secret", "M2j72kT7I_drnYoZs2PWvFOX5GM"
        ));
    }

    public String uploadImage(String filePath) {
        try {
            Map uploadResult = cloudinary.uploader().upload(filePath, ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary", e);
        }
    }
}
