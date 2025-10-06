CREATE TABLE usuario(
   codigo SERIAL PRIMARY KEY,
   nome VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL,
   senha VARCHAR(150) NOT NULL
);

CREATE TABLE permissao(
   codigo SERIAL PRIMARY KEY,
   descricao VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_permissao(
   codigo_usuario SERIAL NOT NULL,
   codigo_permissao SERIAL NOT NULL,
   PRIMARY KEY (codigo_usuario, codigo_permissao),
   FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
   FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);

INSERT INTO usuario (codigo, nome, email, senha) VALUES (1, 'Administrador', 'admin@crescendodigital.com', '$2a$10$Z5KaQSunzTwyxX5pgqXVL.hiyTVAWUrDUgfaj90jTtidFXGo2b.q6');
INSERT INTO usuario (codigo, nome, email, senha) VALUES (2, 'Maria Silva', 'maria@crescendodigital.com', '$2a$10$Lt8wss198.Pwdr8P7IXiLurRwaigl4JVK.aLX8u8s9hiP6DpmIpuy');

INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao (codigo, descricao) VALUES (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (8, 'ROLE_PESQUISAR_LANCAMENTO');

INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1,8);

INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2,2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2,5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2,8);

