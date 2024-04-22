INSERT INTO `person_attribute_type`
    (`name`, `description`, `format`, `foreign_key`, `searchable`, `creator`, `date_created`, `retired`, `sort_weight`, `uuid`)
VALUES
	('Nationality', 'The nationality of the patient.', 'java.lang.String', NULL, 0, 1, NOW(), 0, 15, '8ab9b8af-7c6c-40fb-96cf-c638f5c920b9')
ON DUPLICATE KEY UPDATE uuid=uuid;
