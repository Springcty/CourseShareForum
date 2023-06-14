package com.software.blogcommon.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class NewAttachBean implements Serializable {
    private Integer articleId;
    private MultipartFile content;
}
