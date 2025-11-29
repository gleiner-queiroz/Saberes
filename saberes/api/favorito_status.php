<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

$aluno_id    = isset($_GET['aluno_id']) ? intval($_GET['aluno_id']) : 0;
$conteudo_id = isset($_GET['conteudo_id']) ? intval($_GET['conteudo_id']) : 0;

if ($aluno_id <= 0 || $conteudo_id <= 0) {
    echo json_encode([
        'sucesso'  => false,
        'favorito' => false,
        'erro'     => 'Parâmetros inválidos.'
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

$sql = "SELECT 1 FROM favoritos WHERE aluno_id = ? AND conteudo_id = ? LIMIT 1";
$stmt = $conn->prepare($sql);
$stmt->bind_param('ii', $aluno_id, $conteudo_id);
$stmt->execute();
$result = $stmt->get_result();

$favorito = $result->num_rows > 0;

echo json_encode([
    'sucesso'  => true,
    'favorito' => $favorito,
    'erro'     => null
], JSON_UNESCAPED_UNICODE);

$stmt->close();
$conn->close();
