package com.helpduck.helpduckusers.model.user;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.model.NullStringVerify;

public class UserUpdater {
	private NullStringVerify verifier = new NullStringVerify();

	private void UpdateData(User user, User updatedUser) {
		if (!verifier.verify(updatedUser.getFirstName())) {
			user.setFirstName(updatedUser.getFirstName());
		}

		if (!verifier.verify(updatedUser.getLastName())) {
			user.setLastName(updatedUser.getLastName());
		}

		if (!verifier.verify(updatedUser.getEmail())) {
			user.setEmail(updatedUser.getEmail());
		}

		// att DateTime when the user's info was updated
		user.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
	}

	public void Update(User user, User updatedUser) {
		UpdateData(user, updatedUser);
	}
}
