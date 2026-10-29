
INSERT INTO clouds (name) VALUES ('BUTTS');
INSERT INTO clouds (name) VALUES ('SNACKS');
INSERT INTO clouds (name) VALUES ('COLORS');
INSERT INTO clouds (name) VALUES ('POSITIVE');
INSERT INTO clouds (name) VALUES ('OPPOSITE FEELINGS');
INSERT INTO clouds (name) VALUES ('FRUIT');

INSERT INTO words (name, explicit) VALUES ('ass', true);
INSERT INTO words (name, explicit) VALUES ('arse', true);
INSERT INTO words (name, explicit) VALUES ('butt', false);
INSERT INTO words (name, explicit) VALUES ('milkshake', false);
INSERT INTO words (name, explicit) VALUES ('apple', false);
INSERT INTO words (name, explicit) VALUES ('orange', false);
INSERT INTO words (name, explicit) VALUES ('pear', false);
INSERT INTO words (name, explicit) VALUES ('donut', false);
INSERT INTO words (name, explicit) VALUES ('red', false);
INSERT INTO words (name, explicit) VALUES ('green', false);
INSERT INTO words (name, explicit) VALUES ('purple', false);
INSERT INTO words (name, explicit) VALUES ('joyful', false);
INSERT INTO words (name, explicit) VALUES ('exultation', false);
INSERT INTO words (name, explicit) VALUES ('happy', false);
INSERT INTO words (name, explicit) VALUES ('sad', false);
INSERT INTO words (name, explicit) VALUES ('soda', false);

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='BUTTS'),
        (SELECT word_id FROM words WHERE name='butts'));

SELECT name FROM clouds WHERE explicit==true;  --none

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='BUTTS'),
        (SELECT word_id FROM words WHERE name='arse'));

SELECT name FROM clouds WHERE explicit==true;  --BUTTS

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='BUTTS'),
        (SELECT word_id FROM words WHERE name='ass'));

SELECT name FROM clouds WHERE explicit==true;   --BUTTS

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='FRUIT'),
        (SELECT word_id FROM words WHERE name='apple'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='FRUIT'),
        (SELECT word_id FROM words WHERE name='orange'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='FRUIT'),
        (SELECT word_id FROM words WHERE name='pear'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='SNACKS'),
        (SELECT word_id FROM words WHERE name='milkshake'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='SNACKS'),
        (SELECT word_id FROM words WHERE name='donut'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='SNACKS'),
        (SELECT word_id FROM words WHERE name='soda'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='POSITIVE'),
        (SELECT word_id FROM words WHERE name='happy'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='POSITIVE'),
        (SELECT word_id FROM words WHERE name='joyful'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='OPPOSITE FEELINGS'),
        (SELECT word_id FROM words WHERE name='happy'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='OPPOSITE FEELINGS'),
        (SELECT word_id FROM words WHERE name='sad'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='COLORS'),
        (SELECT word_id FROM words WHERE name='red'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='COLORS'),
        (SELECT word_id FROM words WHERE name='green'));

INSERT INTO clouds_words(cloud_id, word_id)
VALUES ((SELECT cloud_id FROM clouds WHERE name='COLORS'),
        (SELECT word_id FROM words WHERE name='purple'));