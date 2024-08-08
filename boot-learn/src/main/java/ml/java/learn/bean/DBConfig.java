package ml.java.learn.bean;

import ml.java.learn.service.StorageService;
import ml.java.learn.service.impl.AliyunStorageImpl;
import ml.java.learn.service.impl.HuaWeiStorageImpl;
import ml.java.learn.service.impl.LocalStorageImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: jiml
 * @Date: 2024/8/8 19:54
 * @Description:
 */

@Component
public class DBConfig {

    /**
     * 当存在属性ml.study.storage.type时装配该实例
     * 如果没有该属性值也装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "ml.study.storage.type", havingValue = "local", matchIfMissing = true)
    public StorageService createLocalDataSource() {
        return new LocalStorageImpl();
    }

    /**
     * 当存在属性ml.study.storage.type时装配该实例
     * 如果没有该属性值不装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "ml.study.storage.type", havingValue = "huawei", matchIfMissing = false)
    public StorageService createHuaweiDataSource() {
        return new HuaWeiStorageImpl();
    }


    /**
     * 当存在属性ml.study.storage.type时装配该实例
     * 如果没有该属性值不装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "ml.study.storage.type", havingValue = "aliyun", matchIfMissing = false)
    public StorageService createAliyunDataSource() {
        return new AliyunStorageImpl();
    }

}
