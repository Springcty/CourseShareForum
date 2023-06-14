package com.software.blogserver.vo;

import com.software.blogserver.entity.Labels;
import lombok.Data;

import java.io.Serializable;

@Data
public class LabelsVO extends Labels implements Serializable {
    private Integer labelCount;
}
