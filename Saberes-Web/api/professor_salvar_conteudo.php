<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$raw = file_get_contents('php://input');
$data = json_decode($raw, true);
if (!is_array($data)) $data = $_POST;

$professor_id = (int)($data['professor_id'] ?? 0);
$titulo       = trim($data['titulo'] ?? '');
$materia      = trim($data['materia'] ?? '');
$serie        = trim($data['serie'] ?? '');
$texto        = trim($data['texto'] ?? '');
$imagens      = trim($data['imagens'] ?? '');
$videos       = trim($data['videos'] ?? '');
$links        = trim($data['links'] ?? '');

if ($professor_id <= 0 || $titulo === '' || $materia === '' || $serie === '') {
    echo json_encode(['sucesso' => false, 'erro' => 'Dados obrigatórios ausentes.'], JSON_UNESCAPED_UNICODE);
    exit;
}

$stmt = $conn->prepare("INSERT INTO conteudos (professor_id, titulo, materia, serie, texto, imagens, videos, links) 
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
$stmt->bind_param('isssssss', $professor_id, $titulo, $materia, $serie, $texto, $imagens, $videos, $links);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode(['sucesso' => true, 'id' => $stmt->insert_id, 'erro' => null], JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(['sucesso' => false, 'erro' => 'Erro ao salvar conteúdo.'], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
