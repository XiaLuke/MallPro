package cn.xf.thirdparty.component;

import cn.xf.common.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送消息
 *
 * @author XF
 * @date 2022/09/14
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.cloud.alicloud.msg")
public class SendMessage {


    private String host;
    private String path;
    private String appcode;

    public void sendCode(String phone,String code) {
        String host = this.host;
        String path = this.path;
        String method = "POST";
        String appcode = this.appcode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", code);
        querys.put("mobile", phone);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
