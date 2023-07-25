USE example;

CREATE TABLE IF NOT EXISTS item
(
    id INT NOT NULL AUTO_INCREMENT,
    name CHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS seller
(
    id INT NOT NULL AUTO_INCREMENT,
    name CHAR(100) NOT NULL,
    PRIMARY KEY(id)

);

CREATE TABLE IF NOT EXISTS sellable_item
(
    id INT NOT NULL AUTO_INCREMENT,
    available BOOLEAN,
    seller_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY(id),
    INDEX seller_id_ind (seller_id),
    INDEX item_ind (item_id),
    FOREIGN KEY (seller_id)
           REFERENCES seller(id),
    FOREIGN KEY (item_id)
           REFERENCES item(id)
);


INSERT INTO item (name)
VALUES ("CARD 1"),("CARD 2");

INSERT INTO seller (name)
VALUES ("SELLER 1");

INSERT INTO sellable_item (available, seller_id, item_id)
values (TRUE,1,1),
(FALSE,1,2);