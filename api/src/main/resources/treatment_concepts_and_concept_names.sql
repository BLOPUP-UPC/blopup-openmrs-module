INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Medication Name', '3', '4', '1', NOW(), 'a721776b-fd0f-41ea-821b-0d0df94d5560');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Medication Name', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57193',
        (SELECT concept_id
         FROM concept
         WHERE uuid = 'a721776b-fd0f-41ea-821b-0d0df94d5560'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Recommended By', '3', '4', '1', NOW(), 'c1164da7-0b4f-490f-85da-0c4aac4ca8a1');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Recommended By', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57194',
        (SELECT concept_id
         FROM concept
         WHERE uuid = 'c1164da7-0b4f-490f-85da-0c4aac4ca8a1'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Active', '3', '4', '1', NOW(), '81f60010-961e-4bc5-aa04-435c7ace1ee3');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Active', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57195',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '81f60010-961e-4bc5-aa04-435c7ace1ee3'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Treatment Notes', '3', '4', '1', NOW(), 'dfa881a4-5c88-4057-958b-f583c8edbdef');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Treatment Notes', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57196',
        (SELECT concept_id
         FROM concept
         WHERE uuid = 'dfa881a4-5c88-4057-958b-f583c8edbdef'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Medication Type', '3', '4', '1', NOW(), '1a8f49cc-488b-4788-adb3-72c499108772');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Medication Type', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57197',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '1a8f49cc-488b-4788-adb3-72c499108772'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('ACE Inhibitor', '10', '4', '1', NOW(), '467f7d87-8c2e-4519-9e81-048c2c7824fd');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('ACE Inhibitor', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57198',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '467f7d87-8c2e-4519-9e81-048c2c7824fd'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Diuretic', '10', '4', '1', NOW(), 'a7fa1f5f-1ca3-4fe4-b02b-bd1dcc90201b');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Diuretic', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57199',
        (SELECT concept_id
         FROM concept
         WHERE uuid = 'a7fa1f5f-1ca3-4fe4-b02b-bd1dcc90201b'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Calcium Channel Blocker', '10', '4', '1', NOW(), '2146fbb8-8a8a-44f5-81de-2bee8ec4edce');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Calcium Channel Blocker', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57100',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '2146fbb8-8a8a-44f5-81de-2bee8ec4edce'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('Beta Blocker', '10', '4', '1', NOW(), 'f2c7ec86-6fe0-4e6a-bfe9-c73380228177');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('Beta Blocker', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57101',
        (SELECT concept_id
         FROM concept
         WHERE uuid = 'f2c7ec86-6fe0-4e6a-bfe9-c73380228177'));

INSERT INTO concept (description,datatype_id,class_id,creator,date_created,uuid)
VALUES ('ARA II', '10', '4', '1', NOW(), '87e51329-cc96-426d-bc71-ccef8892ce71');

INSERT INTO concept_name (name, locale, locale_preferred, concept_name_type, creator, date_created, uuid, concept_id)
VALUES ('ARA II', 'en', '1', 'FULLY_SPECIFIED', '1', NOW(), '0bb104e6-c1bb-4896-ae34-f594eae57102',
        (SELECT concept_id
         FROM concept
         WHERE uuid = '87e51329-cc96-426d-bc71-ccef8892ce71'));
