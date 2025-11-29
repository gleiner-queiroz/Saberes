<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$professor_id = (int)($_GET['professor_id'] ?? 0);
$busca        = $_GET['busca'] ?? '';

if ($professor_id <= 0) {
    echo json_encode([], JSON_UNESCAPED_UNICODE);
    exit;
}

$sql = "SELECT id, titulo, materia, serie, criado_em
        FROM conteudos
        WHERE professor_id = ?";
$params = [$professor_id];
$types  = 'i';

if ($busca !== '') {
    $sql .= " AND titulo LIKE ?";
    $params[] = '%' . $busca . '%';
    $types   .= 's';
}

$sql .= " ORDER BY criado_em DESC";

$stmt = $conn->prepare($sql);
$stmt->bind_param($types, ...$params);
$stmt->execute();
$result = $stmt->get_result();

$conteudos = [];
while ($row = $result->fetch_assoc()) {
    $conteudos[] = $row;
}

echo json_encode($conteudos, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
