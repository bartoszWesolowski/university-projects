CREATE TABLE public.films_urls
(
  id SERIAL primary key,
  url text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.films_urls
  OWNER TO postgres;

CREATE TABLE public.films
(
  id integer NOT NULL,
  url text,
  title text,
  votes integer,
  peoplewanttosee integer,
  stars double precision,
  director text,
  actors text[],
  actorsstars double precision[],
  actorsstarsavg double precision,
  CONSTRAINT films_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.films
  OWNER TO postgres;
 
  
SELECT id, url FROM films_urls