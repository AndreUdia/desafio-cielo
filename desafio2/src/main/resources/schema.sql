CREATE SEQUENCE cliente_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE cliente
(
    id                  BIGINT       NOT NULL,
    uuid                VARCHAR(255),
    cadastro_nacional   VARCHAR(255) NOT NULL,
    razao_social        VARCHAR(255),
    categoria_comercial VARCHAR(255),
    nome                VARCHAR(255),
    email               VARCHAR(255),
    cpf_contato         VARCHAR(255),
    tipo_cliente        SMALLINT,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

ALTER TABLE cliente
    ADD CONSTRAINT uc_cliente_cadastronacional UNIQUE (cadastro_nacional);
