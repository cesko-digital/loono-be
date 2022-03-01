CREATE TABLE account_aud (
                             id bigint NOT NULL,
                             rev integer NOT NULL,
                             revtype smallint,
                             appointment_reminder_emails_opt_in boolean,
                             birthdate date,
                             leaderboard_anonymization_opt_in boolean,
                             newsletter_opt_in boolean,
                             nickname text,
                             points integer,
                             preferred_email text,
                             profile_image_url text,
                             sex text,
                             uid text
);

CREATE TABLE badge_aud (
                           account_id bigint NOT NULL,
                           type character varying(255) NOT NULL,
                           rev integer NOT NULL,
                           revtype smallint,
                           last_update_on timestamp without time zone,
                           level integer
);

CREATE TABLE examination_record_aud (
                                        id bigint NOT NULL,
                                        rev integer NOT NULL,
                                        revtype smallint,
                                        first_exam boolean,
                                        planned_date timestamp without time zone,
                                        status text,
                                        type text,
                                        uuid text,
                                        account_id bigint
);

CREATE TABLE healthcare_category_aud (
                                         id bigint NOT NULL,
                                         rev integer NOT NULL,
                                         revtype smallint,
                                         value text
);

CREATE TABLE healthcare_provider_aud (
                                         institution_id bigint NOT NULL,
                                         location_id bigint NOT NULL,
                                         rev integer NOT NULL,
                                         revtype smallint,
                                         administrative_district text,
                                         care_form text,
                                         care_type text,
                                         city text,
                                         code text,
                                         district text,
                                         district_code text,
                                         email text,
                                         fax text,
                                         house_number text,
                                         hq_city text,
                                         hq_district text,
                                         hq_district_code text,
                                         hq_house_number text,
                                         hq_postal_code text,
                                         hq_region text,
                                         hq_region_code text,
                                         hq_street text,
                                         ico text,
                                         institution_type text,
                                         lat double precision,
                                         lawyer_form_code text,
                                         layer_form text,
                                         lng double precision,
                                         person_type text,
                                         person_type_code text,
                                         phone_number text,
                                         postal_code text,
                                         region text,
                                         region_code text,
                                         specialization text,
                                         street text,
                                         substitute text,
                                         title text,
                                         website text
);

CREATE TABLE healthcare_provider_category_aud (
                                                  rev integer NOT NULL,
                                                  institution_id bigint NOT NULL,
                                                  location_id bigint NOT NULL,
                                                  id bigint NOT NULL,
                                                  revtype smallint
);

CREATE TABLE selfexamination_record_aud (
                                            id bigint NOT NULL,
                                            rev integer NOT NULL,
                                            revtype smallint,
                                            due_date date,
                                            result text,
                                            status text,
                                            type text,
                                            uuid text,
                                            waiting_to date,
                                            account_id bigint
);

ALTER TABLE ONLY account_aud
    ADD CONSTRAINT account_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY badge_aud
    ADD CONSTRAINT badge_aud_pkey PRIMARY KEY (account_id, type, rev);

ALTER TABLE ONLY examination_record_aud
    ADD CONSTRAINT examination_record_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY healthcare_category_aud
    ADD CONSTRAINT healthcare_category_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY healthcare_provider_aud
    ADD CONSTRAINT healthcare_provider_aud_pkey PRIMARY KEY (institution_id, location_id, rev);

ALTER TABLE ONLY healthcare_provider_category_aud
    ADD CONSTRAINT healthcare_provider_category_aud_pkey PRIMARY KEY (rev, institution_id, location_id, id);

ALTER TABLE ONLY selfexamination_record_aud
    ADD CONSTRAINT selfexamination_record_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY examination_record_aud
    ADD CONSTRAINT fk5lxlg92fn2q6mul14ly8n5fbr FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY account_aud
    ADD CONSTRAINT fkaexie5n0kol2mjlvo03ii45d0 FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY selfexamination_record_aud
    ADD CONSTRAINT fkg1uyxqsvkobw6dnx74h1nj2x2 FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY healthcare_provider_category_aud
    ADD CONSTRAINT fkivi6tglk4kbdxq4sa25n6s43f FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY healthcare_category_aud
    ADD CONSTRAINT fkj0f3nuqp8iv9ms2w6h6025soe FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY badge_aud
    ADD CONSTRAINT fkk8lubwsfxvhhsm8ttaou0m0yt FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY healthcare_provider_aud
    ADD CONSTRAINT fkpwg1gu7dho1u49ie27xc4jif8 FOREIGN KEY (rev) REFERENCES revinfo(rev);
