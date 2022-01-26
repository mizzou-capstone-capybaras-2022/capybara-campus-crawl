CREATE TABLE public."SavedPath"
(
    "SavedPathID" SERIAL PRIMARY KEY NOT NULL,
	"endingNodeID" INT NOT NULL,
	"startingNodeID" INT NOT NULL,
	distance real
);
