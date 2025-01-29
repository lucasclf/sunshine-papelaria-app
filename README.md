Sunshine Papelaria

Bem-vindo ao repositório do Sunshine Papelaria, um sistema de controle de estoque e pedidos desenvolvido para simplificar a gestão da papelaria.

📌 Sobre o Projeto

O Sunshine Papelaria é uma aplicação desktop que combina um back-end robusto com um front-end intuitivo para fornecer uma experiência simples e eficiente de controle de clientes, produtos e pedidos.

🏗 Arquitetura

A aplicação é dividida em dois componentes principais:

Back-end: Desenvolvido em Ktor (Kotlin) com persistência em SQLite.

Front-end: Construído com Electron, proporcionando uma interface amigável para o usuário.

🚀 Tecnologias Utilizadas

Back-end: Kotlin, Ktor, SQLite, Exposed ORM

Front-end: Electron, HTML, CSS, JavaScript

Empacotamento: Electron Builder, GraalVM (para compilação nativa do back-end)

📂 Estrutura do Repositório

sunshine-papelaria/
│── backend/               # Back-end (Ktor + SQLite)
│   ├── src/
│   │   ├── Main.kt
│   ├── build.gradle.kts
│   ├── gradlew
│
│── frontend/              # Front-end (Electron)
│   ├── src/
│   │   ├── main.js
│   ├── package.json
│
│── start-app.sh           # Script para iniciar tudo (Linux)
│── start-app.bat          # Script para iniciar tudo (Windows)
│── README.md

🛠 Como Rodar o Projeto

🔹 Pré-requisitos

Node.js (para rodar o Electron)

JDK 17+ (para rodar o back-end Kotlin)

Gradle (para build do Ktor)

🔹 Passo a Passo

Clone o repositório:

git clone https://github.com/seu-usuario/sunshine-papelaria.git
cd sunshine-papelaria

Rodando o Back-end

cd backend
./gradlew run  # Linux/macOS
gradlew.bat run  # Windows

Rodando o Front-end

cd frontend
npm install
npm start

(Opcional) Gerar Executável Único

npm run build  # Gera um executável com Electron Builder

📌 Funcionalidades

✅ Cadastro e gerenciamento de clientes✅ Cadastro e controle de estoque de produtos✅ Registro e acompanhamento de pedidos✅ Interface simples e intuitiva✅ Banco de dados local (SQLite)

🔗 Licença

Este projeto está sob a licença MIT. Sinta-se à vontade para contribuir e aprimorar o Sunshine Papelaria! ☀️