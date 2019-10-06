USE `partyrdb`;
DROP procedure IF EXISTS `get_users`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_user`(
	IN i_user_hash VARCHAR(254),
    IN i_email VARCHAR(64),
    IN i_first_name VARCHAR(254),
    IN i_last_name VARCHAR(254),
    IN i_profile_image_url VARCHAR(254)
)
BEGIN
	INSERT INTO `partyrdb`.`partyr_users` ( 
		`user_hash`, 
        `first_name`, 
        `last_name`, 
        `email`, 
        `profile_image_url`
	) VALUES (
		i_user_hash,
        i_first_name, 
        i_last_name, 
        i_email, 
        i_profile_image_url
	);
END$$

DELIMITER ;