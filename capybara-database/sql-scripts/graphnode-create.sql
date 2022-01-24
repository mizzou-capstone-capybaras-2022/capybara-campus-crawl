CREATE TABLE public."GraphNode"
(
    "NodeID" SERIAL PRIMARY KEY NOT NULL,
    latitude double precision,
    longitude double precision,
    description character varying
);

ALTER TABLE IF EXISTS public."GraphNode"
    OWNER to capy;