package cn.gxf;

import org.apache.catalina.LifecycleException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class MyPlatformApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyPlatformApplication.class, args);

	}

}

