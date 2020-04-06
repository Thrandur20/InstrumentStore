DROP TABLE IF EXISTS STORE CASCADE;
DROP TABLE IF EXISTS VENDOR CASCADE;
DROP TABLE IF EXISTS INSTRUMENT CASCADE;
DROP SEQUENCE IF EXISTS SQ_VENDOR;
DROP SEQUENCE IF EXISTS SQ_INSTRUMENT;
DROP SEQUENCE IF EXISTS SQ_STORE;

CREATE SEQUENCE SQ_VENDOR START WITH 1 INCREMENT BY 50;

CREATE TABLE VENDOR(
    VENDOR_ID BIGINT DEFAULT SQ_VENDOR.nextval PRIMARY KEY,
    VENDOR_NAME VARCHAR(256) NOT NULL
);

CREATE SEQUENCE SQ_INSTRUMENT START WITH 1 INCREMENT BY 50;

CREATE TABLE INSTRUMENT(
    INSTRUMENT_ID BIGINT DEFAULT SQ_INSTRUMENT.nextval PRIMARY KEY,
    INSTRUMENT_NAME VARCHAR(256) NOT NULL
);

CREATE SEQUENCE SQ_STORE START WITH 1 INCREMENT BY 50;

CREATE TABLE STORE (
    STORE_ID BIGINT DEFAULT SQ_STORE.nextval PRIMARY KEY,
    PRICE DECIMAL(15,3) NOT NULL,
    VENDOR_ID BIGINT NOT NULL
    REFERENCES VENDOR(VENDOR_ID),
    INSTRUMENT_ID BIGINT NOT NULL
    REFERENCES INSTRUMENT(INSTRUMENT_ID),
    ENTRY_DATE TIMESTAMP NOT NULL
);


INSERT INTO VENDOR(VENDOR_NAME) VALUES
('Amazon'),
('Google'),
('Apple'),
('Facebook'),
('IBM'),
('Microsoft');


INSERT INTO INSTRUMENT(INSTRUMENT_NAME) VALUES
('Instrument1'),
('Instrument2'),
('Instrument3'),
('Instrument4'),
('Instrument5'),
('Instrument6'),
('Instrument7'),
('Instrument8'),
('Instrument9'),
('Instrument10');

INSERT INTO STORE(PRICE, VENDOR_ID, INSTRUMENT_ID, ENTRY_DATE) VALUES
(51417.813, 1, 1, parsedateTime('20191205', 'yyyyMMdd')),
(11097556.729, 1, 51, parsedateTime('20191209', 'yyyyMMdd')),
(37461676.461, 1, 101, parsedateTime('20191217', 'yyyyMMdd')),
(81881348.701, 1, 151, parsedateTime('20191220', 'yyyyMMdd')),
(93077297.973, 1, 201, parsedateTime('20191227', 'yyyyMMdd')),
(99197295.945, 1, 251, parsedateTime('20200101', 'yyyyMMdd')),
(101872690.093, 1, 301, parsedateTime('20200107', 'yyyyMMdd')),
(117326145.031, 1, 351, parsedateTime('20200115', 'yyyyMMdd')),
(119196064.302, 1, 401, parsedateTime('20200124', 'yyyyMMdd')),
(133917464.181, 1, 451, parsedateTime('20200128', 'yyyyMMdd')),
(139308652.530, 51, 1, parsedateTime('20200203', 'yyyyMMdd')),
(143681947.917, 51, 51, parsedateTime('20200210', 'yyyyMMdd')),
(158719406.216, 51, 101, parsedateTime('20200220', 'yyyyMMdd')),
(167671045.376, 51, 151, parsedateTime('20200225', 'yyyyMMdd')),
(176474927.169, 51, 201, parsedateTime('20200303', 'yyyyMMdd')),
(182683510.359, 51, 251, parsedateTime('20200304', 'yyyyMMdd')),
(224118187.556, 51, 301, parsedateTime('20200312', 'yyyyMMdd')),
(257073252.057, 51, 351, parsedateTime('20200313', 'yyyyMMdd')),
(261570198.212, 51, 401, parsedateTime('20200316', 'yyyyMMdd')),
(267028997.073, 51, 451, parsedateTime('20200320', 'yyyyMMdd')),
(287261769.698, 101, 1, parsedateTime('20200324', 'yyyyMMdd')),
(310104041.793, 101, 51, parsedateTime('20200325', 'yyyyMMdd')),
(311379427.577, 101, 101, parsedateTime('20200330', 'yyyyMMdd')),
(333543151.171, 101, 151, parsedateTime('20200401', 'yyyyMMdd')),
(350856762.190, 101, 201, parsedateTime('20200403', 'yyyyMMdd')),
(358271928.636, 101, 251, parsedateTime('20191206', 'yyyyMMdd')),
(360042971.058, 101, 301, parsedateTime('20191209', 'yyyyMMdd')),
(374366384.620, 101, 351, parsedateTime('20191216', 'yyyyMMdd')),
(375723229.969, 101, 401, parsedateTime('20191223', 'yyyyMMdd')),
(386733472.715, 101, 451, parsedateTime('20200101', 'yyyyMMdd')),
(411703103.715, 151, 1, parsedateTime('20200110', 'yyyyMMdd')),
(416497534.443, 151, 51, parsedateTime('20200120', 'yyyyMMdd')),
(430802164.680, 151, 101, parsedateTime('20200122', 'yyyyMMdd')),
(443677840.248, 151, 151, parsedateTime('20200123', 'yyyyMMdd')),
(454317450.036, 151, 201, parsedateTime('20200128', 'yyyyMMdd')),
(482384479.383, 151, 251, parsedateTime('20200130', 'yyyyMMdd')),
(495739698.959, 151, 301, parsedateTime('20200204', 'yyyyMMdd')),
(516099096.542, 151, 351, parsedateTime('20200207', 'yyyyMMdd')),
(585221959.093, 151, 401, parsedateTime('20200213', 'yyyyMMdd')),
(630473968.615, 151, 451, parsedateTime('20200217', 'yyyyMMdd')),
(659367529.610, 201, 1, parsedateTime('20200218', 'yyyyMMdd')),
(662428695.860, 201, 51, parsedateTime('20200220', 'yyyyMMdd')),
(673259719.158, 201, 101, parsedateTime('20200221', 'yyyyMMdd')),
(707802051.544, 201, 151, parsedateTime('20200303', 'yyyyMMdd')),
(718647372.488, 201, 201, parsedateTime('20200310', 'yyyyMMdd')),
(756238886.595, 201, 251, parsedateTime('20200317', 'yyyyMMdd')),
(764374831.362, 201, 301, parsedateTime('20200323', 'yyyyMMdd')),
(765423830.420, 201, 351, parsedateTime('20200324', 'yyyyMMdd')),
(784127658.470, 201, 401, parsedateTime('20200326', 'yyyyMMdd')),
(797634175.854, 201, 1, parsedateTime('20200327', 'yyyyMMdd')),
(812632466.958, 251, 51, parsedateTime('20200225', 'yyyyMMdd')),
(813697473.703, 251, 101, parsedateTime('20200228', 'yyyyMMdd')),
(836407868.770, 251, 151, parsedateTime('20200302', 'yyyyMMdd')),
(887386364.383, 251, 201, parsedateTime('20200303', 'yyyyMMdd')),
(966922880.686, 251, 251, parsedateTime('20200305', 'yyyyMMdd')),
(975152975.960, 251, 301, parsedateTime('20200316', 'yyyyMMdd')),
(987174198.981, 251, 351, parsedateTime('20200319', 'yyyyMMdd')),
(988923138.200, 251, 401, parsedateTime('20200320', 'yyyyMMdd')),
(990399724.180, 251, 451, parsedateTime('20200326', 'yyyyMMdd'));