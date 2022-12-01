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
\.


--
-- Data for Name: brands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.brands (id_brand, name, sid) FROM stdin;
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id_category, sid, name) FROM stdin;
\.


--
-- Data for Name: counties; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.counties (id_county, name, sid) FROM stdin;
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
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id_product, name, sid, date_received, fk_brand, fk_category, fk_supplier, received_by, sku, price, discount, quantity, description, warehouse) FROM stdin;
\.


--
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchases (id_purchases, sid, fk_supplier, invoice_number, date_received, bill_value, purchase_status, warehouse) FROM stdin;
\.


--
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.suppliers (id_supplier, name, sid, lead_time, phone_number, email) FROM stdin;
\.


--
-- Data for Name: towns; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.towns (id_town, name, sid, fk_county) FROM stdin;
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
\.


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.address_id_seq', 1, false);


--
-- Name: brand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.brand_id_seq', 1, false);


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 1, false);


--
-- Name: counties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.counties_id_seq', 1, false);


--
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.image_id_seq', 1, false);


--
-- Name: line_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.line_item_id_seq', 1, false);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 1, false);


--
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchases_id_seq', 1, false);


--
-- Name: supplier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.supplier_id_seq', 1, false);


--
-- Name: towns_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.towns_id_seq', 1, false);


--
-- Name: warehouse_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.warehouse_id_seq', 1, false);


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

