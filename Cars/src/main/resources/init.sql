DROP TABLE IF EXISTS car;

CREATE TABLE car(
    id SERIAL PRIMARY KEY,
    brand VARCHAR(15) NOT NULL,
    model VARCHAR(15) NOT NULL,
    age_of_produce INT NOT NULL,
    price INT NOT NULL
);

INSERT INTO car(brand, model, age_of_produce, price) VALUES('Subaru', 'Outback', 2022, 6000000);
INSERT INTO car(brand, model, age_of_produce, price) VALUES('Kia', 'Rio', 2019, 2000000);
INSERT INTO car(brand, model, age_of_produce, price) VALUES('Subaru', 'Forester', 2021, 4500000);
INSERT INTO car(brand, model, age_of_produce, price) VALUES('Nissan', 'Juke', 2014, 1100000);
INSERT INTO car(brand, model, age_of_produce, price) VALUES('Subaru', 'XV', 2020, 4000000);