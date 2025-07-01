document.addEventListener('DOMContentLoaded', () => {
    const cadastroForm = document.getElementById('cadastro-form');

    cadastroForm.addEventListener('submit', (e) => {
        e.preventDefault(); // Impede o envio padrão do formulário

        const titulo = document.getElementById('titulo-conteudo').value;
        const disciplina = document.getElementById('disciplina-conteudo').value;
        const professor = document.getElementById('professor-conteudo').value;
        const texto = document.getElementById('texto-conteudo').value;
        const videoUrl = document.getElementById('video-url').value;
        const imageUrl = document.getElementById('imagem-url').value;

        // Validação básica
        if (!titulo || !disciplina || !professor || (!texto && !videoUrl && !imageUrl)) {
            alert('Por favor, preencha o título, a disciplina, o professor e pelo menos um tipo de conteúdo (texto, vídeo ou imagem).');
            return;
        }

        // Aqui você enviaria os dados para um servidor real.
        // Como estamos apenas com HTML/CSS/JS, vamos simular o envio.
        const novoConteudo = {
            titulo,
            disciplina,
            professor,
            texto,
            videoUrl,
            imageUrl,
            // Gerar um ID único para o conteúdo (simulado)
            id: Math.random().toString(36).substring(2, 9)
        };

        console.log('Novo Conteúdo Cadastrado (simulado):', novoConteudo);
        alert('Conteúdo cadastrado com sucesso! (Esta é uma demonstração, o conteúdo não é salvo permanentemente)');

        // Limpar o formulário após o "cadastro"
        cadastroForm.reset();
    });
});