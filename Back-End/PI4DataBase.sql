CREATE DATABASE PIDataBase;

-- Use o banco de dados PIDataBase
USE PIDataBase;

-- Tabela para informações pessoais
CREATE TABLE PersonalInfo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Tabela para endereço
CREATE TABLE Address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    complement VARCHAR(255)
);

-- Tabela para agendamento de consultas
CREATE TABLE Appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    specialty VARCHAR(255) NOT NULL,
    clinic VARCHAR(255) NOT NULL,
    insurance VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL
);

-- Tabela que relaciona as informações pessoais, endereço e agendamento
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    personal_info_id INT,
    address_id INT,
    appointment_id INT,
    FOREIGN KEY (personal_info_id) REFERENCES PersonalInfo(id),
    FOREIGN KEY (address_id) REFERENCES Address(id),
    FOREIGN KEY (appointment_id) REFERENCES Appointment(id)
);

