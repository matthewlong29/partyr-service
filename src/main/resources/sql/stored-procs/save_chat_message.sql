USE `partyrdb`;
DROP procedure IF EXISTS `save_chat_message`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `save_chat_message`(
  IN i_username VARCHAR(32),
  IN i_chat_message VARCHAR(512),
  IN i_time_of_chat_message TIMESTAMP
)
BEGIN
  INSERT INTO `partyrdb`.`chat`(
    `username`,
    `chat_message`,
    `time_of_chat_message`
  ) VALUES (
    i_username,
    i_chat_message,
    i_time_of_chat_message
  );
END$$

DELIMITER ;
