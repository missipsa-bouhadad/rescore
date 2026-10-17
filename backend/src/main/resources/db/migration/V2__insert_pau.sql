-- Pau city, with UPPA and its student domain
INSERT INTO cities (name, slug, region)
VALUES ('Pau', 'pau', 'Nouvelle-Aquitaine');

INSERT INTO universities (city_id, name, short_name)
VALUES ((SELECT id FROM cities WHERE slug = 'pau'),
        'Université de Pau et des Pays de l''Adour', 'UPPA');

INSERT INTO allowed_email_domains (university_id, domain)
VALUES ((SELECT id FROM universities WHERE short_name = 'UPPA'),
        'etud.univ-pau.fr');
