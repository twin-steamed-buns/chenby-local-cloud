package cn.com.chenby.lcoal.test.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    @RequestMapping("control")
    public String control() {
        return "control/remoteControl";
    }

    @RequestMapping("screen1")
    public String screen1() {
        return "screen/screen_1";
    }

    @RequestMapping("screen2")
    public String screen2() {
        return "screen/screen_2";
    }


}
