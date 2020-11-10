INSERT INTO `usuario` (`id`, `nome`, `cpf`, `senha`, `tipo`) VALUES
(DEFAULT, 'Usuario Cliente', '14848682002', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', 0);

INSERT INTO `usuario` (`id`, `nome`, `cpf`, `senha`, `tipo`) VALUES
(DEFAULT, 'Usuario Funcionario', '27546823099', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', 1);

INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_CLIENTE', 'Acesso somento a métodos destinados a clietes', TRUE);

INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_FUNCIONARIO', 'Acesso somente a métodos destinados a funcionários', TRUE);

INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE cpf = '14848682002'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_CLIENTE')
);

INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE cpf = '27546823099'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_FUNCIONARIO')
);