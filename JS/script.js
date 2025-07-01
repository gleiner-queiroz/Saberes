document.addEventListener('DOMContentLoaded', () => {

    // Dados de exemplo para as disciplinas
    const disciplinas = [
        { nome: 'Matemática', icone: 'fas fa-calculator', url: 'materia.html?disciplina=Matematica' },
        { nome: 'Português', icone: 'fas fa-book-open', url: 'materia.html?disciplina=Portugues' },
        { nome: 'Redação', icone: 'fas fa-pen-nib', url: 'materia.html?disciplina=Redacao' },
        { nome: 'História', icone: 'fas fa-landmark', url: 'materia.html?disciplina=Historia' },
        { nome: 'Geografia', icone: 'fas fa-globe-americas', url: 'materia.html?disciplina=Geografia' },
        { nome: 'Física', icone: 'fas fa-atom', url: 'materia.html?disciplina=Fisica' },
        { nome: 'Química', icone: 'fas fa-flask', url: 'materia.html?disciplina=Quimica' },
        { nome: 'Biologia', icone: 'fas fa-dna', url: 'materia.html?disciplina=Biologia' },
        { nome: 'Filosofia', icone: 'fas fa-brain', url: 'materia.html?disciplina=Filosofia' },
        { nome: 'Sociologia', icone: 'fas fa-users', url: 'materia.html?disciplina=Sociologia' },
    ];

    const disciplinasGrid = document.getElementById('disciplinas-grid');
    const searchInput = document.getElementById('disciplina-search');

    // Função para renderizar os cards das disciplinas
    function renderDisciplinas(disciplinasArray) {
        disciplinasGrid.innerHTML = ''; // Limpa o grid antes de renderizar
        disciplinasArray.forEach(disciplina => {
            const card = document.createElement('a');
            card.href = disciplina.url;
            card.className = 'disciplina-card';
            card.innerHTML = `
                <i class="${disciplina.icone}"></i>
                <h3>${disciplina.nome}</h3>
                <p>Conteúdo para ENEM</p>
            `;
            disciplinasGrid.appendChild(card);
        });
    }

    // Carrega todas as disciplinas ao iniciar a página
    renderDisciplinas(disciplinas);

    // Adiciona evento de pesquisa
    if (searchInput) {
        searchInput.addEventListener('input', (event) => {
            const searchTerm = event.target.value.toLowerCase();
            const filteredDisciplinas = disciplinas.filter(disciplina =>
                disciplina.nome.toLowerCase().includes(searchTerm)
            );
            renderDisciplinas(filteredDisciplinas);
        });
    }
});