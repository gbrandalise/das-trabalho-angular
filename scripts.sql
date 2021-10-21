--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4 (Debian 13.4-1.pgdg100+1)
-- Dumped by pg_dump version 13.4

-- Started on 2021-10-21 19:39:10 UTC

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16393)
-- Name: client; Type: TABLE; Schema: public; Owner: pedidos
--

CREATE TABLE public.client (
    id bigint NOT NULL,
    cpf character varying(255),
    first_name character varying(255),
    last_name character varying(255)
);


ALTER TABLE public.client OWNER TO pedidos;

--
-- TOC entry 200 (class 1259 OID 16391)
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: pedidos
--

CREATE SEQUENCE public.client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_id_seq OWNER TO pedidos;

--
-- TOC entry 2972 (class 0 OID 0)
-- Dependencies: 200
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pedidos
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 203 (class 1259 OID 32771)
-- Name: order_item; Type: TABLE; Schema: public; Owner: pedidos
--

CREATE TABLE public.order_item (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    order_id bigint,
    product_id bigint
);


ALTER TABLE public.order_item OWNER TO pedidos;

--
-- TOC entry 202 (class 1259 OID 32769)
-- Name: order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: pedidos
--

CREATE SEQUENCE public.order_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_item_id_seq OWNER TO pedidos;

--
-- TOC entry 2973 (class 0 OID 0)
-- Dependencies: 202
-- Name: order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pedidos
--

ALTER SEQUENCE public.order_item_id_seq OWNED BY public.order_item.id;


--
-- TOC entry 205 (class 1259 OID 32779)
-- Name: product; Type: TABLE; Schema: public; Owner: pedidos
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE public.product OWNER TO pedidos;

--
-- TOC entry 204 (class 1259 OID 32777)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: pedidos
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO pedidos;

--
-- TOC entry 2974 (class 0 OID 0)
-- Dependencies: 204
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pedidos
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- TOC entry 207 (class 1259 OID 32787)
-- Name: purchase_order; Type: TABLE; Schema: public; Owner: pedidos
--

CREATE TABLE public.purchase_order (
    id bigint NOT NULL,
    date date NOT NULL,
    client_id bigint
);


ALTER TABLE public.purchase_order OWNER TO pedidos;

--
-- TOC entry 206 (class 1259 OID 32785)
-- Name: purchase_order_id_seq; Type: SEQUENCE; Schema: public; Owner: pedidos
--

CREATE SEQUENCE public.purchase_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchase_order_id_seq OWNER TO pedidos;

--
-- TOC entry 2975 (class 0 OID 0)
-- Dependencies: 206
-- Name: purchase_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pedidos
--

ALTER SEQUENCE public.purchase_order_id_seq OWNED BY public.purchase_order.id;


--
-- TOC entry 2822 (class 2604 OID 16396)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 2823 (class 2604 OID 32774)
-- Name: order_item id; Type: DEFAULT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.order_item ALTER COLUMN id SET DEFAULT nextval('public.order_item_id_seq'::regclass);


--
-- TOC entry 2824 (class 2604 OID 32782)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- TOC entry 2825 (class 2604 OID 32790)
-- Name: purchase_order id; Type: DEFAULT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.purchase_order ALTER COLUMN id SET DEFAULT nextval('public.purchase_order_id_seq'::regclass);


--
-- TOC entry 2827 (class 2606 OID 16398)
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- TOC entry 2829 (class 2606 OID 32776)
-- Name: order_item order_item_pkey; Type: CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_pkey PRIMARY KEY (id);


--
-- TOC entry 2831 (class 2606 OID 32784)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2833 (class 2606 OID 32792)
-- Name: purchase_order purchase_order_pkey; Type: CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT purchase_order_pkey PRIMARY KEY (id);


--
-- TOC entry 2836 (class 2606 OID 32803)
-- Name: purchase_order fk30v1b5ywmk0n0rasy577mcijx; Type: FK CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.purchase_order
    ADD CONSTRAINT fk30v1b5ywmk0n0rasy577mcijx FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- TOC entry 2835 (class 2606 OID 32798)
-- Name: order_item fk551losx9j75ss5d6bfsqvijna; Type: FK CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT fk551losx9j75ss5d6bfsqvijna FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 2834 (class 2606 OID 32793)
-- Name: order_item fklbn1xgrugcgx68cetsqa6f7px; Type: FK CONSTRAINT; Schema: public; Owner: pedidos
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT fklbn1xgrugcgx68cetsqa6f7px FOREIGN KEY (order_id) REFERENCES public.purchase_order(id);


-- Completed on 2021-10-21 19:39:10 UTC

--
-- PostgreSQL database dump complete
--

