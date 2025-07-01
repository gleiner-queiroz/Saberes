document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const conteudoId = params.get('id'); // Pega o ID do conteúdo da URL

    const conteudoPageTitle = document.getElementById('conteudo-page-title');
    const conteudoTitulo = document.getElementById('conteudo-titulo');
    const conteudoProfessor = document.getElementById('conteudo-professor');
    const conteudoCorpo = document.getElementById('conteudo-corpo');
    const commentsList = document.getElementById('comments-list');
    const commentForm = document.getElementById('comment-form');
    const commentText = document.getElementById('comment-text');

    // Dados de exemplo para conteúdos (simulando um banco de dados)
    const mockConteudos = {
        '1': {
            titulo: 'Equações de 2º Grau: Teoria e Resolução',
            professor: 'Prof. Manoel',
            texto: `As equações de 2º grau são expressões matemáticas do tipo $ax^2 + bx + c = 0$, onde $a$, $b$ e $c$ são coeficientes reais e $a \\neq 0$. Elas são fundamentais em diversas áreas da matemática e física.
            <br><br>
            A forma mais comum de resolver uma equação de 2º grau é utilizando a Fórmula de Bhaskara:
            <br>
            $$x = \\frac{-b \\pm \\sqrt{b^2 - 4ac}}{2a}$$
            <br>
            O termo $\\Delta = b^2 - 4ac$ é chamado de discriminante. Ele determina a natureza das raízes:
            <ul>
                <li>Se $\\Delta > 0$, há duas raízes reais e distintas.</li>
                <li>Se $\\Delta = 0$, há duas raízes reais e iguais.</li>
                <li>Se $\\Delta < 0$, não há raízes reais.</li>
            </ul>
            <br>
            **Exemplo:** Resolva a equação $x^2 - 5x + 6 = 0$.
            Aqui, $a=1$, $b=-5$, $c=6$.
            $\\Delta = (-5)^2 - 4 \\cdot 1 \\cdot 6 = 25 - 24 = 1$.
            Como $\\Delta > 0$, teremos duas raízes.
            $x = \\frac{-(-5) \\pm \\sqrt{1}}{2 \\cdot 1} = \\frac{5 \\pm 1}{2}$
            $x_1 = \\frac{5 + 1}{2} = \\frac{6}{2} = 3$
            $x_2 = \\frac{5 - 1}{2} = \\frac{4}{2} = 2$
            <br><br>
            As raízes são $x=3$ e $x=2$.
            `,
            videoUrl: 'https://www.youtube.com/embed/dQw4w9WgXcQ', // Exemplo de vídeo (Rick Astley)
            imageUrl: 'https://placehold.co/600x300/4a90e2/ffffff?text=Imagem+Exemplo+Equacao',
            comentarios: [
                { autor: 'Aluno 1', texto: 'Muito boa a explicação! Entendi agora.' },
                { autor: 'Aluno 2', texto: 'Professor, qual a aplicação disso na física?' }
            ]
        },
        '2': {
            titulo: 'Progressão Aritmética e Geométrica: Conceitos Essenciais',
            professor: 'Prof. Manoel',
            texto: `**Progressão Aritmética (PA):** É uma sequência numérica em que cada termo, a partir do segundo, é igual ao anterior somado a uma constante chamada razão (r).
            <br>Exemplo: $(2, 5, 8, 11, ...)$ onde $r=3$.
            <br>Fórmula do termo geral: $a_n = a_1 + (n-1)r$
            <br><br>
            **Progressão Geométrica (PG):** É uma sequência numérica em que cada termo, a partir do segundo, é igual ao anterior multiplicado por uma constante chamada razão (q).
            <br>Exemplo: $(2, 6, 18, 54, ...)$ onde $q=3$.
            <br>Fórmula do termo geral: $a_n = a_1 \\cdot q^{n-1}$
            `,
            videoUrl: null,
            imageUrl: 'https://placehold.co/600x300/2ecc71/ffffff?text=Imagem+Exemplo+PA+PG',
            comentarios: [
                { autor: 'Aluno 3', texto: 'Achei a explicação da PG bem clara!' }
            ]
        }
        // Adicione mais conteúdos conforme necessário
    };

    // Função para carregar e exibir o conteúdo
    function loadConteudo(id) {
        const conteudo = mockConteudos[id];
        if (conteudo) {
            conteudoPageTitle.textContent = `${conteudo.titulo} - Saberes`;
            conteudoTitulo.textContent = conteudo.titulo;
            conteudoProfessor.textContent = conteudo.professor;
            conteudoCorpo.innerHTML = conteudo.texto;

            if (conteudo.videoUrl) {
                const videoHtml = `<div class="video-container"><iframe src="${conteudo.videoUrl}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>`;
                conteudoCorpo.innerHTML += videoHtml;
            }
            if (conteudo.imageUrl) {
                const imageHtml = `<div class="image-container"><img src="${conteudo.imageUrl}" alt="${conteudo.titulo}"></div>`;
                conteudoCorpo.innerHTML += imageHtml;
            }

            renderComentarios(conteudo.comentarios);
        } else {
            conteudoTitulo.textContent = 'Conteúdo Não Encontrado';
            conteudoCorpo.innerHTML = '<p>Desculpe, o conteúdo que você procura não foi encontrado.</p>';
        }
    }

    // Função para renderizar os comentários
    function renderComentarios(comentariosArray) {
        commentsList.innerHTML = '';
        if (comentariosArray && comentariosArray.length > 0) {
            comentariosArray.forEach(comment => {
                const commentDiv = document.createElement('div');
                commentDiv.className = 'comment-item';
                commentDiv.innerHTML = `
                    <p><strong>${comment.autor}:</strong> ${comment.texto}</p>
                `;
                commentsList.appendChild(commentDiv);
            });
        } else {
            commentsList.innerHTML = '<p class="no-comments">Nenhum comentário ainda. Seja o primeiro a comentar!</p>';
        }
    }

    // Adiciona novo comentário (simulado)
    commentForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const newCommentText = commentText.value.trim();
        if (newCommentText) {
            const currentConteudo = mockConteudos[conteudoId];
            if (currentConteudo) {
                const newComment = { autor: 'Você (Aluno)', texto: newCommentText }; // Simula o autor
                currentConteudo.comentarios.push(newComment);
                renderComentarios(currentConteudo.comentarios);
                commentText.value = ''; // Limpa o campo de texto
                alert('Comentário enviado! (Apenas demonstração, não é salvo permanentemente)');
            }
        } else {
            alert('Por favor, escreva um comentário antes de enviar.');
        }
    });

    // Carrega o conteúdo ao carregar a página
    if (conteudoId) {
        loadConteudo(conteudoId);
    } else {
        conteudoTitulo.textContent = 'Nenhum Conteúdo Selecionado';
        conteudoCorpo.innerHTML = '<p>Por favor, selecione um conteúdo da página de disciplinas.</p>';
    }
});