package top.kaluna.wiki.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author Yuery
 * @date 2022/1/16/0016 - 10:41
 */
public interface DocMapperCust {

    void increaseViewCount(@Param("id") Long id);

    void increaseVoteCount(@Param("id") Long id);
}
