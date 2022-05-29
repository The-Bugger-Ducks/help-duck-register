package com.helpduck.helpduckusers.model.user;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.model.NullStringVerify;

public class UserUpdater {
	private NullStringVerify verifier = new NullStringVerify();

	public void updateData(User user, User updatedUser) {
		if (!verifier.verify(updatedUser.getFirstName())) {
			user.setFirstName(updatedUser.getFirstName());
		}

		if (!verifier.verify(updatedUser.getLastName())) {
			user.setLastName(updatedUser.getLastName());
		}

		if (!verifier.verify(updatedUser.getEmail())) {
			user.setEmail(updatedUser.getEmail());
		}

		if (!verifier.verify(updatedUser.getRole())) {
			user.setRole(updatedUser.getRole());
		}

		if (!verifier.verify(updatedUser.getDepartment())) {
			user.setDepartment(updatedUser.getDepartment());
		}

		// att DateTime when the user's info was updated
		user.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
	}
}
