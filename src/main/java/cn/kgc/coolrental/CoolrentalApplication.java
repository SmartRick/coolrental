package cn.kgc.coolrental;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
public class CoolrentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolrentalApplication.class, args);
    }

}
