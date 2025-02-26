--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: ensure_single_true(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.ensure_single_true() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.selected THEN
        UPDATE recipe
        SET selected = FALSE
        WHERE idx <> NEW.idx;
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.ensure_single_true() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: error; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.error (
    idx integer NOT NULL,
    type character varying NOT NULL,
    message character varying NOT NULL,
    created_t timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.error OWNER TO postgres;

--
-- Name: error_idx_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.error_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.error_idx_seq OWNER TO postgres;

--
-- Name: error_idx_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.error_idx_seq OWNED BY public.error.idx;


--
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    idx integer NOT NULL,
    recipe_no integer NOT NULL,
    item_no integer NOT NULL,
    lane1 smallint DEFAULT '-1'::integer NOT NULL,
    lane2 smallint DEFAULT '-1'::integer NOT NULL,
    lane3 smallint DEFAULT '-1'::integer NOT NULL,
    lane4 smallint DEFAULT '-1'::integer NOT NULL,
    lane5 smallint DEFAULT '-1'::integer NOT NULL,
    lane6 smallint DEFAULT '-1'::integer NOT NULL,
    created_t timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.history OWNER TO postgres;

--
-- Name: history_idx_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.history_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.history_idx_seq OWNER TO postgres;

--
-- Name: history_idx_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.history_idx_seq OWNED BY public.history.idx;


--
-- Name: recipe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipe (
    idx integer NOT NULL,
    nickname character varying NOT NULL,
    type character varying(8) NOT NULL,
    weight character varying(128) NOT NULL,
    selected boolean DEFAULT false NOT NULL
);


ALTER TABLE public.recipe OWNER TO postgres;

--
-- Name: setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.setting (
    idx integer NOT NULL,
    output_cnt integer DEFAULT 180 NOT NULL,
    title character varying(128),
    exp_white integer DEFAULT 450 NOT NULL,
    exp_brown integer DEFAULT 800 NOT NULL,
    y1 integer DEFAULT 0 NOT NULL,
    y2 integer DEFAULT 1 NOT NULL,
    y3 integer DEFAULT 0 NOT NULL,
    y4 integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.setting OWNER TO postgres;

--
-- Name: setting_idx_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.setting_idx_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.setting_idx_seq OWNER TO postgres;

--
-- Name: setting_idx_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.setting_idx_seq OWNED BY public.setting.idx;


--
-- Name: error idx; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.error ALTER COLUMN idx SET DEFAULT nextval('public.error_idx_seq'::regclass);


--
-- Name: history idx; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history ALTER COLUMN idx SET DEFAULT nextval('public.history_idx_seq'::regclass);


--
-- Name: setting idx; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setting ALTER COLUMN idx SET DEFAULT nextval('public.setting_idx_seq'::regclass);


--
-- Name: error error_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.error
    ADD CONSTRAINT error_pkey PRIMARY KEY (idx);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (idx);


--
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (idx);


--
-- Name: setting setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setting
    ADD CONSTRAINT setting_pkey PRIMARY KEY (idx);


--
-- Name: recipe set_single_active; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_single_active AFTER UPDATE OF selected ON public.recipe FOR EACH ROW WHEN ((new.selected = true)) EXECUTE FUNCTION public.ensure_single_true();


--
-- Name: history history_recipe_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_recipe_fk FOREIGN KEY (recipe_no) REFERENCES public.recipe(idx) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

INSERT INTO public.setting(title) values('default');
insert into public.recipe(idx, nickname, type, weight, selected) values(1, 'abc', 'White', 'test.net', true);
insert into public.history(recipe_no, item_no) values(1,1);