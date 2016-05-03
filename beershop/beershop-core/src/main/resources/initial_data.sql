INSERT INTO `beer` (`id`, `alcoholLevel`, `capacity`, `comment`, `discountAmount`, `name`, `price`, `legendary`) VALUES
(1, 11.6, 0.5, 'A világ egyik legerõsebb söre.', 0, 'Ultra sör', 400, b'0'),
(2, 6.5, 0.5, 'A világ fõleg Európai országaiban kedvelt sör.', 0, 'Bivaly sör', 250, b'0'),
(3, 5.7, 0.5, 'Egy igazán habos sör az unalmas hétköznapokra.', 0, 'Habos sör', 299, b'0'),
(4, 6.4, 0.33, 'Egy jó buliban mindig van szükség egy jó pofa sörre.', 0, 'Jópofa sör', 320, b'0'),
(5, 7.2, 0.5, 'Az egyik legfinomabb barna sör amit inni fog.', 33, 'Bebarnult sör', 399, b'0'),
(6, 2.2, 0.5, 'Egy igazán jól elkészített lime ízesítésû sör.', 0, 'Lime sör', 235, b'0'),
(7, 5.6, 0.5, 'Egy igazán habosra készített söröcske.', 0, 'Meghabosodott sör', 259, b'0'),
(8, 4.9, 50, 'A nyári bulik egyik alapvetõ kelléke, egy 50 literes party hordó.', 50, 'Party hordó', 15999, b'1'),
(9, 4.5, 25, 'Egy meleg nyári délutánon a haverokkal akár 25 litert is képesek vagyunk elfogyasztani, jéghidegen a legjobb.', 0, '25 L hordó', 8000, b'0'),
(10, 3.6, 0.5, 'A mindennapok szerencséire való hûsítõ ital. (http://www.iconka.com)', 0, 'Szerencse sör', 310, b'0'),
(11, 5.5, 50, 'Csappal ellátott party hordó.', 0, 'Csapos party hordó', 19000, b'1'),
(12, 3.6, 0.5, 'A legjobb barátok szerencse söre, most párban. (http://www.iconka.com)', 0, 'Barátság sör', 459, b'0'),
(13, 4.9, 0.5, 'Igazán hûsítõ dobozos söröcske.', 0, 'Jéghegy sör', 259, b'0'),
(14, 4.5, 0.5, 'Hûsítõ, októberi finomság.', 15, 'Õszi sör', 359, b'0'),
(15, 7.6, 0.5, 'Kezdetben fehér aztán pedig barna sör is akár.', 0, 'Fehér-Barna sör', 320, b'0'),
(16, 2.4, 0.33, 'Sárga sör, az unalmas hétköznapokra.', 0, 'Sárga sör', 240, b'0');

INSERT INTO `storageitem` (`id`, `quantity`, `beer_id`) VALUES
(1, 122, 1),
(2, 16, 2),
(3, 0, 3),
(4, 60, 4),
(5, 31, 5),
(6, 16, 6),
(7, 0, 7),
(8, 63, 8),
(9, 3, 9),
(10, 13, 10),
(11, 21, 11),
(12, 43, 12),
(13, 10, 13),
(14, 16, 14),
(15, 23, 15),
(16, 30, 16);

INSERT INTO `cart` (`id`, `user_id`) VALUES
(1, null);


INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO `user` (`id`, `dateOfBirth`, `email`, `password`, `points`, `username`, `experiencePoints`, `cart_id`, `money`) VALUES
(1, '1995-10-20 00:00:00', 'holi60@gmail.com', '$2a$10$2xgwOhINMMmb8L9jXcoXsO7w0ie3418DOU0q3hZ7IxYF9ULGlEw76', 0, 'Holi60', '750.00', null, '115500.00');

INSERT INTO `user_role` (`User_id`, `roles_id`) VALUES
(1, 1),
(1, 2);

UPDATE `cart` SET `user_id` = 1 where `id` = 1;
UPDATE `user` SET `cart_id` = 1 WHERE `id` = 1;



