CREATE TABLE movie(
    movie_cd INT AUTO_INCREMENT PRIMARY KEY,
    movie_title VARCHAR(100),
    movie_url VARCHAR(300),
    trailer_url VARCHAR(300),
    release_date DATE,
    running_time INT,
    film_rate INT
);

CREATE TABLE movie_info(
    movie_cd INT PRIMARY KEY,
    synopsis VARCHAR(2000),
    director VARCHAR(50),
    cast VARCHAR(100),
    distributor VARCHAR(50),
    FOREIGN KEY(movie_cd) REFERENCES movie(movie_cd)
);

CREATE TABLE member( 
    mem_id VARCHAR(50) PRIMARY KEY,
    mem_pw VARCHAR(2000),
    mem_birth DATE,
    mem_address VARCHAR(100),
    mem_phone VARCHAR(12),
    join_date DATE
);

CREATE TABLE review( 
    mem_id VARCHAR(50),
    movie_cd INT,
    review_date DATE,
    review_time TIMESTAMP,
    grade DECIMAL(3,1),
    review_contents VARCHAR(300),
    FOREIGN KEY(mem_id) REFERENCES member(mem_id),
    FOREIGN KEY(movie_cd) REFERENCES movie(movie_cd)
);

CREATE TABLE screening_schedule( 
    screening_cd INT AUTO_INCREMENT PRIMARY KEY,
    movie_cd INT,
    screening_date DATE,
    theater_cd INT,
    FOREIGN KEY(movie_cd) REFERENCES movie(movie_cd),
    FOREIGN KEY(theater_cd) REFERENCES theater(theater_cd)
);

CREATE TABLE theater( 
    theater_cd INT PRIMARY KEY,
    cinema_cd INT,
    theater_no INT,
    theater_type VARCHAR(20),
    FOREIGN KEY(cinema_cd) REFERENCES cinema(cinema_cd)
);

CREATE TABLE cinema( 
    cinema_cd INT PRIMARY KEY, 
    cinema_name VARCHAR(15),
    cinema_location VARCHAR(100),
    parking_info VARCHAR(100), 
    cinema_phone VARCHAR(12)
);

CREATE TABLE seat( 
    seat_cd INT AUTO_INCREMENT PRIMARY KEY,
    theater_cd INT,
    seat_row_no CHAR(1),
    seat_col_no INT,
    FOREIGN KEY(theater_cd) REFERENCES theater(theater_cd)
);

CREATE TABLE ticketing( 
    ticket_cd INT AUTO_INCREMENT PRIMARY KEY,
    mem_id VARCHAR(50),
    adult INT,
    teenager INT,
    child INT,
    screening_cd INT,
    FOREIGN KEY(mem_id) REFERENCES member(mem_id),
    FOREIGN KEY(screening_cd) REFERENCES screening_schedule(screening_cd)
);

CREATE TABLE payment(
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_cd INT,
    payment_date DATE,
    payment_method VARCHAR(50),
    card_number VARCHAR(16),
    expiration_date DATE,
    cvv VARCHAR(3),
    amount DECIMAL(10,2),
    FOREIGN KEY(ticket_cd) REFERENCES ticketing(ticket_cd)
);
