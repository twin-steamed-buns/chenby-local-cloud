package cn.com.chenby.local.test.customizeAnnotation.controller;

import cn.com.chenby.local.test.customizeAnnotation.annotation.TestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestAnnotationController {

    @RequestMapping("/test")
    @TestLog
    public String test(){
        log.debug("============  test now  ============");
        return "go";
    }
}
