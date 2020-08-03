package redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestRedisController {

    @RequestMapping("/test")
    public String test(){
        log.debug("============  test now  ============");
        return "go";
    }
}
