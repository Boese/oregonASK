-- MySQL Script generated by MySQL Workbench
-- Thu Oct 23 13:13:33 2014
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema OREGONASKDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `OREGONASKDB` ;

-- -----------------------------------------------------
-- Schema OREGONASKDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `OREGONASKDB` DEFAULT CHARACTER SET utf8 ;
USE `OREGONASKDB` ;

-- -----------------------------------------------------
-- Table `OREGONASKDB`.`NUTRITION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`NUTRITION` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`NUTRITION` (
  `nutrition_id` INT(11) NOT NULL AUTO_INCREMENT,
  `nutrition_info` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`nutrition_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SCHOOL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SCHOOL` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SCHOOL` (
  `SCHOOL_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(250) NOT NULL,
  `ELEMENTARY` TINYINT(1) NULL DEFAULT '0',
  `MIDDLE` TINYINT(1) NULL DEFAULT '0',
  `HIGH` TINYINT(1) NULL DEFAULT '0',
  `CITY` VARCHAR(45) NULL DEFAULT NULL,
  `STATE` VARCHAR(45) NULL DEFAULT NULL,
  `ZIP` VARCHAR(45) NULL DEFAULT NULL,
  `STREET` VARCHAR(45) NULL DEFAULT NULL,
  `CONGRESSIONAL_DISTRICT` VARCHAR(45) NULL DEFAULT NULL,
  `COUNTY` VARCHAR(45) NULL DEFAULT NULL,
  `SCHOOL_DISTRICT` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`SCHOOL_ID`),
  UNIQUE INDEX `SCHOOL_ID_UNIQUE` (`SCHOOL_ID` ASC),
  UNIQUE INDEX `SCHOOL_UN_IN` (`NAME` ASC, `COUNTY` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 29100
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SPONSOR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SPONSOR` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SPONSOR` (
  `sponsor_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `agr_number` VARCHAR(45) NULL DEFAULT NULL,
  `sponsor_type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`sponsor_id`),
  UNIQUE INDEX `PROVIDER_ID_UNIQUE` (`sponsor_id` ASC),
  UNIQUE INDEX `PROVIDER_UN_IN` (`name` ASC),
  UNIQUE INDEX `agr_number_UNIQUE` (`agr_number` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 12482
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`NUTRITION_LINK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`NUTRITION_LINK` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`NUTRITION_LINK` (
  `nutrition_link_id` INT(11) NOT NULL AUTO_INCREMENT,
  `school_id` INT(11) NULL DEFAULT NULL,
  `sponsor_id` INT(11) NULL DEFAULT NULL,
  `nutrition_id` INT(11) NOT NULL,
  `year` INT(11) NOT NULL,
  PRIMARY KEY (`nutrition_link_id`),
  UNIQUE INDEX `nutrition_link_un` (`nutrition_id` ASC, `year` ASC),
  INDEX `fk_NUTRITION_LINK_NUTRITION1_idx` (`nutrition_id` ASC),
  INDEX `fk_NUTRITION_LINK_SPONSOR1_idx` (`sponsor_id` ASC),
  INDEX `fk_NUTRITION_LINK_SCHOOL1_idx` (`school_id` ASC),
  CONSTRAINT `fk_NUTRITION_LINK_NUTRITION1`
    FOREIGN KEY (`nutrition_id`)
    REFERENCES `OREGONASKDB`.`NUTRITION` (`nutrition_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NUTRITION_LINK_SCHOOL1`
    FOREIGN KEY (`school_id`)
    REFERENCES `OREGONASKDB`.`SCHOOL` (`SCHOOL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NUTRITION_LINK_SPONSOR1`
    FOREIGN KEY (`sponsor_id`)
    REFERENCES `OREGONASKDB`.`SPONSOR` (`sponsor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`PROGRAM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`PROGRAM` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`PROGRAM` (
  `PROGRAM_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(250) NOT NULL,
  `LICENSE_NUMBER` VARCHAR(45) NULL DEFAULT NULL,
  `NOTES` VARCHAR(1000) NULL DEFAULT NULL,
  `CONTACT_NAME` VARCHAR(1000) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(250) NULL DEFAULT NULL,
  `PHONE` VARCHAR(45) NULL DEFAULT NULL,
  `CITY` VARCHAR(45) NULL DEFAULT NULL,
  `STATE` VARCHAR(45) NULL DEFAULT NULL,
  `ZIP` VARCHAR(45) NULL DEFAULT NULL,
  `STREET` VARCHAR(45) NULL DEFAULT NULL,
  `COUNTY` VARCHAR(45) NULL DEFAULT NULL,
  `SCHOOL_SERVED` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_ID`),
  UNIQUE INDEX `PROGRAM_UN_ID` (`NAME` ASC, `CITY` ASC, `SCHOOL_SERVED` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 41321
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`PROGRAM_INFO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`PROGRAM_INFO` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`PROGRAM_INFO` (
  `PROGRAM_INFO_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `AVERAGE_DAILY_ATTENDANCE` VARCHAR(45) NULL DEFAULT NULL,
  `MAX_CAPACITY` VARCHAR(45) NULL DEFAULT NULL,
  `OFFERED_BEFORE_SCHOOL` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_AFTER_SCHOOL` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_MONDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_TUESDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_WEDNESDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_THURSDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_FRIDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_SATURDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_SUNDAY` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_WEEKENDS` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_EVENINGS` TINYINT(1) NULL DEFAULT NULL,
  `OFFERED_BREAKS` TINYINT(1) NULL DEFAULT NULL,
  `AVERAGE_HOURS_PER_WEEK` VARCHAR(45) NULL DEFAULT NULL,
  `FOOD_PROVIDED_BEFORE_SCHOOL` TINYINT(1) NULL DEFAULT NULL,
  `FOOD_PROVIDED_AFTER_SCHOOL` TINYINT(1) NULL DEFAULT NULL,
  `FOOD_PROVIDED_DURING_BREAKS` TINYINT(1) NULL DEFAULT NULL,
  `STEM_OFFERED` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`PROGRAM_INFO_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 18256
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`PROGRAM_INFO_BY_YEAR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`PROGRAM_INFO_BY_YEAR` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`PROGRAM_INFO_BY_YEAR` (
  `PROGRAM_INFO_BY_YEAR_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `YEAR` INT(11) NOT NULL,
  `PROGRAM_ID` INT(11) NOT NULL,
  `PROGRAM_INFO_ID` INT(11) NOT NULL,
  PRIMARY KEY (`PROGRAM_INFO_BY_YEAR_ID`),
  UNIQUE INDEX `PROGRAM_INFO_UN_ID` (`YEAR` ASC, `PROGRAM_ID` ASC),
  INDEX `FK_PROGRAM_INFO_BY_YEAR_PROGRAM_INFO1_IDX` (`PROGRAM_INFO_ID` ASC),
  INDEX `FK_PROGRAM_INFO_BY_YEAR_PROGRAM1_IDX` (`PROGRAM_ID` ASC),
  CONSTRAINT `FK_PROGRAM_INFO_BY_YEAR_PROGRAM1`
    FOREIGN KEY (`PROGRAM_ID`)
    REFERENCES `OREGONASKDB`.`PROGRAM` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROGRAM_INFO_BY_YEAR_PROGRAM_INFO1`
    FOREIGN KEY (`PROGRAM_INFO_ID`)
    REFERENCES `OREGONASKDB`.`PROGRAM_INFO` (`PROGRAM_INFO_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 15057
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SCHOOL_INFO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SCHOOL_INFO` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SCHOOL_INFO` (
  `SCHOOL_INFO_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TOTAL_STUDENT_COUNT` VARCHAR(45) NULL DEFAULT NULL,
  `FREE_LUNCH_COUNT` VARCHAR(45) NULL DEFAULT NULL,
  `REDUCED_LUNCH_COUNT` VARCHAR(45) NULL DEFAULT NULL,
  `PERCENT_MEET_READING` VARCHAR(45) NULL DEFAULT NULL,
  `PERCENT_MINORITY` VARCHAR(45) NULL DEFAULT NULL,
  `PERCENT_WHITE` VARCHAR(45) NULL DEFAULT NULL,
  `PERCENT_FR` VARCHAR(45) NULL DEFAULT NULL,
  `LO_GRADE_OFFERED` VARCHAR(45) NULL DEFAULT NULL,
  `HI_GRADE_OFFERED` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`SCHOOL_INFO_ID`),
  UNIQUE INDEX `SCHOOL_INFO_ID_UNIQUE` (`SCHOOL_INFO_ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 17623
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SCHOOL_INFO_BY_YEAR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SCHOOL_INFO_BY_YEAR` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SCHOOL_INFO_BY_YEAR` (
  `SCHOOL_INFO_BY_YEAR_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `YEAR` INT(11) NOT NULL,
  `SCHOOL_ID` INT(11) NOT NULL,
  `SCHOOL_INFO_ID` INT(11) NOT NULL,
  PRIMARY KEY (`SCHOOL_INFO_BY_YEAR_ID`),
  UNIQUE INDEX `SCHOOL_INFO_BY_YEAR_ID_UNIQUE` (`SCHOOL_INFO_BY_YEAR_ID` ASC),
  UNIQUE INDEX `SCHOOL_INFO_UN_ID` (`YEAR` ASC, `SCHOOL_ID` ASC),
  INDEX `FK_SCHOOL_INFO_BY_YEAR_SCHOOL1_IDX` (`SCHOOL_ID` ASC),
  INDEX `FK_SCHOOL_INFO_BY_YEAR_SCHOOL_INFO1_IDX` (`SCHOOL_INFO_ID` ASC),
  CONSTRAINT `FK_SCHOOL_INFO_BY_YEAR_SCHOOL1`
    FOREIGN KEY (`SCHOOL_ID`)
    REFERENCES `OREGONASKDB`.`SCHOOL` (`SCHOOL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_SCHOOL_INFO_BY_YEAR_SCHOOL_INFO1`
    FOREIGN KEY (`SCHOOL_INFO_ID`)
    REFERENCES `OREGONASKDB`.`SCHOOL_INFO` (`SCHOOL_INFO_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 17533
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SUMMERFOOD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SUMMERFOOD` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SUMMERFOOD` (
  `summerfood_id` INT(11) NOT NULL AUTO_INCREMENT,
  `site_name` VARCHAR(250) NOT NULL,
  `site_number` VARCHAR(45) NOT NULL,
  `street` VARCHAR(250) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `zip` VARCHAR(45) NULL DEFAULT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  `county` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`summerfood_id`),
  UNIQUE INDEX `site_number_UNIQUE` (`site_number` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1607
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`SUMMERFOOD_LINK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`SUMMERFOOD_LINK` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`SUMMERFOOD_LINK` (
  `summerfood_link_id` INT(11) NOT NULL AUTO_INCREMENT,
  `school_id` INT(11) NULL DEFAULT NULL,
  `sponsor_id` INT(11) NULL DEFAULT NULL,
  `summerfood_id` INT(11) NOT NULL,
  `year` INT(11) NOT NULL,
  PRIMARY KEY (`summerfood_link_id`),
  UNIQUE INDEX `summerfood_un` (`summerfood_id` ASC, `year` ASC),
  INDEX `fk_SUMMERFOOD_LINK_SUMMERFOOD1_idx` (`summerfood_id` ASC),
  INDEX `fk_SUMMERFOOD_LINK_SPONSOR1_idx` (`sponsor_id` ASC),
  INDEX `fk_SUMMERFOOD_LINK_SCHOOL1_idx` (`school_id` ASC),
  CONSTRAINT `fk_SUMMERFOOD_LINK_SCHOOL1`
    FOREIGN KEY (`school_id`)
    REFERENCES `OREGONASKDB`.`SCHOOL` (`SCHOOL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SUMMERFOOD_LINK_SPONSOR1`
    FOREIGN KEY (`sponsor_id`)
    REFERENCES `OREGONASKDB`.`SPONSOR` (`sponsor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SUMMERFOOD_LINK_SUMMERFOOD1`
    FOREIGN KEY (`summerfood_id`)
    REFERENCES `OREGONASKDB`.`SUMMERFOOD` (`summerfood_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5624
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `OREGONASKDB`.`YMCACW_LINK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OREGONASKDB`.`YMCACW_LINK` ;

CREATE TABLE IF NOT EXISTS `OREGONASKDB`.`YMCACW_LINK` (
  `ymcacw_link` INT(11) NOT NULL AUTO_INCREMENT,
  `school_id` INT(11) NULL DEFAULT NULL,
  `sponsor_id` INT(11) NULL DEFAULT NULL,
  `program_id` INT(11) NOT NULL,
  PRIMARY KEY (`ymcacw_link`),
  UNIQUE INDEX `program_id_UNIQUE` (`program_id` ASC),
  INDEX `PROGRAM_ID_FK_IDX` (`program_id` ASC),
  INDEX `fk_YMCACW_LINK_SPONSOR1_idx` (`sponsor_id` ASC),
  INDEX `fk_YMCACW_LINK_SCHOOL1_idx` (`school_id` ASC),
  CONSTRAINT `FK_PROGRAM_ID`
    FOREIGN KEY (`program_id`)
    REFERENCES `OREGONASKDB`.`PROGRAM` (`PROGRAM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_YMCACW_LINK_SCHOOL1`
    FOREIGN KEY (`school_id`)
    REFERENCES `OREGONASKDB`.`SCHOOL` (`SCHOOL_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_YMCACW_LINK_SPONSOR1`
    FOREIGN KEY (`sponsor_id`)
    REFERENCES `OREGONASKDB`.`SPONSOR` (`sponsor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 45175
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
