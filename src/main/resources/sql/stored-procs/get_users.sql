USE `partyrdb`;
DROP procedure IF EXISTS `get_users`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_users` (
  IN i_query_string VARCHAR(255)
)
BEGIN
	SELECT * FROM `partyrdb`.`partyr_users` WHERE
    username LIKE concat('%', i_query_string, '%') or 
    first_name LIKE concat('%', i_query_string, '%') or 
    last_name LIKE concat('%', i_query_string, '%') or 
    email LIKE concat('%', i_query_string, '%')
  ORDER BY online_status desc, first_name;
END$$

DELIMITER ;