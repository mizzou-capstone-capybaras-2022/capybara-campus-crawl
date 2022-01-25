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
