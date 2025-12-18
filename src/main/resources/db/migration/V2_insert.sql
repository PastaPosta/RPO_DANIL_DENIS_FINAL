INSERT INTO t_group (name) VALUES
    ('Group A'),
    ('Group B'),
    ('Group C');

INSERT INTO t_subject (name, teacher, length) VALUES
    ('Mathematics', 'Mr. Akylbek', 90),
    ('Physics', 'Mrs. Zhanar', 120),
    ('History', 'Mr. Serik', 60),
    ('Programming', 'Ms. Aigerim', 150);

INSERT INTO t_student (full_name, age, group_id) VALUES
    ('Alibek Nurly', 20, 1),
    ('Dana Serik', 19, 1),
    ('Miras Toleu', 21, 2),
    ('Aruzhan Nurlan', 18, 3);

INSERT INTO t_group_subject (group_id, subject_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 4),
    (3, 3);

INSERT INTO t_permissions (name) VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');

INSERT INTO t_users (id, username, password) VALUES
    (1, 'user', '$2a$12$Aim0/5fGs6PWupAVLlbBJe1kslsSQhc.R5mtoJIBSjfGi/n44hnVm'),
    (2, 'admin', '$2a$12$TfSdoZ0HD4Ao0ZjKba1Z8ef7PE7Vvbwx9CkdgsjCo4rQvWQcjK5sC');

INSERT INTO t_users_permissions (user_id, permissions_id) VALUES
    (1, 1),
    (2, 2);