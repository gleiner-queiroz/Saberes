<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

// filtros opcionais: materia, serie, busca
$materia = isset($_GET['materia']) && $_GET['materia'] !== '' ? $_GET['materia'] : null;
$serie   = isset($_GET['serie'])   && $_GET['serie']   !== '' ? $_GET['serie']   : null;
$busca   = isset($_GET['busca'])   && $_GET['busca']   !== '' ? $_GET['busca']   : null;

$sql = "SELECT c.id, c.titulo, c.materia, c.serie, 
               p.nome AS professor_nome,
               c.criado_em
        FROM conteudos c
        JOIN professores p ON p.id = c.professor_id
        WHERE 1=1";

$params = [];
$types  = '';

if ($materia !== null) {
    $sql .= " AND c.materia = ?";
    $params[] = $materia;
    $types   .= 's';
}
if ($serie !== null) {
    $sql .= " AND c.serie = ?";
    $params[] = $serie;
    $types   .= 's';
}
if ($busca !== null) {
    $sql .= " AND c.titulo LIKE ?";
    $params[] = '%' . $busca . '%';
    $types   .= 's';
}

$sql .= " ORDER BY c.criado_em DESC";

$stmt = $conn->prepare($sql);

if (!empty($params)) {
    $stmt->bind_param($types, ...$params);
}
$stmt->execute();
$result = $stmt->get_result();

$conteudos = [];
while ($row = $result->fetch_assoc()) {
    $conteudos[] = [
        'id'             => (int)$row['id'],
        'titulo'         => $row['titulo'],
        'materia'        => $row['materia'],
        'serie'          => $row['serie'],
        'professor_nome' => $row['professor_nome'],
        'criado_em'      => $row['criado_em']
    ];
}

echo json_encode($conteudos, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
