CREATE TABLE user_email(
   user_id SERIAL PRIMARY KEY,
   email VARCHAR(50),
   password VARCHAR(20)
);

INSERT INTO user_email (email, password) VALUES ('rivaldosouzateste@outlook.com', 'Riva@2010');
