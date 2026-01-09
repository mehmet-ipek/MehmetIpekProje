<?php
$host = 'localhost';
$dbname = 'restaurant_db'; // 🔴 TEK VE DOĞRU DB
$username = 'root';
$password = '';

try {
    $db = new PDO(
        "mysql:host=$host;dbname=$dbname;charset=utf8",
        $username,
        $password
    );
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $e) {
    die("Veritabanı bağlantı hatası: " . $e->getMessage());
}

session_start();

if (!isset($_SESSION['cart'])) {
    $_SESSION['cart'] = [];
}

function getCartCount() {
    return array_sum(array_column($_SESSION['cart'], 'quantity'));
}
