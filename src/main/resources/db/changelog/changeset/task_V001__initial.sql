CREATE TABLE IF NOT EXISTS users
(
    id         uuid PRIMARY KEY,
    first_name varchar(128) NOT NULL,
    last_name  varchar(128) NOT NULL,
    email      varchar(128) NOT NULL,
    role       varchar(128) NOT NULL
);

CREATE TABLE task
(
    id                    bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    start_point           varchar(256) NOT NULL,
    end_point             varchar(256) NOT NULL,
    user_id               uuid         NOT NULL,
    cargo_description     varchar(512) NOT NULL,
    vehicle_licence_plate varchar(10)  NOT NULL,
    company_id            varchar(12)  NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE route
(
    id         bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at timestamp,
    ended_at   timestamp,
    task_id    bigint    NOT NULL,

    CONSTRAINT fk_task_id FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE
);

CREATE TABLE route_event
(
    id          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    event_type  varchar(128) NOT NULL,
    occurred_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    route_id    bigint    NOT NULL,

    CONSTRAINT fk_route_id FOREIGN KEY (route_id) REFERENCES route (id) ON DELETE CASCADE
);

CREATE TABLE location
(
    id          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    latitude    varchar(128) NOT NULL,
    longitude   varchar(128) NOT NULL,
    recorded_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    route_id    bigint       NOT NULL,

    CONSTRAINT fk_route_id FOREIGN KEY (route_id) REFERENCES route (id) ON DELETE CASCADE
);
