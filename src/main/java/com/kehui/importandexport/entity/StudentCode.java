package com.kehui.importandexport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author eternity
 * @create 2020-09-02 22:33
 */
@Data
@TableName("student_code")
public class StudentCode {
    private String code;
}
