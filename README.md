# ToDoList App

Um aplicativo **ToDoList** simples e funcional desenvolvido em **Kotlin** utilizando a moderna toolkit **Jetpack Compose** para construção da interface.

## Características

- Gerenciamento de tarefas com título, descrição e status de conclusão
- Interface reativa e responsiva baseada em Jetpack Compose
- Validações robustas para garantir dados corretos
- Uso de arquitetura MVVM com ViewModel e StateFlow para gerenciamento de estado
- Implementação de testes unitários focados em qualidade e segurança do código
- Design simples e clean baseado no Material3

## Tecnologias Utilizadas

- Kotlin
- Jetpack Compose
- Android Architecture Components (ViewModel, StateFlow, LiveData)
- Material3 para UI moderna
- Coroutines para operações assíncronas
- Gradle como sistema de build

## Funcionalidades

- Adicionar, atualizar e remover tarefas
- Validação de campos de entrada com feedback ao usuário
- Listagem dinâmica das tarefas com LazyColumn
- Mensagens de erro exibidas e limpadas automaticamente
- Estrutura de projeto modular e organizada

## Como Executar o Projeto

1. Clone este repositório:
   ```
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   ```
2. Abra o projeto no Android Studio (versão recomendada Arctic Fox ou superior)
3. Sincronize as dependências Gradle
4. Conecte um dispositivo físico ou emulador Android
5. Execute o app

## Arquitetura e Código

- A arquitetura do app segue o padrão MVVM, separando claramente a UI (Jetpack Compose), lógica de apresentação (ViewModel) e modelo de dados (`Task`).
- Validações centralizadas na camada de serviço para garantir integridade dos dados.
- Uso extensivo de StateFlow para comunicação reativa entre ViewModel e UI.
- Código escrito com foco em qualidade, legibilidade e manutenção.

## Próximos Passos para o Projeto

- Adicionar persistência local utilizando Room Database
- Implementar sincronização via Firebase ou outro backend
- Melhorias na experiência do usuário (UX) e design
- Adição de testes instrumentados e UI tests
- Modularização e melhorias de performance

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests.



***

Se precisar, posso ajudar a gerar também arquivos de modelo para issues, templates de PR, ou documentação técnica interna para o projeto. Quer?
