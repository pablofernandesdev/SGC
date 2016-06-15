SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `sgc` DEFAULT CHARACTER SET utf8 ;
USE `sgc` ;

-- -----------------------------------------------------
-- Table `sgc`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`categoria` (
  `idCategoria` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`cliente` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `sexo` CHAR(1) NOT NULL,
  `nascimento` DATE NOT NULL,
  `logradouro` VARCHAR(100) NOT NULL,
  `complemento` VARCHAR(100) NOT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  `cidade` VARCHAR(50) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `cep` VARCHAR(8) NOT NULL,
  `telFixo` VARCHAR(11) NOT NULL,
  `telCel` VARCHAR(11) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`empresa` (
  `idEmpresa` INT(11) NOT NULL AUTO_INCREMENT,
  `razaoSocial` VARCHAR(100) NOT NULL,
  `nomeFantasia` VARCHAR(100) NOT NULL,
  `CNPJ` VARCHAR(14) NOT NULL,
  `logradouro` VARCHAR(100) NOT NULL,
  `complemento` VARCHAR(100) NOT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  `cidade` VARCHAR(50) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `telFixo` VARCHAR(11) NOT NULL,
  `telCel` VARCHAR(11) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idEmpresa`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`produto` (
  `idProduto` INT(11) NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(100) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `quantidade` INT(11) NOT NULL,
  `precoCusto` DOUBLE NOT NULL,
  `margemLucro` DOUBLE NOT NULL,
  `precoVenda` DOUBLE NOT NULL,
  `idCategoria` INT(11) NOT NULL,
  PRIMARY KEY (`idProduto`),
  INDEX `fk_Produto_Categoria1_idx` (`idCategoria` ASC),
  CONSTRAINT `fk_Produto_Categoria1`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `sgc`.`categoria` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`fornecedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`fornecedor` (
  `idFornecedor` INT(11) NOT NULL AUTO_INCREMENT,
  `razaoSocial` VARCHAR(100) NOT NULL,
  `nomeFantasia` VARCHAR(100) NOT NULL,
  `CNPJ` VARCHAR(14) NOT NULL,
  `logradouro` VARCHAR(100) NOT NULL,
  `complemento` VARCHAR(100) NOT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  `cidade` VARCHAR(50) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `telFixo` VARCHAR(11) NOT NULL,
  `telCel` VARCHAR(11) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idFornecedor`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`entradaproduto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`entradaproduto` (
  `idEntradaProduto` INT(11) NOT NULL AUTO_INCREMENT,
  `data` DATE NOT NULL,
  `quantidadeEntrada` INT(11) NOT NULL,
  `idProduto` INT(11) NOT NULL,
  `idFornecedor` INT(11) NULL,
  PRIMARY KEY (`idEntradaProduto`),
  INDEX `fk_entradaproduto_produto1_idx` (`idProduto` ASC),
  INDEX `fk_entradaproduto_fornecedor1_idx` (`idFornecedor` ASC),
  CONSTRAINT `fk_entradaproduto_produto1`
    FOREIGN KEY (`idProduto`)
    REFERENCES `sgc`.`produto` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_entradaproduto_fornecedor1`
    FOREIGN KEY (`idFornecedor`)
    REFERENCES `sgc`.`fornecedor` (`idFornecedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 10;


-- -----------------------------------------------------
-- Table `sgc`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `sexo` CHAR(1) NOT NULL,
  `nascimento` DATE NOT NULL,
  `status` CHAR(1) NOT NULL,
  `usuario` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(10) NOT NULL,
  `logradouro` VARCHAR(100) NOT NULL,
  `complemento` VARCHAR(100) NOT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  `cidade` VARCHAR(50) NOT NULL,
  `uf` CHAR(2) NOT NULL,
  `cep` VARCHAR(8) NOT NULL,
  `telFixo` VARCHAR(11) NOT NULL,
  `telCel` VARCHAR(11) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`venda` (
  `idVenda` INT(11) NOT NULL AUTO_INCREMENT,
  `dataVenda` DATE NOT NULL,
  `total` DOUBLE NOT NULL,
  `idUsuario` INT(11) NOT NULL,
  `idCliente` INT(11) NOT NULL,
  `idEmpresa` INT(11) NOT NULL,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_venda_usuario1_idx` (`idUsuario` ASC),
  INDEX `fk_venda_cliente1_idx` (`idCliente` ASC),
  INDEX `fk_venda_empresa1_idx` (`idEmpresa` ASC),
  CONSTRAINT `fk_venda_cliente1`
    FOREIGN KEY (`idCliente`)
    REFERENCES `sgc`.`cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venda_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `sgc`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venda_empresa1`
    FOREIGN KEY (`idEmpresa`)
    REFERENCES `sgc`.`empresa` (`idEmpresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sgc`.`saidaproduto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgc`.`saidaproduto` (
  `idSaidaProduto` INT(11) NOT NULL AUTO_INCREMENT,
  `quantidadeSaida` INT(11) NOT NULL,
  `data` DATE NOT NULL,
  `produto_idProduto` INT(11) NOT NULL,
  `venda_idVenda` INT(11) NOT NULL,
  `precoVendaSaida` DOUBLE NOT NULL,
  PRIMARY KEY (`idSaidaProduto`),
  INDEX `produto_idProduto` (`produto_idProduto` ASC),
  INDEX `venda_idVenda_idx` (`venda_idVenda` ASC),
  CONSTRAINT `saidaproduto_ibfk_2`
    FOREIGN KEY (`produto_idProduto`)
    REFERENCES `sgc`.`produto` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `venda_idVenda`
    FOREIGN KEY (`venda_idVenda`)
    REFERENCES `sgc`.`venda` (`idVenda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
