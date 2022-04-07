package com.helpduck.helpduckusers.model;

import org.springframework.data.domain.Page;

public interface LinkAdd<T> {
	public void addLink(Page<T> list);

	public void addLink(T object);
}