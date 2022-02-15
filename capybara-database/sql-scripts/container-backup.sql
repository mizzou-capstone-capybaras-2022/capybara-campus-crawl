--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2 (Debian 14.2-1.pgdg110+1)
-- Dumped by pg_dump version 14.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: capy
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Building; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."Building" (
    "BuildingID" integer NOT NULL,
    name character varying,
    "NodeID" integer,
    geojson text
);


ALTER TABLE public."Building" OWNER TO capy;

--
-- Name: Building_BuildingID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."Building_BuildingID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Building_BuildingID_seq" OWNER TO capy;

--
-- Name: Building_BuildingID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Building_BuildingID_seq" OWNED BY public."Building"."BuildingID";


--
-- Name: Door; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."Door" (
    "DoorID" integer NOT NULL,
    "NodeID" integer NOT NULL,
    "BuildingID" integer NOT NULL
);


ALTER TABLE public."Door" OWNER TO capy;

--
-- Name: Door_DoorID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."Door_DoorID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Door_DoorID_seq" OWNER TO capy;

--
-- Name: Door_DoorID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Door_DoorID_seq" OWNED BY public."Door"."DoorID";


--
-- Name: GraphEdge; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."GraphEdge" (
    "EdgeID" integer NOT NULL,
    "Node1ID" integer NOT NULL,
    "Node2ID" integer NOT NULL,
    fromtoaction character varying,
    tofromaction character varying,
    bidirectional boolean DEFAULT false,
    distance real,
    pathshape text
);


ALTER TABLE public."GraphEdge" OWNER TO capy;

--
-- Name: GraphEdge_EdgeID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."GraphEdge_EdgeID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."GraphEdge_EdgeID_seq" OWNER TO capy;

--
-- Name: GraphEdge_EdgeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."GraphEdge_EdgeID_seq" OWNED BY public."GraphEdge"."EdgeID";


--
-- Name: GraphNode; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."GraphNode" (
    "NodeID" integer NOT NULL,
    latitude double precision,
    longitude double precision,
    description character varying
);


ALTER TABLE public."GraphNode" OWNER TO capy;

--
-- Name: GraphNode_NodeID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."GraphNode_NodeID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."GraphNode_NodeID_seq" OWNER TO capy;

--
-- Name: GraphNode_NodeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."GraphNode_NodeID_seq" OWNED BY public."GraphNode"."NodeID";


--
-- Name: PIMetrics; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."PIMetrics" (
    "MetricsID" integer NOT NULL,
    "NodeID" integer NOT NULL,
    "Time" timestamp with time zone,
    "Intensity" integer
);


ALTER TABLE public."PIMetrics" OWNER TO capy;

--
-- Name: PIMetrics_MetricsID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."PIMetrics_MetricsID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."PIMetrics_MetricsID_seq" OWNER TO capy;

--
-- Name: PIMetrics_MetricsID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."PIMetrics_MetricsID_seq" OWNED BY public."PIMetrics"."MetricsID";


--
-- Name: PathEdges; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."PathEdges" (
    "PathEdgeID" integer NOT NULL,
    "SavedPathID" integer NOT NULL,
    "EdgeID" integer NOT NULL
);


ALTER TABLE public."PathEdges" OWNER TO capy;

--
-- Name: PathEdges_PathEdgeID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."PathEdges_PathEdgeID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."PathEdges_PathEdgeID_seq" OWNER TO capy;

--
-- Name: PathEdges_PathEdgeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."PathEdges_PathEdgeID_seq" OWNED BY public."PathEdges"."PathEdgeID";


--
-- Name: Place; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."Place" (
    "PlaceID" integer NOT NULL,
    "NodeID" integer NOT NULL,
    "Name" character varying,
    "PlaceType" character varying
);


ALTER TABLE public."Place" OWNER TO capy;

--
-- Name: Place_PlaceID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."Place_PlaceID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Place_PlaceID_seq" OWNER TO capy;

--
-- Name: Place_PlaceID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Place_PlaceID_seq" OWNED BY public."Place"."PlaceID";


--
-- Name: Room; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."Room" (
    "RoomID" integer NOT NULL,
    "NodeID" integer NOT NULL,
    "BuildingID" integer NOT NULL,
    "Name" character varying,
    "RoomNumber" integer
);


ALTER TABLE public."Room" OWNER TO capy;

--
-- Name: Room_RoomID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."Room_RoomID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Room_RoomID_seq" OWNER TO capy;

--
-- Name: Room_RoomID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Room_RoomID_seq" OWNED BY public."Room"."RoomID";


--
-- Name: SavedPath; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."SavedPath" (
    "SavedPathID" integer NOT NULL,
    "endingNodeID" integer NOT NULL,
    "startingNodeID" integer NOT NULL,
    distance real
);


ALTER TABLE public."SavedPath" OWNER TO capy;

--
-- Name: SavedPath_SavedPathID_seq; Type: SEQUENCE; Schema: public; Owner: capy
--

CREATE SEQUENCE public."SavedPath_SavedPathID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."SavedPath_SavedPathID_seq" OWNER TO capy;

--
-- Name: SavedPath_SavedPathID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."SavedPath_SavedPathID_seq" OWNED BY public."SavedPath"."SavedPathID";


--
-- Name: Building BuildingID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building" ALTER COLUMN "BuildingID" SET DEFAULT nextval('public."Building_BuildingID_seq"'::regclass);


--
-- Name: Door DoorID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door" ALTER COLUMN "DoorID" SET DEFAULT nextval('public."Door_DoorID_seq"'::regclass);


--
-- Name: GraphEdge EdgeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge" ALTER COLUMN "EdgeID" SET DEFAULT nextval('public."GraphEdge_EdgeID_seq"'::regclass);


--
-- Name: GraphNode NodeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphNode" ALTER COLUMN "NodeID" SET DEFAULT nextval('public."GraphNode_NodeID_seq"'::regclass);


--
-- Name: PIMetrics MetricsID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics" ALTER COLUMN "MetricsID" SET DEFAULT nextval('public."PIMetrics_MetricsID_seq"'::regclass);


--
-- Name: PathEdges PathEdgeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges" ALTER COLUMN "PathEdgeID" SET DEFAULT nextval('public."PathEdges_PathEdgeID_seq"'::regclass);


--
-- Name: Place PlaceID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place" ALTER COLUMN "PlaceID" SET DEFAULT nextval('public."Place_PlaceID_seq"'::regclass);


--
-- Name: Room RoomID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room" ALTER COLUMN "RoomID" SET DEFAULT nextval('public."Room_RoomID_seq"'::regclass);


--
-- Name: SavedPath SavedPathID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath" ALTER COLUMN "SavedPathID" SET DEFAULT nextval('public."SavedPath_SavedPathID_seq"'::regclass);


--
-- Data for Name: Building; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."Building" VALUES (68, 'Alumni Center', 68, '{}');
INSERT INTO public."Building" VALUES (69, 'Anheuser-Busch /agriculture science building', 69, '{}');
INSERT INTO public."Building" VALUES (70, 'Bingham Commons', 70, '{}');
INSERT INTO public."Building" VALUES (71, 'Black Culture Center', 71, '{}');
INSERT INTO public."Building" VALUES (72, 'Brooks Hall', 72, '{}');
INSERT INTO public."Building" VALUES (73, 'Center for Missouri Studies', 73, '{}');
INSERT INTO public."Building" VALUES (74, 'Center Hall', 74, '{}');
INSERT INTO public."Building" VALUES (75, 'Clydesdale hall', 75, '{}');
INSERT INTO public."Building" VALUES (76, 'College Ave', 76, '{}');
INSERT INTO public."Building" VALUES (77, 'Conley Ave Parking structure', 77, '{}');
INSERT INTO public."Building" VALUES (78, 'Connaway Hall', 78, '{}');
INSERT INTO public."Building" VALUES (79, 'Crowder Hall', 79, '{}');
INSERT INTO public."Building" VALUES (80, 'Defoe-Graham Hall', 80, '{}');
INSERT INTO public."Building" VALUES (81, 'Discovery Hall', 81, '{}');
INSERT INTO public."Building" VALUES (82, 'Dogwood Hall', 82, '{}');
INSERT INTO public."Building" VALUES (83, 'Eckles Hall', 83, '{}');
INSERT INTO public."Building" VALUES (84, 'Excellence Hall', 84, '{}');
INSERT INTO public."Building" VALUES (85, 'Galena', 85, '{}');
INSERT INTO public."Building" VALUES (86, 'Gannett', 86, '{}');
INSERT INTO public."Building" VALUES (87, 'Gateway hall', 87, '{}');
INSERT INTO public."Building" VALUES (88, 'Gillett Hall', 88, '{}');
INSERT INTO public."Building" VALUES (89, 'Hatch Hall', 89, '{}');
INSERT INTO public."Building" VALUES (90, 'Hawthorn', 90, '{}');
INSERT INTO public."Building" VALUES (91, 'Lottes Health Science Library', 91, '{}');
INSERT INTO public."Building" VALUES (92, 'Heinkel Building', 92, '{}');
INSERT INTO public."Building" VALUES (93, 'Hearnes Multipurpose Building', 93, '{}');
INSERT INTO public."Building" VALUES (94, 'Hitt Street Parking Structure', 94, '{}');
INSERT INTO public."Building" VALUES (95, 'Hospital', 95, '{}');
INSERT INTO public."Building" VALUES (96, 'Hudson', 96, '{}');
INSERT INTO public."Building" VALUES (97, 'Jesse Hall', 97, '{}');
INSERT INTO public."Building" VALUES (98, 'Johnston Hall', 98, '{}');
INSERT INTO public."Building" VALUES (99, 'Lee Hills Hall', 99, '{}');
INSERT INTO public."Building" VALUES (100, 'lefevre', 100, '{}');
INSERT INTO public."Building" VALUES (101, 'Lewis and Clark Hall', 101, '{}');
INSERT INTO public."Building" VALUES (102, 'Loeb', 102, '{}');
INSERT INTO public."Building" VALUES (103, 'Lowry', 103, '{}');
INSERT INTO public."Building" VALUES (104, 'Mark Twain', 104, '{}');
INSERT INTO public."Building" VALUES (105, 'Mathmatical sciences', 105, '{}');
INSERT INTO public."Building" VALUES (106, 'McDavid', 106, '{}');
INSERT INTO public."Building" VALUES (107, 'McKnee Gym', 107, '{}');
INSERT INTO public."Building" VALUES (108, 'McReynolds', 108, '{}');
INSERT INTO public."Building" VALUES (109, 'Mizzou Arena', 109, '{}');
INSERT INTO public."Building" VALUES (110, 'Memorial Stadium', 110, '{}');
INSERT INTO public."Building" VALUES (111, 'Naka Hall', 111, '{}');
INSERT INTO public."Building" VALUES (112, 'Neff', 112, '{}');
INSERT INTO public."Building" VALUES (113, 'north hall', 113, '{}');
INSERT INTO public."Building" VALUES (114, 'Parking structure 7', 114, '{}');
INSERT INTO public."Building" VALUES (115, 'pershing commons', 115, '{}');
INSERT INTO public."Building" VALUES (116, 'professional building', 116, '{}');
INSERT INTO public."Building" VALUES (117, 'psychology building', 117, '{}');
INSERT INTO public."Building" VALUES (118, 'respect', 118, '{}');
INSERT INTO public."Building" VALUES (119, 'Responsibility', 119, '{}');
INSERT INTO public."Building" VALUES (120, 'Rollins', 120, '{}');
INSERT INTO public."Building" VALUES (121, 'Schurz', 121, '{}');
INSERT INTO public."Building" VALUES (122, 'Schweitzer', 122, '{}');
INSERT INTO public."Building" VALUES (123, 'South hall', 123, '{}');
INSERT INTO public."Building" VALUES (124, 'Stankowski', 124, '{}');
INSERT INTO public."Building" VALUES (125, 'Stephens hall', 125, '{}');
INSERT INTO public."Building" VALUES (126, 'Student Rec', 126, '{}');
INSERT INTO public."Building" VALUES (127, 'Student Success center', 127, '{}');
INSERT INTO public."Building" VALUES (128, 'TAPS(tiger ave parking structure', 128, '{}');
INSERT INTO public."Building" VALUES (129, 'Theatre', 129, '{}');
INSERT INTO public."Building" VALUES (130, 'Tucker', 130, '{}');
INSERT INTO public."Building" VALUES (131, 'Turner Ave Parking Structure', 131, '{}');
INSERT INTO public."Building" VALUES (132, 'University ave parking structure', 132, '{}');
INSERT INTO public."Building" VALUES (133, 'Vet', 133, '{}');
INSERT INTO public."Building" VALUES (134, 'Wolpers', 134, '{}');


--
-- Data for Name: Door; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: GraphEdge; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: GraphNode; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."GraphNode" VALUES (68, 38.9437187, -92.32955217, 'The Mizzou Alumni Center');
INSERT INTO public."GraphNode" VALUES (69, 38.94271735, -92.32471347, 'Home of College of Agriculture');
INSERT INTO public."GraphNode" VALUES (70, 38.94038395, -92.32093289, 'Dining and Fine Arts');
INSERT INTO public."GraphNode" VALUES (71, 38.94117984, -92.32417703, 'GOBCC works to make the University of Missouri and Columbia community a more inclusive and welcoming environment for diverse populations through education, outreach, and collaboration.');
INSERT INTO public."GraphNode" VALUES (72, 38.93871078, -92.33204395, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (73, 38.94878467, -92.33031929, 'State Historical Society');
INSERT INTO public."GraphNode" VALUES (74, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (75, 38.94003555, -92.31746614, 'Veterinary Medicine & Surgery');
INSERT INTO public."GraphNode" VALUES (76, 38.94101503, -92.32136071, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (77, 38.94514142, -92.33156919, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (78, 38.94265059, -92.31871605, 'Office of Animal Resources');
INSERT INTO public."GraphNode" VALUES (79, 38.93985092, -92.3298955, 'Military Science');
INSERT INTO public."GraphNode" VALUES (80, 38.94010544, -92.32614577, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (81, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (82, 38.93962977, -92.3268646, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (83, 38.94167427, -92.32031465, 'Food Science');
INSERT INTO public."GraphNode" VALUES (84, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (85, 38.94011587, -92.32722133, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (86, 38.94767388, -92.3284927, 'Journalism');
INSERT INTO public."GraphNode" VALUES (87, 38.93858664, -92.32290566, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (88, 38.94131544, -92.32310414, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (89, 38.94066872, -92.32037902, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (90, 38.94042672, -92.3265481, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (91, 38.93983214, -92.32866168, 'Health Sciences Library');
INSERT INTO public."GraphNode" VALUES (92, 38.94920917, -92.33009934, 'Missouri Press');
INSERT INTO public."GraphNode" VALUES (93, 38.9343013, -92.3306036, 'Intercollegiate Athletics');
INSERT INTO public."GraphNode" VALUES (94, 38.94758627, -92.32615113, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (95, 38.9386993, -92.32809305, 'Hospital');
INSERT INTO public."GraphNode" VALUES (96, 38.9417452, -92.32253551, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (97, 38.94521756, -92.32870594, 'main administration building for the University of Missouri.');
INSERT INTO public."GraphNode" VALUES (98, 38.94189957, -92.32454181, 'Dining and Res Life');
INSERT INTO public."GraphNode" VALUES (99, 38.94873148, -92.32917398, 'Journalism');
INSERT INTO public."GraphNode" VALUES (100, 38.94606138, -92.32293248, 'Biological Science');
INSERT INTO public."GraphNode" VALUES (101, 38.94331399, -92.33329654, 'Health Science');
INSERT INTO public."GraphNode" VALUES (102, 38.9481, -92.3317, '');
INSERT INTO public."GraphNode" VALUES (103, 38.9453, -92.3273, '');
INSERT INTO public."GraphNode" VALUES (104, 38.9453, -92.3327, 'Res Life and dining');
INSERT INTO public."GraphNode" VALUES (105, 38.9478, -92.3281, 'lots of Teacher offices');
INSERT INTO public."GraphNode" VALUES (106, 38.9478, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (107, 38.9435, -92.3252, 'grad student offices / athletic reaserch');
INSERT INTO public."GraphNode" VALUES (108, 38.9484, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (109, 38.9325, -92.3338, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (110, 38.936, -92.3332, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (111, 38.9467, -92.3314, 'Engineering');
INSERT INTO public."GraphNode" VALUES (112, 38.9478, -92.3282, '');
INSERT INTO public."GraphNode" VALUES (113, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (114, 38.937, -92.3252, 'Parking');
INSERT INTO public."GraphNode" VALUES (115, 38.9395, -92.326, 'hitt street market. Student grocerry');
INSERT INTO public."GraphNode" VALUES (116, 38.9468, -92.3267, 'teacher offices');
INSERT INTO public."GraphNode" VALUES (117, 38.9492, -92.3295, '');
INSERT INTO public."GraphNode" VALUES (118, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (119, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (120, 38.9417, -92.323, 'dining hall');
INSERT INTO public."GraphNode" VALUES (121, 38.94, -92.3213, 'residential Life');
INSERT INTO public."GraphNode" VALUES (122, 38.9454, -92.3225, '');
INSERT INTO public."GraphNode" VALUES (123, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (124, 38.9408, -92.3292, 'rec sports field');
INSERT INTO public."GraphNode" VALUES (125, 38.9459, -92.3224, '');
INSERT INTO public."GraphNode" VALUES (126, 38.9414, -92.3266, 'The rec');
INSERT INTO public."GraphNode" VALUES (127, 38.9453, -92.3267, '');
INSERT INTO public."GraphNode" VALUES (128, 38.9385, -92.3302, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (129, 38.9492, -92.3279, '');
INSERT INTO public."GraphNode" VALUES (130, 38.9437, -92.3242, '');
INSERT INTO public."GraphNode" VALUES (131, 38.9438, -92.3308, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (132, 38.9471, -92.3239, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (133, 38.9413, -92.3176, 'University Vet');
INSERT INTO public."GraphNode" VALUES (134, 38.9415, -92.325, 'Residential Life');


--
-- Data for Name: PIMetrics; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: PathEdges; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: Place; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: Room; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Data for Name: SavedPath; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- Name: Building_BuildingID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Building_BuildingID_seq"', 134, true);


--
-- Name: Door_DoorID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Door_DoorID_seq"', 1, false);


--
-- Name: GraphEdge_EdgeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."GraphEdge_EdgeID_seq"', 1, false);


--
-- Name: GraphNode_NodeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."GraphNode_NodeID_seq"', 134, true);


--
-- Name: PIMetrics_MetricsID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."PIMetrics_MetricsID_seq"', 1, false);


--
-- Name: PathEdges_PathEdgeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."PathEdges_PathEdgeID_seq"', 1, false);


--
-- Name: Place_PlaceID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Place_PlaceID_seq"', 1, false);


--
-- Name: Room_RoomID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Room_RoomID_seq"', 1, false);


--
-- Name: SavedPath_SavedPathID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."SavedPath_SavedPathID_seq"', 1, false);


--
-- Name: Building Building_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building"
    ADD CONSTRAINT "Building_pkey" PRIMARY KEY ("BuildingID");


--
-- Name: Door Door_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT "Door_NodeID_key" UNIQUE ("NodeID");


--
-- Name: Door Door_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT "Door_pkey" PRIMARY KEY ("DoorID");


--
-- Name: GraphEdge GraphEdge_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT "GraphEdge_pkey" PRIMARY KEY ("EdgeID");


--
-- Name: GraphNode GraphNode_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphNode"
    ADD CONSTRAINT "GraphNode_pkey" PRIMARY KEY ("NodeID");


--
-- Name: PIMetrics PIMetrics_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT "PIMetrics_NodeID_key" UNIQUE ("NodeID");


--
-- Name: PIMetrics PIMetrics_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT "PIMetrics_pkey" PRIMARY KEY ("MetricsID");


--
-- Name: PathEdges PathEdges_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT "PathEdges_pkey" PRIMARY KEY ("PathEdgeID");


--
-- Name: Place Place_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT "Place_NodeID_key" UNIQUE ("NodeID");


--
-- Name: Place Place_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT "Place_pkey" PRIMARY KEY ("PlaceID");


--
-- Name: Room Room_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT "Room_NodeID_key" UNIQUE ("NodeID");


--
-- Name: Room Room_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT "Room_pkey" PRIMARY KEY ("RoomID");


--
-- Name: SavedPath SavedPath_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT "SavedPath_pkey" PRIMARY KEY ("SavedPathID");


--
-- Name: Building Building_NodeID_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building"
    ADD CONSTRAINT "Building_NodeID_fkey" FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: Room fk_buildingid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT fk_buildingid FOREIGN KEY ("BuildingID") REFERENCES public."Building"("BuildingID");


--
-- Name: Door fk_buildingid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT fk_buildingid FOREIGN KEY ("BuildingID") REFERENCES public."Building"("BuildingID");


--
-- Name: PathEdges fk_edgeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fk_edgeid FOREIGN KEY ("EdgeID") REFERENCES public."GraphEdge"("EdgeID");


--
-- Name: GraphEdge fk_ending_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT fk_ending_nodeid FOREIGN KEY ("Node2ID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: Place fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: Room fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: Door fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: PIMetrics fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: PathEdges fk_savedpathid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fk_savedpathid FOREIGN KEY ("SavedPathID") REFERENCES public."SavedPath"("SavedPathID");


--
-- Name: GraphEdge fk_starting_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT fk_starting_nodeid FOREIGN KEY ("Node1ID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: PathEdges fkeqhxhgwrv3wh4e4xk8lmqaonl; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fkeqhxhgwrv3wh4e4xk8lmqaonl FOREIGN KEY ("PathEdgeID") REFERENCES public."GraphEdge"("EdgeID");


--
-- Name: SavedPath fkgrg9qsnykg6gcv3a6gjxvblmf; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT fkgrg9qsnykg6gcv3a6gjxvblmf FOREIGN KEY ("startingNodeID") REFERENCES public."GraphNode"("NodeID");


--
-- Name: SavedPath fko7hrj0xg1477xoygeoxrvqfbm; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT fko7hrj0xg1477xoygeoxrvqfbm FOREIGN KEY ("endingNodeID") REFERENCES public."GraphNode"("NodeID");


--
-- PostgreSQL database dump complete
--

