USE `partyrdb`;
DROP procedure IF EXISTS `get_black_hand_roles`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_black_hand_roles` ()
BEGIN
  SELECT 
    `black_hand_roles`.`role_id`,
    `black_hand_roles`.`faction`,
    `black_hand_roles`.`role_name`,
    `black_hand_roles`.`day_ability_description`,
    `black_hand_roles`.`night_ability_description`,
    `black_hand_roles`.`attribute_description`,
    `black_hand_roles`.`goal_description`
  FROM black_hand_roles;
END$$

DELIMITER ;
