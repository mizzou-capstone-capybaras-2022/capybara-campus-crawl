--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2 (Debian 14.2-1.pgdg110+1)
-- Dumped by pg_dump version 14.1

-- Started on 2022-02-20 09:22:27 UTC

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: capy
--

--
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: capy
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16385)
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
-- TOC entry 210 (class 1259 OID 16390)
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
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 210
-- Name: Building_BuildingID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Building_BuildingID_seq" OWNED BY public."Building"."BuildingID";


--
-- TOC entry 211 (class 1259 OID 16391)
-- Name: Door; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."Door" (
    "DoorID" integer NOT NULL,
    "NodeID" integer NOT NULL,
    "BuildingID" integer NOT NULL
);


ALTER TABLE public."Door" OWNER TO capy;

--
-- TOC entry 212 (class 1259 OID 16394)
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
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 212
-- Name: Door_DoorID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Door_DoorID_seq" OWNED BY public."Door"."DoorID";


--
-- TOC entry 213 (class 1259 OID 16395)
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
-- TOC entry 214 (class 1259 OID 16401)
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
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 214
-- Name: GraphEdge_EdgeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."GraphEdge_EdgeID_seq" OWNED BY public."GraphEdge"."EdgeID";


--
-- TOC entry 215 (class 1259 OID 16402)
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
-- TOC entry 216 (class 1259 OID 16407)
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
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 216
-- Name: GraphNode_NodeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."GraphNode_NodeID_seq" OWNED BY public."GraphNode"."NodeID";


--
-- TOC entry 217 (class 1259 OID 16408)
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
-- TOC entry 218 (class 1259 OID 16411)
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
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 218
-- Name: PIMetrics_MetricsID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."PIMetrics_MetricsID_seq" OWNED BY public."PIMetrics"."MetricsID";


--
-- TOC entry 219 (class 1259 OID 16412)
-- Name: PathEdges; Type: TABLE; Schema: public; Owner: capy
--

CREATE TABLE public."PathEdges" (
    "PathEdgeID" integer NOT NULL,
    "SavedPathID" integer NOT NULL,
    "EdgeID" integer NOT NULL
);


ALTER TABLE public."PathEdges" OWNER TO capy;

--
-- TOC entry 220 (class 1259 OID 16415)
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
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 220
-- Name: PathEdges_PathEdgeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."PathEdges_PathEdgeID_seq" OWNED BY public."PathEdges"."PathEdgeID";


--
-- TOC entry 221 (class 1259 OID 16416)
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
-- TOC entry 222 (class 1259 OID 16421)
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
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 222
-- Name: Place_PlaceID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Place_PlaceID_seq" OWNED BY public."Place"."PlaceID";


--
-- TOC entry 223 (class 1259 OID 16422)
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
-- TOC entry 224 (class 1259 OID 16427)
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
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 224
-- Name: Room_RoomID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."Room_RoomID_seq" OWNED BY public."Room"."RoomID";


--
-- TOC entry 225 (class 1259 OID 16428)
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
-- TOC entry 226 (class 1259 OID 16431)
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
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 226
-- Name: SavedPath_SavedPathID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: capy
--

ALTER SEQUENCE public."SavedPath_SavedPathID_seq" OWNED BY public."SavedPath"."SavedPathID";


--
-- TOC entry 3207 (class 2604 OID 16432)
-- Name: Building BuildingID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building" ALTER COLUMN "BuildingID" SET DEFAULT nextval('public."Building_BuildingID_seq"'::regclass);


--
-- TOC entry 3208 (class 2604 OID 16433)
-- Name: Door DoorID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door" ALTER COLUMN "DoorID" SET DEFAULT nextval('public."Door_DoorID_seq"'::regclass);


--
-- TOC entry 3210 (class 2604 OID 16434)
-- Name: GraphEdge EdgeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge" ALTER COLUMN "EdgeID" SET DEFAULT nextval('public."GraphEdge_EdgeID_seq"'::regclass);


--
-- TOC entry 3211 (class 2604 OID 16435)
-- Name: GraphNode NodeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphNode" ALTER COLUMN "NodeID" SET DEFAULT nextval('public."GraphNode_NodeID_seq"'::regclass);


--
-- TOC entry 3212 (class 2604 OID 16436)
-- Name: PIMetrics MetricsID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics" ALTER COLUMN "MetricsID" SET DEFAULT nextval('public."PIMetrics_MetricsID_seq"'::regclass);


--
-- TOC entry 3213 (class 2604 OID 16437)
-- Name: PathEdges PathEdgeID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges" ALTER COLUMN "PathEdgeID" SET DEFAULT nextval('public."PathEdges_PathEdgeID_seq"'::regclass);


--
-- TOC entry 3214 (class 2604 OID 16438)
-- Name: Place PlaceID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place" ALTER COLUMN "PlaceID" SET DEFAULT nextval('public."Place_PlaceID_seq"'::regclass);


--
-- TOC entry 3215 (class 2604 OID 16439)
-- Name: Room RoomID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room" ALTER COLUMN "RoomID" SET DEFAULT nextval('public."Room_RoomID_seq"'::regclass);


--
-- TOC entry 3216 (class 2604 OID 16440)
-- Name: SavedPath SavedPathID; Type: DEFAULT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath" ALTER COLUMN "SavedPathID" SET DEFAULT nextval('public."SavedPath_SavedPathID_seq"'::regclass);


--
-- TOC entry 3396 (class 0 OID 16385)
-- Dependencies: 209
-- Data for Name: Building; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."Building" VALUES (535, 'Alumni Center', 1150, '{}');
INSERT INTO public."Building" VALUES (536, 'Anheuser-Busch /agriculture science building', 1151, '{}');
INSERT INTO public."Building" VALUES (537, 'Bingham Commons', 1152, '{}');
INSERT INTO public."Building" VALUES (538, 'Black Culture Center', 1153, '{}');
INSERT INTO public."Building" VALUES (539, 'Brooks Hall', 1154, '{}');
INSERT INTO public."Building" VALUES (540, 'Center for Missouri Studies', 1155, '{}');
INSERT INTO public."Building" VALUES (541, 'Center Hall', 1156, '{}');
INSERT INTO public."Building" VALUES (542, 'Clydesdale hall', 1157, '{}');
INSERT INTO public."Building" VALUES (543, 'College Ave', 1158, '{}');
INSERT INTO public."Building" VALUES (544, 'Conley Ave Parking structure', 1159, '{}');
INSERT INTO public."Building" VALUES (545, 'Connaway Hall', 1160, '{}');
INSERT INTO public."Building" VALUES (546, 'Crowder Hall', 1161, '{}');
INSERT INTO public."Building" VALUES (547, 'Defoe-Graham Hall', 1162, '{}');
INSERT INTO public."Building" VALUES (548, 'Discovery Hall', 1163, '{}');
INSERT INTO public."Building" VALUES (549, 'Dogwood Hall', 1164, '{}');
INSERT INTO public."Building" VALUES (550, 'Eckles Hall', 1165, '{}');
INSERT INTO public."Building" VALUES (551, 'Excellence Hall', 1166, '{}');
INSERT INTO public."Building" VALUES (552, 'Galena', 1167, '{}');
INSERT INTO public."Building" VALUES (553, 'Gannett', 1168, '{}');
INSERT INTO public."Building" VALUES (554, 'Gateway hall', 1169, '{}');
INSERT INTO public."Building" VALUES (555, 'Gillett Hall', 1170, '{}');
INSERT INTO public."Building" VALUES (556, 'Hatch Hall', 1171, '{}');
INSERT INTO public."Building" VALUES (557, 'Hawthorn', 1172, '{}');
INSERT INTO public."Building" VALUES (558, 'Lottes Health Science Library', 1173, '{}');
INSERT INTO public."Building" VALUES (559, 'Heinkel Building', 1174, '{}');
INSERT INTO public."Building" VALUES (560, 'Hearnes Multipurpose Building', 1175, '{}');
INSERT INTO public."Building" VALUES (561, 'Hitt Street Parking Structure', 1176, '{}');
INSERT INTO public."Building" VALUES (562, 'Hospital', 1177, '{}');
INSERT INTO public."Building" VALUES (563, 'Hudson', 1178, '{}');
INSERT INTO public."Building" VALUES (564, 'Jesse Hall', 1179, '{}');
INSERT INTO public."Building" VALUES (565, 'Johnston Hall', 1180, '{}');
INSERT INTO public."Building" VALUES (566, 'Lee Hills Hall', 1181, '{}');
INSERT INTO public."Building" VALUES (567, 'lefevre', 1182, '{}');
INSERT INTO public."Building" VALUES (568, 'Lewis and Clark Hall', 1183, '{}');
INSERT INTO public."Building" VALUES (569, 'Loeb', 1184, '{}');
INSERT INTO public."Building" VALUES (570, 'Lowry', 1185, '{}');
INSERT INTO public."Building" VALUES (571, 'Mark Twain', 1186, '{}');
INSERT INTO public."Building" VALUES (572, 'Mathmatical sciences', 1187, '{}');
INSERT INTO public."Building" VALUES (573, 'McDavid', 1188, '{}');
INSERT INTO public."Building" VALUES (574, 'McKnee Gym', 1189, '{}');
INSERT INTO public."Building" VALUES (575, 'McReynolds', 1190, '{}');
INSERT INTO public."Building" VALUES (576, 'Mizzou Arena', 1191, '{}');
INSERT INTO public."Building" VALUES (577, 'Memorial Stadium', 1192, '{}');
INSERT INTO public."Building" VALUES (578, 'Naka Hall', 1193, '{}');
INSERT INTO public."Building" VALUES (579, 'Neff', 1194, '{}');
INSERT INTO public."Building" VALUES (580, 'north hall', 1195, '{}');
INSERT INTO public."Building" VALUES (581, 'Parking structure 7', 1196, '{}');
INSERT INTO public."Building" VALUES (582, 'pershing commons', 1197, '{}');
INSERT INTO public."Building" VALUES (583, 'professional building', 1198, '{}');
INSERT INTO public."Building" VALUES (584, 'psychology building', 1199, '{}');
INSERT INTO public."Building" VALUES (585, 'respect', 1200, '{}');
INSERT INTO public."Building" VALUES (586, 'Responsibility', 1201, '{}');
INSERT INTO public."Building" VALUES (587, 'Rollins', 1202, '{}');
INSERT INTO public."Building" VALUES (588, 'Schurz', 1203, '{}');
INSERT INTO public."Building" VALUES (589, 'Schweitzer', 1204, '{}');
INSERT INTO public."Building" VALUES (590, 'South hall', 1205, '{}');
INSERT INTO public."Building" VALUES (591, 'Stankowski', 1206, '{}');
INSERT INTO public."Building" VALUES (592, 'Stephens hall', 1207, '{}');
INSERT INTO public."Building" VALUES (593, 'Student Rec', 1208, '{}');
INSERT INTO public."Building" VALUES (594, 'Student Success center', 1209, '{}');
INSERT INTO public."Building" VALUES (595, 'TAPS(tiger ave parking structure', 1210, '{}');
INSERT INTO public."Building" VALUES (596, 'Theatre', 1211, '{}');
INSERT INTO public."Building" VALUES (597, 'Tucker', 1212, '{}');
INSERT INTO public."Building" VALUES (598, 'Turner Ave Parking Structure', 1213, '{}');
INSERT INTO public."Building" VALUES (599, 'University ave parking structure', 1214, '{}');
INSERT INTO public."Building" VALUES (600, 'Vet', 1215, '{}');
INSERT INTO public."Building" VALUES (601, 'Wolpers', 1216, '{}');
INSERT INTO public."Building" VALUES (602, 'Agricultural Engineering', 1217, '{}');
INSERT INTO public."Building" VALUES (603, 'Pickard Hall', 1218, '{}');
INSERT INTO public."Building" VALUES (604, 'Noyes Hall', 1219, '{}');
INSERT INTO public."Building" VALUES (605, 'Curtis Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (606, 'Lafferre Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (607, 'Parker Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (608, 'Ellis Library', NULL, '{}');
INSERT INTO public."Building" VALUES (609, 'Herman Schlundt Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (610, 'Gwynn / Stanley Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (611, 'Student Center', NULL, '{}');
INSERT INTO public."Building" VALUES (612, 'Cornell Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (613, 'Arts & Sciences', NULL, '{}');
INSERT INTO public."Building" VALUES (614, 'Middlebush Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (615, 'Stewart Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (616, 'Chemistry Building', NULL, '{}');
INSERT INTO public."Building" VALUES (617, 'McAlester Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (618, 'Hulston hall', NULL, '{}');
INSERT INTO public."Building" VALUES (619, 'Memorial Union', NULL, '{}');
INSERT INTO public."Building" VALUES (620, 'Mumford Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (621, 'Switzler Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (622, 'Tate Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (623, 'Geological Sciences', NULL, '{}');
INSERT INTO public."Building" VALUES (624, 'Natural resources (Ag Science)', NULL, '{}');
INSERT INTO public."Building" VALUES (625, 'Strickland Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (626, 'Gentry Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (627, 'Walters hall', NULL, '{}');
INSERT INTO public."Building" VALUES (628, 'Fine Arts Building', NULL, '{}');
INSERT INTO public."Building" VALUES (629, 'Swallow Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (630, 'Southwest Village', NULL, '{}');
INSERT INTO public."Building" VALUES (631, 'Whitten Hall', NULL, '{}');
INSERT INTO public."Building" VALUES (632, 'Physics Building', NULL, '{}');
INSERT INTO public."Building" VALUES (633, 'Life Sciences Center', NULL, '{}');
INSERT INTO public."Building" VALUES (634, 'Reynolds Journalism Institude', NULL, '{}');


--
-- TOC entry 3398 (class 0 OID 16391)
-- Dependencies: 211
-- Data for Name: Door; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."Door" VALUES (946, 1220, 605);
INSERT INTO public."Door" VALUES (947, 1221, 605);
INSERT INTO public."Door" VALUES (948, 1222, 605);
INSERT INTO public."Door" VALUES (949, 1223, 606);
INSERT INTO public."Door" VALUES (950, 1224, 606);
INSERT INTO public."Door" VALUES (951, 1225, 606);
INSERT INTO public."Door" VALUES (952, 1226, 606);
INSERT INTO public."Door" VALUES (953, 1227, 606);
INSERT INTO public."Door" VALUES (954, 1228, 607);
INSERT INTO public."Door" VALUES (955, 1229, 607);
INSERT INTO public."Door" VALUES (956, 1230, 608);
INSERT INTO public."Door" VALUES (957, 1231, 608);
INSERT INTO public."Door" VALUES (958, 1232, 609);
INSERT INTO public."Door" VALUES (959, 1233, 609);
INSERT INTO public."Door" VALUES (960, 1234, 609);
INSERT INTO public."Door" VALUES (961, 1235, 610);
INSERT INTO public."Door" VALUES (962, 1236, 610);
INSERT INTO public."Door" VALUES (963, 1237, 610);
INSERT INTO public."Door" VALUES (964, 1238, 610);
INSERT INTO public."Door" VALUES (965, 1239, 611);
INSERT INTO public."Door" VALUES (966, 1240, 611);
INSERT INTO public."Door" VALUES (967, 1241, 611);
INSERT INTO public."Door" VALUES (968, 1242, 611);
INSERT INTO public."Door" VALUES (969, 1243, 612);
INSERT INTO public."Door" VALUES (970, 1244, 612);
INSERT INTO public."Door" VALUES (971, 1245, 612);
INSERT INTO public."Door" VALUES (972, 1246, 613);
INSERT INTO public."Door" VALUES (973, 1247, 613);
INSERT INTO public."Door" VALUES (974, 1248, 613);
INSERT INTO public."Door" VALUES (975, 1249, 613);
INSERT INTO public."Door" VALUES (976, 1250, 614);
INSERT INTO public."Door" VALUES (977, 1251, 614);
INSERT INTO public."Door" VALUES (978, 1252, 614);
INSERT INTO public."Door" VALUES (979, 1253, 614);
INSERT INTO public."Door" VALUES (980, 1254, 614);
INSERT INTO public."Door" VALUES (981, 1255, 615);
INSERT INTO public."Door" VALUES (982, 1256, 615);
INSERT INTO public."Door" VALUES (983, 1257, 616);
INSERT INTO public."Door" VALUES (984, 1258, 616);
INSERT INTO public."Door" VALUES (985, 1259, 616);
INSERT INTO public."Door" VALUES (986, 1260, 616);
INSERT INTO public."Door" VALUES (987, 1261, 617);
INSERT INTO public."Door" VALUES (988, 1262, 617);
INSERT INTO public."Door" VALUES (989, 1263, 617);
INSERT INTO public."Door" VALUES (990, 1264, 618);
INSERT INTO public."Door" VALUES (991, 1265, 618);
INSERT INTO public."Door" VALUES (992, 1266, 618);
INSERT INTO public."Door" VALUES (993, 1267, 619);
INSERT INTO public."Door" VALUES (994, 1268, 619);
INSERT INTO public."Door" VALUES (995, 1269, 619);
INSERT INTO public."Door" VALUES (996, 1270, 620);
INSERT INTO public."Door" VALUES (997, 1271, 620);
INSERT INTO public."Door" VALUES (998, 1272, 620);
INSERT INTO public."Door" VALUES (999, 1273, 621);
INSERT INTO public."Door" VALUES (1000, 1274, 621);
INSERT INTO public."Door" VALUES (1001, 1275, 622);
INSERT INTO public."Door" VALUES (1002, 1276, 622);
INSERT INTO public."Door" VALUES (1003, 1277, 622);
INSERT INTO public."Door" VALUES (1004, 1278, 622);
INSERT INTO public."Door" VALUES (1005, 1279, 622);
INSERT INTO public."Door" VALUES (1006, 1280, 623);
INSERT INTO public."Door" VALUES (1007, 1281, 623);
INSERT INTO public."Door" VALUES (1008, 1282, 624);
INSERT INTO public."Door" VALUES (1009, 1283, 624);
INSERT INTO public."Door" VALUES (1010, 1284, 624);
INSERT INTO public."Door" VALUES (1011, 1285, 624);
INSERT INTO public."Door" VALUES (1012, 1286, 624);
INSERT INTO public."Door" VALUES (1013, 1287, 624);
INSERT INTO public."Door" VALUES (1014, 1288, 625);
INSERT INTO public."Door" VALUES (1015, 1289, 625);
INSERT INTO public."Door" VALUES (1016, 1290, 625);
INSERT INTO public."Door" VALUES (1017, 1291, 626);
INSERT INTO public."Door" VALUES (1018, 1292, 626);
INSERT INTO public."Door" VALUES (1019, 1293, 626);
INSERT INTO public."Door" VALUES (1020, 1294, 626);
INSERT INTO public."Door" VALUES (1021, 1295, 626);
INSERT INTO public."Door" VALUES (1022, 1296, 627);
INSERT INTO public."Door" VALUES (1023, 1297, 627);
INSERT INTO public."Door" VALUES (1024, 1298, 627);
INSERT INTO public."Door" VALUES (1025, 1299, 628);
INSERT INTO public."Door" VALUES (1026, 1300, 628);
INSERT INTO public."Door" VALUES (1027, 1301, 628);
INSERT INTO public."Door" VALUES (1028, 1302, 628);
INSERT INTO public."Door" VALUES (1029, 1303, 628);
INSERT INTO public."Door" VALUES (1030, 1304, 628);
INSERT INTO public."Door" VALUES (1031, 1305, 629);
INSERT INTO public."Door" VALUES (1032, 1306, 629);
INSERT INTO public."Door" VALUES (1033, 1307, 629);
INSERT INTO public."Door" VALUES (1034, 1308, 629);
INSERT INTO public."Door" VALUES (1035, 1309, 630);
INSERT INTO public."Door" VALUES (1036, 1310, 630);
INSERT INTO public."Door" VALUES (1037, 1311, 631);
INSERT INTO public."Door" VALUES (1038, 1312, 631);
INSERT INTO public."Door" VALUES (1039, 1313, 632);
INSERT INTO public."Door" VALUES (1040, 1314, 632);
INSERT INTO public."Door" VALUES (1041, 1315, 633);
INSERT INTO public."Door" VALUES (1042, 1316, 633);
INSERT INTO public."Door" VALUES (1043, 1317, 633);
INSERT INTO public."Door" VALUES (1044, 1318, 633);
INSERT INTO public."Door" VALUES (1045, 1319, 633);
INSERT INTO public."Door" VALUES (1046, 1320, 633);
INSERT INTO public."Door" VALUES (1047, 1321, 634);
INSERT INTO public."Door" VALUES (1048, 1322, 634);
INSERT INTO public."Door" VALUES (1049, 1323, 634);
INSERT INTO public."Door" VALUES (1050, 1324, 634);


--
-- TOC entry 3400 (class 0 OID 16395)
-- Dependencies: 213
-- Data for Name: GraphEdge; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."GraphEdge" VALUES (569, 1282, 1283, '', '', true, 179.66667, '{}');
INSERT INTO public."GraphEdge" VALUES (570, 1282, 1284, '', '', true, 315, '{}');
INSERT INTO public."GraphEdge" VALUES (571, 1282, 1285, '', '', true, 380.33334, '{}');
INSERT INTO public."GraphEdge" VALUES (572, 1282, 1286, '', '', true, 555.3333, '{}');
INSERT INTO public."GraphEdge" VALUES (573, 1282, 1287, '', '', true, 788.6667, '{}');
INSERT INTO public."GraphEdge" VALUES (574, 1283, 1285, '', '', true, 315, '{}');
INSERT INTO public."GraphEdge" VALUES (575, 1283, 1286, '', '', true, 357, '{}');
INSERT INTO public."GraphEdge" VALUES (576, 1283, 1287, '', '', true, 532, '{}');
INSERT INTO public."GraphEdge" VALUES (577, 1283, 1284, '', '', true, 765.3, '{}');
INSERT INTO public."GraphEdge" VALUES (578, 1284, 1285, '', '', true, 198.3, '{}');
INSERT INTO public."GraphEdge" VALUES (579, 1284, 1286, '', '', true, 380.3, '{}');
INSERT INTO public."GraphEdge" VALUES (580, 1284, 1287, '', '', true, 613.7, '{}');
INSERT INTO public."GraphEdge" VALUES (581, 1285, 1286, '', '', true, 245, '{}');
INSERT INTO public."GraphEdge" VALUES (582, 1285, 1287, '', '', true, 478.3, '{}');
INSERT INTO public."GraphEdge" VALUES (583, 1286, 1287, '', '', true, 245, '{}');
INSERT INTO public."GraphEdge" VALUES (584, 1313, 1314, '', '', true, 158.66667, '{}');
INSERT INTO public."GraphEdge" VALUES (585, 1315, 1316, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (586, 1315, 1317, '', '', true, 389.7, '{}');
INSERT INTO public."GraphEdge" VALUES (587, 1315, 1318, '', '', true, 196, '{}');
INSERT INTO public."GraphEdge" VALUES (588, 1315, 1319, '', '', true, 301, '{}');
INSERT INTO public."GraphEdge" VALUES (589, 1315, 1320, '', '', true, 214.7, '{}');
INSERT INTO public."GraphEdge" VALUES (590, 1316, 1317, '', '', true, 203, '{}');
INSERT INTO public."GraphEdge" VALUES (591, 1316, 1318, '', '', true, 107.3, '{}');
INSERT INTO public."GraphEdge" VALUES (592, 1316, 1319, '', '', true, 261.3, '{}');
INSERT INTO public."GraphEdge" VALUES (593, 1316, 1320, '', '', true, 347.7, '{}');
INSERT INTO public."GraphEdge" VALUES (594, 1317, 1318, '', '', true, 210, '{}');
INSERT INTO public."GraphEdge" VALUES (595, 1317, 1319, '', '', true, 380.3, '{}');
INSERT INTO public."GraphEdge" VALUES (596, 1317, 1320, '', '', true, 452.7, '{}');
INSERT INTO public."GraphEdge" VALUES (597, 1318, 1319, '', '', true, 170.3, '{}');
INSERT INTO public."GraphEdge" VALUES (598, 1318, 1320, '', '', true, 242.7, '{}');
INSERT INTO public."GraphEdge" VALUES (599, 1319, 1320, '', '', true, 86.3, '{}');
INSERT INTO public."GraphEdge" VALUES (600, 1291, 1292, '', '', true, 126, '{}');
INSERT INTO public."GraphEdge" VALUES (601, 1291, 1293, '', '', true, 170.3, '{}');
INSERT INTO public."GraphEdge" VALUES (602, 1291, 1294, '', '', true, 238, '{}');
INSERT INTO public."GraphEdge" VALUES (603, 1291, 1295, '', '', true, 60.7, '{}');
INSERT INTO public."GraphEdge" VALUES (604, 1292, 1293, '', '', true, 86.3, '{}');
INSERT INTO public."GraphEdge" VALUES (605, 1292, 1294, '', '', true, 154, '{}');
INSERT INTO public."GraphEdge" VALUES (606, 1292, 1295, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (607, 1293, 1294, '', '', true, 128.3, '{}');
INSERT INTO public."GraphEdge" VALUES (608, 1293, 1295, '', '', true, 231, '{}');
INSERT INTO public."GraphEdge" VALUES (609, 1294, 1295, '', '', true, 298.7, '{}');
INSERT INTO public."GraphEdge" VALUES (610, 1232, 1233, '', '', true, 98, '{}');
INSERT INTO public."GraphEdge" VALUES (611, 1232, 1234, '', '', true, 130.7, '{}');
INSERT INTO public."GraphEdge" VALUES (612, 1233, 1234, '', '', true, 102.7, '{}');
INSERT INTO public."GraphEdge" VALUES (613, 1235, 1236, '', '', true, 163.3, '{}');
INSERT INTO public."GraphEdge" VALUES (614, 1235, 1237, '', '', true, 270.7, '{}');
INSERT INTO public."GraphEdge" VALUES (615, 1235, 1238, '', '', true, 457.3, '{}');
INSERT INTO public."GraphEdge" VALUES (616, 1236, 1237, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (617, 1236, 1238, '', '', true, 373.3, '{}');
INSERT INTO public."GraphEdge" VALUES (618, 1237, 1238, '', '', true, 242.7, '{}');
INSERT INTO public."GraphEdge" VALUES (619, 1220, 1221, '', '', true, 98, '{}');
INSERT INTO public."GraphEdge" VALUES (620, 1220, 1222, '', '', true, 151.7, '{}');
INSERT INTO public."GraphEdge" VALUES (621, 1221, 1222, '', '', true, 114.3, '{}');
INSERT INTO public."GraphEdge" VALUES (622, 1239, 1240, '', '', true, 326, '{}');
INSERT INTO public."GraphEdge" VALUES (623, 1239, 1241, '', '', true, 270, '{}');
INSERT INTO public."GraphEdge" VALUES (624, 1239, 1242, '', '', true, 211, '{}');
INSERT INTO public."GraphEdge" VALUES (625, 1240, 1241, '', '', true, 190, '{}');
INSERT INTO public."GraphEdge" VALUES (626, 1240, 1242, '', '', true, 349, '{}');
INSERT INTO public."GraphEdge" VALUES (627, 1241, 1242, '', '', true, 214, '{}');
INSERT INTO public."GraphEdge" VALUES (628, 1288, 1289, '', '', true, 204, '{}');
INSERT INTO public."GraphEdge" VALUES (629, 1288, 1290, '', '', true, 320, '{}');
INSERT INTO public."GraphEdge" VALUES (630, 1289, 1290, '', '', true, 111, '{}');
INSERT INTO public."GraphEdge" VALUES (631, 1243, 1244, '', '', true, 274, '{}');
INSERT INTO public."GraphEdge" VALUES (632, 1243, 1245, '', '', true, 429, '{}');
INSERT INTO public."GraphEdge" VALUES (633, 1244, 1245, '', '', true, 116, '{}');
INSERT INTO public."GraphEdge" VALUES (634, 1246, 1247, '', '', true, 48, '{}');
INSERT INTO public."GraphEdge" VALUES (635, 1246, 1248, '', '', true, 173, '{}');
INSERT INTO public."GraphEdge" VALUES (636, 1246, 1249, '', '', true, 180, '{}');
INSERT INTO public."GraphEdge" VALUES (637, 1247, 1248, '', '', true, 175, '{}');
INSERT INTO public."GraphEdge" VALUES (638, 1247, 1249, '', '', true, 179, '{}');
INSERT INTO public."GraphEdge" VALUES (639, 1248, 1249, '', '', true, 38, '{}');
INSERT INTO public."GraphEdge" VALUES (640, 1264, 1265, '', '', true, 50, '{}');
INSERT INTO public."GraphEdge" VALUES (641, 1264, 1266, '', '', true, 203, '{}');
INSERT INTO public."GraphEdge" VALUES (642, 1265, 1266, '', '', true, 228, '{}');
INSERT INTO public."GraphEdge" VALUES (643, 1230, 1231, '', '', true, 216, '{}');
INSERT INTO public."GraphEdge" VALUES (644, 1275, 1276, '', '', true, 276, '{}');
INSERT INTO public."GraphEdge" VALUES (645, 1275, 1277, '', '', true, 191, '{}');
INSERT INTO public."GraphEdge" VALUES (646, 1275, 1278, '', '', true, 207, '{}');
INSERT INTO public."GraphEdge" VALUES (647, 1275, 1279, '', '', true, 261, '{}');
INSERT INTO public."GraphEdge" VALUES (648, 1276, 1277, '', '', true, 108, '{}');
INSERT INTO public."GraphEdge" VALUES (649, 1276, 1278, '', '', true, 116, '{}');
INSERT INTO public."GraphEdge" VALUES (650, 1276, 1279, '', '', true, 133, '{}');
INSERT INTO public."GraphEdge" VALUES (651, 1277, 1278, '', '', true, 45, '{}');
INSERT INTO public."GraphEdge" VALUES (652, 1277, 1279, '', '', true, 113, '{}');
INSERT INTO public."GraphEdge" VALUES (653, 1278, 1279, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (654, 1261, 1262, '', '', true, 118.1104, '{}');
INSERT INTO public."GraphEdge" VALUES (655, 1261, 1263, '', '', true, 70.86624, '{}');
INSERT INTO public."GraphEdge" VALUES (656, 1262, 1263, '', '', true, 129.92143, '{}');
INSERT INTO public."GraphEdge" VALUES (657, 1228, 1229, '', '', true, 59.0552, '{}');
INSERT INTO public."GraphEdge" VALUES (658, 1223, 1224, '', '', true, 221.457, '{}');
INSERT INTO public."GraphEdge" VALUES (659, 1223, 1225, '', '', true, 261.31927, '{}');
INSERT INTO public."GraphEdge" VALUES (660, 1223, 1226, '', '', true, 468.01245, '{}');
INSERT INTO public."GraphEdge" VALUES (661, 1223, 1227, '', '', true, 283.46497, '{}');
INSERT INTO public."GraphEdge" VALUES (662, 1224, 1225, '', '', true, 200.78767, '{}');
INSERT INTO public."GraphEdge" VALUES (663, 1224, 1226, '', '', true, 354.3312, '{}');
INSERT INTO public."GraphEdge" VALUES (664, 1224, 1227, '', '', true, 177.1656, '{}');
INSERT INTO public."GraphEdge" VALUES (665, 1225, 1226, '', '', true, 320.37445, '{}');
INSERT INTO public."GraphEdge" VALUES (666, 1225, 1227, '', '', true, 236.2208, '{}');
INSERT INTO public."GraphEdge" VALUES (667, 1226, 1227, '', '', true, 305.61066, '{}');
INSERT INTO public."GraphEdge" VALUES (668, 1309, 1310, '', '', true, 170, '{}');
INSERT INTO public."GraphEdge" VALUES (669, 1257, 1258, '', '', true, 133, '{}');
INSERT INTO public."GraphEdge" VALUES (670, 1259, 1260, '', '', true, 186.7, '{}');
INSERT INTO public."GraphEdge" VALUES (671, 1255, 1256, '', '', true, 175, '{}');
INSERT INTO public."GraphEdge" VALUES (672, 1296, 1297, '', '', true, 152.5, '{}');
INSERT INTO public."GraphEdge" VALUES (673, 1296, 1298, '', '', true, 152.5, '{}');
INSERT INTO public."GraphEdge" VALUES (674, 1297, 1298, '', '', true, 52.5, '{}');
INSERT INTO public."GraphEdge" VALUES (675, 1270, 1271, '', '', true, 90, '{}');
INSERT INTO public."GraphEdge" VALUES (676, 1270, 1272, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (677, 1271, 1272, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (678, 1299, 1300, '', '', true, 45, '{}');
INSERT INTO public."GraphEdge" VALUES (679, 1301, 1302, '', '', true, 60, '{}');
INSERT INTO public."GraphEdge" VALUES (680, 1301, 1303, '', '', true, 100, '{}');
INSERT INTO public."GraphEdge" VALUES (681, 1301, 1304, '', '', true, 102.5, '{}');
INSERT INTO public."GraphEdge" VALUES (682, 1302, 1303, '', '', true, 40, '{}');
INSERT INTO public."GraphEdge" VALUES (683, 1302, 1304, '', '', true, 100, '{}');
INSERT INTO public."GraphEdge" VALUES (684, 1303, 1304, '', '', true, 60, '{}');
INSERT INTO public."GraphEdge" VALUES (685, 1267, 1268, '', '', true, 142.5, '{}');
INSERT INTO public."GraphEdge" VALUES (686, 1267, 1269, '', '', true, 197.5, '{}');
INSERT INTO public."GraphEdge" VALUES (687, 1268, 1269, '', '', true, 222.5, '{}');
INSERT INTO public."GraphEdge" VALUES (688, 1311, 1312, '', '', true, 80, '{}');
INSERT INTO public."GraphEdge" VALUES (689, 1250, 1251, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (690, 1250, 1252, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (691, 1250, 1253, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (692, 1250, 1254, '', '', true, 350, '{}');
INSERT INTO public."GraphEdge" VALUES (693, 1251, 1252, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (694, 1251, 1253, '', '', true, 232.5, '{}');
INSERT INTO public."GraphEdge" VALUES (695, 1251, 1254, '', '', true, 350, '{}');
INSERT INTO public."GraphEdge" VALUES (696, 1252, 1253, '', '', true, 75, '{}');
INSERT INTO public."GraphEdge" VALUES (697, 1252, 1254, '', '', true, 135, '{}');
INSERT INTO public."GraphEdge" VALUES (698, 1253, 1254, '', '', true, 162.5, '{}');
INSERT INTO public."GraphEdge" VALUES (699, 1305, 1307, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (700, 1305, 1308, '', '', true, 97.5, '{}');
INSERT INTO public."GraphEdge" VALUES (701, 1306, 1307, '', '', true, 97.5, '{}');
INSERT INTO public."GraphEdge" VALUES (702, 1306, 1308, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (703, 1321, 1322, '', '', true, 50, '{}');
INSERT INTO public."GraphEdge" VALUES (704, 1321, 1323, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (705, 1321, 1324, '', '', true, 132.5, '{}');
INSERT INTO public."GraphEdge" VALUES (706, 1322, 1323, '', '', true, 115, '{}');
INSERT INTO public."GraphEdge" VALUES (707, 1322, 1324, '', '', true, 132.5, '{}');
INSERT INTO public."GraphEdge" VALUES (708, 1323, 1324, '', '', true, 62.5, '{}');
INSERT INTO public."GraphEdge" VALUES (709, 1280, 1281, '', '', true, 274, '{}');
INSERT INTO public."GraphEdge" VALUES (710, 1273, 1274, '', '', true, 101, '{}');


--
-- TOC entry 3402 (class 0 OID 16402)
-- Dependencies: 215
-- Data for Name: GraphNode; Type: TABLE DATA; Schema: public; Owner: capy
--

INSERT INTO public."GraphNode" VALUES (1150, 38.9437187, -92.32955217, 'The Mizzou Alumni Center');
INSERT INTO public."GraphNode" VALUES (1151, 38.94271735, -92.32471347, 'Home of College of Agriculture');
INSERT INTO public."GraphNode" VALUES (1152, 38.94038395, -92.32093289, 'Dining and Fine Arts');
INSERT INTO public."GraphNode" VALUES (1153, 38.94117984, -92.32417703, 'GOBCC works to make the University of Missouri and Columbia community a more inclusive and welcoming environment for diverse populations through education, outreach, and collaboration.');
INSERT INTO public."GraphNode" VALUES (1154, 38.93871078, -92.33204395, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1155, 38.94878467, -92.33031929, 'State Historical Society');
INSERT INTO public."GraphNode" VALUES (1156, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1157, 38.94003555, -92.31746614, 'Veterinary Medicine & Surgery');
INSERT INTO public."GraphNode" VALUES (1158, 38.94101503, -92.32136071, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1159, 38.94514142, -92.33156919, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (1160, 38.94265059, -92.31871605, 'Office of Animal Resources');
INSERT INTO public."GraphNode" VALUES (1161, 38.93985092, -92.3298955, 'Military Science');
INSERT INTO public."GraphNode" VALUES (1162, 38.94010544, -92.32614577, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1163, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1164, 38.93962977, -92.3268646, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1165, 38.94167427, -92.32031465, 'Food Science');
INSERT INTO public."GraphNode" VALUES (1166, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1167, 38.94011587, -92.32722133, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1168, 38.94767388, -92.3284927, 'Journalism');
INSERT INTO public."GraphNode" VALUES (1169, 38.93858664, -92.32290566, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1170, 38.94131544, -92.32310414, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1171, 38.94066872, -92.32037902, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1172, 38.94042672, -92.3265481, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1173, 38.93983214, -92.32866168, 'Health Sciences Library');
INSERT INTO public."GraphNode" VALUES (1174, 38.94920917, -92.33009934, 'Missouri Press');
INSERT INTO public."GraphNode" VALUES (1175, 38.9343013, -92.3306036, 'Intercollegiate Athletics');
INSERT INTO public."GraphNode" VALUES (1176, 38.94758627, -92.32615113, 'Parking & Transportation Svcs');
INSERT INTO public."GraphNode" VALUES (1177, 38.9386993, -92.32809305, 'Hospital');
INSERT INTO public."GraphNode" VALUES (1178, 38.9417452, -92.32253551, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1179, 38.94521756, -92.32870594, 'main administration building for the University of Missouri.');
INSERT INTO public."GraphNode" VALUES (1180, 38.94189957, -92.32454181, 'Dining and Res Life');
INSERT INTO public."GraphNode" VALUES (1181, 38.94873148, -92.32917398, 'Journalism');
INSERT INTO public."GraphNode" VALUES (1182, 38.94606138, -92.32293248, 'Biological Science');
INSERT INTO public."GraphNode" VALUES (1183, 38.94331399, -92.33329654, 'Health Science');
INSERT INTO public."GraphNode" VALUES (1184, 38.9481, -92.3317, '');
INSERT INTO public."GraphNode" VALUES (1185, 38.9453, -92.3273, '');
INSERT INTO public."GraphNode" VALUES (1186, 38.9453, -92.3327, 'Res Life and dining');
INSERT INTO public."GraphNode" VALUES (1187, 38.9478, -92.3281, 'lots of Teacher offices');
INSERT INTO public."GraphNode" VALUES (1188, 38.9478, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (1189, 38.9435, -92.3252, 'grad student offices / athletic reaserch');
INSERT INTO public."GraphNode" VALUES (1190, 38.9484, -92.3315, 'res life');
INSERT INTO public."GraphNode" VALUES (1191, 38.9325, -92.3338, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (1192, 38.936, -92.3332, 'Sports stadium');
INSERT INTO public."GraphNode" VALUES (1193, 38.9467, -92.3314, 'Engineering');
INSERT INTO public."GraphNode" VALUES (1194, 38.9478, -92.3282, '');
INSERT INTO public."GraphNode" VALUES (1195, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1196, 38.937, -92.3252, 'Parking');
INSERT INTO public."GraphNode" VALUES (1197, 38.9395, -92.326, 'hitt street market. Student grocerry');
INSERT INTO public."GraphNode" VALUES (1198, 38.9468, -92.3267, 'teacher offices');
INSERT INTO public."GraphNode" VALUES (1199, 38.9492, -92.3295, '');
INSERT INTO public."GraphNode" VALUES (1200, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1201, 38.9399, -92.3229, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1202, 38.9417, -92.323, 'dining hall');
INSERT INTO public."GraphNode" VALUES (1203, 38.94, -92.3213, 'residential Life');
INSERT INTO public."GraphNode" VALUES (1204, 38.9454, -92.3225, '');
INSERT INTO public."GraphNode" VALUES (1205, 38.93894444, -92.33323216, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1206, 38.9408, -92.3292, 'rec sports field ');
INSERT INTO public."GraphNode" VALUES (1207, 38.9459, -92.3224, '');
INSERT INTO public."GraphNode" VALUES (1208, 38.9414, -92.3266, 'The rec');
INSERT INTO public."GraphNode" VALUES (1209, 38.9453, -92.3267, '');
INSERT INTO public."GraphNode" VALUES (1210, 38.9385, -92.3302, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (1211, 38.9492, -92.3279, '');
INSERT INTO public."GraphNode" VALUES (1212, 38.9437, -92.3242, '');
INSERT INTO public."GraphNode" VALUES (1213, 38.9438, -92.3308, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (1214, 38.9471, -92.3239, 'Parking structure');
INSERT INTO public."GraphNode" VALUES (1215, 38.9413, -92.3176, 'University Vet');
INSERT INTO public."GraphNode" VALUES (1216, 38.9415, -92.325, 'Residential Life');
INSERT INTO public."GraphNode" VALUES (1217, 38.9412, -92.32, '');
INSERT INTO public."GraphNode" VALUES (1218, 38.9467, -92.3281, '');
INSERT INTO public."GraphNode" VALUES (1219, 38.9467, -92.3305, '');
INSERT INTO public."GraphNode" VALUES (1220, 38.944687, -92.323696, 'Door A for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (1221, 38.944808, -92.323466, 'Door B for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (1222, 38.944739, -92.323231, 'Door C for Curtis Hall');
INSERT INTO public."GraphNode" VALUES (1223, 38.946331, -92.33086, 'Door A for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (1224, 38.9464736, -92.3301081, 'Door B for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (1225, 38.9459633, -92.3293808, 'Door C for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (1226, 38.9458082, -92.3292866, 'Door D for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (1227, 38.9457067, -92.330097, 'Door E for Lafferre Hall');
INSERT INTO public."GraphNode" VALUES (1228, 38.9471941, -92.3305285, 'Door A for Parker Hall');
INSERT INTO public."GraphNode" VALUES (1229, 38.9473539, -92.3303967, 'Door B for Parker Hall');
INSERT INTO public."GraphNode" VALUES (1230, 38.94470061, -92.32661798, 'Door A for Ellis Library');
INSERT INTO public."GraphNode" VALUES (1231, 38.94422206, -92.32689518, 'Door B for Ellis Library');
INSERT INTO public."GraphNode" VALUES (1232, 38.944916, -92.323179, 'Door A for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (1233, 38.944979, -92.322961, 'Door B for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (1234, 38.944781, -92.322899, 'Door C for Herman Schlundt Hall');
INSERT INTO public."GraphNode" VALUES (1235, 38.944559, -92.324973, 'Door A for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (1236, 38.944766, -92.324814, 'Door B for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (1237, 38.944762, -92.324467, 'Door C for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (1238, 38.94469, -92.323824, 'Door D for Gwynn / Stanley Hall');
INSERT INTO public."GraphNode" VALUES (1239, 38.94236171, -92.32665998, 'Door A for Student Center');
INSERT INTO public."GraphNode" VALUES (1240, 38.94258711, -92.32747269, 'Door B for Student Center');
INSERT INTO public."GraphNode" VALUES (1241, 38.94287784, -92.32711778, 'Door C for Student Center');
INSERT INTO public."GraphNode" VALUES (1242, 38.94291868, -92.32649618, 'Door D for Student Center');
INSERT INTO public."GraphNode" VALUES (1243, 38.94252678, -92.32967813, 'Door A for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (1244, 38.94309004, -92.32937235, 'Door B for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (1245, 38.9432465, -92.32967276, 'Door C for Cornell Hall');
INSERT INTO public."GraphNode" VALUES (1246, 38.94337216, -92.32725642, 'Door A for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (1247, 38.94337216, -92.32741624, 'Door B for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (1248, 38.94377127, -92.32726701, 'Door C for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (1249, 38.94377576, -92.32740661, 'Door D for Arts & Sciences');
INSERT INTO public."GraphNode" VALUES (1250, -92.32728034, 38.9460885, 'Door A for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (1251, -92.32714355, 38.9460885, 'Door B for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (1252, -92.32669562, 38.94611561, 'Door C for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (1253, -92.32669026, 38.94627207, 'Door D for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (1254, -92.3265186, 38.9459529, 'Door E for Middlebush Hall');
INSERT INTO public."GraphNode" VALUES (1255, -92.32370362, 38.94508614, 'Door A for Stewart Hall');
INSERT INTO public."GraphNode" VALUES (1256, -92.32395977, 38.94510387, 'Door B for Stewart Hall');
INSERT INTO public."GraphNode" VALUES (1257, 38.944591, -92.322905, 'Door A for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (1258, 38.944253, -92.322949, 'Door B for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (1259, 38.944222, -92.322239, 'Door C for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (1260, 38.944068, -92.322657, 'Door D for Chemistry Building');
INSERT INTO public."GraphNode" VALUES (1261, 38.9475435, -92.3305047, 'Door A for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (1262, 38.9475198, -92.3301054, 'Door B for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (1263, 38.947731, -92.3305094, 'Door C for McAlester Hall');
INSERT INTO public."GraphNode" VALUES (1264, 38.94374731, 92.32824324, 'Door A for Hulston hall');
INSERT INTO public."GraphNode" VALUES (1265, 38.94375854, -92.32841654, 'Door B for Hulston hall');
INSERT INTO public."GraphNode" VALUES (1266, 38.94392103, 92.32781, 'Door C for Hulston hall');
INSERT INTO public."GraphNode" VALUES (1267, -92.32529014, 38.94506841, 'Door A for Memorial Union');
INSERT INTO public."GraphNode" VALUES (1268, -92.32529551, 38.94547728, 'Door B for Memorial Union');
INSERT INTO public."GraphNode" VALUES (1269, -92.32485563, 38.94541887, 'Door C for Memorial Union');
INSERT INTO public."GraphNode" VALUES (1270, -92.32503533, 38.9460134, 'Door A for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (1271, -92.32485831, 38.94601653, 'Door B for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (1272, -92.32494146, 38.94617402, 'Door C for Mumford Hall');
INSERT INTO public."GraphNode" VALUES (1273, -92.32928798, 38.94672161, 'Door A for Switzler Hall');
INSERT INTO public."GraphNode" VALUES (1274, -92.32955888, 38.94662252, 'Door B for Switzler Hall');
INSERT INTO public."GraphNode" VALUES (1275, 38.94471878, -92.32790592, 'Door A for Tate Hall');
INSERT INTO public."GraphNode" VALUES (1276, 38.94454, -92.327998, 'Door B for Tate Hall');
INSERT INTO public."GraphNode" VALUES (1277, 38.94464914, -92.32811291, 'Door C for Tate Hall');
INSERT INTO public."GraphNode" VALUES (1278, 38.94478841, -92.32810232, 'Door D for Tate Hall');
INSERT INTO public."GraphNode" VALUES (1279, 38.944909, -92.327978, 'Door E for Tate Hall');
INSERT INTO public."GraphNode" VALUES (1280, 38.94726447, -92.32915847, 'Door A for Geological Sciences');
INSERT INTO public."GraphNode" VALUES (1281, 38.94731098, -92.32957951, 'Door B for Geological Sciences');
INSERT INTO public."GraphNode" VALUES (1282, 38.943259, -92.325279, 'Door A for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1283, 38.942949, -92.325436, 'Door B for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1284, 38.942548, -92.325451, 'Door C for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1285, 38.942418, -92.324874, 'Door D for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1286, 38.942591, -92.324058, 'Door E for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1287, 38.943136, -92.324221, 'Door F for Natural resources (Ag Science)');
INSERT INTO public."GraphNode" VALUES (1288, 38.94233242, -92.32802481, 'Door A for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (1289, 38.94268139, -92.32784414, 'Door B for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (1290, 38.94291763, -92.32789799, 'Door C for Strickland Hall');
INSERT INTO public."GraphNode" VALUES (1291, 38.944179, -92.324912, 'Door A for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (1292, 38.9443, -92.324919, 'Door B for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (1293, 38.944425, -92.325008, 'Door C for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (1294, 38.944307, -92.325238, 'Door D for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (1295, 38.944231, -92.32476, 'Door E for Gentry Hall');
INSERT INTO public."GraphNode" VALUES (1296, -92.32437551, 38.94598211, 'Door A for Walters hall');
INSERT INTO public."GraphNode" VALUES (1297, -92.32395306, 38.946028, 'Door B for Walters hall');
INSERT INTO public."GraphNode" VALUES (1298, -92.3239544, 38.94587363, 'Door C for Walters hall');
INSERT INTO public."GraphNode" VALUES (1299, -92.32623696, 38.94579019, 'Door A for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1300, -92.3258695, 38.94577767, 'Door B for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1301, -92.3258239, 38.94540427, 'Door C for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1302, -92.32593924, 38.94515185, 'Door D for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1303, -92.32621014, 38.94515394, 'Door E for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1304, -92.32631475, 38.94539384, 'Door F for Fine Arts Building');
INSERT INTO public."GraphNode" VALUES (1305, -92.32823789, 38.94581105, 'Door A for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (1306, -92.32826337, 38.9456233, 'Door B for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (1307, -92.32795626, 38.94577976, 'Door C for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (1308, -92.32795492, 38.94560974, 'Door D for Swallow Hall');
INSERT INTO public."GraphNode" VALUES (1309, 38.93932727, -92.33171672, 'Door A for Southwest Village');
INSERT INTO public."GraphNode" VALUES (1310, 38.939, -92.3318, 'Door B for Southwest Village');
INSERT INTO public."GraphNode" VALUES (1311, -92.32530087, 38.94572135, 'Door A for Whitten Hall');
INSERT INTO public."GraphNode" VALUES (1312, -92.32510239, 38.94577976, 'Door B for Whitten Hall');
INSERT INTO public."GraphNode" VALUES (1313, 38.943853, -92.322682, 'Door A for Physics Building');
INSERT INTO public."GraphNode" VALUES (1314, 38.943417, -92.322689, 'Door B for Physics Building');
INSERT INTO public."GraphNode" VALUES (1315, 38.943138, -92.323608, 'Door A for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1316, 38.943141, -92.323256, 'Door B for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1317, 38.943068, -92.322642, 'Door C for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1318, 38.942875, -92.323241, 'Door D for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1319, 38.942794, -92.323618, 'Door E for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1320, 38.942911, -92.32382, 'Door F for Life Sciences Center');
INSERT INTO public."GraphNode" VALUES (1321, -92.32801259, 38.9475216, 'Door A for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (1322, -92.32780874, 38.94751743, 'Door B for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (1323, -92.32804209, 38.94720035, 'Door C for Reynolds Journalism Institude');
INSERT INTO public."GraphNode" VALUES (1324, -92.32775509, 38.94727545, 'Door D for Reynolds Journalism Institude');


--
-- TOC entry 3404 (class 0 OID 16408)
-- Dependencies: 217
-- Data for Name: PIMetrics; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- TOC entry 3406 (class 0 OID 16412)
-- Dependencies: 219
-- Data for Name: PathEdges; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- TOC entry 3408 (class 0 OID 16416)
-- Dependencies: 221
-- Data for Name: Place; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- TOC entry 3410 (class 0 OID 16422)
-- Dependencies: 223
-- Data for Name: Room; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- TOC entry 3412 (class 0 OID 16428)
-- Dependencies: 225
-- Data for Name: SavedPath; Type: TABLE DATA; Schema: public; Owner: capy
--



--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 210
-- Name: Building_BuildingID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Building_BuildingID_seq"', 634, true);


--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 212
-- Name: Door_DoorID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Door_DoorID_seq"', 1050, true);


--
-- TOC entry 3431 (class 0 OID 0)
-- Dependencies: 214
-- Name: GraphEdge_EdgeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."GraphEdge_EdgeID_seq"', 710, true);


--
-- TOC entry 3432 (class 0 OID 0)
-- Dependencies: 216
-- Name: GraphNode_NodeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."GraphNode_NodeID_seq"', 1324, true);


--
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 218
-- Name: PIMetrics_MetricsID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."PIMetrics_MetricsID_seq"', 1, false);


--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 220
-- Name: PathEdges_PathEdgeID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."PathEdges_PathEdgeID_seq"', 1, false);


--
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 222
-- Name: Place_PlaceID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Place_PlaceID_seq"', 1, false);


--
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 224
-- Name: Room_RoomID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."Room_RoomID_seq"', 1, false);


--
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 226
-- Name: SavedPath_SavedPathID_seq; Type: SEQUENCE SET; Schema: public; Owner: capy
--

SELECT pg_catalog.setval('public."SavedPath_SavedPathID_seq"', 1, false);


--
-- TOC entry 3218 (class 2606 OID 16442)
-- Name: Building Building_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building"
    ADD CONSTRAINT "Building_pkey" PRIMARY KEY ("BuildingID");


--
-- TOC entry 3220 (class 2606 OID 16444)
-- Name: Door Door_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT "Door_NodeID_key" UNIQUE ("NodeID");


--
-- TOC entry 3222 (class 2606 OID 16446)
-- Name: Door Door_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT "Door_pkey" PRIMARY KEY ("DoorID");


--
-- TOC entry 3224 (class 2606 OID 16448)
-- Name: GraphEdge GraphEdge_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT "GraphEdge_pkey" PRIMARY KEY ("EdgeID");


--
-- TOC entry 3226 (class 2606 OID 16450)
-- Name: GraphNode GraphNode_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphNode"
    ADD CONSTRAINT "GraphNode_pkey" PRIMARY KEY ("NodeID");


--
-- TOC entry 3228 (class 2606 OID 16452)
-- Name: PIMetrics PIMetrics_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT "PIMetrics_NodeID_key" UNIQUE ("NodeID");


--
-- TOC entry 3230 (class 2606 OID 16454)
-- Name: PIMetrics PIMetrics_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT "PIMetrics_pkey" PRIMARY KEY ("MetricsID");


--
-- TOC entry 3232 (class 2606 OID 16456)
-- Name: PathEdges PathEdges_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT "PathEdges_pkey" PRIMARY KEY ("PathEdgeID");


--
-- TOC entry 3234 (class 2606 OID 16458)
-- Name: Place Place_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT "Place_NodeID_key" UNIQUE ("NodeID");


--
-- TOC entry 3236 (class 2606 OID 16460)
-- Name: Place Place_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT "Place_pkey" PRIMARY KEY ("PlaceID");


--
-- TOC entry 3238 (class 2606 OID 16462)
-- Name: Room Room_NodeID_key; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT "Room_NodeID_key" UNIQUE ("NodeID");


--
-- TOC entry 3240 (class 2606 OID 16464)
-- Name: Room Room_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT "Room_pkey" PRIMARY KEY ("RoomID");


--
-- TOC entry 3242 (class 2606 OID 16466)
-- Name: SavedPath SavedPath_pkey; Type: CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT "SavedPath_pkey" PRIMARY KEY ("SavedPathID");


--
-- TOC entry 3243 (class 2606 OID 16467)
-- Name: Building Building_NodeID_fkey; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Building"
    ADD CONSTRAINT "Building_NodeID_fkey" FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3253 (class 2606 OID 16472)
-- Name: Room fk_buildingid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT fk_buildingid FOREIGN KEY ("BuildingID") REFERENCES public."Building"("BuildingID");


--
-- TOC entry 3244 (class 2606 OID 16477)
-- Name: Door fk_buildingid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT fk_buildingid FOREIGN KEY ("BuildingID") REFERENCES public."Building"("BuildingID");


--
-- TOC entry 3249 (class 2606 OID 16482)
-- Name: PathEdges fk_edgeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fk_edgeid FOREIGN KEY ("EdgeID") REFERENCES public."GraphEdge"("EdgeID");


--
-- TOC entry 3246 (class 2606 OID 16487)
-- Name: GraphEdge fk_ending_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT fk_ending_nodeid FOREIGN KEY ("Node2ID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3252 (class 2606 OID 16492)
-- Name: Place fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Place"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3254 (class 2606 OID 16497)
-- Name: Room fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Room"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3245 (class 2606 OID 16502)
-- Name: Door fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."Door"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3248 (class 2606 OID 16507)
-- Name: PIMetrics fk_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PIMetrics"
    ADD CONSTRAINT fk_nodeid FOREIGN KEY ("NodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3250 (class 2606 OID 16512)
-- Name: PathEdges fk_savedpathid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fk_savedpathid FOREIGN KEY ("SavedPathID") REFERENCES public."SavedPath"("SavedPathID");


--
-- TOC entry 3247 (class 2606 OID 16517)
-- Name: GraphEdge fk_starting_nodeid; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."GraphEdge"
    ADD CONSTRAINT fk_starting_nodeid FOREIGN KEY ("Node1ID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3251 (class 2606 OID 16522)
-- Name: PathEdges fkeqhxhgwrv3wh4e4xk8lmqaonl; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."PathEdges"
    ADD CONSTRAINT fkeqhxhgwrv3wh4e4xk8lmqaonl FOREIGN KEY ("PathEdgeID") REFERENCES public."GraphEdge"("EdgeID");


--
-- TOC entry 3255 (class 2606 OID 16527)
-- Name: SavedPath fkgrg9qsnykg6gcv3a6gjxvblmf; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT fkgrg9qsnykg6gcv3a6gjxvblmf FOREIGN KEY ("startingNodeID") REFERENCES public."GraphNode"("NodeID");


--
-- TOC entry 3256 (class 2606 OID 16532)
-- Name: SavedPath fko7hrj0xg1477xoygeoxrvqfbm; Type: FK CONSTRAINT; Schema: public; Owner: capy
--

ALTER TABLE ONLY public."SavedPath"
    ADD CONSTRAINT fko7hrj0xg1477xoygeoxrvqfbm FOREIGN KEY ("endingNodeID") REFERENCES public."GraphNode"("NodeID");


-- Completed on 2022-02-20 09:22:27 UTC

--
-- PostgreSQL database dump complete
--

