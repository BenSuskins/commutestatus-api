create schema commutestatus;

create table commutestatus."user"
(
    id           SERIAL             not null
        constraint user_pkey
            primary key,
    email        text               not null,
    first_name   text               not null,
    last_name    text               not null,
    date_created date default now() not null,
    auth_id      text               not null
);

create table commutestatus.station
(
    id     SERIAL               not null
        constraint station_pkey
            primary key,
    crs    text                 not null,
    name   text                 not null,
    active boolean default true not null
);

create table commutestatus.user_preference
(
    id              SERIAL not null
        constraint user_preference_pkey
            primary key,
    user_id         long   not null
        constraint user_preference_user_id_fk
            references station,
    home_station_id long   not null,
    work_station_id long   not null
);