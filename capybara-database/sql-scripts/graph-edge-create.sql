CREATE TABLE public."GraphEdge"
(
    "EdgeID" SERIAL PRIMARY KEY NOT NULL,
    "StartingNodeID" INT NOT NULL,
    "EndingNodeID" INT NOT NULL,
    distance INT,
    CONSTRAINT fk_starting_nodeid
		FOREIGN KEY("StartingNodeID") 
			REFERENCES public."GraphNode"("NodeID"),
    CONSTRAINT fk_ending_nodeid
		FOREIGN KEY("EndingNodeID") 
			REFERENCES public."GraphNode"("NodeID")
);

ALTER TABLE IF EXISTS public."GraphEdge"
    OWNER to capy;