CREATE TABLE IF NOT EXISTS `recipes_to_ingredients` (
  recipe_id     INTEGER(10) UNSIGNED NOT NULL,
  ingredient_id INTEGER(10) UNSIGNED NOT NULL ,
  amount        INTEGER(5) ,
  unit_id       INTEGER(10) UNSIGNED ,

  FOREIGN KEY (recipe_id) REFERENCES `recipes`(id) ON DELETE CASCADE ,
  FOREIGN KEY (ingredient_id) REFERENCES `ingredients`(id) ON DELETE CASCADE ,
  FOREIGN KEY (unit_id) REFERENCES `units`(id) ON DELETE SET NULL ,

  CONSTRAINT IX_id UNIQUE INDEX (recipe_id, ingredient_id)
);