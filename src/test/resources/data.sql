insert into unit (id, name, initials) values (1, 'Teaspoon', 'tsp');
insert into unit (id, name, initials) values (2, 'Tablespoon', 'tbsp');
insert into unit (id, name, initials) values (3, 'Fluid ounce', 'fl oz');
insert into unit (id, name, initials) values (4, 'Gill', '1/2 cup');
insert into unit (id, name, initials) values (5, 'Cup', 'cup');
insert into unit (id, name, initials) values (6, 'Pint', 'pt');
insert into unit (id, name, initials) values (7, 'Quart', 'qt');
insert into unit (id, name, initials) values (8, 'Gallon', 'gal');
insert into unit (id, name, initials) values (9, 'Millilitre', 'ml');
insert into unit (id, name, initials) values (10, 'Litre', 'l');
insert into unit (id, name, initials) values (11, 'Decilitre', 'dl');
insert into unit (id, name, initials) values (12, 'Pound', 'lb');
insert into unit (id, name, initials) values (13, 'Ounce', 'oz');
insert into unit (id, name, initials) values (14, 'Milligram', 'mg');
insert into unit (id, name, initials) values (15, 'Gram', 'g');
insert into unit (id, name, initials) values (16, 'Kilogram', 'kg');
insert into unit (id, name, initials) values (17, 'Unit', 'un');

insert into recipe(id, name, vegetarian, people, instructions) values (1, 'Chicken', false, 3, 'Cook a chicken');
insert into recipe(id, name, vegetarian, people, instructions) values (2, 'Rice', true, 5, 'Cook a rice');

insert into ingredient(id, name, amount, recipe_id, unit_id) values (1, 'Chicken', 1.00, 1, 16);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (2, 'Onion', 1.00, 1, 17);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (3, 'Tomato', 2.00, 1, 17);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (4, 'Oil', 150.00, 1, 9);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (5, 'Rice', 1.00, 2, 16);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (6, 'Water', 1.00, 2, 10);
insert into ingredient(id, name, amount, recipe_id, unit_id) values (7, 'Salt', 1.00, 2, 6);