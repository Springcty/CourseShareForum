package com.software.blogserver.vo;

import com.software.blogserver.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesVO extends Categories implements Serializable {
    private Integer categoryCount;
}
