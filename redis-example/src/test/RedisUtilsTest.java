import com.example.redis.RedisExampleApplication;
import com.example.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = RedisExampleApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RedisUtilsTest {

    @Resource
    private RedisUtil redisUtils;

    @Test
    public void testRedis() {
        String stringKey = "login-user-1";
        redisUtils.set(stringKey, "newrain");
        log.info(redisUtils.get(stringKey));
    }


}