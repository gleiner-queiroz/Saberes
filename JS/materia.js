document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const disciplinaNome = params.get('disciplina');
    
    const materiaTitle = document.getElementById('materia-title');
    const materiaMainTitle = document.getElementById('materia-main-title');
    const materiaNomeSpan = document.getElementById('materia-nome');
    const conteudoLista = document.getElementById('conteudo-lista');
    const conteudoSearch = document.getElementById('conteudo-search');

    if (disciplinaNome) {
        materiaTitle.textContent = `${disciplinaNome} - Saberes`;
        materiaNomeSpan.textContent = disciplinaNome;
    }

    // Dados de exemplo para o conteúdo de uma matéria
    const conteudos = [
        { titulo: 'Equações de 2º Grau', professor: 'Prof. Manoel', url: 'conteudo.html?id=1' },
        { titulo: 'Progressão Aritmética e Geométrica', professor: 'Prof. Manoel', url: 'conteudo.html?id=2' },
        { titulo: 'Funções Trigonométricas', professor: 'Prof. Manoel', url: 'conteudo.html?id=3' },
        { titulo: 'Geometria Espacial', professor: 'Prof. Manoel', url: 'conteudo.html?id=4' },
        { titulo: 'Logaritmos', professor: 'Prof. Manoel', url: 'conteudo.html?id=5' },
    ];

    function renderConteudos(conteudosArray) {
        conteudoLista.innerHTML = '';
        if (conteudosArray.length === 0) {
            conteudoLista.innerHTML = '<p class="no-results">Nenhum conteúdo encontrado.</p>';
            return;
        }
        conteudosArray.forEach(conteudo => {
            const card = document.createElement('a');
            card.href = conteudo.url;
            card.className = 'conteudo-card';
            card.innerHTML = `
                <h3>${conteudo.titulo}</h3>
                <p>Professor: ${conteudo.professor}</p>
            `;
            conteudoLista.appendChild(card);
        });
    }

    renderConteudos(conteudos);

    // Adiciona evento de pesquisa
    if (conteudoSearch) {
        conteudoSearch.addEventListener('input', (event) => {
            const searchTerm = event.target.value.toLowerCase();
            const filteredConteudos = conteudos.filter(conteudo =>
                conteudo.titulo.toLowerCase().includes(searchTerm) ||
                conteudo.professor.toLowerCase().includes(searchTerm)
            );
            renderConteudos(filteredConteudos);
        });
    }
});