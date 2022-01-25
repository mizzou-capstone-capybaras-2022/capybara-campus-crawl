CREATE TABLE public."Door"
(
    "DoorID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "BuildingID" INT NOT NULL,
    CONSTRAINT fk_nodeid
		FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID"),
    CONSTRAINT fk_buildingID 
		FOREIGN KEY("BuildingID") 
			REFERENCES public."Building"("BuildingID")
);
