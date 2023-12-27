package exclude;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengxi.song
 * @date 2023/11/6
 */
@Configuration
public class ImportConf {

    @Bean
    public ExcludeBean excludeBean() {
        return new ExcludeBean("name");
    }
}
