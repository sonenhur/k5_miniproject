CREATE TABLE movie (
    movie_id INT PRIMARY KEY,
    title VARCHAR(100),
    release_date DATE,
    running_time​ VARCHAR(50),
    rating VARCHAR(50),
    genre VARCHAR(50),
    synopsis VARCHAR(2000),
    director VARCHAR(50),
    cast VARCHAR(100)	
);

CREATE TABLE screening ( 
    screening_id INT PRIMARY KEY,
    movie_id INT,
    screening_date DATE,
    start_time TIME,
    end_time TIME,
    theater_id INT,
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY(theater_id) REFERENCES theater(theater_id)
);

CREATE TABLE cinema ( 
    cinema_id INT PRIMARY KEY, 
    name VARCHAR(15),
    location VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE theater ( 
    theater_id INT PRIMARY KEY,
    cinema_id INT,
    theater_no INT,
    theater_type VARCHAR(20),
    FOREIGN KEY(cinema_id) REFERENCES cinema(cinema_id)
);

CREATE TABLE seat ( 
    seat_id INT PRIMARY KEY,
    theater_id INT,
    seat_row CHAR(1),
    seat_col INT,
    FOREIGN KEY(theater_id) REFERENCES theater(theater_id)
);

CREATE TABLE member ( 
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(2000),
    name VARCHAR(50),
    birth DATE,
    join_date DATE
);

CREATE TABLE reservation ( 
    reservation_id INT PRIMARY KEY,
    email VARCHAR(50),
    adult INT,
    teenager INT,
    child INT,
    screening_id INT,
    FOREIGN KEY(email) REFERENCES member(email),
    FOREIGN KEY(screening_id) REFERENCES screening(screening_id)
);

CREATE TABLE payment (
    payment_id INT PRIMARY KEY,
    reservation_id INT,
    payment_date DATE,
    payment_method VARCHAR(50),
    amount INT,
    FOREIGN KEY(reservation_id) REFERENCES reservation(reservation_id)
);

CREATE TABLE review ( 
    email VARCHAR(50),
    movie_id INT,
    review_date DATE,
    rating DECIMAL(3,2),
    content VARCHAR(300),
    FOREIGN KEY(email) REFERENCES member(email),
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id)
);
