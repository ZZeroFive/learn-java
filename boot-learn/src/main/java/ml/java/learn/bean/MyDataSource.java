package ml.java.learn.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: jiml
 * @Date: 2024/8/8 19:54
 * @Description: 自定义数据源
 */

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "ml.study.storage")
public class MyDataSource {

    private String type;
    private String uName;
    private String pwd;


    @PostConstruct
    public void init() {
        log.info("装配的DataSource信息: {}", this);
    }
}
