INSERT INTO usuario (nome, email, cpf, senha, ativo, data_nascimento, criado_em, editado_em)
VALUES (
           'admin',
           'admin@cwi.com.br',
           '00000000000',
           'sua senha com hash(procure por bcrypt)',
           true,
           '1990-01-01',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO permissao (nome, usuario_id)
VALUES ('ADMIN', (SELECT id FROM usuario WHERE email = 'arthur.souto@cwi.com.br'));
