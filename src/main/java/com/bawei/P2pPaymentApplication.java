package com.bawei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.bawei.mapper")
@EnableFeignClients
public class P2pPaymentApplication {

	public static void main(String[] args) {
		System.out.println("分支hostfix");
		SpringApplication.run(P2pPaymentApplication.class, args);
	}

}
