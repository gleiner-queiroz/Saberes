<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

$id = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($id <= 0) {
    echo json_encode(['erro' => 'ID inválido'], JSON_UNESCAPED_UNICODE);
    exit;
}

$sql = "SELECT c.*, p.nome AS professor_nome
        FROM conteudos c
        JOIN professores p ON p.id = c.professor_id
        WHERE c.id = ?";

$stmt = $conn->prepare($sql);
$stmt->bind_param('i', $id);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    $conteudo = [
        'id'            => (int)$row['id'],
        'titulo'        => $row['titulo'],
        'materia'       => $row['materia'],
        'serie'         => $row['serie'],
        'texto'         => $row['texto'],
        'imagens'       => $row['imagens'],
        'videos'        => $row['videos'],
        'links'         => $row['links'],
        'professor_id'  => (int)$row['professor_id'],
        'professor_nome'=> $row['professor_nome']
    ];
    echo json_encode($conteudo, JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(['erro' => 'Conteúdo não encontrado'], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
