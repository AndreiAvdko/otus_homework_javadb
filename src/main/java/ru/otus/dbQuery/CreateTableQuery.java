package ru.otus.dbQuery;

public class CreateTableQuery {
    public static final String createStudentTableQuerry = "create table if not exists \"Student\"\n" +
            "(\n" +
            "    id   integer generated always as identity (maxvalue 10000)\n" +
            "        constraint \"Student_pk\"\n" +
            "            primary key,\n" +
            "    name varchar not null,\n" +
            "    sex  char,\n" +
            "    age  integer not null\n" +
            ");";

    public static final String createGroupTableQuerry = "create table if not exists public.\"Group\"\n" +
            "(\n" +
            "    name       varchar not null,\n" +
            "    curator_id integer not null\n" +
            "        constraint \"Group_Curator_id_fk\"\n" +
            "            references public.\"Curator\",\n" +
            "    id         integer generated always as identity (maxvalue 10000)\n" +
            "        constraint \"Group_pk\"\n" +
            "            primary key\n" +
            ");";

    public static final String createCuratorTableQuerry = "create table if not exists public.\"Curator\"\n" +
            "(\n" +
            "    id   integer generated always as identity (maxvalue 10000)\n" +
            "        constraint \"Curator_pk\"\n" +
            "            primary key,\n" +
            "    name varchar not null\n" +
            ");";
}
