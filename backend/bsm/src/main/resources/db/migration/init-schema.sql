CREATE TABLE address (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   zip_code VARCHAR(255),
   public_place VARCHAR(255),
   neighborhood VARCHAR(255),
   complement VARCHAR(255),
   city VARCHAR(255),
   state VARCHAR(255),
   CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE customer (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   usuario_id BIGINT,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE expense (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   total DOUBLE PRECISION,
   days_beetwen_installments INTEGER,
   release_date date,
   quantity_of_installments INTEGER,
   due_date date,
   expense_type_id BIGINT,
   CONSTRAINT pk_expense PRIMARY KEY (id)
);

CREATE TABLE expense_type (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   generate_installments BOOLEAN,
   is_active BOOLEAN,
   CONSTRAINT pk_expense_type PRIMARY KEY (id)
);

CREATE TABLE installment (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   installment_value DOUBLE PRECISION,
   due_date date,
   expense_id BIGINT,
   CONSTRAINT pk_installment PRIMARY KEY (id)
);

CREATE TABLE message_template (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255),
   body_message VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_message_template PRIMARY KEY (id)
);

CREATE TABLE nivel_acesso (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   authority VARCHAR(255),
   CONSTRAINT pk_nivelacesso PRIMARY KEY (id)
);

CREATE TABLE payment_method (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_payment_method PRIMARY KEY (id)
);

CREATE TABLE procedimento (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   price DOUBLE PRECISION,
   is_active BOOLEAN,
   CONSTRAINT pk_procedimento PRIMARY KEY (id)
);

CREATE TABLE product_procedure_types (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_product_procedure_types PRIMARY KEY (id)
);

CREATE TABLE produto (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255),
   price DOUBLE PRECISION,
   brand VARCHAR(255),
   quantity INTEGER,
   is_active BOOLEAN,
   CONSTRAINT pk_produto PRIMARY KEY (id)
);

CREATE TABLE profissionais_procedimentos (
  procedimento_id BIGINT NOT NULL,
   profissional_id BIGINT NOT NULL,
   CONSTRAINT pk_profissionaisprocedimentos PRIMARY KEY (procedimento_id, profissional_id)
);

CREATE TABLE profissional (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   usuario_id BIGINT,
   CONSTRAINT pk_profissional PRIMARY KEY (id)
);

CREATE TABLE usuario (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   phone_number VARCHAR(255),
   cpf VARCHAR(255),
   rg VARCHAR(255),
   endereco_id INTEGER,
   nivelacesso_id BIGINT,
   is_active BOOLEAN,
   CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE usuario ADD CONSTRAINT uc_usuario_email UNIQUE (email);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE expense ADD CONSTRAINT FK_EXPENSE_ON_EXPENSETYPE FOREIGN KEY (expense_type_id) REFERENCES expense_type (id);

ALTER TABLE installment ADD CONSTRAINT FK_INSTALLMENT_ON_EXPENSE FOREIGN KEY (expense_id) REFERENCES expense (id);

ALTER TABLE profissional ADD CONSTRAINT FK_PROFISSIONAL_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE usuario ADD CONSTRAINT FK_USUARIO_ON_ENDERECO FOREIGN KEY (endereco_id) REFERENCES address (id);

ALTER TABLE usuario ADD CONSTRAINT FK_USUARIO_ON_NIVELACESSO FOREIGN KEY (nivelacesso_id) REFERENCES nivel_acesso (id);

ALTER TABLE profissionais_procedimentos ADD CONSTRAINT fk_propro_on_procedure FOREIGN KEY (procedimento_id) REFERENCES procedimento (id);

ALTER TABLE profissionais_procedimentos ADD CONSTRAINT fk_propro_on_professional FOREIGN KEY (profissional_id) REFERENCES profissional (id);
CREATE TABLE access_level (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   authority VARCHAR(255),
   CONSTRAINT pk_access_level PRIMARY KEY (id)
);

CREATE TABLE address (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   zip_code VARCHAR(255),
   public_place VARCHAR(255),
   neighborhood VARCHAR(255),
   complement VARCHAR(255),
   city VARCHAR(255),
   state VARCHAR(255),
   CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE customer (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   usertab_id BIGINT,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE expense (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   total DOUBLE PRECISION,
   days_beetwen_installments INTEGER,
   release_date date,
   quantity_of_installments INTEGER,
   due_date date,
   expense_type_id BIGINT,
   CONSTRAINT pk_expense PRIMARY KEY (id)
);

CREATE TABLE expense_type (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   generate_installments BOOLEAN,
   is_active BOOLEAN,
   CONSTRAINT pk_expense_type PRIMARY KEY (id)
);

CREATE TABLE installment (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   installment_value DOUBLE PRECISION,
   due_date date,
   expense_id BIGINT,
   CONSTRAINT pk_installment PRIMARY KEY (id)
);

CREATE TABLE message_template (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255),
   body_message VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_message_template PRIMARY KEY (id)
);

CREATE TABLE payment_method (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_payment_method PRIMARY KEY (id)
);

CREATE TABLE procedure (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   price DOUBLE PRECISION,
   is_active BOOLEAN,
   CONSTRAINT pk_procedure PRIMARY KEY (id)
);

CREATE TABLE product (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255),
   price DOUBLE PRECISION,
   brand VARCHAR(255),
   quantity INTEGER,
   is_active BOOLEAN,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_procedure_types (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   description VARCHAR(255),
   is_active BOOLEAN,
   CONSTRAINT pk_product_procedure_types PRIMARY KEY (id)
);

CREATE TABLE professional (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   usertab_id BIGINT,
   CONSTRAINT pk_professional PRIMARY KEY (id)
);

CREATE TABLE professional_procedures (
  procedure_id BIGINT NOT NULL,
   professional_id BIGINT NOT NULL,
   CONSTRAINT pk_professional_procedures PRIMARY KEY (procedure_id, professional_id)
);

CREATE TABLE usertab (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   phone_number VARCHAR(255),
   cpf VARCHAR(255),
   rg VARCHAR(255),
   address_id INTEGER,
   accesslevel_id BIGINT,
   is_active BOOLEAN,
   CONSTRAINT pk_usertab PRIMARY KEY (id)
);

ALTER TABLE usertab ADD CONSTRAINT uc_usertab_email UNIQUE (email);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_USERTAB FOREIGN KEY (usertab_id) REFERENCES usertab (id);

ALTER TABLE expense ADD CONSTRAINT FK_EXPENSE_ON_EXPENSETYPE FOREIGN KEY (expense_type_id) REFERENCES expense_type (id);

ALTER TABLE installment ADD CONSTRAINT FK_INSTALLMENT_ON_EXPENSE FOREIGN KEY (expense_id) REFERENCES expense (id);

ALTER TABLE professional ADD CONSTRAINT FK_PROFESSIONAL_ON_USERTAB FOREIGN KEY (usertab_id) REFERENCES usertab (id);

ALTER TABLE usertab ADD CONSTRAINT FK_USERTAB_ON_ACCESSLEVEL FOREIGN KEY (accesslevel_id) REFERENCES access_level (id);

ALTER TABLE usertab ADD CONSTRAINT FK_USERTAB_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE professional_procedures ADD CONSTRAINT fk_propro_on_procedure FOREIGN KEY (procedure_id) REFERENCES procedure (id);

ALTER TABLE professional_procedures ADD CONSTRAINT fk_propro_on_professional FOREIGN KEY (professional_id) REFERENCES professional (id);