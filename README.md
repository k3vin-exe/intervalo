# Intervalo

Rede social acadêmica voltada à comunidade da Unoesc — projeto desenvolvido para o componente curricular de Desenvolvimento Mobile.

> Atividade Avaliativa 2 (Atividade Problematizadora) — implementação do aplicativo projetado na [Atividade Avaliativa 1](#).

**Alunos:** Kauan Natanael de Oliveira e Kévin Brayan dos Santos Teixeira
**Curso:** Análise e Desenvolvimento de Sistemas — UNOESC

## Sobre o projeto

O Intervalo é uma rede social acadêmica pensada para aproximar estudantes, professores e grupos institucionais em um único aplicativo, centralizando publicações, materiais e comunicação que normalmente ficam dispersos entre diferentes canais.

Esta entrega implementa o núcleo funcional do produto projetado na Atividade Avaliativa 1: autenticação, feed de publicações e CRUD completo de posts, integrado a um backend real (Firebase).

## Funcionalidades implementadas

- **Autenticação** anônima automática via Firebase Authentication
- **Feed em tempo real**, atualizado automaticamente via Cloud Firestore
- **Criar** publicação
- **Editar** publicação existente
- **Excluir** publicação (com confirmação)

## Tecnologias

- Java + Android Studio
- Gradle (Kotlin DSL)
- Firebase Authentication
- Cloud Firestore

## Como rodar o projeto

1. Clone o repositório:
   ```
   git clone https://github.com/k3vin-exe/intervalo.git
   ```
2. Abra a pasta no Android Studio.
3. Crie um projeto no [console do Firebase](https://console.firebase.google.com) e conecte-o ao app pelo menu **Tools > Firebase**, adicionando **Authentication** e **Cloud Firestore** ao projeto (isso baixa automaticamente o `google-services.json`, que não é versionado neste repositório por conter credenciais do projeto).
4. No console do Firebase, habilite o provedor **Anonymous** em Authentication > Sign-in method.
5. Configure as regras do Firestore para exigir autenticação:
   ```
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /posts/{postId} {
         allow read, write: if request.auth != null;
       }
     }
   }
   ```
6. Rode o app em um emulador ou dispositivo físico.

## Estrutura do código

```
app/src/main/java/com/devmobile/intervalo/
├── MainActivity.java        # Tela principal: feed, autenticação e CRUD de posts
├── PostAdapter.java         # Adapter da RecyclerView do feed
└── model/
    └── Post.java            # Modelo de dados de uma publicação
```

## Relatório técnico

O relatório técnico completo, com detalhamento da implementação, escolhas de arquitetura, integração com API e princípios de UX aplicados, está disponível em [`relatorio-tecnico.docx`](./relatorio-tecnico.docx) neste repositório.

## Demonstração

- Vídeo de apresentação: 

https://github.com/user-attachments/assets/19c630e7-4d15-4f6c-a98a-8ad9e0c599f1


- APK para instalação: ver [Releases](../../releases)
