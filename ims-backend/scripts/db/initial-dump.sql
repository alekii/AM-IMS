--
-- PostgreSQL database dump
--

-- Dumped from database version 12.11
-- Dumped by pg_dump version 12.12 (Ubuntu 12.12-0ubuntu0.20.04.1)

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
-- Name: address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.address_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.address_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addresses (
    id_address integer DEFAULT nextval('public.address_id_seq'::regclass) NOT NULL,
    street_name character varying(255) NOT NULL,
    latitude double precision,
    longitude double precision,
    map_url character varying(255),
    fk_town integer NOT NULL
);


ALTER TABLE public.addresses OWNER TO postgres;

--
-- Name: brand_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.brand_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.brand_id_seq OWNER TO postgres;

--
-- Name: brands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.brands (
    id_brand integer DEFAULT nextval('public.brand_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    sid character varying(255) NOT NULL
);


ALTER TABLE public.brands OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO postgres;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id_category integer DEFAULT nextval('public.category_id_seq'::regclass) NOT NULL,
    sid character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- Name: counties_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.counties_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.counties_id_seq OWNER TO postgres;

--
-- Name: counties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.counties (
    id_county smallint DEFAULT nextval('public.counties_id_seq'::regclass) NOT NULL,
    name character varying(45) NOT NULL,
    sid character varying(255) NOT NULL
);


ALTER TABLE public.counties OWNER TO postgres;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_id_seq OWNER TO postgres;

--
-- Name: images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.images (
    id_image integer DEFAULT nextval('public.image_id_seq'::regclass) NOT NULL,
    sid character varying(255) NOT NULL,
    fk_product integer NOT NULL,
    image_path character varying(255) NOT NULL
);


ALTER TABLE public.images OWNER TO postgres;

--
-- Name: line_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.line_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.line_item_id_seq OWNER TO postgres;

--
-- Name: line_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.line_items (
    id_line_item integer DEFAULT nextval('public.line_item_id_seq'::regclass) NOT NULL,
    sid character varying(255) NOT NULL,
    fk_product integer NOT NULL,
    fk_purchases integer NOT NULL,
    price double precision NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.line_items OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO postgres;

--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id_product integer DEFAULT nextval('public.product_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    sid character varying(255) NOT NULL,
    date_received timestamp without time zone,
    fk_brand integer NOT NULL,
    fk_category integer NOT NULL,
    fk_supplier integer NOT NULL,
    received_by character varying(255),
    sku character varying(255),
    price double precision NOT NULL,
    discount double precision,
    quantity integer NOT NULL,
    description character varying(1024),
    warehouse character varying(255) NOT NULL
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Name: purchases_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchases_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchases_id_seq OWNER TO postgres;

--
-- Name: purchases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchases (
    id_purchases integer DEFAULT nextval('public.purchases_id_seq'::regclass) NOT NULL,
    sid character varying(255) NOT NULL,
    fk_supplier integer,
    invoice_number integer,
    date_received timestamp without time zone,
    bill_value double precision,
    purchase_status character varying(255) NOT NULL,
    warehouse character varying(255) NOT NULL
);


ALTER TABLE public.purchases OWNER TO postgres;

--
-- Name: supplier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.supplier_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.supplier_id_seq OWNER TO postgres;

--
-- Name: suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suppliers (
    id_supplier integer DEFAULT nextval('public.supplier_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    sid character varying(255) NOT NULL,
    lead_time integer,
    phone_number character varying(255),
    email character varying(255)
);


ALTER TABLE public.suppliers OWNER TO postgres;

--
-- Name: towns_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.towns_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.towns_id_seq OWNER TO postgres;

--
-- Name: towns; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.towns (
    id_town integer DEFAULT nextval('public.towns_id_seq'::regclass) NOT NULL,
    name character varying(45) NOT NULL,
    sid character varying(255) NOT NULL,
    fk_county integer NOT NULL
);


ALTER TABLE public.towns OWNER TO postgres;

--
-- Name: warehouses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.warehouses (
    id_warehouse integer NOT NULL,
    name character varying(45) NOT NULL,
    sid character varying(255) NOT NULL,
    phone_number character varying(255),
    fk_address integer NOT NULL,
    contact_name character varying(255),
    tracking_numbers_count integer,
    created_at timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL
);


ALTER TABLE public.warehouses OWNER TO postgres;

--
-- Name: warehouse_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.warehouse_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.warehouse_id_seq OWNER TO postgres;

--
-- Name: warehouse_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.warehouse_id_seq OWNED BY public.warehouses.id_warehouse;


--
-- Name: warehouse_town_coverages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.warehouse_town_coverages_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.warehouse_town_coverages_id_seq OWNER TO postgres;

--
-- Name: warehouse_town_coverages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.warehouse_town_coverages (
    id_warehouse_town_coverage integer DEFAULT nextval('public.warehouse_town_coverages_id_seq'::regclass) NOT NULL,
    fk_town integer NOT NULL,
    fk_warehouse integer NOT NULL
);


ALTER TABLE public.warehouse_town_coverages OWNER TO postgres;

--
-- Name: warehouses id_warehouse; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouses ALTER COLUMN id_warehouse SET DEFAULT nextval('public.warehouse_id_seq'::regclass);


--
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.addresses (id_address, street_name, latitude, longitude, map_url, fk_town) FROM stdin;
1	5th Avenue	30.82609	148.20398	http://map1.com	1
11	Lumber Way	5.87776	40.45254	http://map2.com	41
21	Lilypad Passage	14.94752	15.83095	http://map3.com	31
31	Sapphire Way	39.28248	58.25683	http://map4.com	21
41	5th Avenue	-21.18794	-48.18496	http://map5.com	11
51	42nd Freedom Avenue	-24.59142	67.39737	http://map6.com	31
\.


--
-- Data for Name: brands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.brands (id_brand, name, sid) FROM stdin;
1	sony	36b327c1-3451-0fb7-8f5b-1324d90de4ec
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id_category, sid, name) FROM stdin;
1	18b327c1-0598-4fb7-8f5b-4321d90de4ec	electronics
\.


--
-- Data for Name: counties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.counties (id_county, name, sid) FROM stdin;
1	ELDORET	1d545f3d-7dce-4463-9ca6-48f45f03d444
11	MOMBASA	4af2a28d-5c9a-428a-badd-6249275c06c4
21	NAIROBI	1e965b1c1-1c63-4012-a1da-26cd32a5307
31	NAKURU	f960fd96-32d9-4caa-85c8-edb476d7ebcc
41	KIAMBU	ecc40f2d-1844-4004-b497-f947f561444d
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	100.202208091445	initial database reset	SQL	V1.0.1/V1.0.0/V100_202208091445__initial_database_reset.sql	1045460240	postgres	2022-12-01 12:07:33.21121	52	t
2	100.202208091530	initial database creation	SQL	V1.0.1/V1.0.0/V100_202208091530__initial_database_creation.sql	1399925023	postgres	2022-12-01 12:07:33.324967	447	t
3	100.202208151927	add contactname column to warehouse	SQL	V1.0.1/V1.0.0/V100_202208151927__add_contactname_column_to_warehouse.sql	334218740	postgres	2022-12-01 12:07:33.797736	4	t
4	100.202208151945	add tracking number count column to warehouse	SQL	V1.0.1/V1.0.0/V100_202208151945__add_tracking_number_count_column_to_warehouse.sql	-1489024794	postgres	2022-12-01 12:07:33.820857	4	t
5	100.202209012324	add created at field to warehouse	SQL	V1.0.1/V1.0.0/V100_202209012324__add_created_at_field_to_warehouse.sql	-587844985	postgres	2022-12-01 12:07:33.844652	3	t
6	100.202210010809	add stock entities	SQL	V1.0.1/V1.0.0/V100_202210010809__add_stock_entities.sql	-1610112618	postgres	2022-12-01 12:07:33.870544	975	t
7	100.202210111025	link stock to warehouse	SQL	V1.0.1/V1.0.0/V100_202210111025__link_stock_to_warehouse.sql	-88303288	postgres	2022-12-01 12:07:34.867017	3	t
\.


--
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.images (id_image, sid, fk_product, image_path) FROM stdin;
\.


--
-- Data for Name: line_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.line_items (id_line_item, sid, fk_product, fk_purchases, price, quantity) FROM stdin;
1	14b327c1-6789-5fb7-8f5b-4321d00de4ec	1	1	4	6564
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id_product, name, sid, date_received, fk_brand, fk_category, fk_supplier, received_by, sku, price, discount, quantity, description, warehouse) FROM stdin;
1	socket	18b401c1-0598-4fb7-9f5b-5678d23de4ec	2022-10-11 21:09:00.252271	1	1	1	me	fjhfgsdhfgsdhfgdshfg	34534	0	4	this is a socket	90b327c1-0598-4fb7-8f5b-4321d09de4ec
\.


--
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchases (id_purchases, sid, fk_supplier, invoice_number, date_received, bill_value, purchase_status, warehouse) FROM stdin;
1	1d545f3d-7dce-4463-9ca6-43f43f03d324	1	22362735	2022-10-11 21:05:42.252271	4234	1	06f60db0-90b6-4119-b267-7246a8c8428c
\.


--
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suppliers (id_supplier, name, sid, lead_time, phone_number, email) FROM stdin;
1	XB Suppliers	1d545f3d-7dce-4463-9ca6-43f43f03d324	2	+254789098987	xbsuppliers@xb.com
\.


--
-- Data for Name: towns; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.towns (id_town, name, sid, fk_county) FROM stdin;
1	Eldoret	7fd1ba57-b0c9-403c-bf27-e8e4457cc33c	1
11	Mombasa	be5652f5-e6c3-41e2-a8f3-10f2c6dfb8b1	11
21	Nairobi	d040a3f8-58be-448c-ad26-015b9be054fc	21
31	Nakuru	b6015b7f-abfc-4bb9-8630-221fece27cd4	31
41	Kiambu	2064eb28-d366-4323-b000-a8a27a0d6eb0	41
\.


--
-- Data for Name: warehouse_town_coverages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.warehouse_town_coverages (id_warehouse_town_coverage, fk_town, fk_warehouse) FROM stdin;
\.


--
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.warehouses (id_warehouse, name, sid, phone_number, fk_address, contact_name, tracking_numbers_count, created_at) FROM stdin;
1	Warehouse1	969f795c-b550-4f4e-80de-0db4c317581a	+254734567892	1	Manager1	365423	2022-09-06 21:07:15.039575
51	Warehouse2	06f60db0-90b6-4119-b267-7246a8c8428c	+254767234156	11	Manager2	158355	2022-09-06 21:06:47.051642
101	Warehouse3	3e083c68-f193-4bfc-b53c-7a58869c9048	+254723890567	21	Manager3	159452	2022-09-06 21:06:13.669522
151	Warehouse4	18b401c1-0598-4fb7-9f5b-7417d25de8ec	+254737234678	31	Manager4	337433	2022-09-06 20:53:58.864771
201	Warehouse5	a9b3746f-63d0-41d6-9589-f3ac4aadf44c	+254712908453	41	Manager5	116564	2022-09-06 21:05:42.252271
251	Warehouse6	9d771afd-ff6c-49c5-a120-c7da7774b0f5	+254708452985	51	Manager6	721218	2022-09-06 21:01:40.593941
\.


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.address_id_seq', 51, true);


--
-- Name: brand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.brand_id_seq', 1, true);


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 1, true);


--
-- Name: counties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.counties_id_seq', 41, true);


--
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.image_id_seq', 1, false);


--
-- Name: line_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.line_item_id_seq', 1, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 1, true);


--
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchases_id_seq', 1, true);


--
-- Name: supplier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.supplier_id_seq', 1, true);


--
-- Name: towns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.towns_id_seq', 41, true);


--
-- Name: warehouse_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.warehouse_id_seq', 251, true);


--
-- Name: warehouse_town_coverages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.warehouse_town_coverages_id_seq', 1, false);


--
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id_address);


--
-- Name: addresses addresses_unique_id_address; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_unique_id_address UNIQUE (id_address);


--
-- Name: brands brand_name_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brand_name_unique_idx UNIQUE (name);


--
-- Name: brands brand_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brand_sid_unique_idx UNIQUE (sid);


--
-- Name: brands brands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brands_pkey PRIMARY KEY (id_brand);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id_category);


--
-- Name: categories category_name_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT category_name_unique_idx UNIQUE (name);


--
-- Name: categories category_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT category_sid_unique_idx UNIQUE (sid);


--
-- Name: counties counties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.counties
    ADD CONSTRAINT counties_pkey PRIMARY KEY (id_county);


--
-- Name: counties counties_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.counties
    ADD CONSTRAINT counties_sid_unique_idx UNIQUE (sid);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: images image_sid_unique_index; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT image_sid_unique_index UNIQUE (sid);


--
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id_image);


--
-- Name: line_items line_item_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.line_items
    ADD CONSTRAINT line_item_sid_unique_idx UNIQUE (sid);


--
-- Name: line_items line_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.line_items
    ADD CONSTRAINT line_items_pkey PRIMARY KEY (id_line_item);


--
-- Name: products product_name_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT product_name_unique_idx UNIQUE (name);


--
-- Name: products product_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT product_sid_unique_idx UNIQUE (sid);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id_product);


--
-- Name: purchases purchases_invoice_number_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_invoice_number_unique_idx UNIQUE (invoice_number);


--
-- Name: purchases purchases_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id_purchases);


--
-- Name: purchases purchases_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_sid_unique_idx UNIQUE (sid);


--
-- Name: suppliers supplier_email_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT supplier_email_unique_idx UNIQUE (email);


--
-- Name: suppliers supplier_name_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT supplier_name_unique_idx UNIQUE (name);


--
-- Name: suppliers supplier_phone_number_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT supplier_phone_number_unique_idx UNIQUE (phone_number);


--
-- Name: suppliers supplier_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT supplier_sid_unique_idx UNIQUE (sid);


--
-- Name: suppliers suppliers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_pkey PRIMARY KEY (id_supplier);


--
-- Name: towns towns_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.towns
    ADD CONSTRAINT towns_pkey PRIMARY KEY (id_town);


--
-- Name: towns towns_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.towns
    ADD CONSTRAINT towns_sid_unique_idx UNIQUE (sid);


--
-- Name: warehouses warehouse_name_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouses
    ADD CONSTRAINT warehouse_name_unique_idx UNIQUE (name);


--
-- Name: warehouses warehouse_sid_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouses
    ADD CONSTRAINT warehouse_sid_unique_idx UNIQUE (sid);


--
-- Name: warehouse_town_coverages warehouse_town_coverage_town_unique_idx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouse_town_coverages
    ADD CONSTRAINT warehouse_town_coverage_town_unique_idx UNIQUE (fk_warehouse, fk_town);


--
-- Name: warehouse_town_coverages warehouse_town_coverages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouse_town_coverages
    ADD CONSTRAINT warehouse_town_coverages_pkey PRIMARY KEY (id_warehouse_town_coverage);


--
-- Name: warehouses warehouses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouses
    ADD CONSTRAINT warehouses_pkey PRIMARY KEY (id_warehouse);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: addresses fk_address_town; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT fk_address_town FOREIGN KEY (fk_town) REFERENCES public.towns(id_town);


--
-- Name: products fk_product_brand; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk_product_brand FOREIGN KEY (fk_brand) REFERENCES public.brands(id_brand);


--
-- Name: products fk_product_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk_product_category FOREIGN KEY (fk_category) REFERENCES public.categories(id_category);


--
-- Name: line_items fk_product_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.line_items
    ADD CONSTRAINT fk_product_id FOREIGN KEY (fk_product) REFERENCES public.products(id_product);


--
-- Name: images fk_product_image; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT fk_product_image FOREIGN KEY (fk_product) REFERENCES public.products(id_product);


--
-- Name: products fk_product_supplier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk_product_supplier FOREIGN KEY (fk_supplier) REFERENCES public.suppliers(id_supplier);


--
-- Name: line_items fk_purchases_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.line_items
    ADD CONSTRAINT fk_purchases_id FOREIGN KEY (fk_purchases) REFERENCES public.purchases(id_purchases);


--
-- Name: purchases fk_purchases_supplier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT fk_purchases_supplier FOREIGN KEY (fk_supplier) REFERENCES public.suppliers(id_supplier);


--
-- Name: warehouse_town_coverages fk_town_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouse_town_coverages
    ADD CONSTRAINT fk_town_id FOREIGN KEY (fk_town) REFERENCES public.towns(id_town);


--
-- Name: towns fk_towns_county; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.towns
    ADD CONSTRAINT fk_towns_county FOREIGN KEY (fk_county) REFERENCES public.counties(id_county);


--
-- Name: warehouses fk_warehouse_address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouses
    ADD CONSTRAINT fk_warehouse_address FOREIGN KEY (fk_address) REFERENCES public.addresses(id_address);


--
-- Name: warehouse_town_coverages fk_warehouse_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.warehouse_town_coverages
    ADD CONSTRAINT fk_warehouse_id FOREIGN KEY (fk_warehouse) REFERENCES public.warehouses(id_warehouse);


--
-- PostgreSQL database dump complete
--

