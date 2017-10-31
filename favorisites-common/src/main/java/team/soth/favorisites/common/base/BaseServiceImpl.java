package team.soth.favorisites.common.base;


import team.soth.favorisites.common.util.SpringContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by thinkam on 17-10-31.
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {
	public Mapper mapper;

	@Override
	public long countByExample(Example example) {
		try {
			Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
			Object result = countByExample.invoke(mapper, example.getClass());
			return Long.parseLong(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteByExample(Example example) {
		try {
			Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
			Object result = deleteByExample.invoke(mapper, example.getClass());
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		try {
			Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
			Object result = deleteByPrimaryKey.invoke(mapper, id);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insert(Record record) {
		try {
			Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
			Object result = insert.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertSelective(Record record) {
		try {
			Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
			Object result = insertSelective.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Record> selectByExample(Example example) {
		try {
			Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
			Object result = selectByExample.invoke(mapper, example);
			return (List<Record>) result;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Record selectByPrimaryKey(Integer id) {
		try {
			Method selectByPrimaryKey = mapper.getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
			Object result = selectByPrimaryKey.invoke(mapper, id);
			return (Record) result;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateByExampleSelective(Record record, Example example) {
		try {
			Method updateByExampleSelective = mapper.getClass().getDeclaredMethod("updateByExampleSelective", record.getClass(), example.getClass());
			Object result = updateByExampleSelective.invoke(mapper, record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateByExample(Record record, Example example) {
		try {
			Method updateByExample = mapper.getClass().getDeclaredMethod("updateByExample", record.getClass(), example.getClass());
			Object result = updateByExample.invoke(mapper, record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Record record) {
		try {
			Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
			Object result = updateByPrimaryKeySelective.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Record record) {
		try {
			Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
			Object result = updateByPrimaryKey.invoke(mapper, record);
			return Integer.parseInt(String.valueOf(result));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void initMapper() {
		this.mapper = SpringContextUtil.getBean(getMapperClass());
	}

	/**
	 * 获取类泛型class
	 *
	 * @return
	 */
	public Class<Mapper> getMapperClass() {
		return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
