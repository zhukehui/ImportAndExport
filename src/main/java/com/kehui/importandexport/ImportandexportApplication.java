package com.kehui.importandexport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kehui.importandexport.mapper")
public class ImportandexportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportandexportApplication.class, args);
    }

}
