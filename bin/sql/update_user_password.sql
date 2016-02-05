-- update_user_password.sql
UPDATE cms_myuser SET password = ? WHERE username = ? AND password = ?;