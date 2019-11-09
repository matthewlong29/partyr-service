USE `partyrdb`;
DROP procedure IF EXISTS `get_online_users`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_online_users`(
  IN i_query_string VARCHAR(255)
)
BEGIN
	SELECT * FROM partyr_users WHERE 
      online_status = 'ONLINE' AND (
        username LIKE concat('%', i_query_string, '%') or 
        first_name LIKE concat('%', i_query_string, '%') or 
        last_name LIKE concat('%', i_query_string, '%') or 
        email LIKE concat('%', i_query_string, '%')
      )
    ORDER BY first_name;
END$$

DELIMITER ;
