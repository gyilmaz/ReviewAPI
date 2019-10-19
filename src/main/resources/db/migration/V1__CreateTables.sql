CREATE TABLE review
(
    id          BIGINT AUTO_INCREMENT  PRIMARY KEY ,
    title       VARCHAR(255)                        NOT NULL,
    text        VARCHAR(10000)                      NULL,
    created_ts  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    product_id  BIGINT                              NOT NULL
);

CREATE TABLE product
(
    id          BIGINT AUTO_INCREMENT  PRIMARY KEY ,
    name        VARCHAR(255)  NOT NULL,
    description VARCHAR(100)  NOT NULL
);

CREATE TABLE comment
(
    id          BIGINT AUTO_INCREMENT  PRIMARY KEY ,
    title       VARCHAR(255)                         NOT NULL,
    text        VARCHAR(10000)                       NULL,
    created_ts  TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NULL,
    review_id   BIGINT                               NOT NULL
);