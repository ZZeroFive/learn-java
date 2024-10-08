package ml.java.learn.controller;

import lombok.extern.slf4j.Slf4j;
import ml.java.learn.bean.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: jiml
 * @Date: 2024/7/31 20:52
 * @Description:
 */

@RequestMapping(value = "helloController")
@RestController
@Slf4j
public class HelloController {

    @GetMapping(value = "greet")
    public Result<String> greet( @RequestParam(required = true, name = "name") String name) {
        String greet = "hello " + name;
        log.info("用户请求参数信息: {}", name);
        return Result.ok(greet);
    }

    @GetMapping(value = "/{id}/info")
    public Result<Void> demoGetPathVariable(@PathVariable(value = "id") String id) {
        log.info("用户请ID: {}", id);
        return Result.ok();
    }


}
