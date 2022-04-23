CREATE SEQUENCE notification_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE notification_log (
                                  id bigint PRIMARY KEY DEFAULT nextval('notification_log_seq'),
                                  name text,
                                  content text,
                                  includeExternalUserIds text,
                                  scheduleTimeOfDay text,
                                  delayedOption text,
                                  largeImage text,
                                  iosAttachments text
);
