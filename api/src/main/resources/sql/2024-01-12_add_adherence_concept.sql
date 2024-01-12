INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Treatment Adherence', '3', '4', '1', NOW(), '87e51329-cc96-426d-bc71-ccef8892ce72');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Treatment Adherence', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57103',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '87e51329-cc96-426d-bc71-ccef8892ce72'));