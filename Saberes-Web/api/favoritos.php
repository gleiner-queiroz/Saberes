<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

$aluno_id = isset($_GET['aluno_id']) ? intval($_GET['aluno_id']) : 0;

if ($aluno_id <= 0) {
    echo json_encode([
        'sucesso' => false,
        'erro' => 'ID de aluno inválido.',
        'dados' => []
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

/*
    Tabelas supostas:
    - favoritos (id, aluno_id, conteudo_id)
    - conteudos (id, titulo, materia, serie, professor_id, criado_em)
    - professores (id, nome, email, ...)
*/

$sql = "
    SELECT 
        c.id,
        c.titulo,
        c.materia,
        c.serie,
        p.nome AS professor_nome,
        c.criado_em
    FROM favoritos f
    INNER JOIN conteudos c ON c.id = f.conteudo_id
    LEFT JOIN professores p ON p.id = c.professor_id
    WHERE f.aluno_id = ?
    ORDER BY f.id DESC
";

$stmt = $conn->prepare($sql);
$stmt->bind_param('i', $aluno_id);
$stmt->execute();
$result = $stmt->get_result();

$favoritos = [];
while ($row = $result->fetch_assoc()) {
    $favoritos[] = [
        'id'             => (int) $row['id'],
        'titulo'         => $row['titulo'],
        'materia'        => $row['materia'],
        'serie'          => $row['serie'],
        'professor_nome' => $row['professor_nome'],
        'criado_em'      => $row['criado_em']
    ];
}

echo json_encode($favoritos, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
