CREATE TABLE public."Building"
(
    "BuildingID" SERIAL PRIMARY KEY NOT NULL,
    name character varying,
    geojson json
);

ALTER TABLE IF EXISTS public."Building"
    OWNER to capy;