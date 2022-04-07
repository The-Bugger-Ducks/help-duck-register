package com.helpduck.helpduckusers.model.user;

import com.helpduck.helpduckusers.controller.UserController;
import com.helpduck.helpduckusers.model.LinkAdd;
import com.helpduck.helpduckusers.model.hateoas.UserHateoas;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;


@Component
public class UserLinkAdder implements LinkAdd<UserHateoas> {

	@Override
	public void addLink(Page<UserHateoas> list) {
		for (UserHateoas user : list) {
			String id = user.getId();
			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UserController.class)
							.getUser(id))
					.withSelfRel();
			user.add(selfLink);
		}
	}

	@Override
	public void addLink(UserHateoas object) {
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UserController.class)
						.getUsers(null))
				.withRel("users");
		object.add(selfLink);
	}
}