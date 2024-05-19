
CREATE TABLE `annonces` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `datedepub` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `liked` int(11) NOT NULL,
  `rating` double DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `annonces` (`id`, `titre`, `datedepub`, `description`, `liked`, `rating`, `user_id`) VALUES
(3, 'azezaeazea', '2024-03-07 15:22:05', 'azeazeaeaze', 1, NULL, 1),
(4, 'bmw serie3', '2024-03-07 23:38:00', 'jante alu', 0, NULL, 1);


CREATE TABLE `commnet` (
  `id` int(11) NOT NULL,
  `annonces_id` int(11) DEFAULT NULL,
  `contenu` varchar(255) NOT NULL,
  `datecommnt` datetime NOT NULL,
  `liked` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `commnet` (`id`, `annonces_id`, `contenu`, `datecommnt`, `liked`) VALUES
(1, 3, 'woow', '2024-03-07 22:41:11', 1);



CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20240214182448', '2024-03-07 12:01:00', 163),
('DoctrineMigrations\\Version20240214183240', '2024-03-07 12:01:00', 7),
('DoctrineMigrations\\Version20240214185719', '2024-03-07 12:01:00', 17),
('DoctrineMigrations\\Version20240214185811', '2024-03-07 12:01:00', 59),
('DoctrineMigrations\\Version20240216122008', '2024-03-07 12:01:00', 5);



CREATE TABLE `echange` (
  `id` int(11) NOT NULL,
  `etat` varchar(255) NOT NULL DEFAULT 'non validé',
  `offre` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `echange` (`id`, `etat`, `offre`, `image`, `user_id`) VALUES
(2, 'non valide', 'azert', '56007342a20a47c55253bdd086206c3b.jpg', NULL),
(3, 'non valide', 'zertsf', '0137f8a2304bf372ed8d97629bec8668.png', NULL),
(4, 'non valide', 'valii', '0fb65359550b14386bd46ce3990a9af1.png', NULL),
(5, 'non valide', 'ghkjhjj', 'c5cd71a38c089a05bc96a82cd7e4a871.png', NULL),
(6, 'non valide', 'test', 'e356723fd3fc3721c000c9ec87cab394.png', NULL);



CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `event_name` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `place` varchar(255) NOT NULL,
  `description` varchar(1023) NOT NULL,
  `image` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `event` (`id`, `event_name`, `capacity`, `start_date`, `end_date`, `place`, `description`, `image`, `user_id`) VALUES
(3, 'dakar 3', 299, '2024-06-17 00:00:00', '2027-07-18 00:00:00', 'tunis', 'azertyyyyyyyy', 'C:\\Users\\Achraf Nagmar\\Downloads\\dakar-2-taille-et-coloris-au-choix.jpg', NULL),
(6, 'F1', 19, '2024-04-24 00:00:00', '2024-04-28 00:00:00', 'zarzouna', 'La Formule 1 est la plus prestigieuse et rapide catégorie de course automobile au monde', 'C:\\Users\\Achraf Nagmar\\Downloads\\formula-1-f1-nouveau-logo-bicolore.jpg', NULL),
(7, 'Dakar', 18, '2024-04-26 00:00:00', '2024-04-30 00:00:00', 'Tunis', 'Le Rallye Dakar, événement de renommée mondiale, est une compétition tout-terrain extrême qui défie les pilotes et leurs véhicules à travers des paysages variés et hostiles, testant leur résilience et leur endurance sur des milliers de kilomètres.', 'C:\\Users\\Achraf Nagmar\\Downloads\\téléchargement (2).jpg', NULL),
(8, 'Gran turismo', 64, '2024-05-01 00:00:00', '2024-05-12 00:00:00', 'Tunis', 'Gran Turismo est une série de jeux vidéo de course automobile conçue par le studio japonais Polyphony Digital', 'C:\\Users\\Achraf Nagmar\\Downloads\\téléchargement (3).jpg', NULL),
(10, 'zerzr', 257, '2024-04-28 00:00:00', '2024-05-01 00:00:00', 'Tunis', 'dfdsfsdfsdfs', 'C:\\Users\\Achraf Nagmar\\Downloads\\LOGOKHOUDHWHET.jpg', NULL),
(12, 'aaaaaaaa', 545, '2024-05-07 00:00:00', '2024-05-22 00:00:00', 'tunis', 'aaaaaaaaaa', 'C:\\Users\\Achraf Nagmar\\Downloads\\ad74ce04-162f-49fa-a2be-18f9093ddcc5.jpg', NULL),
(13, 'achrrraaaaf', 200, '2024-05-14 00:00:00', '2024-05-21 00:00:00', 'tunis', 'event a tunis', 'C:\\Users\\Achraf Nagmar\\Downloads\\formula-1-f1-nouveau-logo-bicolore.jpg', NULL);



CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(1023) NOT NULL,
  `add_date` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `inventory` (`id`, `title`, `description`, `add_date`, `user_id`) VALUES
(4, 'test', 'test', '2024-03-07 00:00:00', 2),
(5, 'inventaire1', 'ceci est un inventaire', '2024-03-07 00:00:00', NULL),
(6, 'kjhezz', 'zerlzmerkzr', '2024-05-15 00:00:00', 5);



CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `ref` varchar(255) NOT NULL,
  `part_condition` varchar(255) NOT NULL,
  `photos` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `inventory_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `items` (`id`, `name`, `description`, `ref`, `part_condition`, `photos`, `quantity`, `inventory_id`) VALUES
(1, 'testzer', 'testdtd', 'test123', 'Used', '7c794f7548f78efcaf47f89abeb645a7.png', 5, 4),
(2, 'item1', 'item1', '13item', 'New', '4174f966c4734a2bbc3a5b46861ac581.png', 2, 5);



CREATE TABLE `like` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `messenger_messages` (
  `id` bigint(20) NOT NULL,
  `body` longtext NOT NULL,
  `headers` longtext NOT NULL,
  `queue_name` varchar(190) NOT NULL,
  `created_at` datetime NOT NULL,
  `available_at` datetime NOT NULL,
  `delivered_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `reclamation` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `sujet` varchar(255) NOT NULL,
  `details` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `reclamation` (`id`, `nom`, `sujet`, `details`, `date`, `user_id`) VALUES
(1, 'ertyu', 'sdfghj', 'ygyguuuh', '2024-03-07 18:25:39', NULL),
(2, 'azert', 'kihun', 'ygyguuuh', '2024-03-07 23:44:45', NULL);



CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `nom` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `reservation` (`id`, `event_id`, `nom`, `address`, `phone`, `user_id`) VALUES
(4, 7, 'achraf', 'zarzouna', 96639533, NULL),
(5, 7, 'aziz', 'azziz', 555555555, NULL),
(8, 8, 'efojiz', 'sdfsdf', 12345567, NULL),
(9, 6, 'sdfsfsfs', 'sfsdsfsdf', 1234567, NULL),
(11, 7, 'acht', 'hhjhjhjh', 12345678, NULL),
(12, 7, 'khh', 'kjhu', 54564564, NULL),
(14, 10, 'ejrzerozjrzrj', 'zeorjzprj', 12345678, NULL),
(15, 3, 'nnghffh', 'utiyuyiy', 122345, NULL),
(16, 8, 'zertyu', 'ygby', 456213, NULL),
(17, 3, 'kjkgh', 'kjlk', 123456, 5),
(18, 13, 'achraf', 'zarzouna', 12345678, 5);



CREATE TABLE `reset_password_request` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `selector` varchar(20) NOT NULL,
  `hashed_token` varchar(100) NOT NULL,
  `requested_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `expires_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(180) NOT NULL,
  `roles` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '(DC2Type:json)',
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `createdat` date NOT NULL,
  `age` int(11) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `profession` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



INSERT INTO `user` (`id`, `email`, `roles`, `password`, `name`, `firstname`, `createdat`, `age`, `phone`, `profession`) VALUES
(1, 'test@test.test', '[\"ROLE_ADMIN\"]', '$2y$13$qdU03KFXNkh4i3GKxI341eDdAkgPvTnt8HlYD7DqlxVznas11v2za', 'test123', 'test', '2024-03-07', 20, '12345678', 'test'),
(2, 'test1@test1.test', '[\"ROLE_ADMIN\"]', '$2y$13$XGemiiLjR69W6uV94793POAI4PZew.PE9uMDmRUdNkXH1e8vejiO6', 'test', 'test', '2024-03-07', 20, '12345678', 'test'),
(3, 'test12@test.com', '[\"ROLE_USER\"]', '$2y$13$Hw6OASjQiNoEyuNwTyDXM.wGV9.H/rjAYZ7AKVla0cgvESmaVcFu.', 'testttttt', 'azertyu', '2024-03-07', 18, '81478523', 'azertyuio'),
(4, 'aziz@gmail.com', 'ROLE_ADMIN', '$2y$13$Cpo9ZJ0njjVbdjp9tz47quQx0tkgbiFpnpKnT4Rn5PwULwIVOhYly', 'aziz', 'aziiz', '2024-05-15', 22, '12345678', 'doc'),
(5, 'azizl@gmail.com', 'ROLE_USER', '$2y$13$B57wFmAraP.k91FsgOxPi.8fPeTyoBEya3dCekdJnpvXq6fIli8am', 'aziz', 'aziz', '2024-05-15', 22, '81478523', 'doc');


ALTER TABLE `annonces`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_CB988C6FA76ED395` (`user_id`);


ALTER TABLE `commnet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_7BD564464C2885D7` (`annonces_id`);


ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);


ALTER TABLE `echange`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_B577E3BFA76ED395` (`user_id`);


ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_3BAE0AA7A76ED395` (`user_id`);

ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_B12D4A36A76ED395` (`user_id`);


ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_E11EE94D9EEA759` (`inventory_id`);


ALTER TABLE `like`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `messenger_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  ADD KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  ADD KEY `IDX_75EA56E016BA31DB` (`delivered_at`);


ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_CE606404A76ED395` (`user_id`);


ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_42C8495571F7E88B` (`event_id`),
  ADD KEY `IDX_42C84955A76ED395` (`user_id`);


ALTER TABLE `reset_password_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_7CE748AA76ED395` (`user_id`);


ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_8D93D649E7927C74` (`email`);


ALTER TABLE `annonces`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;


ALTER TABLE `commnet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


ALTER TABLE `echange`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;


ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;


ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


ALTER TABLE `like`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `messenger_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


ALTER TABLE `reclamation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;


ALTER TABLE `reset_password_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;


ALTER TABLE `annonces`
  ADD CONSTRAINT `FK_CB988C6FA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


ALTER TABLE `commnet`
  ADD CONSTRAINT `FK_7BD564464C2885D7` FOREIGN KEY (`annonces_id`) REFERENCES `annonces` (`id`);


ALTER TABLE `echange`
  ADD CONSTRAINT `FK_B577E3BFA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


ALTER TABLE `event`
  ADD CONSTRAINT `FK_3BAE0AA7A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


ALTER TABLE `inventory`
  ADD CONSTRAINT `FK_B12D4A36A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


ALTER TABLE `items`
  ADD CONSTRAINT `FK_E11EE94D9EEA759` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`id`);


ALTER TABLE `reclamation`
  ADD CONSTRAINT `FK_CE606404A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_42C8495571F7E88B` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FK_42C84955A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


ALTER TABLE `reset_password_request`
  ADD CONSTRAINT `FK_7CE748AA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);


