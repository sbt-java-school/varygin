CREATE TABLE IF NOT EXISTS `recipes_to_ingredients` (
  recipe_id     NUMBER(10) NOT NULL,
  ingredient_id NUMBER(10) NOT NULL ,
  amount        NUMBER(5,1) ,
  unit_id       NUMBER(10) ,

  FOREIGN KEY (recipe_id) REFERENCES `recipes`(id) ON DELETE CASCADE ,
  FOREIGN KEY (ingredient_id) REFERENCES `ingredients`(id) ON DELETE CASCADE ,
  FOREIGN KEY (unit_id) REFERENCES `units`(id) ON DELETE SET NULL ,

  CONSTRAINT IX_id UNIQUE (recipe_id, ingredient_id)
)