CREATE TABLE public.statue
(
    statue_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    statue_name character varying(50) NOT NULL,
    views integer NOT NULL DEFAULT 0,
    interactions integer NOT NULL DEFAULT 0,
    writes integer NOT NULL DEFAULT 0,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    altitude double precision NOT NULL,
    degree double precision NOT NULL,
    PRIMARY KEY (statue_id),
    UNIQUE (statue_name)
);

CREATE TABLE public.user
(
    user_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    username character varying(50) NOT NULL,
    email character varying(320) NOT NULL,
    password character varying(200) NOT NULL,
    reset_key character varying(200) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    sex character varying(20) NOT NULL,
    birthday bigint NOT NULL,
    creation_date bigint NOT NULL,
    views integer NOT NULL DEFAULT 0,
    interactions integer NOT NULL DEFAULT 0,
    writes integer NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id),
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE public.cloud
(
    statue_id integer NOT NULL,
    cloud_id integer NOT NULL,
    user_id integer NOT NULL,
    submission_time bigint NOT NULL,
    PRIMARY KEY (statue_id, cloud_id),
    FOREIGN KEY (user_id) REFERENCES public."user" (user_id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (statue_id) REFERENCES public.statue (statue_id) ON UPDATE CASCADE ON DELETE SET NULL
);