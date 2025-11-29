-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 29/11/2025 às 23:26
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `saberes`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `alunos`
--

CREATE TABLE `alunos` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `serie` varchar(20) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `alunos`
--

INSERT INTO `alunos` (`id`, `nome`, `email`, `senha`, `serie`, `criado_em`) VALUES
(8, 'Yara Paula dos Santos Couto', 'yarap1070@gmail.com', 'p@457#*', '3º ano', '2025-11-29 22:11:51'),
(9, 'Carlos Daniel Silva Madureira', 'Carlosdmadureira@gmail.com', 'Biblia1000%', '3º ano', '2025-11-29 22:13:02'),
(11, 'Lucas Carbone Vieira', 'ggcarbonegg@gmail.com', 'Carbone0707', '3º ano', '2025-11-29 22:16:24');

-- --------------------------------------------------------

--
-- Estrutura para tabela `conteudos`
--

CREATE TABLE `conteudos` (
  `id` int(11) NOT NULL,
  `professor_id` int(11) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `materia` varchar(100) NOT NULL,
  `serie` varchar(50) NOT NULL,
  `texto` longtext DEFAULT NULL,
  `imagens` text DEFAULT NULL,
  `videos` text DEFAULT NULL,
  `links` text DEFAULT NULL,
  `criado_em` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `conteudos`
--

INSERT INTO `conteudos` (`id`, `professor_id`, `titulo`, `materia`, `serie`, `texto`, `imagens`, `videos`, `links`, `criado_em`) VALUES
(7, 9, 'Circunferência', 'Matemática', '2º ano', 'A circunferência é uma linha curva fechada e plana, formada por todos os pontos que estão à mesma distância de um ponto central. É o contorno de um círculo e tem elementos como o centro, raio (distância do centro a qualquer ponto da linha), diâmetro (que é o dobro do raio e passa pelo centro) e comprimento. A fórmula para calcular seu comprimento é \\(C=2\\pi r\\) ou \\(C=\\pi d\\).', 'https://www.todamateria.com.br/circunferencia/', 'https://www.youtube.com/watch?v=ZJvkD2zEFVQ', 'https://www.todamateria.com.br/circunferencia/', '2025-11-29 22:01:01'),
(9, 10, 'Química Orgânica', 'Química', '3º ano', 'A química orgânica é o ramo da química que estuda os compostos que contêm carbono como elemento principal, formando cadeias e moléculas complexas. Esses compostos são fundamentais para a vida e para a criação de materiais do dia a dia, como plásticos, medicamentos e combustíveis. A matéria envolve propriedades, classificação e reações de compostos de carbono, incluindo elementos como hidrogênio, oxigênio, nitrogênio e enxofre.', 'https://br.pinterest.com/pin/171488698306698944/', 'https://www.youtube.com/watch?v=OcMxZl_HaH8', 'https://www.todamateria.com.br/quimica-organica/', '2025-11-29 22:04:56'),
(10, 11, 'Eletromagnetismo', 'Física', '3º ano', 'O eletromagnetismo é o ramo da física que estuda as interações entre cargas elétricas e campos magnéticos, unindo eletricidade e magnetismo como fenômenos interligados. Ele explica como cargas em movimento (correntes elétricas) geram um campo magnético ao seu redor, e como a variação de um campo magnético cria um campo elétrico, sendo fundamental para entender fenômenos naturais e diversas tecnologias modernas. \nPrincípios fundamentais\nRelação entre eletricidade e magnetismo: O eletromagnetismo é a união de duas forças: a elétrica (gerada por cargas estáticas) e a magnética (gerada por cargas em movimento ou ímãs permanentes). \nCampos eletromagnéticos: Partículas eletricamente carregadas criam campos elétricos e magnéticos ao seu redor, que por sua vez interagem com outras partículas carregadas. \nComo funcionam:\nUma corrente elétrica (cargas em movimento) cria um campo magnético. \nUma variação no fluxo magnético produz um campo elétrico. \nAplicações\nTecnologia cotidiana: É a base de quase todas as tecnologias que usamos, incluindo televisores, computadores, telefones celulares, micro-ondas e a internet. \nMotores e geradores: O eletromagnetismo é usado para converter energia elétrica em mecânica e vice-versa. \nComunicação: As ondas eletromagnéticas (como rádio, TV, Wi-Fi) permitem a transmissão de informações a longa distância. \nIndústria: Processos como soldagem e aquecimento por indução utilizam campos eletromagnéticos para aquecer materiais de forma eficiente. \nFenômenos naturais: O estudo do eletromagnetismo é crucial para entender fenômenos como a Aurora Boreal, que ocorre devido à interação entre partículas do vento solar e a atmosfera da Terra.', 'https://www.todamateria.com.br/eletromagnetismo/', 'https://www.youtube.com/watch?v=AFACIE-UH6A', 'https://brasilescola.uol.com.br/fisica/eletromagnetismo.htm', '2025-11-29 22:08:00');

-- --------------------------------------------------------

--
-- Estrutura para tabela `favoritos`
--

CREATE TABLE `favoritos` (
  `id` int(11) NOT NULL,
  `aluno_id` int(11) NOT NULL,
  `conteudo_id` int(11) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `favoritos`
--

INSERT INTO `favoritos` (`id`, `aluno_id`, `conteudo_id`, `criado_em`) VALUES
(11, 11, 10, '2025-11-29 22:22:52'),
(12, 11, 9, '2025-11-29 22:22:57'),
(13, 8, 7, '2025-11-29 22:23:20'),
(14, 9, 10, '2025-11-29 22:23:54'),
(15, 9, 9, '2025-11-29 22:23:59');

-- --------------------------------------------------------

--
-- Estrutura para tabela `professores`
--

CREATE TABLE `professores` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `materia` varchar(100) NOT NULL,
  `formacao` varchar(100) NOT NULL,
  `serie` varchar(50) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `professores`
--

INSERT INTO `professores` (`id`, `nome`, `email`, `senha`, `materia`, `formacao`, `serie`, `criado_em`) VALUES
(9, 'Heleno Vieira da Silva', 'helenoo.vieira@gmail.com', 'SCV27070203', 'Matemática', 'Licenciatura', '1º ano, 2º ano, 3º ano', '2025-11-29 21:39:45'),
(10, 'Elany Gizely Carbone Vieira', 'egcarbonevieira@gmail.com', 'egcv1977', 'Química', 'Licenciatura', '1º ano, 2º ano', '2025-11-29 21:41:37'),
(11, 'Henrique Candido Feitosa', 'henrique.cfpb@gmail.com', 'hcf222018', 'Física', 'Licenciatura', '1º ano, 2º ano, 3º ano', '2025-11-29 21:55:37');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `alunos`
--
ALTER TABLE `alunos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices de tabela `conteudos`
--
ALTER TABLE `conteudos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `professor_id` (`professor_id`);

--
-- Índices de tabela `favoritos`
--
ALTER TABLE `favoritos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `aluno_conteudo` (`aluno_id`,`conteudo_id`),
  ADD KEY `conteudo_id` (`conteudo_id`);

--
-- Índices de tabela `professores`
--
ALTER TABLE `professores`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `alunos`
--
ALTER TABLE `alunos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `conteudos`
--
ALTER TABLE `conteudos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `favoritos`
--
ALTER TABLE `favoritos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `professores`
--
ALTER TABLE `professores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `conteudos`
--
ALTER TABLE `conteudos`
  ADD CONSTRAINT `conteudos_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professores` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `favoritos`
--
ALTER TABLE `favoritos`
  ADD CONSTRAINT `favoritos_ibfk_1` FOREIGN KEY (`aluno_id`) REFERENCES `alunos` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `favoritos_ibfk_2` FOREIGN KEY (`conteudo_id`) REFERENCES `conteudos` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
