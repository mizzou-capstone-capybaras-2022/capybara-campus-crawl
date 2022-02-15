CREATE TABLE public."Building"
(
    "BuildingID" SERIAL PRIMARY KEY NOT NULL,
    name character varying,
    "NodeID" INT DEFAULT NULL,
    geojson text,
    FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID")
);
