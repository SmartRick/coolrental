package cn.kgc.coolrental;

import cn.kgc.coolrental.config.UploadProperties;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.URL;

@SpringBootApplication
@EnableConfigurationProperties(UploadProperties.class)
public class CoolrentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolrentalApplication.class, args);
    }

}
