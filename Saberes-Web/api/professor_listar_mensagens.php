<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$professor_id = (int)($_GET['professor_id'] ?? 0);
$conteudo_id  = (int)($_GET['conteudo_id'] ?? 0);

if ($professor_id <= 0) {
    echo json_encode([], JSON_UNESCAPED_UNICODE);
    exit;
}

$sql = "SELECT m.*, a.nome AS aluno_nome, c.titulo AS conteudo_titulo
        FROM mensagens m
        JOIN alunos a ON a.id = m.aluno_id
        JOIN conteudos c ON c.id = m.conteudo_id
        WHERE m.professor_id = ?";

$params = [$professor_id];
$types  = 'i';

if ($conteudo_id > 0) {
    $sql .= " AND m.conteudo_id = ?";
    $params[] = $conteudo_id;
    $types   .= 'i';
}

$sql .= " ORDER BY m.enviado_em ASC";

$stmt = $conn->prepare($sql);
$stmt->bind_param($types, ...$params);
$stmt->execute();
$result = $stmt->get_result();

$mensagens = [];
while ($row = $result->fetch_assoc()) {
    $mensagens[] = $row;
}

echo json_encode($mensagens, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
