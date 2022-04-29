package com.helpduck.helpduckusers.model;

import com.helpduck.helpduckusers.enums.RoleEnum;

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

	public boolean verify(RoleEnum role) {
		boolean nullValue = true;
		if (!(role == null)) {
			nullValue = false;
		}
		return nullValue;
	}
}