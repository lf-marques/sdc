CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `tipo` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sexo` VARCHAR(1) NOT NULL,
  `data_Nascimento` DATE NOT NULL,
  `rg` VARCHAR(9) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `rg_UNIQUE` (`rg` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_Cliente_Usuario_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Cliente_Usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Endereco` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rua` VARCHAR(255) NOT NULL,
  `numero` INT NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `uf` VARCHAR(2) NOT NULL,
  `cliente_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Endereco_Cliente1_idx` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `fk_Endereco_Cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Cartao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(16) NOT NULL,
  `data_Validade` DATE NOT NULL,
  `bloqueado` TINYINT NOT NULL,
  `cliente_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Cartao_Cliente1_idx` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `fk_Cartao_Cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Combustivel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` INT NOT NULL,
  `valor` DOUBLE NOT NULL,
  `data_Atualizacao` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Abastecimento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE NOT NULL,
  `confirmacao_Pagamento` TINYINT NOT NULL,
  `confirmacao_Abastecimento` TINYINT NOT NULL,
  `data_Validade` DATE NOT NULL,
  `placa` VARCHAR(8) NOT NULL,
  `combustivel_id` INT NOT NULL,
  `cartao_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Abastecimento_Combustivel1_idx` (`combustivel_id` ASC) VISIBLE,
  INDEX `fk_Abastecimento_Cartao1_idx` (`cartao_id` ASC) VISIBLE,
  CONSTRAINT `fk_Abastecimento_Combustivel1`
    FOREIGN KEY (`combustivel_id`)
    REFERENCES `Combustivel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Abastecimento_Cartao1`
    FOREIGN KEY (`cartao_id`)
    REFERENCES `Cartao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;