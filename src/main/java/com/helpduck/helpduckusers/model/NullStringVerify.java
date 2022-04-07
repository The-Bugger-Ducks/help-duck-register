package com.helpduck.helpduckusers.model;

public class NullStringVerify {

	public boolean verify(String data) {
		boolean nullValue = true;
		if (!(data == null)) {
			if (!data.isBlank()) {
				nullValue = false;
			}
		}
		return nullValue;
	}
}