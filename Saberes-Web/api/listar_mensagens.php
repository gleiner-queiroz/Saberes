<?php
require_once 'db.php';
header('Content-Type: application/json; charset=utf-8');

$conteudo_id = isset($_GET['conteudo_id']) ? (int)$_GET['conteudo_id'] : 0;
$aluno_id    = isset($_GET['aluno_id'])    ? (int)$_GET['aluno_id']    : 0;

if ($conteudo_id <= 0 || $aluno_id <= 0) {
    echo json_encode([], JSON_UNESCAPED_UNICODE);
    exit;
}

$sql = "SELECT m.*, a.nome AS aluno_nome, p.nome AS professor_nome
        FROM mensagens m
        JOIN alunos a ON a.id = m.aluno_id
        JOIN professores p ON p.id = m.professor_id
        WHERE m.conteudo_id = ? AND m.aluno_id = ?
        ORDER BY m.enviado_em ASC";

$stmt = $conn->prepare($sql);
$stmt->bind_param('ii', $conteudo_id, $aluno_id);
$stmt->execute();
$result = $stmt->get_result();

$mensagens = [];
while ($row = $result->fetch_assoc()) {
    $mensagens[] = [
        'id'            => (int)$row['id'],
        'conteudo_id'   => (int)$row['conteudo_id'],
        'aluno_id'      => (int)$row['aluno_id'],
        'professor_id'  => (int)$row['professor_id'],
        'origem'        => $row['origem'],
        'texto'         => $row['texto'],
        'enviado_em'    => $row['enviado_em'],
        'aluno_nome'    => $row['aluno_nome'],
        'professor_nome'=> $row['professor_nome']
    ];
}

echo json_encode($mensagens, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
