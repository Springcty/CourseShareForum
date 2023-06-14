package com.software.blogserver.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileOperation {

    public void saveFile(MultipartFile file, String path, String fileName) throws IOException {
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File f = new File(path + fileName);
        file.transferTo(f);
    }
}
