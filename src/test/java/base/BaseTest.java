package base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件

@ContextConfiguration({ "classpath:spring/spring-service.xml", "classpath:spring/spring-web.xml" })
@WebAppConfiguration
public class BaseTest {
    @Test
    public void test() throws Exception {
        return;
    }
}