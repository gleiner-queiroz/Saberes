<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$raw = file_get_contents('php://input');
$data = json_decode($raw, true);
if (!is_array($data)) $data = $_POST;

$nome     = trim($data['nome']     ?? '');
$email    = trim($data['email']    ?? '');
$senha    = trim($data['senha']    ?? '');
$materia  = trim($data['materia']  ?? '');
$formacao = trim($data['formacao'] ?? '');
$serie    = trim($data['serie']    ?? '');

if ($nome === '' || $email === '' || $senha === '' || $materia === '' || $formacao === '' || $serie === '') {
    echo json_encode(['sucesso' => false, 'erro' => 'Preencha todos os campos.'], JSON_UNESCAPED_UNICODE);
    exit;
}

$stmt = $conn->prepare("INSERT INTO professores (nome, email, senha, materia, formacao, serie) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param('ssssss', $nome, $email, $senha, $materia, $formacao, $serie);

if ($stmt->execute()) {
    echo json_encode(['sucesso' => true, 'erro' => null], JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(['sucesso' => false, 'erro' => 'E-mail já cadastrado ou erro no servidor.'], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
