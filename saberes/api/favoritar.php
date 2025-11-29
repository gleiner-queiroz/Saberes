<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

// Lê JSON ou POST
$raw = file_get_contents('php://input');
$data = json_decode($raw, true);
if (!is_array($data)) {
    $data = $_POST;
}

$aluno_id    = isset($data['aluno_id'])    ? (int)$data['aluno_id']    : 0;
$conteudo_id = isset($data['conteudo_id']) ? (int)$data['conteudo_id'] : 0;

if ($aluno_id <= 0 || $conteudo_id <= 0) {
    echo json_encode([
        'sucesso'  => false,
        'favorito' => false,
        'erro'     => 'Parâmetros inválidos.'
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

// Verifica se já é favorito
$stmt = $conn->prepare("SELECT id FROM favoritos WHERE aluno_id = ? AND conteudo_id = ?");
$stmt->bind_param('ii', $aluno_id, $conteudo_id);
$stmt->execute();
$res = $stmt->get_result();

if ($row = $res->fetch_assoc()) {
    // Já existe -> remover (desfavoritar)
    $fav_id = (int)$row['id'];
    $del = $conn->prepare("DELETE FROM favoritos WHERE id = ?");
    $del->bind_param('i', $fav_id);
    $del->execute();
    $del->close();

    echo json_encode([
        'sucesso'  => true,
        'favorito' => false,
        'erro'     => null
    ], JSON_UNESCAPED_UNICODE);
} else {
    // Não existe -> inserir (favoritar)
    $ins = $conn->prepare("INSERT INTO favoritos (aluno_id, conteudo_id) VALUES (?, ?)");
    $ins->bind_param('ii', $aluno_id, $conteudo_id);
    if ($ins->execute()) {
        echo json_encode([
            'sucesso'  => true,
            'favorito' => true,
            'erro'     => null
        ], JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode([
            'sucesso'  => false,
            'favorito' => false,
            'erro'     => 'Erro ao salvar favorito.'
        ], JSON_UNESCAPED_UNICODE);
    }
    $ins->close();
}

$stmt->close();
$conn->close();