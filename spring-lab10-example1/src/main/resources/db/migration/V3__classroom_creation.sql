CREATE TABLE classroom (
                        id_classroom BIGINT NOT NULL AUTO_INCREMENT,
                        classroom_name VARCHAR(50) NOT NULL,
                        classroom_number BIGINT,

                        PRIMARY KEY (id_classroom)
);

Alter table users
add foreign key (id_classroom) references classroom(id_classroom);