package com.updapy.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DozerHelper {

	@Autowired
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
}