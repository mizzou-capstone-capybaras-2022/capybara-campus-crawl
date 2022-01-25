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
