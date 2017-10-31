package team.soth.favorisites.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by thinkam on 17-10-31.
 */
public interface BaseService<Record, Example> {
	long countByExample(Example example);

	int deleteByExample(Example example);

	int deleteByPrimaryKey(Integer id);

	int insert(Record record);

	int insertSelective(Record record);

	List<Record> selectByExample(Example example);

	Record selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

	int updateByExample(@Param("record") Record record, @Param("example") Example example);

	int updateByPrimaryKeySelective(Record record);

	int updateByPrimaryKey(Record record);

	void initMapper();
}
