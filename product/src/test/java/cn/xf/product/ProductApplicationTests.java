package cn.xf.product;

import cn.xf.product.entity.BrandEntity;
import cn.xf.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Test
    void fileUpload() throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tB5UAcGJtvCVwSGdNbD";
        String accessKeySecret = "wEjGOiGRWRGsi8uzydRBTjESDYXkqm";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "mallprotest";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "2020041119404981.png";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath = "E:\\File\\Study\\Note\\picture\\jvm\\2020041119404981.png";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream(filePath);
        // 创建PutObject请求。
        ossClient.putObject(bucketName, objectName, inputStream);

        ossClient.shutdown();
        System.out.println("上传完成");

    }

    @Test
    void contextLoads() {
        BrandEntity brand = new BrandEntity();
        brand.setDescript("你叉叉");
        brandService.saveOrUpdate(brand);
        System.out.println("success");
    }

}
