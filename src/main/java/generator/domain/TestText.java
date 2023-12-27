package generator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zengxi.song
 * @date 2023/11/23
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_text")
public class TestText {

    @Id
    private Long id;

    private String uuid;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;
}
