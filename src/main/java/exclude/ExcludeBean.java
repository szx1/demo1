package exclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

/**
 * @author zengxi.song
 * @date 2023/11/6
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcludeBean {

    private String name;

    @Bean
    public ExcludeBeanTwo excludeBeanTwo() {
        return new ExcludeBeanTwo();
    }
}
