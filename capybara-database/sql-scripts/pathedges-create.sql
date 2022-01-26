CREATE TABLE IF NOT EXISTS public."PathEdges"
(
    "PathEdgeID" SERIAL PRIMARY KEY NOT NULL,
    "SavedPathID" integer NOT NULL,    
	"EdgeID" integer NOT NULL,

    CONSTRAINT fk_savedPathID
		FOREIGN KEY("SavedPathID") 
			REFERENCES public."SavedPath"("SavedPathID"),
    CONSTRAINT fk_edgeID
		FOREIGN KEY("EdgeID") 
			REFERENCES public."GraphEdge"("EdgeID")
)
