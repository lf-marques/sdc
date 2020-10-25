CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `tipo` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Cliente` (
  `sexo` VARCHAR(1) NOT NULL,
  `data_Nascimento` DATE NOT NULL,
  `rg` VARCHAR(9) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_Cliente_Usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `Usuario` (`id`)
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
    REFERENCES `Cliente` (`usuario_id`)
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
  `cartao_id` INT NOT NULL,
  `combustivel_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Abastecimento_Cartao1_idx` (`cartao_id` ASC) VISIBLE,
  INDEX `fk_Abastecimento_Combustivel1_idx` (`combustivel_id` ASC) VISIBLE,
  CONSTRAINT `fk_Abastecimento_Cartao1`
    FOREIGN KEY (`cartao_id`)
    REFERENCES `Cartao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Abastecimento_Combustivel1`
    FOREIGN KEY (`combustivel_id`)
    REFERENCES `Combustivel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;