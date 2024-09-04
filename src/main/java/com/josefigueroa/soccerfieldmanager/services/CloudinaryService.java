package com.josefigueroa.soccerfieldmanager.services;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;


    public Map<String, Object> uploadImg(MultipartFile file, String folder) throws IOException{
        String originalFileName = file.getOriginalFilename();

        if(originalFileName==null){
            throw new IllegalArgumentException("el archivo es nulo");
        }

        String newName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

        String timeStamp = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());

        String fileName = newName + "_" + timeStamp;

        Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folder,"public_id", fileName));

        return uploadResult;
    }
    
}
