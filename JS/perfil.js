document.addEventListener('DOMContentLoaded', () => {
    const userTypeSelect = document.getElementById('user-type-select');
    const alunoFields = document.querySelector('.aluno-fields');
    const professorFields = document.querySelectorAll('.professor-fields');
    const professorAreaBtn = document.getElementById('professor-area-btn');
    const perfilForm = document.getElementById('perfil-form');

    // Função para alternar a exibição dos campos
    function toggleUserFields() {
        const userType = userTypeSelect.value;
        if (userType === 'aluno') {
            alunoFields.style.display = 'block';
            professorFields.forEach(field => field.style.display = 'none');
            professorAreaBtn.style.display = 'none';
        } else if (userType === 'professor') {
            alunoFields.style.display = 'none';
            professorFields.forEach(field => field.style.display = 'block');
            professorAreaBtn.style.display = 'block';
        }
    }

    // Inicializa a exibição correta dos campos
    toggleUserFields();

    // Adiciona o evento de mudança ao select
    userTypeSelect.addEventListener('change', toggleUserFields);

    // Simula o salvamento de dados (apenas no console)
    perfilForm.addEventListener('submit', (e) => {
        e.preventDefault();
        alert('Informações de perfil salvas com sucesso! (Funcionalidade de salvamento é apenas uma demonstração)');
        // Aqui você enviaria os dados para um servidor real
        console.log('Dados do perfil salvos:', {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            telefone: document.getElementById('telefone').value,
            tipo: userTypeSelect.value,
            serie: userTypeSelect.value === 'aluno' ? document.getElementById('serie').value : undefined,
            formacao: userTypeSelect.value === 'professor' ? document.getElementById('formacao').value : undefined,
            materia: userTypeSelect.value === 'professor' ? document.getElementById('materia').value : undefined,
            biografia: document.getElementById('bio').value
        });
    });
});