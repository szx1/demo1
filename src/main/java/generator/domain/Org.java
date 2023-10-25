package generator.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @author zengxi.song
 * @date 2023/10/22
 */
@Setter
@Getter
@Table(name = "bifrost_org")
@ToString
public class Org {
    /**
     * 主键
     */
    @Id
    private Long id;

    private String uuid;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    @Column(name = "gmt_modify")
    private Date gmtModify;

    /**
     * 机构号唯一
     */
    private String code;

    /**
     * 机构级别：1、2、3
     */
    @Column(name = "org_level")
    private Integer level;

    /**
     * 上级机构uuid 可为null
     */
    @Column(name = "parent_uuid")
    private String parentUuid;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构英文名称
     */
    private String enName;

    /**
     * 创建用户账号
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 修改用户账号
     */
    @Column(name = "update_by")
    private String updateBy;
    /**
     * 子机构，显示用
     */
    List<Org> children;
}
