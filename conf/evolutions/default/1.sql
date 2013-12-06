# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table abs_submission (
  id                        bigint not null,
  title                     varchar(255),
  cite                      varchar(255),
  link                      varchar(255),
  url                       varchar(255),
  publication_date          timestamp,
  abstract_body             clob,
  owner_email               varchar(255),
  constraint pk_abs_submission primary key (id))
;

create table user (
  email                     varchar(255) not null,
  password                  varchar(255),
  fullname                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence abs_submission_seq;

create sequence user_seq;

alter table abs_submission add constraint fk_abs_submission_owner_1 foreign key (owner_email) references user (email) on delete restrict on update restrict;
create index ix_abs_submission_owner_1 on abs_submission (owner_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists abs_submission;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists abs_submission_seq;

drop sequence if exists user_seq;

