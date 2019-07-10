--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 11.3

-- Started on 2019-07-10 08:41:39

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

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 24613)
-- Name: lookup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lookup (
    lookup_id bigint NOT NULL,
    name character varying(255),
    category_type character varying(255)
);


ALTER TABLE public.lookup OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 24628)
-- Name: lookup_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lookup_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lookup_id_seq OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 24621)
-- Name: user_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_group (
    user_group_id bigint NOT NULL,
    lookup_id bigint NOT NULL,
    user_id bigint NOT NULL,
    view_seq bigint NOT NULL,
    menu_id bigint
);


ALTER TABLE public.user_group OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 24630)
-- Name: user_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_group_id_seq OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 24626)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 24605)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    username character varying(255),
    full_name character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 24632)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2143 (class 0 OID 24613)
-- Dependencies: 186
-- Data for Name: lookup; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lookup (lookup_id, name, category_type) FROM stdin;
1	PromoCard	menu_type
2	CategoryCard	menu_type
3	FlashSaleCard	menu_type
4	HistoryCard	menu_type
5	NewsCard	menu_type
\.


--
-- TOC entry 2144 (class 0 OID 24621)
-- Dependencies: 187
-- Data for Name: user_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_group (user_group_id, lookup_id, user_id, view_seq, menu_id) FROM stdin;
1	1	1	1	1
2	2	1	2	2
3	3	1	3	3
4	4	1	4	4
5	5	1	5	5
6	1	2	1	1
8	3	2	3	3
10	5	2	5	5
11	1	3	1	1
7	2	2	2	5
9	4	2	4	2
12	2	3	2	3
13	3	3	3	2
14	4	3	4	5
15	5	3	5	4
\.


--
-- TOC entry 2142 (class 0 OID 24605)
-- Dependencies: 185
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, username, full_name) FROM stdin;
1	userA	kojiro sasaki
2	userB	sasaki
3	userC	kenshin
\.


--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 189
-- Name: lookup_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lookup_id_seq', 5, true);


--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 190
-- Name: user_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_group_id_seq', 15, true);


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 188
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 3, true);


--
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 191
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- TOC entry 2020 (class 2606 OID 24620)
-- Name: lookup lookup_inquiry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lookup
    ADD CONSTRAINT lookup_inquiry_pkey PRIMARY KEY (lookup_id);


--
-- TOC entry 2022 (class 2606 OID 24625)
-- Name: user_group users_group_inquiry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT users_group_inquiry_pkey PRIMARY KEY (user_group_id);


--
-- TOC entry 2018 (class 2606 OID 24612)
-- Name: users users_inquiry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_inquiry_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2024 (class 2606 OID 24639)
-- Name: user_group fk7k9ade3lqbo483u9vuryxmm34; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT fk7k9ade3lqbo483u9vuryxmm34 FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2023 (class 2606 OID 24634)
-- Name: user_group fkd007vch3pht8uv7foclhd40kt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_group
    ADD CONSTRAINT fkd007vch3pht8uv7foclhd40kt FOREIGN KEY (menu_id) REFERENCES public.lookup(lookup_id);


-- Completed on 2019-07-10 08:41:40

--
-- PostgreSQL database dump complete
--

