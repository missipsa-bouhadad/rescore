-- CROUS residences in Pau 
INSERT INTO residences (city_id, name, address, type)
SELECT c.id, r.name, r.address, 'CROUS'
FROM cities c,
     (VALUES ('Clé des Champs', '7 rue Saint-John Perse, 64000 Pau'),
             ('Ernest Gabard', 'Rue Honoré Baradat, 64000 Pau'),
             ('Francis Jammes', '12 avenue du Doyen Robert Poplawski, 64000 Pau'),
             ('Gaston Phoebus', 'Avenue du Doyen Robert Poplawski, 64000 Pau'),
             ('Le Thélème', 'Rue des Frères Camors, 64000 Pau'),
             ('Marguerite Broquedis', '6 place Laherrère, 64000 Pau'),
             ('Ronsard', '34 rue Ronsard, 64000 Pau')) AS r (name, address)
WHERE c.slug = 'pau';
