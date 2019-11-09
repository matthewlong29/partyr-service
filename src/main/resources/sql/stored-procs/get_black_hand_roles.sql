USE `partyrdb`;
DROP procedure IF EXISTS `get_black_hand_roles`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_black_hand_roles` ()
BEGIN
  SELECT * FROM black_hand_roles;
END$$

DELIMITER ;
