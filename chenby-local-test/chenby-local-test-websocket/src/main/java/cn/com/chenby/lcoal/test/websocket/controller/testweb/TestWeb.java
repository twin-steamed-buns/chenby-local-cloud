package cn.com.chenby.lcoal.test.websocket.controller.testweb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testWeb")
public class TestWeb {

    @RequestMapping("/testWebApi")
    public String test(@RequestParam String type){
        String result = "success";
        System.out.println("type:>>>"+type);
        Integer typeInt = Integer.parseInt(type);
//        System.out.println("num:>>>"+num);
        JSONArray ja = new JSONArray();
        for(int i = 1;i<10;i++){
            JSONObject jo = new JSONObject();
            String x = "2010/0"+i+"/01 00:00:00";
            int y = i * 10 * typeInt.intValue();
            jo.put("x",x);
            jo.put("y",y);

            ja.add(jo);
        }
        System.out.println(ja.toJSONString());
        return ja.toJSONString();
    }
}
