create table character
(
    id bigint  not null primary key,
    color_skin  varchar(255),
    first_name  varchar(255),
    is_disabled boolean not null,
    last_name   varchar(255)
);

create table episode
(
    id      bigint  not null primary key,
    episode integer not null,
    name    varchar(255),
    season  integer not null
);

create table episode_character
(
    episode_id bigint not null
        constraint fk22fkbr1r5q4o6q15qkhjipyf9
            references episode,
    character_id bigint not null
        constraint fkbs2evhtydowb65yt1qvdcks7a
            references character
);

create sequence character_seq;

create sequence episode_seq;