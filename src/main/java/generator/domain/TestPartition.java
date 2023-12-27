package generator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zengxi.song
 * @date 2023/11/27
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "innodb_partitioned_table")
public class TestPartition {

    @Id
    private Long id;

    @Column(name = "task_uuid")
    private String taskUuid;

    private String name;

    private Integer age;
}
