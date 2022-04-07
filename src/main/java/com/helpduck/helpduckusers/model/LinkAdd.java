package com.helpduck.helpduckusers.model;

import org.springframework.data.domain.Page;


public interface LinkAdd<T> {
	public void addLink(Page<T> lista);

	public void addLink(T object);
}