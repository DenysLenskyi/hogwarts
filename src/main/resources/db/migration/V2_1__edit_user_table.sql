ALTER TABLE hogwarts."user"
    ADD COLUMN USERNAME TEXT UNIQUE;
ALTER TABLE hogwarts."user"
    ADD COLUMN PASSWORD TEXT UNIQUE;

UPDATE hogwarts."user"
SET USERNAME = 'remuslupin',
    PASSWORD = 'remuslupin'
WHERE first_name = 'Remus'
  AND last_name = 'Lupin';

UPDATE hogwarts."user"
SET USERNAME = 'pomonasprout',
    PASSWORD = 'pomonasprout'
WHERE first_name = 'Pomona'
  AND last_name = 'Sprout';

UPDATE hogwarts."user"
SET USERNAME = 'severussnape',
    PASSWORD = 'severussnape'
WHERE first_name = 'Severus'
  AND last_name = 'Snape';

UPDATE hogwarts."user"
SET USERNAME = 'minervamcgonagall',
    PASSWORD = 'minervamcgonagall'
WHERE first_name = 'Minerva'
  AND last_name = 'McGonagall';

UPDATE hogwarts."user"
SET USERNAME = 'lunascamander',
    PASSWORD = 'lunascamander'
WHERE first_name = 'Luna'
  AND last_name = 'Scamander';

UPDATE hogwarts."user"
SET USERNAME = 'firenze',
    PASSWORD = 'firenze'
WHERE first_name = 'Firenze';

UPDATE hogwarts."user"
SET USERNAME = 'filiusflitwick',
    PASSWORD = 'filiusflitwick'
WHERE first_name = 'Filius'
  AND last_name = 'Flitwick';

UPDATE hogwarts."user"
SET USERNAME = 'elspethmacgillony',
    PASSWORD = 'elspethmacgillony'
WHERE first_name = 'Elspeth'
  AND last_name = 'MacGillony';

UPDATE hogwarts."user"
SET USERNAME = 'rolandahooch',
    PASSWORD = 'rolandahooch'
WHERE first_name = 'Rolanda'
  AND last_name = 'Hooch';

UPDATE hogwarts."user"
SET USERNAME = 'jakubgorski',
    PASSWORD = 'jakubgorski'
WHERE first_name = 'Jakub'
  AND last_name = 'Gorski';

UPDATE hogwarts."user"
SET USERNAME = 'septimavector',
    PASSWORD = 'septimavector'
WHERE first_name = 'Septima'
  AND last_name = 'Vector';

UPDATE hogwarts."user"
SET USERNAME = 'rubeushagrid',
    PASSWORD = 'rubeushagrid'
WHERE first_name = 'Rubeus'
  AND last_name = 'Hagrid';

UPDATE hogwarts."user"
SET USERNAME = 'harrypotter',
    PASSWORD = 'harrypotter'
WHERE first_name = 'Harry'
  AND last_name = 'Potter';

UPDATE hogwarts."user"
SET USERNAME = 'hermionegranger',
    PASSWORD = 'hermionegranger'
WHERE first_name = 'Hermione'
  AND last_name = 'Granger';

UPDATE hogwarts."user"
SET USERNAME = 'nevillelongbottom',
    PASSWORD = 'nevillelongbottom'
WHERE first_name = 'Neville'
  AND last_name = 'Longbottom';

UPDATE hogwarts."user"
SET USERNAME = 'ronaldweasley',
    PASSWORD = 'ronaldweasley'
WHERE first_name = 'Ronald'
  AND last_name = 'Weasley';

UPDATE hogwarts."user"
SET USERNAME = 'lavenderbrown',
    PASSWORD = 'lavenderbrown'
WHERE first_name = 'Lavender'
  AND last_name = 'Brown';

UPDATE hogwarts."user"
SET USERNAME = 'deanthomas',
    PASSWORD = 'deanthomas'
WHERE first_name = 'Dean'
  AND last_name = 'Thomas';

UPDATE hogwarts."user"
SET USERNAME = 'seamusfinnigan',
    PASSWORD = 'seamusfinnigan'
WHERE first_name = 'Seamus'
  AND last_name = 'Finnigan';

UPDATE hogwarts."user"
SET USERNAME = 'parvatipatil',
    PASSWORD = 'parvatipatil'
WHERE first_name = 'Parvati'
  AND last_name = 'Patil';