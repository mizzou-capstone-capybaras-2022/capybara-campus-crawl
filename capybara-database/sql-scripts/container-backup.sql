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
    "NodeID" INT DEFAULT NULL,
    geojson text,
    FOREIGN KEY("NodeID") 
			REFERENCES public."GraphNode"("NodeID")
);

CREATE TABLE public."GraphEdge"
(
    "EdgeID" SERIAL PRIMARY KEY NOT NULL,
    "Node1ID" INT NOT NULL,
    "Node2ID" INT NOT NULL,
    FromToAction character varying,
    ToFromAction character varying,
    bidirectional boolean DEFAULT false,
    distance real,
    pathShape text,
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
);

CREATE TABLE public."Place"
(
    "PlaceID" SERIAL PRIMARY KEY NOT NULL,
    "NodeID" INT NOT NULL UNIQUE,
    "Name" character varying,
    "PlaceType" character varying,
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

-- Create Graphnodes --
INSERT INTO public."GraphNode" VALUES (1, 38.9437187, -92.32955217, 'The Mizzou Alumni Center');
INSERT INTO public."GraphNode" VALUES (2, 38.94271735, -92.32471347, 'Home of College of Agriculture');
INSERT INTO public."GraphNode" VALUES (3, 38.94038395, -92.32093289, 'Dining and Fine Arts');
INSERT INTO public."GraphNode" VALUES (4, 38.94117984, -92.32417703, 'GOBCC works to make the University of Missouri and Columbia community a more inclusive and welcoming environment for diverse populations through education, outreach, and collaboration.');
INSERT INTO public."GraphNode" VALUES (5, 38.93871078, -92.33204395, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (6, 38.94878467, -92.33031929, 'State Historical Society');
INSERT INTO public."GraphNode" VALUES (7, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (8, 38.94003555, -92.31746614, 'Veterinary Medicine & Surgery');
INSERT INTO public."GraphNode" VALUES (9, 38.94101503, -92.32136071, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (10, 38.94514142, -92.33156919, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (11, 38.94265059, -92.31871605, 'Office of Animal Resources');
INSERT INTO public."GraphNode" VALUES (12, 38.93985092, -92.3298955, 'Military Science');
INSERT INTO public."GraphNode" VALUES (13, 38.94010544, -92.32614577, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (14, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (15, 38.93962977, -92.3268646, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (16, 38.94167427, -92.32031465, 'Food Science');
INSERT INTO public."GraphNode" VALUES (17, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (18, 38.94011587, -92.32722133, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (19, 38.94767388, -92.3284927, 'Journalism');
INSERT INTO public."GraphNode" VALUES (20, 38.93858664, -92.32290566, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (21, 38.94131544, -92.32310414, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (22, 38.94066872, -92.32037902, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (23, 38.94042672, -92.3265481, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (24, 38.93983214, -92.32866168, 'Health Sciences Library');
INSERT INTO public."GraphNode" VALUES (25, 38.94920917, -92.33009934, 'Missouri Press');
INSERT INTO public."GraphNode" VALUES (26, 38.9343013, -92.3306036, 'Intercollegiate Athletics');
INSERT INTO public."GraphNode" VALUES (27, 38.94758627, -92.32615113, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (28, 38.9386993, -92.32809305, 'Hospital');
INSERT INTO public."GraphNode" VALUES (29, 38.9417452, -92.32253551, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (30, 38.94521756, -92.32870594, 'main administration building for the University of Missouri.');
INSERT INTO public."GraphNode" VALUES (31, 38.94189957, -92.32454181, 'Dining and Res Life');
INSERT INTO public."GraphNode" VALUES (32, 38.94873148, -92.32917398, 'Journalism');
INSERT INTO public."GraphNode" VALUES (33, 38.94606138, -92.32293248, 'Biological Science');
INSERT INTO public."GraphNode" VALUES (34, 38.94331399, -92.33329654, 'Health Science');
INSERT INTO public."GraphNode" VALUES (35, 38.9481, -92.3317, '');
INSERT INTO public."GraphNode" VALUES (36, 38.9453, -92.3273, '');
INSERT INTO public."GraphNode" VALUES (37, 38.9453, -92.3327, 'Res Life and dining');
INSERT INTO public."GraphNode" VALUES (38, 38.9478, -92.3281, 'lots of Teacher offices');
INSERT INTO public."GraphNode" VALUES (39, 38.9478, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (40, 38.9435, -92.3252, 'grad student offices / athletic reaserch');
INSERT INTO public."GraphNode" VALUES (41, 38.9484, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (42, 38.9325, -92.3338, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (43, 38.936, -92.3332, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (44, 38.9467, -92.3314, 'Engineering');
INSERT INTO public."GraphNode" VALUES (45, 38.9478, -92.3282, '');
INSERT INTO public."GraphNode" VALUES (46, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (47, 38.937, -92.3252, 'Parking');
INSERT INTO public."GraphNode" VALUES (48, 38.9395, -92.326, 'hitt street market. Student grocerry');
INSERT INTO public."GraphNode" VALUES (49, 38.9468, -92.3267, 'teacher offices');
INSERT INTO public."GraphNode" VALUES (50, 38.9492, -92.3295, '');
INSERT INTO public."GraphNode" VALUES (51, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (52, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (53, 38.9417, -92.323, 'dining hall');
INSERT INTO public."GraphNode" VALUES (54, 38.94, -92.3213, 'residential Life');
INSERT INTO public."GraphNode" VALUES (55, 38.9454, -92.3225, '');
INSERT INTO public."GraphNode" VALUES (56, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (57, 38.9408, -92.3292, 'rec sports field ');
INSERT INTO public."GraphNode" VALUES (58, 38.9459, -92.3224, '');
INSERT INTO public."GraphNode" VALUES (59, 38.9414, -92.3266, 'The rec');
INSERT INTO public."GraphNode" VALUES (60, 38.9453, -92.3267, '');
INSERT INTO public."GraphNode" VALUES (61, 38.9385, -92.3302, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (62, 38.9492, -92.3279, '');
INSERT INTO public."GraphNode" VALUES (63, 38.9437, -92.3242, '');
INSERT INTO public."GraphNode" VALUES (64, 38.9438, -92.3308, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (65, 38.9471, -92.3239, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (66, 38.9413, -92.3176, 'University Vet');
INSERT INTO public."GraphNode" VALUES (67, 38.9415, -92.325, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (68, 38.9412, -92.32, '');
INSERT INTO public."GraphNode" VALUES (69, 38.9467, -92.3281, '');
INSERT INTO public."GraphNode" VALUES (70, 38.9467, -92.3305, '');
INSERT INTO public."GraphNode" VALUES (71, 38.94575055, -92.33129561, '');
INSERT INTO public."GraphNode" VALUES (72, -92.32437551, 38.94598211, 'Door A for Walters Hall');
INSERT INTO public."GraphNode" VALUES (73, -92.32395306, 38.946028, 'Door B for Walters Hall');
INSERT INTO public."GraphNode" VALUES (74, -92.3239544, 38.94587363, 'Door C for Walters Hall');
INSERT INTO public."GraphNode" VALUES (75, 38.94726447, -92.32915847, 'Door A for Geological Science');
INSERT INTO public."GraphNode" VALUES (76, 38.94731098, -92.32957951, 'Door B for Geological Science');
INSERT INTO public."GraphNode" VALUES (77, 38.94604042, -92.33167786, 'Door A for Academic Support Center');
INSERT INTO public."GraphNode" VALUES (78, 38.94614629, -92.33114678, 'Door B for Academic Support Center');
INSERT INTO public."GraphNode" VALUES (79, 38.94626467, -92.3316383, 'Door C for Academic Support Center');
INSERT INTO public."GraphNode" VALUES (80, 38.943259, -92.325279, 'Door A for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (81, 38.942949, -92.325436, 'Door B for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (82, 38.942548, -92.325451, 'Door C for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (83, 38.942418, -92.324874, 'Door D for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (84, 38.942591, -92.324058, 'Door E for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (85, 38.943136, -92.324221, 'Door F for Natural Resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (86, 38.944687, -92.323696, 'Door A for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (87, 38.944808, -92.323466, 'Door B for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (88, 38.944739, -92.323231, 'Door C for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (89, 38.946331, -92.33086, 'Door A for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (90, 38.9464736, -92.3301081, 'Door B for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (91, 38.9459633, -92.3293808, 'Door C for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (92, 38.9458082, -92.3292866, 'Door D for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (93, 38.9457067, -92.330097, 'Door E for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (94, 38.9471941, -92.3305285, 'Door A for Parker Hall');
INSERT INTO public."GraphNode" VALUES (95, 38.9473539, -92.3303967, 'Door B for Parker Hall');
INSERT INTO public."GraphNode" VALUES (96, 38.94470061, -92.32661798, 'Door A for Ellis Library');
INSERT INTO public."GraphNode" VALUES (97, 38.94422206, -92.32689518, 'Door B for Ellis Library');
INSERT INTO public."GraphNode" VALUES (98, 38.944916, -92.323179, 'Door A for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (99, 38.944979, -92.322961, 'Door B for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (100, 38.944781, -92.322899, 'Door C for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (101, 38.944559, -92.324973, 'Door A for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (102, 38.944766, -92.324814, 'Door B for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (103, 38.944762, -92.324467, 'Door C for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (104, 38.94469, -92.323824, 'Door D for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (105, 38.94236171, -92.32665998, 'Door A for Student Center');
INSERT INTO public."GraphNode" VALUES (106, 38.94258711, -92.32747269, 'Door B for Student Center');
INSERT INTO public."GraphNode" VALUES (107, 38.94287784, -92.32711778, 'Door C for Student Center');
INSERT INTO public."GraphNode" VALUES (108, 38.94291868, -92.32649618, 'Door D for Student Center');
INSERT INTO public."GraphNode" VALUES (109, 38.94252678, -92.32967813, 'Door A for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (110, 38.94309004, -92.32937235, 'Door B for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (111, 38.9432465, -92.32967276, 'Door C for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (112, -92.32978016, 38.94497245, 'Door A for Townsend Hall');
INSERT INTO public."GraphNode" VALUES (113, -92.32960314, 38.94475549, 'Door B for Townsend Hall');
INSERT INTO public."GraphNode" VALUES (114, -92.33008325, 38.94465536, 'Door C for Townsend Hall');
INSERT INTO public."GraphNode" VALUES (115, -92.33057141, 38.94480139, 'Door D for Townsend Hall');
INSERT INTO public."GraphNode" VALUES (116, -92.33034343, 38.94499957, 'Door E for Townsend Hall');
INSERT INTO public."GraphNode" VALUES (117, 38.94337216, -92.32725642, 'Door A for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (118, 38.94337216, -92.32741624, 'Door B for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (119, 38.94377127, -92.32726701, 'Door C for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (120, 38.94377576, -92.32740661, 'Door D for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (121, -92.329699, 38.94549, 'Door A for Hill Hall');
INSERT INTO public."GraphNode" VALUES (122, -92.329721, 38.945107, 'Door B for Hill Hall');
INSERT INTO public."GraphNode" VALUES (123, 38.94374731, 92.32824324, 'Door A for Hulston Hall');
INSERT INTO public."GraphNode" VALUES (124, 38.94375854, -92.32841654, 'Door B for Hulston Hall');
INSERT INTO public."GraphNode" VALUES (125, 38.94392103, 92.32781, 'Door C for Hulston Hall');
INSERT INTO public."GraphNode" VALUES (126, -92.32728034, 38.9460885, 'Door A for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (127, -92.32714355, 38.9460885, 'Door B for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (128, -92.32669562, 38.94611561, 'Door C for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (129, -92.32669026, 38.94627207, 'Door D for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (130, -92.3265186, 38.9459529, 'Door E for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (131, -92.32370362, 38.94508614, 'Door A for Stewart Hall');
INSERT INTO public."GraphNode" VALUES (132, -92.32395977, 38.94510387, 'Door B for Stewart Hall');
INSERT INTO public."GraphNode" VALUES (133, 38.944591, -92.322905, 'Door A for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (134, 38.944253, -92.322949, 'Door B for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (135, 38.944222, -92.322239, 'Door C for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (136, 38.944068, -92.322657, 'Door D for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (137, 38.9475435, -92.3305047, 'Door A for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (138, 38.9475198, -92.3301054, 'Door B for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (139, 38.947731, -92.3305094, 'Door C for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (140, -92.32529014, 38.94506841, 'Door A for Memorial Union');
INSERT INTO public."GraphNode" VALUES (141, -92.32529551, 38.94547728, 'Door B for Memorial Union');
INSERT INTO public."GraphNode" VALUES (142, -92.32485563, 38.94541887, 'Door C for Memorial Union');
INSERT INTO public."GraphNode" VALUES (143, -92.32503533, 38.9460134, 'Door A for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (144, -92.32485831, 38.94601653, 'Door B for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (145, -92.32494146, 38.94617402, 'Door C for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (146, -92.32928798, 38.94672161, 'Door A for Switzler Hall');
INSERT INTO public."GraphNode" VALUES (147, -92.32955888, 38.94662252, 'Door B for Switzler Hall');
INSERT INTO public."GraphNode" VALUES (148, 38.94471878, -92.32790592, 'Door A for Tate Hall');
INSERT INTO public."GraphNode" VALUES (149, 38.94454, -92.327998, 'Door B for Tate Hall');
INSERT INTO public."GraphNode" VALUES (150, 38.94464914, -92.32811291, 'Door C for Tate Hall');
INSERT INTO public."GraphNode" VALUES (151, 38.94478841, -92.32810232, 'Door D for Tate Hall');
INSERT INTO public."GraphNode" VALUES (152, 38.944909, -92.327978, 'Door E for Tate Hall');
INSERT INTO public."GraphNode" VALUES (153, 38.94233242, -92.32802481, 'Door A for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (154, 38.94268139, -92.32784414, 'Door B for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (155, 38.94291763, -92.32789799, 'Door C for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (156, 38.944179, -92.324912, 'Door A for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (157, 38.9443, -92.324919, 'Door B for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (158, 38.944425, -92.325008, 'Door C for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (159, 38.944307, -92.325238, 'Door D for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (160, 38.944231, -92.32476, 'Door E for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (161, -92.32623696, 38.94579019, 'Door A for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (162, -92.3258695, 38.94577767, 'Door B for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (163, -92.3258239, 38.94540427, 'Door C for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (164, -92.32593924, 38.94515185, 'Door D for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (165, -92.32621014, 38.94515394, 'Door E for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (166, -92.32631475, 38.94539384, 'Door F for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (167, -92.32823789, 38.94581105, 'Door A for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (168, -92.32826337, 38.9456233, 'Door B for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (169, -92.32795626, 38.94577976, 'Door C for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (170, -92.32795492, 38.94560974, 'Door D for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (171, 38.93932727, -92.33171672, 'Door A for Southwest Village');
INSERT INTO public."GraphNode" VALUES (172, 38.939, -92.3318, 'Door B for Southwest Village');
INSERT INTO public."GraphNode" VALUES (173, -92.32530087, 38.94572135, 'Door A for Whitten Hall');
INSERT INTO public."GraphNode" VALUES (174, -92.32510239, 38.94577976, 'Door B for Whitten Hall');
INSERT INTO public."GraphNode" VALUES (175, 38.943853, -92.322682, 'Door A for Physics Building');
INSERT INTO public."GraphNode" VALUES (176, 38.943417, -92.322689, 'Door B for Physics Building');
INSERT INTO public."GraphNode" VALUES (177, 38.943138, -92.323608, 'Door A for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (178, 38.943141, -92.323256, 'Door B for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (179, 38.943068, -92.322642, 'Door C for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (180, 38.942875, -92.323241, 'Door D for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (181, 38.942794, -92.323618, 'Door E for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (182, 38.942911, -92.32382, 'Door F for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (183, -92.32801259, 38.9475216, 'Door A for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (184, -92.32780874, 38.94751743, 'Door B for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (185, -92.32804209, 38.94720035, 'Door C for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (186, -92.32775509, 38.94727545, 'Door D for Reynolds Journalism Institude');

-- Create Buildings --
INSERT INTO public."Building" VALUES (1, 'Alumni Center', 1, '{}');
INSERT INTO public."Building" VALUES (2, 'Anheuser-Busch /agriculture science building', 2, '{}');
INSERT INTO public."Building" VALUES (3, 'Bingham Commons', 3, '{}');
INSERT INTO public."Building" VALUES (4, 'Black Culture Center', 4, '{}');
INSERT INTO public."Building" VALUES (5, 'Brooks Hall', 5, '{}');
INSERT INTO public."Building" VALUES (6, 'Center for Missouri Studies', 6, '{}');
INSERT INTO public."Building" VALUES (7, 'Center Hall', 7, '{}');
INSERT INTO public."Building" VALUES (8, 'Clydesdale hall', 8, '{}');
INSERT INTO public."Building" VALUES (9, 'College Ave', 9, '{}');
INSERT INTO public."Building" VALUES (10, 'Conley Ave Parking structure', 10, '{}');
INSERT INTO public."Building" VALUES (11, 'Connaway Hall', 11, '{}');
INSERT INTO public."Building" VALUES (12, 'Crowder Hall', 12, '{}');
INSERT INTO public."Building" VALUES (13, 'Defoe-Graham Hall', 13, '{}');
INSERT INTO public."Building" VALUES (14, 'Discovery Hall', 14, '{}');
INSERT INTO public."Building" VALUES (15, 'Dogwood Hall', 15, '{}');
INSERT INTO public."Building" VALUES (16, 'Eckles Hall', 16, '{}');
INSERT INTO public."Building" VALUES (17, 'Excellence Hall', 17, '{}');
INSERT INTO public."Building" VALUES (18, 'Galena', 18, '{}');
INSERT INTO public."Building" VALUES (19, 'Gannett', 19, '{}');
INSERT INTO public."Building" VALUES (20, 'Gateway hall', 20, '{}');
INSERT INTO public."Building" VALUES (21, 'Gillett Hall', 21, '{}');
INSERT INTO public."Building" VALUES (22, 'Hatch Hall', 22, '{}');
INSERT INTO public."Building" VALUES (23, 'Hawthorn', 23, '{}');
INSERT INTO public."Building" VALUES (24, 'Lottes Health Science Library', 24, '{}');
INSERT INTO public."Building" VALUES (25, 'Heinkel Building', 25, '{}');
INSERT INTO public."Building" VALUES (26, 'Hearnes Multipurpose Building', 26, '{}');
INSERT INTO public."Building" VALUES (27, 'Hitt Street Parking Structure', 27, '{}');
INSERT INTO public."Building" VALUES (28, 'Hospital', 28, '{}');
INSERT INTO public."Building" VALUES (29, 'Hudson', 29, '{}');
INSERT INTO public."Building" VALUES (30, 'Jesse Hall', 30, '{}');
INSERT INTO public."Building" VALUES (31, 'Johnston Hall', 31, '{}');
INSERT INTO public."Building" VALUES (32, 'Lee Hills Hall', 32, '{}');
INSERT INTO public."Building" VALUES (33, 'lefevre', 33, '{}');
INSERT INTO public."Building" VALUES (34, 'Lewis and Clark Hall', 34, '{}');
INSERT INTO public."Building" VALUES (35, 'Loeb', 35, '{}');
INSERT INTO public."Building" VALUES (36, 'Lowry', 36, '{}');
INSERT INTO public."Building" VALUES (37, 'Mark Twain', 37, '{}');
INSERT INTO public."Building" VALUES (38, 'Mathmatical sciences', 38, '{}');
INSERT INTO public."Building" VALUES (39, 'McDavid', 39, '{}');
INSERT INTO public."Building" VALUES (40, 'McKnee Gym', 40, '{}');
INSERT INTO public."Building" VALUES (41, 'McReynolds', 41, '{}');
INSERT INTO public."Building" VALUES (42, 'Mizzou Arena', 42, '{}');
INSERT INTO public."Building" VALUES (43, 'Memorial Stadium', 43, '{}');
INSERT INTO public."Building" VALUES (44, 'Naka Hall', 44, '{}');
INSERT INTO public."Building" VALUES (45, 'Neff', 45, '{}');
INSERT INTO public."Building" VALUES (46, 'north hall', 46, '{}');
INSERT INTO public."Building" VALUES (47, 'Parking structure 7', 47, '{}');
INSERT INTO public."Building" VALUES (48, 'pershing commons', 48, '{}');
INSERT INTO public."Building" VALUES (49, 'professional building', 49, '{}');
INSERT INTO public."Building" VALUES (50, 'psychology building', 50, '{}');
INSERT INTO public."Building" VALUES (51, 'respect', 51, '{}');
INSERT INTO public."Building" VALUES (52, 'Responsibility', 52, '{}');
INSERT INTO public."Building" VALUES (53, 'Rollins', 53, '{}');
INSERT INTO public."Building" VALUES (54, 'Schurz', 54, '{}');
INSERT INTO public."Building" VALUES (55, 'Schweitzer', 55, '{}');
INSERT INTO public."Building" VALUES (56, 'South hall', 56, '{}');
INSERT INTO public."Building" VALUES (57, 'Stankowski', 57, '{}');
INSERT INTO public."Building" VALUES (58, 'Stephens hall', 58, '{}');
INSERT INTO public."Building" VALUES (59, 'Student Rec', 59, '{}');
INSERT INTO public."Building" VALUES (60, 'Student Success center', 60, '{}');
INSERT INTO public."Building" VALUES (61, 'TAPS(tiger ave parking structure', 61, '{}');
INSERT INTO public."Building" VALUES (62, 'Theatre', 62, '{}');
INSERT INTO public."Building" VALUES (63, 'Tucker', 63, '{}');
INSERT INTO public."Building" VALUES (64, 'Turner Ave Parking Structure', 64, '{}');
INSERT INTO public."Building" VALUES (65, 'University ave parking structure', 65, '{}');
INSERT INTO public."Building" VALUES (66, 'Vet', 66, '{}');
INSERT INTO public."Building" VALUES (67, 'Wolpers', 67, '{}');
INSERT INTO public."Building" VALUES (68, 'Agricultural Engineering', 68, '{}');
INSERT INTO public."Building" VALUES (69, 'Pickard Hall', 69, '{}');
INSERT INTO public."Building" VALUES (70, 'Noyes Hall', 70, '{}');
INSERT INTO public."Building" VALUES (71, 'London Hall', 71, '{}');
INSERT INTO public."Building" VALUES (72, 'Walters Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (73, 'Geological Science', NULL, '{}');
INSERT INTO public."Building" VALUES (74, 'Academic Support Center', NULL, '{}');
INSERT INTO public."Building" VALUES (75, 'Natural Resources (Ag Science)', NULL, '{}');
INSERT INTO public."Building" VALUES (76, 'Curtis Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (77, 'Lafferre Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (78, 'Parker Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (79, 'Ellis Library', NULL, '{}');
INSERT INTO public."Building" VALUES (80, 'Herman Schlundt Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (81, 'Gwynn / Stanley Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (82, 'Student Center', NULL, '{}');
INSERT INTO public."Building" VALUES (83, 'Cornell Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (84, 'Townsend Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (85, 'Arts & Sciences', NULL, '{}');
INSERT INTO public."Building" VALUES (86, 'Hill Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (87, 'Hulston Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (88, 'Middlebush Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (89, 'Stewart Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (90, 'Chemistry Building', NULL, '{}');
INSERT INTO public."Building" VALUES (91, 'McAlester Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (92, 'Memorial Union', NULL, '{}');
INSERT INTO public."Building" VALUES (93, 'Mumford Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (94, 'Switzler Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (95, 'Tate Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (96, 'Strickland Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (97, 'Gentry Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (98, 'Fine Arts Building', NULL, '{}');
INSERT INTO public."Building" VALUES (99, 'Swallow Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (100, 'Southwest Village', NULL, '{}');
INSERT INTO public."Building" VALUES (101, 'Whitten Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (102, 'Physics Building', NULL, '{}');
INSERT INTO public."Building" VALUES (103, 'Life Sciences Center', NULL, '{}');
INSERT INTO public."Building" VALUES (104, 'Reynolds Journalism Institude', NULL, '{}');

-- Create Doors --
INSERT INTO public."Door" VALUES (1, 72, 72);
INSERT INTO public."Door" VALUES (2, 73, 72);
INSERT INTO public."Door" VALUES (3, 74, 72);
INSERT INTO public."Door" VALUES (4, 75, 73);
INSERT INTO public."Door" VALUES (5, 76, 73);
INSERT INTO public."Door" VALUES (6, 77, 74);
INSERT INTO public."Door" VALUES (7, 78, 74);
INSERT INTO public."Door" VALUES (8, 79, 74);
INSERT INTO public."Door" VALUES (9, 80, 75);
INSERT INTO public."Door" VALUES (10, 81, 75);
INSERT INTO public."Door" VALUES (11, 82, 75);
INSERT INTO public."Door" VALUES (12, 83, 75);
INSERT INTO public."Door" VALUES (13, 84, 75);
INSERT INTO public."Door" VALUES (14, 85, 75);
INSERT INTO public."Door" VALUES (15, 86, 76);
INSERT INTO public."Door" VALUES (16, 87, 76);
INSERT INTO public."Door" VALUES (17, 88, 76);
INSERT INTO public."Door" VALUES (18, 89, 77);
INSERT INTO public."Door" VALUES (19, 90, 77);
INSERT INTO public."Door" VALUES (20, 91, 77);
INSERT INTO public."Door" VALUES (21, 92, 77);
INSERT INTO public."Door" VALUES (22, 93, 77);
INSERT INTO public."Door" VALUES (23, 94, 78);
INSERT INTO public."Door" VALUES (24, 95, 78);
INSERT INTO public."Door" VALUES (25, 96, 79);
INSERT INTO public."Door" VALUES (26, 97, 79);
INSERT INTO public."Door" VALUES (27, 98, 80);
INSERT INTO public."Door" VALUES (28, 99, 80);
INSERT INTO public."Door" VALUES (29, 100, 80);
INSERT INTO public."Door" VALUES (30, 101, 81);
INSERT INTO public."Door" VALUES (31, 102, 81);
INSERT INTO public."Door" VALUES (32, 103, 81);
INSERT INTO public."Door" VALUES (33, 104, 81);
INSERT INTO public."Door" VALUES (34, 105, 82);
INSERT INTO public."Door" VALUES (35, 106, 82);
INSERT INTO public."Door" VALUES (36, 107, 82);
INSERT INTO public."Door" VALUES (37, 108, 82);
INSERT INTO public."Door" VALUES (38, 109, 83);
INSERT INTO public."Door" VALUES (39, 110, 83);
INSERT INTO public."Door" VALUES (40, 111, 83);
INSERT INTO public."Door" VALUES (41, 112, 84);
INSERT INTO public."Door" VALUES (42, 113, 84);
INSERT INTO public."Door" VALUES (43, 114, 84);
INSERT INTO public."Door" VALUES (44, 115, 84);
INSERT INTO public."Door" VALUES (45, 116, 84);
INSERT INTO public."Door" VALUES (46, 117, 85);
INSERT INTO public."Door" VALUES (47, 118, 85);
INSERT INTO public."Door" VALUES (48, 119, 85);
INSERT INTO public."Door" VALUES (49, 120, 85);
INSERT INTO public."Door" VALUES (50, 121, 86);
INSERT INTO public."Door" VALUES (51, 122, 86);
INSERT INTO public."Door" VALUES (52, 123, 87);
INSERT INTO public."Door" VALUES (53, 124, 87);
INSERT INTO public."Door" VALUES (54, 125, 87);
INSERT INTO public."Door" VALUES (55, 126, 88);
INSERT INTO public."Door" VALUES (56, 127, 88);
INSERT INTO public."Door" VALUES (57, 128, 88);
INSERT INTO public."Door" VALUES (58, 129, 88);
INSERT INTO public."Door" VALUES (59, 130, 88);
INSERT INTO public."Door" VALUES (60, 131, 89);
INSERT INTO public."Door" VALUES (61, 132, 89);
INSERT INTO public."Door" VALUES (62, 133, 90);
INSERT INTO public."Door" VALUES (63, 134, 90);
INSERT INTO public."Door" VALUES (64, 135, 90);
INSERT INTO public."Door" VALUES (65, 136, 90);
INSERT INTO public."Door" VALUES (66, 137, 91);
INSERT INTO public."Door" VALUES (67, 138, 91);
INSERT INTO public."Door" VALUES (68, 139, 91);
INSERT INTO public."Door" VALUES (69, 140, 92);
INSERT INTO public."Door" VALUES (70, 141, 92);
INSERT INTO public."Door" VALUES (71, 142, 92);
INSERT INTO public."Door" VALUES (72, 143, 93);
INSERT INTO public."Door" VALUES (73, 144, 93);
INSERT INTO public."Door" VALUES (74, 145, 93);
INSERT INTO public."Door" VALUES (75, 146, 94);
INSERT INTO public."Door" VALUES (76, 147, 94);
INSERT INTO public."Door" VALUES (77, 148, 95);
INSERT INTO public."Door" VALUES (78, 149, 95);
INSERT INTO public."Door" VALUES (79, 150, 95);
INSERT INTO public."Door" VALUES (80, 151, 95);
INSERT INTO public."Door" VALUES (81, 152, 95);
INSERT INTO public."Door" VALUES (82, 153, 96);
INSERT INTO public."Door" VALUES (83, 154, 96);
INSERT INTO public."Door" VALUES (84, 155, 96);
INSERT INTO public."Door" VALUES (85, 156, 97);
INSERT INTO public."Door" VALUES (86, 157, 97);
INSERT INTO public."Door" VALUES (87, 158, 97);
INSERT INTO public."Door" VALUES (88, 159, 97);
INSERT INTO public."Door" VALUES (89, 160, 97);
INSERT INTO public."Door" VALUES (90, 161, 98);
INSERT INTO public."Door" VALUES (91, 162, 98);
INSERT INTO public."Door" VALUES (92, 163, 98);
INSERT INTO public."Door" VALUES (93, 164, 98);
INSERT INTO public."Door" VALUES (94, 165, 98);
INSERT INTO public."Door" VALUES (95, 166, 98);
INSERT INTO public."Door" VALUES (96, 167, 99);
INSERT INTO public."Door" VALUES (97, 168, 99);
INSERT INTO public."Door" VALUES (98, 169, 99);
INSERT INTO public."Door" VALUES (99, 170, 99);
INSERT INTO public."Door" VALUES (100, 171, 100);
INSERT INTO public."Door" VALUES (101, 172, 100);
INSERT INTO public."Door" VALUES (102, 173, 101);
INSERT INTO public."Door" VALUES (103, 174, 101);
INSERT INTO public."Door" VALUES (104, 175, 102);
INSERT INTO public."Door" VALUES (105, 176, 102);
INSERT INTO public."Door" VALUES (106, 177, 103);
INSERT INTO public."Door" VALUES (107, 178, 103);
INSERT INTO public."Door" VALUES (108, 179, 103);
INSERT INTO public."Door" VALUES (109, 180, 103);
INSERT INTO public."Door" VALUES (110, 181, 103);
INSERT INTO public."Door" VALUES (111, 182, 103);
INSERT INTO public."Door" VALUES (112, 183, 104);
INSERT INTO public."Door" VALUES (113, 184, 104);
INSERT INTO public."Door" VALUES (114, 185, 104);
INSERT INTO public."Door" VALUES (115, 186, 104);

-- Create GraphEdges --
INSERT INTO public."GraphEdge" VALUES (1, 80, 81, '', '', true, 179.66667, '{}');
INSERT INTO public."GraphEdge" VALUES (2, 80, 82, '', '', true, 315, '{}');
INSERT INTO public."GraphEdge" VALUES (3, 80, 83, '', '', true, 380.33334, '{}');
INSERT INTO public."GraphEdge" VALUES (4, 80, 84, '', '', true, 555.3333, '{}');
INSERT INTO public."GraphEdge" VALUES (5, 80, 85, '', '', true, 788.6667, '{}');
INSERT INTO public."GraphEdge" VALUES (6, 81, 83, '', '', true, 315, '{}');
INSERT INTO public."GraphEdge" VALUES (7, 81, 84, '', '', true, 357, '{}');
INSERT INTO public."GraphEdge" VALUES (8, 81, 85, '', '', true, 532, '{}');
INSERT INTO public."GraphEdge" VALUES (9, 81, 82, '', '', true, 765.3, '{}');
INSERT INTO public."GraphEdge" VALUES (10, 82, 83, '', '', true, 198.3, '{}');
INSERT INTO public."GraphEdge" VALUES (11, 82, 84, '', '', true, 380.3, '{}');
INSERT INTO public."GraphEdge" VALUES (12, 82, 85, '', '', true, 613.7, '{}');
INSERT INTO public."GraphEdge" VALUES (13, 83, 84, '', '', true, 245, '{}');
INSERT INTO public."GraphEdge" VALUES (14, 83, 85, '', '', true, 478.3, '{}');
INSERT INTO public."GraphEdge" VALUES (15, 84, 85, '', '', true, 245, '{}');
INSERT INTO public."GraphEdge" VALUES (16, 175, 176, '', '', true, 158.66667, '{}');
INSERT INTO public."GraphEdge" VALUES (17, 177, 178, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (18, 177, 179, '', '', true, 389.7, '{}');
INSERT INTO public."GraphEdge" VALUES (19, 177, 180, '', '', true, 196, '{}');
INSERT INTO public."GraphEdge" VALUES (20, 177, 181, '', '', true, 301, '{}');
INSERT INTO public."GraphEdge" VALUES (21, 177, 182, '', '', true, 214.7, '{}');
INSERT INTO public."GraphEdge" VALUES (22, 178, 179, '', '', true, 203, '{}');
INSERT INTO public."GraphEdge" VALUES (23, 178, 180, '', '', true, 107.3, '{}');
INSERT INTO public."GraphEdge" VALUES (24, 178, 181, '', '', true, 261.3, '{}');
INSERT INTO public."GraphEdge" VALUES (25, 178, 182, '', '', true, 347.7, '{}');
INSERT INTO public."GraphEdge" VALUES (26, 179, 180, '', '', true, 210, '{}');
INSERT INTO public."GraphEdge" VALUES (27, 179, 181, '', '', true, 380.3, '{}');
INSERT INTO public."GraphEdge" VALUES (28, 179, 182, '', '', true, 452.7, '{}');
INSERT INTO public."GraphEdge" VALUES (29, 180, 181, '', '', true, 170.3, '{}');
INSERT INTO public."GraphEdge" VALUES (30, 180, 182, '', '', true, 242.7, '{}');
INSERT INTO public."GraphEdge" VALUES (31, 181, 182, '', '', true, 86.3, '{}');
INSERT INTO public."GraphEdge" VALUES (32, 156, 157, '', '', true, 126, '{}');
INSERT INTO public."GraphEdge" VALUES (33, 156, 158, '', '', true, 170.3, '{}');
INSERT INTO public."GraphEdge" VALUES (34, 156, 159, '', '', true, 238, '{}');
INSERT INTO public."GraphEdge" VALUES (35, 156, 160, '', '', true, 60.7, '{}');
INSERT INTO public."GraphEdge" VALUES (36, 157, 158, '', '', true, 86.3, '{}');
INSERT INTO public."GraphEdge" VALUES (37, 157, 159, '', '', true, 154, '{}');
INSERT INTO public."GraphEdge" VALUES (38, 157, 160, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (39, 158, 159, '', '', true, 128.3, '{}');
INSERT INTO public."GraphEdge" VALUES (40, 158, 160, '', '', true, 231, '{}');
INSERT INTO public."GraphEdge" VALUES (41, 159, 160, '', '', true, 298.7, '{}');
INSERT INTO public."GraphEdge" VALUES (42, 98, 99, '', '', true, 98, '{}');
INSERT INTO public."GraphEdge" VALUES (43, 98, 100, '', '', true, 130.7, '{}');
INSERT INTO public."GraphEdge" VALUES (44, 99, 100, '', '', true, 102.7, '{}');
INSERT INTO public."GraphEdge" VALUES (45, 101, 102, '', '', true, 163.3, '{}');
INSERT INTO public."GraphEdge" VALUES (46, 101, 103, '', '', true, 270.7, '{}');
INSERT INTO public."GraphEdge" VALUES (47, 101, 104, '', '', true, 457.3, '{}');
INSERT INTO public."GraphEdge" VALUES (48, 102, 103, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (49, 102, 104, '', '', true, 373.3, '{}');
INSERT INTO public."GraphEdge" VALUES (50, 103, 104, '', '', true, 242.7, '{}');
INSERT INTO public."GraphEdge" VALUES (51, 86, 87, '', '', true, 98, '{}');
INSERT INTO public."GraphEdge" VALUES (52, 86, 88, '', '', true, 151.7, '{}');
INSERT INTO public."GraphEdge" VALUES (53, 87, 88, '', '', true, 114.3, '{}');
INSERT INTO public."GraphEdge" VALUES (54, 105, 106, '', '', true, 326, '{}');
INSERT INTO public."GraphEdge" VALUES (55, 105, 107, '', '', true, 270, '{}');
INSERT INTO public."GraphEdge" VALUES (56, 105, 108, '', '', true, 211, '{}');
INSERT INTO public."GraphEdge" VALUES (57, 106, 107, '', '', true, 190, '{}');
INSERT INTO public."GraphEdge" VALUES (58, 106, 108, '', '', true, 349, '{}');
INSERT INTO public."GraphEdge" VALUES (59, 107, 108, '', '', true, 214, '{}');
INSERT INTO public."GraphEdge" VALUES (60, 153, 154, '', '', true, 204, '{}');
INSERT INTO public."GraphEdge" VALUES (61, 153, 155, '', '', true, 320, '{}');
INSERT INTO public."GraphEdge" VALUES (62, 154, 155, '', '', true, 111, '{}');
INSERT INTO public."GraphEdge" VALUES (63, 109, 110, '', '', true, 274, '{}');
INSERT INTO public."GraphEdge" VALUES (64, 109, 111, '', '', true, 429, '{}');
INSERT INTO public."GraphEdge" VALUES (65, 110, 111, '', '', true, 116, '{}');
INSERT INTO public."GraphEdge" VALUES (66, 117, 118, '', '', true, 48, '{}');
INSERT INTO public."GraphEdge" VALUES (67, 117, 119, '', '', true, 173, '{}');
INSERT INTO public."GraphEdge" VALUES (68, 117, 120, '', '', true, 180, '{}');
INSERT INTO public."GraphEdge" VALUES (69, 118, 119, '', '', true, 175, '{}');
INSERT INTO public."GraphEdge" VALUES (70, 118, 120, '', '', true, 179, '{}');
INSERT INTO public."GraphEdge" VALUES (71, 119, 120, '', '', true, 38, '{}');
INSERT INTO public."GraphEdge" VALUES (72, 123, 124, '', '', true, 50, '{}');
INSERT INTO public."GraphEdge" VALUES (73, 123, 125, '', '', true, 203, '{}');
INSERT INTO public."GraphEdge" VALUES (74, 124, 125, '', '', true, 228, '{}');
INSERT INTO public."GraphEdge" VALUES (75, 96, 97, '', '', true, 216, '{}');
INSERT INTO public."GraphEdge" VALUES (76, 148, 149, '', '', true, 276, '{}');
INSERT INTO public."GraphEdge" VALUES (77, 148, 150, '', '', true, 191, '{}');
INSERT INTO public."GraphEdge" VALUES (78, 148, 151, '', '', true, 207, '{}');
INSERT INTO public."GraphEdge" VALUES (79, 148, 152, '', '', true, 261, '{}');
INSERT INTO public."GraphEdge" VALUES (80, 149, 150, '', '', true, 108, '{}');
INSERT INTO public."GraphEdge" VALUES (81, 149, 151, '', '', true, 116, '{}');
INSERT INTO public."GraphEdge" VALUES (82, 149, 152, '', '', true, 133, '{}');
INSERT INTO public."GraphEdge" VALUES (83, 150, 151, '', '', true, 45, '{}');
INSERT INTO public."GraphEdge" VALUES (84, 150, 152, '', '', true, 113, '{}');
INSERT INTO public."GraphEdge" VALUES (85, 151, 152, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (86, 137, 138, '', '', true, 118.1104, '{}');
INSERT INTO public."GraphEdge" VALUES (87, 137, 139, '', '', true, 70.86624, '{}');
INSERT INTO public."GraphEdge" VALUES (88, 138, 139, '', '', true, 129.92143, '{}');
INSERT INTO public."GraphEdge" VALUES (89, 94, 95, '', '', true, 59.0552, '{}');
INSERT INTO public."GraphEdge" VALUES (90, 89, 90, '', '', true, 221.457, '{}');
INSERT INTO public."GraphEdge" VALUES (91, 89, 91, '', '', true, 261.31927, '{}');
INSERT INTO public."GraphEdge" VALUES (92, 89, 92, '', '', true, 468.01245, '{}');
INSERT INTO public."GraphEdge" VALUES (93, 89, 93, '', '', true, 283.46497, '{}');
INSERT INTO public."GraphEdge" VALUES (94, 90, 91, '', '', true, 200.78767, '{}');
INSERT INTO public."GraphEdge" VALUES (95, 90, 92, '', '', true, 354.3312, '{}');
INSERT INTO public."GraphEdge" VALUES (96, 90, 93, '', '', true, 177.1656, '{}');
INSERT INTO public."GraphEdge" VALUES (97, 91, 92, '', '', true, 320.37445, '{}');
INSERT INTO public."GraphEdge" VALUES (98, 91, 93, '', '', true, 236.2208, '{}');
INSERT INTO public."GraphEdge" VALUES (99, 92, 93, '', '', true, 305.61066, '{}');
INSERT INTO public."GraphEdge" VALUES (100, 171, 172, '', '', true, 170, '{}');
INSERT INTO public."GraphEdge" VALUES (101, 133, 134, '', '', true, 133, '{}');
INSERT INTO public."GraphEdge" VALUES (102, 135, 136, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (103, 131, 132, '', '', true, 175, '{}');
INSERT INTO public."GraphEdge" VALUES (104, 72, 73, '', '', true, 152.5, '{}');
INSERT INTO public."GraphEdge" VALUES (105, 72, 74, '', '', true, 152.5, '{}');
INSERT INTO public."GraphEdge" VALUES (106, 73, 74, '', '', true, 52.5, '{}');
INSERT INTO public."GraphEdge" VALUES (107, 143, 144, '', '', true, 90, '{}');
INSERT INTO public."GraphEdge" VALUES (108, 143, 145, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (109, 144, 145, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (110, 161, 162, '', '', true, 45, '{}');
INSERT INTO public."GraphEdge" VALUES (111, 163, 164, '', '', true, 60, '{}');
INSERT INTO public."GraphEdge" VALUES (112, 163, 165, '', '', true, 100, '{}');
INSERT INTO public."GraphEdge" VALUES (113, 163, 166, '', '', true, 102.5, '{}');
INSERT INTO public."GraphEdge" VALUES (114, 164, 165, '', '', true, 40, '{}');
INSERT INTO public."GraphEdge" VALUES (115, 164, 166, '', '', true, 100, '{}');
INSERT INTO public."GraphEdge" VALUES (116, 165, 166, '', '', true, 60, '{}');
INSERT INTO public."GraphEdge" VALUES (117, 140, 141, '', '', true, 142.5, '{}');
INSERT INTO public."GraphEdge" VALUES (118, 140, 142, '', '', true, 197.5, '{}');
INSERT INTO public."GraphEdge" VALUES (119, 141, 142, '', '', true, 222.5, '{}');
INSERT INTO public."GraphEdge" VALUES (120, 173, 174, '', '', true, 80, '{}');
INSERT INTO public."GraphEdge" VALUES (121, 126, 127, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (122, 126, 128, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (123, 126, 129, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (124, 126, 130, '', '', true, 350, '{}');
INSERT INTO public."GraphEdge" VALUES (125, 127, 128, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (126, 127, 129, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (127, 127, 130, '', '', true, 350, '{}');
INSERT INTO public."GraphEdge" VALUES (128, 128, 129, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (129, 128, 130, '', '', true, 135, '{}');
INSERT INTO public."GraphEdge" VALUES (130, 129, 130, '', '', true, 162.5, '{}');
INSERT INTO public."GraphEdge" VALUES (131, 167, 169, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (132, 167, 170, '', '', true, 97.5, '{}');
INSERT INTO public."GraphEdge" VALUES (133, 168, 169, '', '', true, 97.5, '{}');
INSERT INTO public."GraphEdge" VALUES (134, 168, 170, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (135, 183, 184, '', '', true, 50, '{}');
INSERT INTO public."GraphEdge" VALUES (136, 183, 185, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (137, 183, 186, '', '', true, 132.5, '{}');
INSERT INTO public."GraphEdge" VALUES (138, 184, 185, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (139, 184, 186, '', '', true, 132.5, '{}');
INSERT INTO public."GraphEdge" VALUES (140, 185, 186, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (141, 75, 76, '', '', true, 274, '{}');
INSERT INTO public."GraphEdge" VALUES (142, 146, 147, '', '', true, 101, '{}');
INSERT INTO public."GraphEdge" VALUES (143, 121, 122, '', '', true, 138, '{}');
INSERT INTO public."GraphEdge" VALUES (144, 112, 113, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (145, 112, 114, '', '', true, 207.5, '{}');
INSERT INTO public."GraphEdge" VALUES (146, 112, 115, '', '', true, 262.5, '{}');
INSERT INTO public."GraphEdge" VALUES (147, 112, 116, '', '', true, 250, '{}');
INSERT INTO public."GraphEdge" VALUES (148, 113, 114, '', '', true, 200, '{}');
INSERT INTO public."GraphEdge" VALUES (149, 113, 115, '', '', true, 273, '{}');
INSERT INTO public."GraphEdge" VALUES (150, 113, 116, '', '', true, 262.5, '{}');
INSERT INTO public."GraphEdge" VALUES (151, 114, 115, '', '', true, 200, '{}');
INSERT INTO public."GraphEdge" VALUES (152, 114, 116, '', '', true, 200, '{}');
INSERT INTO public."GraphEdge" VALUES (153, 115, 116, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (154, 77, 78, '', '', true, 261, '{}');
INSERT INTO public."GraphEdge" VALUES (155, 77, 79, '', '', true, 124, '{}');
INSERT INTO public."GraphEdge" VALUES (156, 78, 79, '', '', true, 228, '{}');