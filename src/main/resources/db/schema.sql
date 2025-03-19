CREATE TABLE IF NOT EXISTS `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    weekScore INT NOT NULL,
    `rank` INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS friend (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    friendList JSON NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS todo_list (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    content VARCHAR(255) NOT NULL,
    isMission BOOLEAN NOT NULL,
    isChecked BOOLEAN NOT NULL,
    link VARCHAR(512),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS calendar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
    );
    
CREATE TABLE IF NOT EXISTS friend_request (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status INT NOT NULL,  -- 요청 상태 (0: 대기, 1: 수락, 2: 거절)
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user(id) ON DELETE CASCADE
);