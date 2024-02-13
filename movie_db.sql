CREATE TABLE cinema( 
    cinema_id INT PRIMARY KEY, 
    cinema_name VARCHAR(15),
    cinema_location VARCHAR(100),
    cinema_phone VARCHAR(15)
);

CREATE TABLE theater( 
    theater_id INT PRIMARY KEY,
    cinema_id INT,
    theater_no INT,
    theater_type VARCHAR(20),
    FOREIGN KEY(cinema_id) REFERENCES cinema(cinema_id)
);

CREATE TABLE movie(
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_title VARCHAR(100),
    release_date DATE,
    running_time VARCHAR(50),
    film_rate VARCHAR(50),
    genre VARCHAR(50),
    synopsis VARCHAR(2000),
    director VARCHAR(50),
    casts VARCHAR(100)	
);

CREATE TABLE screening_schedule( 
    screening_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT,
    screening_date DATE,
    start_time TIME,
    end_time TIME,
    theater_id INT,
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY(theater_id) REFERENCES theater(theater_id)
);

CREATE TABLE seat( 
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    theater_id INT,
    seat_row_no CHAR(1),
    seat_col_no INT,
    FOREIGN KEY(theater_id) REFERENCES theater(theater_id)
);

CREATE TABLE member( 
    member_id VARCHAR(50) PRIMARY KEY,
    member_pw VARCHAR(2000),
    member_birth DATE,
    member_address VARCHAR(100),
    member_phone VARCHAR(15),
    join_date DATE
);

CREATE TABLE ticketing( 
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(50),
    adult INT,
    teenager INT,
    child INT,
    screening_id INT,
    FOREIGN KEY(member_id) REFERENCES member(member_id),
    FOREIGN KEY(screening_id) REFERENCES screening_schedule(screening_id)
);

CREATE TABLE payment(
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT,
    payment_date DATE,
    payment_method VARCHAR(50),
    amount INT,
    FOREIGN KEY(ticket_id) REFERENCES ticketing(ticket_id)
);

CREATE TABLE review( 
    member_id VARCHAR(50),
    movie_id INT,
    review_date DATE,
    grade DECIMAL(3,2),
    review_contents VARCHAR(300),
    FOREIGN KEY(member_id) REFERENCES member(member_id),
    FOREIGN KEY(movie_id) REFERENCES movie(movie_id)
);
