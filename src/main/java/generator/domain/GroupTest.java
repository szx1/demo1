package generator.domain;

import lombok.Data;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/11/11
 */
@Data
public class GroupTest {

    private String name;

    private List<Test> testList;
}
