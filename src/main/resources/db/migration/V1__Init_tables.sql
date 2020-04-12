create table commutestatus.user
(
    id           SERIAL             not null
        constraint user_pkey
            primary key,
    email        TEXT               not null,
    first_name   TEXT               not null,
    last_name    TEXT               not null,
    date_created DATE default now() not null,
    date_updated DATE,
    auth_id      TEXT               not null
);

create table commutestatus.station
(
    id     SERIAL               not null
        constraint station_pkey
            primary key,
    crs    TEXT                 not null,
    name   TEXT                 not null,
    active BOOLEAN default true not null
);

create table commutestatus.user_preference
(
    id              SERIAL  not null
        constraint user_preference_pkey
            primary key,
    user_id         INTEGER not null
        constraint user_preference_user_id_fk
            references station,
    home_station_id INTEGER not null,
    work_station_id INTEGER not null
);