package generator.mapper;

import cn.tongdun.tianzhou.common.mybatis.mapper.IMapper;
import com.example.demo.entity.UserTotalDO;
import generator.domain.GroupTest;
import generator.domain.Test;
import javafx.util.Pair;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Entity generator.domain.Test
 */
@Mapper
public interface TestMapper extends IMapper<Test> {


    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    List<List<?>> testTotal();

    List<GroupTest> testGroupBy();

    List<List<Test>> testGroupBy2();


    @MapKey("name")
    Map<String, List<String>> getName2TestMap();

    int insertReturnId(@Param("test") Test record);

    List<String> getIdsByName(@Param("names") List<String> names);

    List<Date> getGmtModifyFromName(String name);

    void updateWhenCase(@Param("map") Map<String, String> map);


    void insertDate(@Param("date") Date data);

    Test selectOneOrderById();

    Boolean testBoolean();

    List<Test> testPair(@Param("pairs") Collection<Pair<String, Integer>> pairs);

    void alterTableLength(@Param("length") int length);

    default List<Test> testRecursion(List<Long> ids) {
        List<Test> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            result.add(selectByPrimaryKey(id));
        }
        return result;
    }

}
