
-- USERS
INSERT INTO users (
    id, full_name, email, password, number_phone, created_at, birthday,
    rol, status, profile_photo_id, profile_photo, is_host
) VALUES
      ('u001', 'María Gómez', 'maria.gomez@example.com', '1234',
       '+573001112233', '2025-10-05 14:30:00', '1990-06-15',
       'GUEST', 'ACTIVE', NULL, 'https://example.com/photos/maria.jpg', 0),

      ('u002', 'Juan Pérez', 'juan.perez@example.com', '1234',
       '+573224445566', '2025-09-28 09:45:00', '1985-12-02',
       'HOST', 'ACTIVE', NULL, 'https://example.com/photos/juan.jpg', 1),

      ('u003', 'Laura Mendoza', 'laura.mendoza@example.com', '1234',
       '+573113334455', '2025-10-01 17:20:00', '1998-03-22',
       'GUEST', 'INACTIVE', NULL, NULL, 0);

-- ================================
-- Tabla: adress
-- ================================
INSERT INTO adress (id, adress, city, latitud, longitud) VALUES
                                                             (1, 'Carrera 45 #23-10', 'Medellín', '6.2442', '-75.5812'),
                                                             (2, 'Calle 10 #5-22', 'Cartagena', '10.3910', '-75.4794'),
                                                             (3, 'Carrera 7 #72-50', 'Bogotá', '4.71099', '-74.0721');

-- ================================
-- Tabla: listings
-- ================================
INSERT INTO listings (id, title, description, average_rating, creation_date, adress_id, host_id, nightly_price, status, max_guest) VALUES
                                                                                                                                       ('l001', 'Apartamento moderno con vista panorámica', 'Hermoso apartamento en El Poblado con vista a la ciudad y piscina.', 4.8, '2025-09-15 10:20:00', 1, 'u002', 320000, 'ACTIVE', 4),
                                                                                                                                       ('l002', 'Casa colonial en el centro histórico', 'Casa colonial en Cartagena ideal para grupos grandes, con terraza y jacuzzi.', 4.6, '2025-08-30 18:45:00', 2, 'u002', 580000, 'ACTIVE', 8),
                                                                                                                                       ('l003', 'Estudio céntrico en Chapinero', 'Estudio acogedor en Bogotá cerca de restaurantes y transporte.', 4.2, '2025-10-02 09:10:00', 3, 'u002', 190000, 'INACTIVE', 2);


-- RESERVATIONS
INSERT INTO reservations (
    id, check_in, created_at, check_out, guest_count, discount_code,
    listing_id, user_id, reservations_status, price
) VALUES
      ('r001', '2025-10-10 15:00:00', '2025-10-01 10:30:00', '2025-10-15 11:00:00',
       2, NULL, 'l001', 'u001', 'CONFIRMED', 1600000.00),

      ('r002', '2025-11-05 14:00:00', '2025-10-20 09:00:00', '2025-11-10 10:00:00',
       3, 'PROMO5', 'l002', 'u003', 'PENDING', 2900000.00),

      ('r003', '2025-09-10 16:00:00', '2025-09-01 08:00:00', '2025-09-12 12:00:00',
       1, NULL, 'l003', 'u001', 'CANCELLED', 380000.00);

-- CODIGOSRECUPERACION

INSERT INTO codigos_recuperacion (
    id, code, created_at, expires_at, user_id
) VALUES
      ('p001', '48321', '2025-10-01 08:00:00', '2025-10-01 08:10:00', 'u001'),
      ('p002', '93572', '2025-10-02 09:15:00', '2025-10-02 09:25:00', 'u002'),
      ('p003', '12458', '2025-10-03 10:30:00', '2025-10-03 10:40:00', 'u003');

-- REVIEWS

INSERT INTO reviews (
    id, rating, listing_id, comment, user_id, created_at, reply_at_id
) VALUES
      (1,5, 'l001', 'Hermoso apartamento, muy limpio y bien ubicado.', 'u001', '2025-10-01 11:00:00', NULL),
      (2, 4, 'l002', 'La casa es preciosa, aunque un poco calurosa en la tarde.', 'u003', '2025-09-20 14:30:00', NULL),
      (3, 3, 'l003', 'Buena ubicación, pero faltaba limpieza en el baño.', 'u001', '2025-09-10 09:20:00', NULL);

-- REPLY

INSERT INTO reply (
    message, created_at, review_id, host_id
) VALUES
      ('¡Gracias por tu visita, María! Nos alegra que disfrutaras tu estadía.', '2025-10-02 09:30:00', 1, 'u002'),
      ('Gracias por tu comentario, Laura. Vamos a mejorar la ventilación.', '2025-09-21 10:00:00', 2, 'u002'),
      ('Lamentamos los inconvenientes. Ya revisamos la limpieza con el equipo.', '2025-09-11 15:45:00', 3, 'u002');



-- PROMOTIONS

INSERT INTO promotions (
    promotion, created_at, used, code, expiration_date, listing_id
) VALUES
      (10.00, '2025-09-15', false, 'PROMO10', '2025-12-31 23:59:59', 'l001'),
      (15.00, '2025-09-20', true,  'PROMO15', '2025-10-30 23:59:59', 'l002'),
      (20.00, '2025-10-01', false, 'PROMO20', '2026-01-01 23:59:59', 'l003');