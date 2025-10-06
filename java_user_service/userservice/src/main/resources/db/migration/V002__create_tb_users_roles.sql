create table users_roles (
	user_id INTEGER not null references users(user_id),
	role_id INTEGER not null references roles(roles_id)
);