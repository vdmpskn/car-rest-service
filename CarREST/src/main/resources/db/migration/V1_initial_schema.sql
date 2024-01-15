--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: car; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.car (
    id bigint NOT NULL,
    year integer,
    producer_id bigint
);


ALTER TABLE public.car OWNER TO postgres;

--
-- Name: car_car_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.car_car_type (
    car_id bigint NOT NULL,
    car_type_id bigint NOT NULL
);


ALTER TABLE public.car_car_type OWNER TO postgres;

--
-- Name: car_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.car_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.car_id_seq OWNER TO postgres;

--
-- Name: car_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.car_id_seq OWNED BY public.car.id;


--
-- Name: car_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.car_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.car_seq OWNER TO postgres;

--
-- Name: car_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.car_type (
    id bigint NOT NULL,
    body_type character varying(255)
);


ALTER TABLE public.car_type OWNER TO postgres;

--
-- Name: car_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.car_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.car_type_id_seq OWNER TO postgres;

--
-- Name: car_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.car_type_id_seq OWNED BY public.car_type.id;


--
-- Name: producer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producer (
    id bigint NOT NULL,
    model_name character varying(255),
    producer_name character varying(255)
);


ALTER TABLE public.producer OWNER TO postgres;

--
-- Name: producer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.producer_id_seq OWNER TO postgres;

--
-- Name: producer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producer_id_seq OWNED BY public.producer.id;


--
-- Name: car id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car ALTER COLUMN id SET DEFAULT nextval('public.car_id_seq'::regclass);


--
-- Name: car_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_type ALTER COLUMN id SET DEFAULT nextval('public.car_type_id_seq'::regclass);


--
-- Name: producer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producer ALTER COLUMN id SET DEFAULT nextval('public.producer_id_seq'::regclass);


--
-- Data for Name: car; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car (id, year, producer_id) FROM stdin;
2	2020	2
3	2020	3
4	2020	4
5	2020	5
7	2020	7
8	2020	8
9	2020	9
10	2020	10
11	2020	11
12	2020	12
13	2020	13
14	2020	14
15	2020	15
16	2020	16
17	2020	17
18	2020	18
19	2020	19
20	2020	20
21	2020	21
22	2020	22
23	2020	23
24	2020	24
25	2020	25
26	2020	26
27	2020	27
29	1998	29
30	1998	30
1	2025	1
\.


--
-- Data for Name: car_car_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car_car_type (car_id, car_type_id) FROM stdin;
1	1
2	3
2	2
3	4
4	5
5	6
7	8
8	9
9	10
10	11
11	12
12	13
13	14
14	15
15	16
15	17
16	18
17	19
18	20
19	21
20	22
21	23
22	24
23	25
24	26
25	27
26	28
27	29
29	30
29	31
30	32
\.


--
-- Data for Name: car_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car_type (id, body_type) FROM stdin;
1	SUV
2	CONVERTIBLE
3	COUPE
4	SEDAN
5	PICKUP
6	SEDAN
7	VAN_MINIVAN
8	PICKUP
9	SUV
10	SEDAN
11	PICKUP
12	SEDAN
13	SEDAN
14	SUV
15	SUV
16	CONVERTIBLE
17	COUPE
18	SUV
19	SUV
20	SUV
21	SUV
22	PICKUP
23	SEDAN
24	SUV
25	SUV
26	SUV
27	SUV
28	SUV
29	SUV
30	SEDAN
31	SUV
32	SEDAN
\.


--
-- Data for Name: producer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producer (id, model_name, producer_name) FROM stdin;
1	Escalade ESV	Cadillac
2	Corvette	Chevrolet
4	Silverado 2500 HD Crew Cab	Chevrolet
5	3 Series	BMW
6	Pacifica	Chrysler
7	Colorado Crew Cab	Chevrolet
8	X3	BMW
9	TLX	Acura
10	Silverado 3500 HD Crew Cab	Chevrolet
11	7 Series	BMW
12	Fusion	Ford
13	Envision	Buick
14	SQ5	Audi
15	R8	Audi
16	Traverse	Chevrolet
17	MDX	Acura
18	QX80	INFINITI
19	Encore	Buick
20	Sierra 2500 HD Crew Cab	GMC
21	Insight	Honda
22	XT6	Cadillac
23	XT5	Cadillac
24	XT4	Cadillac
25	Enclave	Buick
26	Q5	Audi
27	Santa Fe	Hyundai
3	RLX	AcurAAA
29	RAV4	Toyota
30	RAV8	Toyota
\.


--
-- Name: car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.car_id_seq', 30, true);


--
-- Name: car_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.car_seq', 1, true);


--
-- Name: car_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.car_type_id_seq', 32, true);


--
-- Name: producer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producer_id_seq', 30, true);


--
-- Name: car_car_type car_car_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_car_type
    ADD CONSTRAINT car_car_type_pkey PRIMARY KEY (car_id, car_type_id);


--
-- Name: car car_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);


--
-- Name: car_type car_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_type
    ADD CONSTRAINT car_type_pkey PRIMARY KEY (id);


--
-- Name: producer producer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producer
    ADD CONSTRAINT producer_pkey PRIMARY KEY (id);


--
-- Name: car uk_5lftbnn5khs6nbos0y2bj6ohk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT uk_5lftbnn5khs6nbos0y2bj6ohk UNIQUE (producer_id);


--
-- Name: car_car_type uk_aoqeivs69rnbp7p4bro0ccr8l; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_car_type
    ADD CONSTRAINT uk_aoqeivs69rnbp7p4bro0ccr8l UNIQUE (car_type_id);


--
-- Name: car_car_type fk3pckqx1wbmsii7gq593se8x2q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_car_type
    ADD CONSTRAINT fk3pckqx1wbmsii7gq593se8x2q FOREIGN KEY (car_type_id) REFERENCES public.car_type(id);


--
-- Name: car fkic586bfxdfnh736rcfey4wakf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT fkic586bfxdfnh736rcfey4wakf FOREIGN KEY (producer_id) REFERENCES public.producer(id);


--
-- Name: car_car_type fkoji48crygxjqeuoenipovnkhl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_car_type
    ADD CONSTRAINT fkoji48crygxjqeuoenipovnkhl FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- PostgreSQL database dump complete
--

