const API_BASE = 'http://localhost/saberes/api/';

async function cadastrarProfessor() {
const nome     = document.getElementById('cadNome').value.trim();
const email    = document.getElementById('cadEmail').value.trim();
const senha    = document.getElementById('cadSenha').value.trim();
const materia  = document.getElementById('cadMateria').value.trim();
const formacao = document.getElementById('cadFormacao').value.trim();
const serie    = document.getElementById('cadSerie').value.trim();
const msg      = document.getElementById('msg');
msg.style.color = '#ff5252';
msg.textContent = '';

if (!nome || !email || !senha || !materia || !formacao || !serie) {
    msg.textContent = 'Preencha todos os campos.';
    return;
}

try {
    const resp = await fetch(API_BASE + 'professor_cadastrar.php', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({ nome, email, senha, materia, formacao, serie })
    });
    const data = await resp.json();
    if (data.sucesso) {
    msg.style.color = '#00c853';
    msg.textContent = 'Cadastro realizado com sucesso! Vá para o login.';
    // opcional: redirecionar automatico
    // setTimeout(() => window.location.href = 'login.html', 1500);
    } else {
    msg.textContent = data.erro || 'Erro ao cadastrar.';
    }
} catch (e) {
    msg.textContent = 'Falha de conexão com o servidor.';
}
}