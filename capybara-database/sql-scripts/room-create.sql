CREATE TABLE public."Room"
(
    "RoomID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "BuildingID" INT NOT NULL,
    "Name" character varying,
    "RoomNumber" INT,
    CONSTRAINT fk_nodeid
		FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID"),
    CONSTRAINT fk_buildingID 
		FOREIGN KEY("BuildingID") 
			REFERENCES public."Building"("BuildingID")
);

ALTER TABLE IF EXISTS public."Room"
    OWNER to capy;