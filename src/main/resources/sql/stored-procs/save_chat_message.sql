USE `partyrdb`;
DROP procedure IF EXISTS `save_chat_message`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `save_chat_message` (
	IN i_email VARCHAR(32),
  IN i_message VARCHAR(512),
  IN i_time_of_message TIMESTAMP
)
BEGIN
	INSERT INTO `partyrdb`.`chat`(
		`email`,
		`message`,
		`time_of_message`
	) VALUES (
		i_email,
		i_message,
		i_time_of_message
	);
END$$

DELIMITER ;
