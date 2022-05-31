--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-05-31 00:44:41

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

SET SESSION AUTHORIZATION 'postgres';

DROP DATABASE "telnet";
--
-- TOC entry 3420 (class 1262 OID 16398)
-- Name: telnet; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "telnet" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'French_Tunisia.1252';


\connect "telnet"

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

SET SESSION AUTHORIZATION 'postgres';

--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 3420
-- Name: DATABASE "telnet"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE "telnet" IS 'stock des composants éléctroniques ';


--
-- TOC entry 868 (class 1247 OID 16971)
-- Name: my_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE "public"."my_type" AS (
	"f1" character varying(10),
	"f2" character varying(50)
);


--
-- TOC entry 232 (class 1255 OID 25424)
-- Name: achat(character varying, integer, character varying, integer, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."achat"(IN "name" character varying, IN "qty" integer, IN "descr" character varying, IN "projet" integer, IN "resp" character varying)
    LANGUAGE "plpgsql"
    AS '
BEGIN

INSERT INTO public."purchase_list "(
	 part_name, qty_needed, "description ", resp,projet)
	VALUES ( name, qty, descr, resp,projet);end;';


--
-- TOC entry 250 (class 1255 OID 17050)
-- Name: achat(character varying, integer, character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."achat"(IN "name" character varying, IN "qty" integer, IN "descr" character varying, IN "projet" character varying, IN "resp" character varying)
    LANGUAGE "plpgsql"
    AS '
BEGIN

INSERT INTO public."purchase_list "(
	 part_name, qty_needed, "description ", resp,projet)
	VALUES ( name, qty, descr, resp,projet);end;';


--
-- TOC entry 251 (class 1255 OID 17093)
-- Name: add_part(character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, "bytea", "bytea", "bytea", character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."add_part"(IN "id" character varying, IN "name" character varying, IN "label" character varying, IN "clas" character varying, IN "access" character varying, IN "origin" character varying, IN "store" character varying, IN "comment" character varying, IN "cat" character varying, IN "qr" "bytea", IN "img" "bytea", IN "datasheet" "bytea", IN "des" character varying)
    LANGUAGE "plpgsql"
    AS '  
    BEGIN 
	
	INSERT INTO public.ressources(
	internal_pn,  name, label, classification, access, origin, storage,  comment, category, qr, image, datasheet, "desc")
	VALUES (id, name, label, clas, access,origin ,store, comment, cat, qr, img, datasheet, des);
	
	
    END  
';


--
-- TOC entry 248 (class 1255 OID 17008)
-- Name: add_user(character varying[], character varying[], character varying[], character varying[], character varying[], character varying[], character varying[]); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."add_user"(IN "tab" character varying[], IN "users" character varying[], IN "parts" character varying[], IN "hist" character varying[], IN "projet" character varying[], IN "store" character varying[], IN "buy" character varying[])
    LANGUAGE "plpgsql"
    AS '
BEGIN

INSERT INTO public.privilege(
	 "table", users, parts, projects, storage, history, buy)
	VALUES ( tab, users, parts, hist,projet,store,buy);END; ';


--
-- TOC entry 239 (class 1255 OID 17003)
-- Name: delete_user(integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."delete_user"(IN "idd" integer)
    LANGUAGE "plpgsql"
    AS '
BEGIN
    delete from login_info where id=idd;
	delete from login where id=idd;
	delete from privilege where id=idd;
END; ';


--
-- TOC entry 237 (class 1255 OID 16796)
-- Name: get_parts(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "public"."get_parts"() RETURNS TABLE("internal_pn" character varying, "part_number" character varying, "name" character varying, "label" character varying, "description_id" bigint, "soft_version" bigint, "parametre" bigint, "classification" character varying, "access" character varying, "origin" character varying, "project_id" character varying, "storage" character varying, "stock" bigint, "created_on" timestamp without time zone, "modified_on" timestamp without time zone, "modified_by" character varying, "comment" character varying, "history_id" bigint, "purchase_id" bigint, "stat" character varying)
    LANGUAGE "plpgsql"
    AS '
begin
	return query 
		select * from ressources;
end;';


--
-- TOC entry 238 (class 1255 OID 16972)
-- Name: get_user_infos(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "public"."get_user_infos"("ident" integer) RETURNS "public"."my_type"
    LANGUAGE "plpgsql"
    AS '

DECLARE
  result_record my_type;

BEGIN
  SELECT  "user", nom
  INTO result_record.f1, result_record.f2
  FROM login_info
  WHERE id = ident;



  RETURN result_record;

END
';


--
-- TOC entry 229 (class 1255 OID 17071)
-- Name: history(character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."history"(IN "id" character varying, IN "resp" character varying, IN "event" character varying)
    LANGUAGE "plpgsql"
    AS '  
    BEGIN 
	INSERT INTO public.history(
	 part_id, resp, event)
	VALUES ( id, resp,event);
    END  
';


--
-- TOC entry 231 (class 1255 OID 16775)
-- Name: login(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "public"."login"("mat" character varying, "pass" character varying) RETURNS integer
    LANGUAGE "plpgsql"
    AS '
declare 
-- variable declaration
begin
	return(SELECT id from login where matricule=mat and mdp=MD5(pass));
end;
';


--
-- TOC entry 230 (class 1255 OID 16737)
-- Name: login_rec(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "public"."login_rec"() RETURNS "trigger"
    LANGUAGE "plpgsql"
    AS '
BEGIN
         CALL login_user(NEW.matricule);
 
    RETURN NEW;
END;
';


--
-- TOC entry 228 (class 1255 OID 16734)
-- Name: login_user(character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."login_user"(IN "mat" character varying)
    LANGUAGE "plpgsql"
    AS '
declare
 begin
INSERT INTO public.login(
	 matricule, mdp)
	VALUES (mat,MD5(''12345678''));
end; ';


--
-- TOC entry 249 (class 1255 OID 17036)
-- Name: update_pwd(character varying, integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE "public"."update_pwd"(IN "pwd" character varying, IN "idd" integer)
    LANGUAGE "plpgsql"
    AS '
BEGIN

UPDATE public.login
	SET  mdp=MD5(pwd)
	WHERE id=idd;
	END; ';


--
-- TOC entry 233 (class 1255 OID 16780)
-- Name: user_type(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "public"."user_type"("ident" integer) RETURNS character varying
    LANGUAGE "plpgsql"
    AS '
declare 
-- variable declaration
begin
return(SELECT "user",nom
	FROM login_info where id=ident);end;
';


SET default_tablespace = '';

SET default_table_access_method = "heap";

--
-- TOC entry 211 (class 1259 OID 16507)
-- Name: bom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."bom" (
    "id" integer NOT NULL,
    "part_id " character varying,
    "designators" character varying DEFAULT '-'::character varying,
    "qty" integer NOT NULL,
    "comment" character varying DEFAULT '-'::character varying,
    "resp" character varying NOT NULL,
    "date" timestamp without time zone DEFAULT "now"(),
    "label" character varying DEFAULT '-'::character varying,
    "name" character varying NOT NULL,
    "projet" integer NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 16506)
-- Name: bom_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."bom_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 210
-- Name: bom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."bom_id_seq" OWNED BY "public"."bom"."id";


--
-- TOC entry 222 (class 1259 OID 17058)
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."history" (
    "id" integer NOT NULL,
    "date" timestamp without time zone DEFAULT "now"() NOT NULL,
    "resp" character varying NOT NULL,
    "event" character varying NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 17057)
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."history_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 221
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."history_id_seq" OWNED BY "public"."history"."id";


--
-- TOC entry 217 (class 1259 OID 16716)
-- Name: login; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."login" (
    "id" integer NOT NULL,
    "matricule" character varying,
    "mdp" character varying
);


--
-- TOC entry 216 (class 1259 OID 16715)
-- Name: login_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."login_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 216
-- Name: login_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."login_id_seq" OWNED BY "public"."login"."id";


--
-- TOC entry 215 (class 1259 OID 16694)
-- Name: login_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."login_info" (
    "id" integer NOT NULL,
    "matricule" character varying NOT NULL,
    "user" character varying NOT NULL,
    "photo" "bytea",
    "date_ajout" timestamp without time zone DEFAULT "now"() NOT NULL,
    "nom" character varying,
    "email" character varying DEFAULT ''::character varying NOT NULL,
    "phone" character varying DEFAULT ''::character varying NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 16693)
-- Name: login_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."login_info_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 214
-- Name: login_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."login_info_id_seq" OWNED BY "public"."login_info"."id";


--
-- TOC entry 220 (class 1259 OID 16979)
-- Name: privilege; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."privilege" (
    "id" integer NOT NULL,
    "table" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL,
    "users" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL,
    "parts" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL,
    "projects" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL,
    "history" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL,
    "buy" character varying[] DEFAULT '{non,non}'::character varying[] NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16978)
-- Name: privilege_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."privilege_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 219
-- Name: privilege_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."privilege_id_seq" OWNED BY "public"."privilege"."id";


--
-- TOC entry 224 (class 1259 OID 25343)
-- Name: projet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."projet" (
    "id" integer NOT NULL,
    "name" character varying NOT NULL,
    "desc" "text" DEFAULT '-'::"text",
    "date" timestamp without time zone DEFAULT "now"(),
    "resp" character varying NOT NULL,
    "team" character varying
);


--
-- TOC entry 223 (class 1259 OID 25342)
-- Name: projet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."projet_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 223
-- Name: projet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."projet_id_seq" OWNED BY "public"."projet"."id";


--
-- TOC entry 213 (class 1259 OID 16537)
-- Name: purchase_list ; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."purchase_list " (
    "id" integer NOT NULL,
    "part_name" character varying NOT NULL,
    "qty_needed" integer NOT NULL,
    "description " character varying,
    "date" timestamp without time zone DEFAULT "now"() NOT NULL,
    "resp" character varying DEFAULT 0 NOT NULL,
    "projet" integer
);


--
-- TOC entry 212 (class 1259 OID 16536)
-- Name: purchase_list _id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."purchase_list _id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 212
-- Name: purchase_list _id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."purchase_list _id_seq" OWNED BY "public"."purchase_list "."id";


--
-- TOC entry 209 (class 1259 OID 16413)
-- Name: ressources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."ressources" (
    "internal_pn" character varying NOT NULL,
    "name" character varying NOT NULL,
    "label" character varying DEFAULT '-'::character varying NOT NULL,
    "classification" character varying DEFAULT '-'::character varying NOT NULL,
    "origin" character varying DEFAULT '-'::character varying NOT NULL,
    "storage" character varying DEFAULT '-'::character varying NOT NULL,
    "created_on" timestamp without time zone DEFAULT "now"() NOT NULL,
    "image" "bytea",
    "desc" "text" DEFAULT '-'::"text",
    "datasheet" "bytea",
    "nb" integer NOT NULL
);


--
-- TOC entry 227 (class 1259 OID 25557)
-- Name: ressources_nb_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."ressources_nb_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 227
-- Name: ressources_nb_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."ressources_nb_seq" OWNED BY "public"."ressources"."nb";


--
-- TOC entry 225 (class 1259 OID 25356)
-- Name: test; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."test" (
    "id" integer NOT NULL,
    "bom_id" integer NOT NULL,
    "test" character varying NOT NULL,
    "valide" boolean,
    "project_id" integer NOT NULL
);


--
-- TOC entry 226 (class 1259 OID 25359)
-- Name: test_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."test_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 226
-- Name: test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."test_id_seq" OWNED BY "public"."test"."id";


--
-- TOC entry 3228 (class 2604 OID 16510)
-- Name: bom id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."bom" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."bom_id_seq"'::"regclass");


--
-- TOC entry 3248 (class 2604 OID 17061)
-- Name: history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."history" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."history_id_seq"'::"regclass");


--
-- TOC entry 3240 (class 2604 OID 16719)
-- Name: login id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."login" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."login_id_seq"'::"regclass");


--
-- TOC entry 3236 (class 2604 OID 16697)
-- Name: login_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."login_info" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."login_info_id_seq"'::"regclass");


--
-- TOC entry 3241 (class 2604 OID 16982)
-- Name: privilege id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."privilege" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."privilege_id_seq"'::"regclass");


--
-- TOC entry 3250 (class 2604 OID 25346)
-- Name: projet id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."projet" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."projet_id_seq"'::"regclass");


--
-- TOC entry 3235 (class 2604 OID 25454)
-- Name: purchase_list  id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."purchase_list " ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."purchase_list _id_seq"'::"regclass");


--
-- TOC entry 3227 (class 2604 OID 25558)
-- Name: ressources nb; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."ressources" ALTER COLUMN "nb" SET DEFAULT "nextval"('"public"."ressources_nb_seq"'::"regclass");


--
-- TOC entry 3253 (class 2604 OID 25360)
-- Name: test id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."test" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."test_id_seq"'::"regclass");


--
-- TOC entry 3257 (class 2606 OID 16514)
-- Name: bom bom_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."bom"
    ADD CONSTRAINT "bom_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3267 (class 2606 OID 17065)
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."history"
    ADD CONSTRAINT "history_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3261 (class 2606 OID 16701)
-- Name: login_info login_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."login_info"
    ADD CONSTRAINT "login_info_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3263 (class 2606 OID 16723)
-- Name: login login_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."login"
    ADD CONSTRAINT "login_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3265 (class 2606 OID 16986)
-- Name: privilege privilege_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."privilege"
    ADD CONSTRAINT "privilege_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3269 (class 2606 OID 25351)
-- Name: projet projet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."projet"
    ADD CONSTRAINT "projet_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3259 (class 2606 OID 25456)
-- Name: purchase_list  purchase_list _pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."purchase_list "
    ADD CONSTRAINT "purchase_list _pkey" PRIMARY KEY ("qty_needed", "id");


--
-- TOC entry 3255 (class 2606 OID 16422)
-- Name: ressources ressources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."ressources"
    ADD CONSTRAINT "ressources_pkey" PRIMARY KEY ("internal_pn");


--
-- TOC entry 3271 (class 2606 OID 25365)
-- Name: test test_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."test"
    ADD CONSTRAINT "test_pkey" PRIMARY KEY ("id");


--
-- TOC entry 3275 (class 2620 OID 16738)
-- Name: login_info test_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "test_trigger" AFTER INSERT ON "public"."login_info" FOR EACH ROW EXECUTE FUNCTION "public"."login_rec"();


--
-- TOC entry 3273 (class 2606 OID 25383)
-- Name: test bom; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."test"
    ADD CONSTRAINT "bom" FOREIGN KEY ("bom_id") REFERENCES "public"."bom"("id") NOT VALID;


--
-- TOC entry 3272 (class 2606 OID 16748)
-- Name: login_info id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."login_info"
    ADD CONSTRAINT "id" FOREIGN KEY ("id") REFERENCES "public"."login"("id") DEFERRABLE INITIALLY DEFERRED NOT VALID;


--
-- TOC entry 3274 (class 2606 OID 25419)
-- Name: test project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."test"
    ADD CONSTRAINT "project" FOREIGN KEY ("project_id") REFERENCES "public"."projet"("id") NOT VALID;


-- Completed on 2022-05-31 00:44:41

--
-- PostgreSQL database dump complete
--

