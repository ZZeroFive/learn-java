package ml.java.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.java.learn.service.StorageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Auther: jiml
 * @Date: 2024/8/6 20:52
 * @Description:
 */



@Slf4j
// @Profile(value = "test")
public class HuaWeiStorageImpl implements StorageService {
    @PostConstruct
    public void init() {
        log.info("激活HuaWeiStorageImpl");
    }
    @Override
    public void store(String content) {
        log.info("HuaWeiStorageImpl 实现store功能");
    }
}
