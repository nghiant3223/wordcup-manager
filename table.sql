create table `groups`
(
    id   int auto_increment
        primary key,
    name char null,
    constraint groups_name_uindex
        unique (name)
);

create table rounds
(
    id   int auto_increment
        primary key,
    name varchar(20) not null,
    code varchar(20) null
);

create table teams
(
    id        int auto_increment,
    name      varchar(50) null,
    fifa_code varchar(5)  null,
    iso2      varchar(10) null,
    constraint teams_fifacode_uindex
        unique (fifa_code),
    constraint teams_id_uindex
        unique (id),
    constraint teams_name_uindex
        unique (name)
);

alter table teams
    add primary key (id);

create table group_performances
(
    team_id      int           not null
        primary key,
    match_played int default 0 null,
    win_count    int default 0 null,
    draw_count   int default 0 null,
    lost_count   int default 0 null,
    goal_for     int default 0 null,
    goal_against int default 0 null,
    group_id     int           null,
    constraint group_performances_groups_id_fk
        foreign key (group_id) references `groups` (id),
    constraint group_performances_teams_id_fk
        foreign key (team_id) references teams (id)
);

create table matches
(
    id           int auto_increment
        primary key,
    home_id      int null,
    away_id      int null,
    home_result  int null,
    away_result  int null,
    home_penalty int null,
    away_penalty int null,
    winner_id    int null,
    round_id     int not null,
    constraint match_teams_id_fk
        foreign key (home_id) references teams (id),
    constraint match_teams_id_fk_2
        foreign key (away_id) references teams (id),
    constraint match_teams_id_fk_3
        foreign key (winner_id) references teams (id),
    constraint matches_rounds_id_fk
        foreign key (round_id) references rounds (id)
);

create table players
(
    id         int auto_increment
        primary key,
    fullname   text          null,
    team_id    int           null,
    goal_count int default 0 null,
    constraint players_teams_id_fk
        foreign key (team_id) references teams (id)
);


