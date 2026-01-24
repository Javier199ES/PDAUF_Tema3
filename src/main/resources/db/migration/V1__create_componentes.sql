-- Migración V1: creación de tabla 'componentes' para tienda de hardware

CREATE TABLE componentes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL
);

-- Datos de ejemplo
INSERT INTO componentes (nombre, categoria, precio, stock, descripcion) VALUES
('Intel Core i5 12400F', 'Procesador', 179.99, 15, 'Procesador de 6 núcleos para gaming'),
('AMD Ryzen 5 5600X', 'Procesador', 209.99, 10, 'Procesador de 6 núcleos con excelente rendimiento'),
('Nvidia RTX 4060', 'Tarjeta Gráfica', 339.00, 8, 'GPU de gama media para juegos modernos'),
('Kingston 16GB DDR4 3200MHz', 'Memoria RAM', 49.99, 25, 'Módulo de memoria DDR4'),
('Samsung 1TB SSD NVMe', 'Almacenamiento', 89.99, 20, 'SSD NVMe de alto rendimiento'),
('MSI B550M PRO', 'Placa Base', 129.90, 12, 'Placa base compatible con Ryzen'),
('Corsair 650W 80+ Bronze', 'Fuente de Alimentación', 69.90, 18, 'Fuente de alimentación eficiente');
