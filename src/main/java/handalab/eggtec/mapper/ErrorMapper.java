package handalab.eggtec.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ErrorMapper {
    int errLog(@Param("type") String type, @Param("s") String s);
}
