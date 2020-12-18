create table `QUERY` (
`id` bigint auto_increment,
`in_sum` bigint,
`in_currency` varchar(3),
`out_currency` varchar(3),
`out_sum` bigint,
`user_id` bigint,

primary key `id`,

key `fk_user_idx` (`user_id`),
constraint `fk_user`
foreign key (`user_id`)
references `USER` (`id`)
);

create table `USER` (
`id` bigint
);