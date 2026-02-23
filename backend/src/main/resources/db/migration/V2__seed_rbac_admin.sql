INSERT INTO sys_permission(code, name, created_at, updated_at)
VALUES
('vehicle:view', '车辆查看', now(), now()),
('protocol:parse', '协议解析', now(), now())
ON CONFLICT (code) DO NOTHING;

INSERT INTO sys_role(code, name, created_at, updated_at)
VALUES ('ADMIN', '系统管理员', now(), now())
ON CONFLICT (code) DO NOTHING;

INSERT INTO sys_role_permission(role_id, permission_id)
SELECT r.id, p.id
FROM sys_role r
JOIN sys_permission p ON p.code IN ('vehicle:view', 'protocol:parse')
WHERE r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO sys_user(username, password, enabled, created_at, updated_at)
VALUES (
    'admin',
    '$2a$10$9VDVY08yhxriK8vwwt4CQeL01aQ5AqjpcM6p4JDwYvH4HSR9Ekg.O', -- Admin@123
    TRUE,
    now(),
    now()
)
ON CONFLICT (username) DO NOTHING;

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.id, r.id
FROM sys_user u
JOIN sys_role r ON r.code = 'ADMIN'
WHERE u.username = 'admin'
ON CONFLICT DO NOTHING;
