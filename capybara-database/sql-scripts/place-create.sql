CREATE TYPE type_of_place AS ENUM ('study', 'dining', 'classroom');

CREATE TABLE public."Place"
(
    "PlaceID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "Name" character varying,
    "PlaceType" type_of_place,
    CONSTRAINT fk_nodeid
		FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID")
);

ALTER TABLE IF EXISTS public."Place"
    OWNER to capy;