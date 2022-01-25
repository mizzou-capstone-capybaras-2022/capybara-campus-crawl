CREATE TABLE public."SavedPath"
(
    "PathID" SERIAL PRIMARY KEY NOT NULL,
	"endingNodeID" INT NOT NULL,
	"startingNodeID" INT NOT NULL,
	"GraphEdgeIDs" INT[] NOT NULL,
	distance real
);
