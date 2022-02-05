package cn.xf.product;

import cn.xf.product.entity.BrandEntity;
import cn.xf.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brand = new BrandEntity();
        brand.setDescript("你叉叉");
        brandService.saveOrUpdate(brand);
        System.out.println("success");
    }

}
