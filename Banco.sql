create database observacao;

use observacao;

CREATE TABLE usuarios (
    usu_id bigint NOT NULL AUTO_INCREMENT,
    usu_nome varchar(20) NOT NULL,
    usu_senha varchar(255) NOT NULL,
    usu_tipo enum('CIDADAO','GERENTE') NOT NULL,
    PRIMARY KEY (usu_id),
    UNIQUE KEY uk_usuario (usu_nome)
);

INSERT INTO usuarios (usu_nome, usu_senha, usu_tipo)
VALUES('master', '1', 'GERENTE');

INSERT INTO usuarios (usu_nome, usu_senha, usu_tipo)
VALUES('cidadao', '1', 'CIDADAO');

CREATE TABLE categorias (
    cat_id bigint NOT NULL AUTO_INCREMENT,
    cat_descricao varchar(255) NOT NULL,
    PRIMARY KEY (cat_id)
);

INSERT INTO categorias (cat_descricao)
VALUES('Iluminação');

INSERT INTO categorias (cat_descricao)
VALUES('Buraco');

INSERT INTO categorias (cat_descricao)
VALUES('Limpeza');

INSERT INTO categorias (cat_descricao)
VALUES('Saúde');

INSERT INTO categorias (cat_descricao)
VALUES('Segurança');

INSERT INTO categorias (cat_descricao)
VALUES('Escolar');

CREATE TABLE solicitacoes (
    slc_id bigint NOT NULL AUTO_INCREMENT,
    slc_usuario bigint DEFAULT NULL,
    slc_protocolo varchar(20) NOT NULL,
    slc_categoria bigint NOT NULL,
    slc_descricao varchar(255) NOT NULL,
    slc_anexo longblob,
    slc_prioridade int NOT NULL,
    slc_status enum('ATENDIMENTO','TRIAGEM','EM EXECUÇÃO','RESOLVIDO','ENCERRADO') NOT NULL,
    slc_localizacao varchar(255) NOT NULL,
    PRIMARY KEY (slc_id),
    KEY rel_solicitacoes_usuario (slc_usuario),
    KEY rel_solicitacoes_categoria (slc_categoria),
    CONSTRAINT rel_solicitacoes_categoria FOREIGN KEY (slc_categoria) REFERENCES categorias (cat_id),
    CONSTRAINT rel_solicitacoes_usuario FOREIGN KEY (slc_usuario) REFERENCES usuarios (usu_id)
);

CREATE TABLE historicostatussolicitacao (
    hss_id bigint NOT NULL AUTO_INCREMENT,
    hss_solicitacao bigint NOT NULL,
    hss_gerente bigint DEFAULT NULL,
    hss_status enum('ATENDIMENTO','TRIAGEM','EM EXECUÇÃO','RESOLVIDO','ENCERRADO') NOT NULL,
    hss_dataMudanca datetime NOT NULL,
    hss_resposta varchar(255) DEFAULT NULL,
    hss_datafinalizacao datetime DEFAULT NULL,
    PRIMARY KEY (hss_id),
    KEY rel_histstatussolic_gerente (hss_gerente),
    KEY rel_histstatussolic_solicitacao (hss_solicitacao),
    CONSTRAINT rel_histstatussolic_gerente FOREIGN KEY (hss_gerente) REFERENCES usuarios (usu_id),
    CONSTRAINT rel_histstatussolic_solicitacao FOREIGN KEY (hss_solicitacao) REFERENCES solicitacoes (slc_id) ON DELETE CASCADE
);