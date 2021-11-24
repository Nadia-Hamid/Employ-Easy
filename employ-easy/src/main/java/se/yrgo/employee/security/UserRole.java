package se.yrgo.employee.security;

import java.util.Set;

import com.google.common.collect.Sets;

public enum UserRole {

	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(UserPermission.COURSE_READ));

	private final Set<UserPermission> permissions;

	private UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
}
