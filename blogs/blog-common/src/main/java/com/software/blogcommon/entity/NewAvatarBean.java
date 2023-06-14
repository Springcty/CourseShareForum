package com.software.blogcommon.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


@Data
public class NewAvatarBean implements Serializable {
    private MultipartFile content;
}

