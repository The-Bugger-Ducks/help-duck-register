package com.helpduck.helpduckusers.model.user;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.model.NullStringVerify;

public class UserUpdater {
	private NullStringVerify verifier = new NullStringVerify();

	private void UpdateData(User client, User updatedClient) {
		if (!verifier.verify(updatedClient.getFirstName())) {
			client.setFirstName(updatedClient.getFirstName());
		}

		if (!verifier.verify(updatedClient.getLastName())) {
			client.setLastName(updatedClient.getLastName());
		}

		if (!verifier.verify(updatedClient.getEmail())) {
			client.setEmail(updatedClient.getEmail());
		}

		
		client.setGender(updatedClient.getGender());

		// att DateTime when the user's info was updated
		client.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
	} 

	public void Update(User client, User updatedClient) {
		UpdateData(client, updatedClient);
	}
}
