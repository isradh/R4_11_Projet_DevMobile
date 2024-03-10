-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : dim. 10 mars 2024 à 22:09
-- Version du serveur : 5.7.39
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bd_powerhouse`
--

-- --------------------------------------------------------

--
-- Structure de la table `appliances`
--

CREATE TABLE `appliances` (
  `id` int(11) NOT NULL,
  `path` longblob NOT NULL,
  `nom` varchar(150) NOT NULL,
  `wattage` int(11) NOT NULL,
  `idUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `appliances`
--

INSERT INTO `appliances` (`id`, `path`, `nom`, `wattage`, `idUser`) VALUES
(1, 0x696d616765732f31302d30332d323032342d313731303038343533312d3234343132302e6a7067, 'machine', 10, 7);

-- --------------------------------------------------------

--
-- Structure de la table `habitats`
--

CREATE TABLE `habitats` (
  `idUser` int(11) NOT NULL,
  `etage` int(11) NOT NULL,
  `superficie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `habitats`
--

INSERT INTO `habitats` (`idUser`, `etage`, `superficie`) VALUES
(7, 1, 9),
(8, 1, 22),
(9, 1, 22),
(10, 1, 11),
(11, 1, 11),
(12, 11, 11),
(13, 12, 122);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `idUser` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mdp` varchar(150) NOT NULL,
  `reset_code` int(10) DEFAULT NULL,
  `code_expiration` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`idUser`, `nom`, `prenom`, `email`, `mdp`, `reset_code`, `code_expiration`) VALUES
(7, 'dhiab', 'isra', 'isra@gmail.com', '$2y$10$gS1vo4TJa2MTAO524vEEWOIGoR/qf3rUPSVz.j66O5nQPs.Jzx97u', 876047, '2024-03-05 23:52:10'),
(8, 'aa', 'aa', 'amira@gmail.com', '$2y$10$CyBph0zJQHf0AJeqMmFadOxItLBcRaldg6Ys9.dehEwGArG8terPq', NULL, NULL),
(9, 'mm', 'mm', 'm@gmail.com', '$2y$10$r3RGLHPXP9eHTZUP9c4PQ.Bwuc05GCA3lnGOP4c2ARTZa3vN.sJra', NULL, NULL),
(10, 'dhi', 'israa', 'isradh1978@gmail.com', '$2y$10$aLgF8LyU41.TeYWvsrf2neum3RSNYDiKF2NDAtyOci2NixWmrhepK', 508728, '2024-03-06 17:40:45'),
(11, 'kadji', 'ines', 'ineskadjii@gmail.com', '$2y$10$wNpNeyUOP3ntY43ikLufBOCvT8qLHEGmhYrLRElItIkNBgQtOF136', 127334, '2024-03-06 17:43:41'),
(12, 'Oum', 'Lamrabet', 'ouum.lamrabet@gmail.com', '$2y$10$X8KZHjgpkgXZ/ub4cL72AOs9QpwMF.xYtoX/d2/ex2kcJTs6gf4w6', 123366, '2024-03-06 17:45:34'),
(13, 'dhiab', 'amira', 'isra.dhiab@gmail.com', '$2y$10$T8I/zuFyVrcEHQejAl.PwufkZeN8rC8nJ34gBQwSee1oBGcdAXt9.', 236156, '2024-03-09 13:12:33');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `appliances`
--
ALTER TABLE `appliances`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_idUser_appliance` (`idUser`);

--
-- Index pour la table `habitats`
--
ALTER TABLE `habitats`
  ADD PRIMARY KEY (`idUser`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `appliances`
--
ALTER TABLE `appliances`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `appliances`
--
ALTER TABLE `appliances`
  ADD CONSTRAINT `FK_idUser_appliance` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `habitats`
--
ALTER TABLE `habitats`
  ADD CONSTRAINT `FK_idUser` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
