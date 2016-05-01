
INSERT INTO `beer` (`id`, `alcoholLevel`, `comment`, `name`, `price`, `capacity`, `discountAmount`) VALUES
(1, 11.6, 'A világ egyik legerõsebb söre.', 'Ultra sör', 400, 0.5, 0),
(2, 6.5, 'A világ fõleg Európai országaiban kedvelt sör.', 'Bivaly sör', 250, 0.5, 0),
(3, 5.7, 'Egy igazán habos sör az unalmas hétköznapokra.', 'Habos sör', 299, 0.5, 0),
(4, 6.4, 'Egy jó buliban mindig van szükség egy jó pofa sörre.', 'Jópofa sör', 320, 0.33, 0),
(5, 7.2, 'Az egyik legfinomabb barna sör amit inni fog.', 'Bebarnult sör', 399, 0.5, 33),
(6, 2.2, 'Egy igazán jól elkészített lime ízesítésû sör.', 'Lime sör', 235, 0.5, 0),
(7, 5.6, 'Egy igazán habosra készített söröcske.', 'Meghabosodott sör', 259, 0.5, 0),
(8, 4.9, 'A nyári bulik egyik alapvetõ kelléke, egy 50 literes party hordó.', 'Party hordó', 15999, 50, 50),
(9, 4.5, 'Egy meleg nyári délutánon a haverokkal akár 25 litert is képesek vagyunk elfogyasztani, jéghidegen a legjobb.', '25 L hordó', 8000, 25, 0),
(10, 3.6, 'A mindennapok szerencséire való hûsítõ ital. (http://www.iconka.com)', 'Szerencse sör', 310, 0.5, 0),
(11, 5.5, 'Csappal ellátott party hordó.', 'Csapos party hordó', 19000, 50, 0),
(12, 3.6, 'A legjobb barátok szerencse söre, most párban. (http://www.iconka.com)', 'Barátság sör', 459, 0.5, 0),
(13, 4.9, 'Igazán hûsítõ dobozos söröcske.', 'Jéghegy sör', 259, 0.5, 0),
(14, 4.5, 'Hûsítõ, októberi finomság.', 'Õszi sör', 359, 0.5, 15),
(15, 7.6, 'Kezdetben fehér aztán pedig barna sör is akár.', 'Fehér-Barna sör', 320, 0.5, 0),
(16, 2.4, 'Sárga sör, az unalmas hétköznapokra.', 'Sárga sör', 240, 0.33, 0);

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



