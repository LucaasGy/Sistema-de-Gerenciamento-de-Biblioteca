<h1 align="center">

  <p>Sistema de Gerenciamento de Biblioteca</p>

</h1>
 <blockquote> Projeto da disciplina EXA 863 - MI de Algoritmos e Programação II da Universidade Estadual de Feira de Santana. </blockquote>

## :computer: Sobre
O **Sistema de Gerenciamento de Biblioteca** em desenvolvimento será capaz de mudar a vida de funcionários da biblioteca e de seus clientes, trazendo facilidade e eficiência tanto para operações básicas como: Pesquisa de livros,
Empréstimos de livros e Reservas de livros, como também praticidade para operações mais complexas como: Controle total de usuários, livros e funcionários no sistema.

## :scroll: Descrição do Projeto

O sistema deve ser capaz de atender aos seguintes requisitos:
- Registro de Livros: Administradores e Bibliotecarios poderão registrar livros no armazenamento do sistema.
- Pesquisa de Livros: usuários do sistema poderão pesquisar por livros presentes no acervo da biblioteca.
- Empréstimo e Devolução: o sistema auxiliará o bibliotecario(a) nas tarefas de empréstimo e devolução dos livros armazenados.
- Reserva de Livros: Leitores poderão reservar livros caso já tenham sido emprestados.
- Renovação de Empréstimos: o sistema dará suporte ao leitor para renovação de um empréstimo ativo.
- Controle de Usuários: Administrador será possível cadastrar, remover ou bloquear usuários caso necessário.
- Relatórios e Estatísticas: o sistema poderá gerar relatórios constando a quantidade de livros emprestados, atrasados e reservados, bem como um histórico de empréstimos de um usuário.
- Gerenciamento de Multas: o sistema poderá calcular e registrar multas para um leitor por atraso na devolução dos livros.
- Gerenciamento de Acervo: Administradores poderão gerenciar o acervo de livros da biblioteca por meio do sistema, incluindo tarefas como adição, remoção e alteração de informação dos livros.
- Controle de Operadores do Sistema: Administradores poderão gerenciar operadores (administradores ou bibliotecarios) do sistema.

## :scroll: Requisitos de Desenvolvimento
- Construção dos diagramas de classe e de casos de uso.
- Github como ferramenta para versionamento de código.
- Uma padronização de commits deverá ser adotada.
- Utilização da ferramenta IDE IntelliJ IDEA para desenvolvimento do código fonte.
- Utilização do padrão de projeto DAO (Data Access Object) para o CRUD.
- Desenvolvimento do Model e testes unitários e de integração.
- Povoamento inicial e persistência dos dados.
- Construção da interface gráfica com JavaFX.

## :computer: Fases do Projeto (status)

O projeto foi dividido nas seguintes etapas de desenvolvimento:
- Fase 1 - Diagramação, implementação e testes(backend) :heavy_check_mark:
- Fase 2 - Persistência de dados :heavy_check_mark:
- Fase 3 - Implementação da interface gráfica(frontend) :heavy_check_mark:

## :gear: Atuais funcionalidades

- Gerenciamento de administradores, bibliotecarios e leitores:
<blockquote> Cadastrar</blockquote>
<blockquote> Atribuir número de ID automaticamente</blockquote>
<blockquote> Atualizar dados</blockquote>
<blockquote> Consultar cadastros por número de ID ou nome</blockquote>
<blockquote> Retornar toda a lista de cadastros do armazenamento</blockquote>
<blockquote> Bloquear/Desbloquear leitores</blockquote>
<blockquote> Relatório com leitores bloqueados no sistema</blockquote>
<blockquote> Relatório com histórico de empréstimos de um leitor</blockquote>
<blockquote> Retirada manual de multas</blockquote>
<blockquote> Deletar cadastro por número de ID ou nome</blockquote>
<blockquote> Deletar toda a lista de armazenamento</blockquote>

- Gerenciamento de livros:
<blockquote> Cadastrar</blockquote>
<blockquote> Atribuir número de ISBN automaticamente</blockquote>
<blockquote> Atualizar dados</blockquote>
<blockquote> Consultar dados por número de ISBN ou titulo</blockquote>
<blockquote> Procurar livros por ISBN, titulo, categoria ou autor</blockquote>
<blockquote> Relatórios com livros atrasados, emprestados e reservados</blockquote>
<blockquote> Relatório com todos os livros armazenados em estoque</blockquote>
<blockquote> Relatório com os livros mais populares em estoque</blockquote>
<blockquote> Alterar disponibilidade manualmente caso não esteja emprestado (reparo ou manutenção)</blockquote>
<blockquote> Deletar cadastro por número de ISBN ou titulo</blockquote>
<blockquote> Deletar toda a lista de armazenamento</blockquote>

- Gerenciamento de empréstimos, reservas e prazos:
<blockquote> Criar</blockquote>
<blockquote> Retirar</blockquote>
<blockquote> Leitor pode ter por vez: 1 empréstimo ativo, 3 reservas ativas e no máximo 3 prazos ativos</blockquote>
<blockquote> Livro pode ter por vez: 1 empréstimo ativo, 4 reservas ativas e no máximo 1 prazo ativo</blockquote>
<blockquote> Renovar empréstimos</blockquote>
<blockquote> Visualizar reservas ativas</blockquote>
<blockquote> Registrar um prazo para o primeiro leitor da fila de reserva de um livro ir realizar o empréstimo</blockquote>
<blockquote> Atribuir automaticamente prazo para o segundo leitor da fila de reserva caso o primeiro fique inoperante (bloqueado,
multado ou removido)</blockquote>
<blockquote> Inicializar a data final do empréstimo para 7 dias após a criação</blockquote>
<blockquote> Inicializar a data final do prazo para 2 dias após a criação</blockquote>
<blockquote> Atualizar informações</blockquote>
<blockquote> Consultar dados pelo ID ou ISBN</blockquote>
<blockquote> Retornar toda a lista de armazenamento</blockquote>
<blockquote> Deletar toda a lista de armazenamento</blockquote>