<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

require_once 'db.php';

$nome  = $_POST['nome']  ?? '';
$serie = $_POST['serie'] ?? '';
$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';

if ($nome === '' || $serie === '' || $email === '' || $senha === '') {
    echo json_encode(['sucesso' => false, 'erro' => 'Campos obrigatórios não preenchidos.']);
    exit;
}

// verifica se email já existe
$stmt = $conn->prepare("SELECT id FROM alunos WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    echo json_encode(['sucesso' => false, 'erro' => 'E-mail já cadastrado.']);
    $stmt->close();
    $conn->close();
    exit;
}
$stmt->close();

$stmt = $conn->prepare("INSERT INTO alunos (nome, serie, email, senha) VALUES (?, ?, ?, ?)");
$stmt->bind_param("ssss", $nome, $serie, $email, $senha);

if ($stmt->execute()) {
    echo json_encode(['sucesso' => true]);
} else {
    echo json_encode(['sucesso' => false, 'erro' => 'Erro ao salvar no banco.']);
}

$stmt->close();
$conn->close();