CREATE TABLE pessoa_fisica
(
    id                  BIGINT       NOT NULL,
    uuid                UUID,
    categoria_comercial VARCHAR(255),
    nome                VARCHAR(255),
    email               VARCHAR(255),
    cpf                 VARCHAR(255) NOT NULL,
    CONSTRAINT pk_pessoafisica PRIMARY KEY (id)
);

ALTER TABLE pessoa_fisica
    ADD CONSTRAINT uc_pessoafisica_cpf UNIQUE (cpf);

CREATE SEQUENCE pessoa_fisica_seq START WITH 1 INCREMENT BY 1;