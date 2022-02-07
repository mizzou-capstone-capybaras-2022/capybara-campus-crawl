CREATE TABLE public."Place"
(
    "PlaceID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "Name" character varying,
    "PlaceType" character varying,
    CONSTRAINT fk_nodeid
		FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID")
);