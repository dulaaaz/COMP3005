# COMP3005
Assignment 3

Youtube link: https://youtu.be/su1hROygPB8
File name: A3Q1jdbc
File path: src/main/java/org/example
testing instructions: on pgAdmin create a new database called A3Q1
                      and then query tool, and run this
                       CREATE TABLE students (
                            student_id SERIAL PRIMARY KEY,
                            first_name TEXT NOT NULL,
                            last_name TEXT NOT NULL,
                            email TEXT NOT NULL UNIQUE,
                            enrollment_date DATE
                        );

once done with that insert the values you want into the table, then you can run the A3Q1jdbc file.


