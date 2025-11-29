<?php
$host = 'localhost';
$user = 'root';
$pass = '123456'; // coloque aqui a senha que você definiu
$db   = 'saberes';

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die(json_encode([
        'sucesso' => false,
        'erro' => 'Falha na conexão com o banco: ' . $conn->connect_error
    ]));
}

$conn->set_charset("utf8mb4");
?>
