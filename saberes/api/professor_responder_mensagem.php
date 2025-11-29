<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$raw = file_get_contents('php://input');
$data = json_decode($raw, true);
if (!is_array($data)) $data = $_POST;

$conteudo_id = (int)($data['conteudo_id'] ?? 0);
$aluno_id    = (int)($data['aluno_id'] ?? 0);
$professor_id= (int)($data['professor_id'] ?? 0);
$texto       = trim($data['texto'] ?? '');

if ($conteudo_id <= 0 || $aluno_id <= 0 || $professor_id <= 0 || $texto === '') {
    echo json_encode(['sucesso' => false, 'erro' => 'Dados inválidos.'], JSON_UNESCAPED_UNICODE);
    exit;
}

$origem = 'professor';

$stmt = $conn->prepare("INSERT INTO mensagens (conteudo_id, aluno_id, professor_id, origem, texto) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param('iiiss', $conteudo_id, $aluno_id, $professor_id, $origem, $texto);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode(['sucesso' => true, 'erro' => null], JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(['sucesso' => false, 'erro' => 'Erro ao salvar mensagem.'], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
