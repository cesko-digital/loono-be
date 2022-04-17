ALTER TABLE server_properties
    ADD COLUMN supported_app_version character varying(255) NOT NULL;

ALTER TABLE server_properties_aud
    ADD COLUMN supported_app_version character varying(255);
