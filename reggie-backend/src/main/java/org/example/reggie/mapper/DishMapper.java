package org.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.reggie.entity.Dish;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    @Update({"<script>",
        "UPDATE dish SET status = #{status} ",
        "WHERE status != #{status} AND id in ",
        "   <foreach collection=\"ids\" item=\"id\" index=\"index\"",
        "            open=\"(\" close=\")\" separator=\",\">",
        "       #{id}",
        "   </foreach> ",
        "</script>"})
    public void changeStatusByIds(List<Long> ids, int status);
}
