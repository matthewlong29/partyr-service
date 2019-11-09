USE `partyrdb`;
DROP procedure IF EXISTS `select_username`; -- TODO change to update

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `select_username`(
  IN i_email VARCHAR(64),
  IN i_username VARCHAR(32)
)
BEGIN
  update `partyrdb`.`partyr_users` 
  set `username` = i_username where `email` = i_email;
END$$

DELIMITER ;

