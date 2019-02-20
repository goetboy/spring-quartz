package pers.goetboy;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author:goetb
 * @date 2019 /01 /28
 **/
@SpringBootApplication
@MapperScan(basePackages = "pers.goetboy.*.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
