USE `partyrdb`;
DROP procedure IF EXISTS `get_all_messages`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_all_messages` ()
BEGIN
	SELECT `chat`.`chat_id`,
		`chat`.`email`,
		`chat`.`message`,
		`chat`.`time_of_message`
	FROM `partyrdb`.`chat` ORDER BY `chat`.`time_of_message`;
END$$

DELIMITER ;
