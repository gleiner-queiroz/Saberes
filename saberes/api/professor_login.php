<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$raw = file_get_contents('php://input');
$data = json_decode($raw, true);
if (!is_array($data)) $data = $_POST;

$email = trim($data['email'] ?? '');
$senha = trim($data['senha'] ?? '');

if ($email === '' || $senha === '') {
    echo json_encode(['sucesso' => false, 'erro' => 'Informe e-mail e senha.'], JSON_UNESCAPED_UNICODE);
    exit;
}

$stmt = $conn->prepare("SELECT id, nome, email, materia, formacao, serie FROM professores WHERE email = ? AND senha = ?");
$stmt->bind_param('ss', $email, $senha);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    echo json_encode([
        'sucesso' => true,
        'professor' => $row,
        'erro' => null
    ], JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode([
        'sucesso' => false,
        'professor' => null,
        'erro' => 'E-mail ou senha inválidos.'
    ], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
