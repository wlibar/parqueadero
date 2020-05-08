-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 11-09-2019 a las 19:08:03
-- Versión del servidor: 10.1.38-MariaDB-0+deb9u1
-- Versión de PHP: 7.0.33-0+deb9u3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `parqueadero`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `balance`
--

CREATE TABLE `balance` (
  `id` int(11) NOT NULL,
  `periodo` varchar(7) DEFAULT NULL,
  `total_entradas` varchar(45) DEFAULT NULL,
  `total_salidas` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id` int(11) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `documento` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `contrasenia` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `cargo` varchar(2) NOT NULL,
  `estado` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id`, `nombres`, `apellidos`, `documento`, `celular`, `contrasenia`, `email`, `cargo`, `estado`) VALUES
(1, 'Hugo Armando', 'Castro Gallardo', '983658852', '3136792098', '123456', 'hugocastrog@hotmail.com', 'A', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entradas_salidas`
--

CREATE TABLE `entradas_salidas` (
  `id` int(11) NOT NULL,
  `placa` varchar(8) NOT NULL,
  `ficha_nro` varchar(20) DEFAULT NULL,
  `fecha_hora_entrada` datetime NOT NULL,
  `fecha_hora_salida` datetime DEFAULT NULL,
  `cascos_nro` varchar(1) NOT NULL,
  `deja_llaves` tinyint(1) DEFAULT NULL,
  `entrada_empleados_id` int(11) NOT NULL,
  `salida_empleados_id` int(11) DEFAULT NULL,
  `casillero_nro` varchar(10) NOT NULL,
  `extravio_tarjeta` tinyint(1) DEFAULT '0',
  `descuento` float NOT NULL,
  `valor_pagar` int(11) NOT NULL,
  `observacion` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entradas_salidas_mensuales`
--

CREATE TABLE `entradas_salidas_mensuales` (
  `id` int(11) NOT NULL,
  `ficha_nro` varchar(20) DEFAULT NULL,
  `pagos_mensuales_id` int(11) DEFAULT NULL,
  `fecha_hora_entrada` datetime NOT NULL,
  `fecha_hora_salida` datetime DEFAULT NULL,
  `cascos_nro` varchar(1) DEFAULT NULL,
  `deja_llaves` tinyint(1) DEFAULT NULL,
  `entrada_empleados_id` int(11) NOT NULL,
  `salida_empleados_id` int(11) DEFAULT NULL,
  `casillero_nro` varchar(10) DEFAULT NULL,
  `extravio_tarjeta` tinyint(1) DEFAULT NULL,
  `observacion` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gastos_mensuales`
--

CREATE TABLE `gastos_mensuales` (
  `id` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `tipos_gastos_id` int(11) DEFAULT NULL,
  `valor` int(11) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos_mensuales`
--

CREATE TABLE `pagos_mensuales` (
  `id` int(11) NOT NULL,
  `placa` varchar(8) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `descuento` int(11) DEFAULT NULL,
  `valor_pagar` int(11) NOT NULL,
  `empleados_id` int(11) DEFAULT NULL,
  `observacion` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_gastos`
--

CREATE TABLE `tipos_gastos` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipos_gastos`
--

INSERT INTO `tipos_gastos` (`id`, `descripcion`) VALUES
(1, 'Energía'),
(2, 'Agua'),
(3, 'Internet'),
(4, 'Salarios');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

CREATE TABLE `vehiculos` (
  `placa` varchar(8) NOT NULL,
  `tipo` varchar(1) NOT NULL,
  `propietario` varchar(45) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `balance`
--
ALTER TABLE `balance`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `entradas_salidas`
--
ALTER TABLE `entradas_salidas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_entradas_salidas_empleados_id` (`entrada_empleados_id`),
  ADD KEY `idx_entradas_salidas_placa` (`placa`),
  ADD KEY `idx_ficha_nro` (`ficha_nro`),
  ADD KEY `fk_entradas_salidas_empleados_salida_idx` (`salida_empleados_id`);

--
-- Indices de la tabla `entradas_salidas_mensuales`
--
ALTER TABLE `entradas_salidas_mensuales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_entradas_salidas_mensuales_pagos_mensuales_id` (`pagos_mensuales_id`),
  ADD KEY `index3` (`ficha_nro`),
  ADD KEY `fk_entradas_salidas_mensuales_empleados_idx` (`entrada_empleados_id`),
  ADD KEY `fk_entradas_salidas_mensuales_empleados_salida_idx` (`salida_empleados_id`);

--
-- Indices de la tabla `gastos_mensuales`
--
ALTER TABLE `gastos_mensuales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_gastos_mensuales_1_idx` (`tipos_gastos_id`);

--
-- Indices de la tabla `pagos_mensuales`
--
ALTER TABLE `pagos_mensuales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_pagos_mensuales_empleados_id` (`empleados_id`),
  ADD KEY `idx_pagos_mensuales_placa` (`placa`);

--
-- Indices de la tabla `tipos_gastos`
--
ALTER TABLE `tipos_gastos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD PRIMARY KEY (`placa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `balance`
--
ALTER TABLE `balance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `entradas_salidas`
--
ALTER TABLE `entradas_salidas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `entradas_salidas_mensuales`
--
ALTER TABLE `entradas_salidas_mensuales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `gastos_mensuales`
--
ALTER TABLE `gastos_mensuales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `pagos_mensuales`
--
ALTER TABLE `pagos_mensuales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tipos_gastos`
--
ALTER TABLE `tipos_gastos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `entradas_salidas`
--
ALTER TABLE `entradas_salidas`
  ADD CONSTRAINT `fk_entradas_salidas_empleados_entrada` FOREIGN KEY (`entrada_empleados_id`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entradas_salidas_empleados_salida` FOREIGN KEY (`salida_empleados_id`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entradas_salidas_vehiculos` FOREIGN KEY (`placa`) REFERENCES `vehiculos` (`placa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `entradas_salidas_mensuales`
--
ALTER TABLE `entradas_salidas_mensuales`
  ADD CONSTRAINT `fk_entradas_salidas_mensuales_empleados_entrada` FOREIGN KEY (`entrada_empleados_id`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entradas_salidas_mensuales_empleados_salida` FOREIGN KEY (`salida_empleados_id`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entradas_salidas_mensuales_pagos_mensuales` FOREIGN KEY (`pagos_mensuales_id`) REFERENCES `pagos_mensuales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `gastos_mensuales`
--
ALTER TABLE `gastos_mensuales`
  ADD CONSTRAINT `fk_gastos_mensuales_tipo_gasto` FOREIGN KEY (`tipos_gastos_id`) REFERENCES `tipos_gastos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagos_mensuales`
--
ALTER TABLE `pagos_mensuales`
  ADD CONSTRAINT `fk_pagos_mensuales_empleados` FOREIGN KEY (`empleados_id`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_pagos_mensuales_vehiculos` FOREIGN KEY (`placa`) REFERENCES `vehiculos` (`placa`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
