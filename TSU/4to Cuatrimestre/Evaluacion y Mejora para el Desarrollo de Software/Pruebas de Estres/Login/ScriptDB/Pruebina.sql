
CREATE TABLE `admin` (
  `nombre` varchar (30),
  `correo` varchar (30),
  `key` varchar (30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `admin`
  ADD PRIMARY KEY (`correo`);

  INSERT INTO `admin` (`nombre`, `correo`, `key`) VALUES
('Joel Alexx', 'j@gmail.com', '123');


-- --------------------------------------------------------

