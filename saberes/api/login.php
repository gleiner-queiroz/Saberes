<?php
require_once 'db.php';

header('Content-Type: application/json; charset=utf-8');

// Lê o corpo bruto da requisição
$raw = file_get_contents('php://input');

// Tenta decodificar como JSON
$data = json_decode($raw, true);

// Se não for JSON (por exemplo, form normal), tenta usar $_POST
if (!is_array($data)) {
    $data = $_POST;
}

$email = isset($data['email']) ? trim($data['email']) : '';
$senha = isset($data['senha']) ? trim($data['senha']) : '';
$tipo  = isset($data['tipo'])  ? trim($data['tipo'])  : 'aluno';

if ($email === '' || $senha === '') {
    echo json_encode([
        'sucesso' => false,
        'usuario' => null,
        'erro'    => 'Informe e-mail e senha.'
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

// Por enquanto só tratamos login de aluno
if ($tipo !== 'aluno') {
    echo json_encode([
        'sucesso' => false,
        'usuario' => null,
        'erro'    => 'Tipo de usuário inválido.'
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

// Consulta no banco (já buscando a coluna serie)
$stmt = $conn->prepare('SELECT id, nome, email, serie FROM alunos WHERE email = ? AND senha = ?');
$stmt->bind_param('ss', $email, $senha);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {

    $usuario = [
        'id'    => (int)$row['id'],
        'nome'  => $row['nome'],
        'email' => $row['email'],
        'tipo'  => 'aluno',
        'serie' => $row['serie']  // <<---- AQUI é o que faltava
    ];

    echo json_encode([
        'sucesso' => true,
        'usuario' => $usuario,
        'erro'    => null
    ], JSON_UNESCAPED_UNICODE);

} else {
    echo json_encode([
        'sucesso' => false,
        'usuario' => null,
        'erro'    => 'E-mail ou senha inválidos.'
    ], JSON_UNESCAPED_UNICODE);
}

$stmt->close();
$conn->close();
