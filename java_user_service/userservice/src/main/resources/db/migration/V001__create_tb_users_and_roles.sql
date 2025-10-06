create table roles (
    roles_id SERIAL PRIMARY KEY,
    roles_name varchar(255) not null
);

create table users (
    user_id SERIAL PRIMARY KEY,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    roles_id INTEGER references roles(roles_id)
);