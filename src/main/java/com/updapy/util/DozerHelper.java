package com.updapy.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component
public class DozerHelper {

	@Inject
	private DozerBeanMapper dozerMapper;

	public <T, U> ArrayList<U> map(List<T> source, Class<U> destType) {
		ArrayList<U> dest = new ArrayList<U>();
		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(dozerMapper.map(element, destType));
		}
		return dest;
	}

	public <T> T map(Object source, Class<T> destinationClass) {
		return dozerMapper.map(source, destinationClass);
	}

	public <T, U> ArrayList<U> mapWithPropertyContext(List<T> source, Class<U> destType, String property) {
		ArrayList<U> dest = new ArrayList<U>();
		for (T element : source) {
			if (element == null) {
				continue;
			}
			try {
				String contextValue = BeanUtils.getProperty(element, property);
				dest.add(dozerMapper.map(element, destType, contextValue));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException("Property not found on bean by introspection!" + e.getMessage());
			}
		}
		return dest;
	}
}