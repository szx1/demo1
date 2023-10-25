package generator.mapper;

import generator.domain.Org;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2023/10/22
 */
public interface OrgMapper extends Mapper<Org> {

    List<Org> testRecursion();

    List<Org> selectByParentUuid(@Param("parentUuid") String parentUuid);

    List<Org> testGroupBy();
}
