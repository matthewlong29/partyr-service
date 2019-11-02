USE `partyrdb`;
DROP procedure IF EXISTS `get_all_chat_messages`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_all_chat_messages`()
BEGIN
	SELECT 
		`chat`.`email`,
		`chat`.`chat_message`,
		`chat`.`time_of_chat_message`
	FROM `partyrdb`.`chat` ORDER BY `chat`.`time_of_chat_message`;
END$$

DELIMITER ;
