package com.tzg.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

/**
 * dozer mapper 对象反射
 *
 * @author xwb
 * @since 2016年1月15日
 */
public final class DozerMapperUtils {

	private static DozerBeanMapper mapper = new DozerBeanMapper();

	public static DozerBeanMapper getInstance() {
		return mapper;
	}

	public static <T> T map(Object object, Class<T> destinationClass) {
		return mapper.map(object, destinationClass);
	}

	public static void map(Object source, Object destination) {
		mapper.map(source, destination);
	}

	public static <T> T map(Object source, Class<T> destinationClass, String mapId) {
		return mapper.map(source, destinationClass, mapId);
	}

	/**
	 * 
	 * list对象转换
	 *
	 * @param desClass
	 *            需要转换的list泛型类
	 * @param souList
	 *            源list
	 * @return
	 */
	public static <T1, T2> List<T1> mapList(Class<T1> desClass, List<T2> souList) {
		List<T1> desList = new ArrayList<T1>();
		if (souList == null || souList.size() == 0)
			return desList;

		for (T2 source : souList) {
			T1 desObject = map(source, desClass);
			if (desObject != null)
				desList.add(desObject);
		}
		return desList;
	}
}
