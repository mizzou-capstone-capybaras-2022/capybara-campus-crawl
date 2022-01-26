CREATE TABLE public."GraphNode"
(
    "NodeID" SERIAL PRIMARY KEY NOT NULL,
    latitude double precision,
    longitude double precision,
    description character varying
);

CREATE TABLE public."Building"
(
    "BuildingID" SERIAL PRIMARY KEY NOT NULL,
    name character varying,
    geojson json
);

CREATE TABLE public."GraphEdge"
(
    "EdgeID" SERIAL PRIMARY KEY NOT NULL,
    "Node1ID" INT NOT NULL,
    "Node2ID" INT NOT NULL,
    FromToAction character varying,
    ToFromAction character varying,
    distance real,
    pathShape json,
    CONSTRAINT fk_starting_nodeid
		FOREIGN KEY("Node1ID") 
			REFERENCES public."GraphNode"("NodeID"),
    CONSTRAINT fk_ending_nodeid
		FOREIGN KEY("Node2ID") 
			REFERENCES public."GraphNode"("NodeID")
);

CREATE TABLE public."SavedPath"
(
  "SavedPathID" SERIAL PRIMARY KEY NOT NULL,
  "endingNodeID" INT NOT NULL,
  "startingNodeID" INT NOT NULL,
  distance real
);

CREATE TABLE IF NOT EXISTS public."PathEdges"
(
  "PathEdgeID" SERIAL PRIMARY KEY NOT NULL,
  "SavedPathID" integer NOT NULL,    
  "EdgeID" integer NOT NULL,
  CONSTRAINT fk_savedPathID FOREIGN KEY("SavedPathID") REFERENCES public."SavedPath"("SavedPathID"),
  CONSTRAINT fk_edgeID FOREIGN KEY("EdgeID") REFERENCES public."GraphEdge"("EdgeID")
)


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

CREATE TABLE public."PIMetrics"
(
    "MetricsID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "Time" timestamp with time zone,
    "Intensity" INT,
    CONSTRAINT fk_nodeid
		FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID")
);

