# Maxima Tech - Desafio Android

Aplicativo que consome API REST para exibir dados de clientes e pedidos com suporte offline e sincronização em background.

[Read in English](README.md)

## Stack Tecnológico

**Core**
- Kotlin - Linguagem principal de desenvolvimento (80%)
- Java - Linguagem de suporte (20%)
- Jetpack Compose - Toolkit de UI
- Material Design 3 - Sistema de design

**Rede**
- Retrofit - Cliente HTTP
- OkHttp - Interceptador de rede
- Gson - Serialização JSON

**Banco de Dados**
- Room - Banco de dados local

**Arquitetura**
- MVVM - Padrão Model-View-ViewModel
- Clean Architecture - Arquitetura em camadas
- Coroutines - Programação assíncrona
- Flow - Streams reativos

**Injeção de Dependência**
- Koin - Framework de injeção de dependência

**Tarefas em Background**
- WorkManager - Sincronização periódica de dados
- Connectivity Manager - Monitoramento de estado da rede

**Gerenciamento de Recursos**
- Classe Dimens customizada - Valores centralizados de espaçamento e dimensões
- String Resources - Concatenação de texto e suporte à localização

**Testes**
- JUnit - Framework de testes unitários
- MockK - Framework de mocking

## Arquitetura

O projeto segue os princípios da Clean Architecture com padrão MVVM:

**Camada de Apresentação**
- Telas Compose UI
- ViewModels com gerenciamento de estado
- Componente de navegação

**Camada de Domínio**
- Use Cases para lógica de negócio
- Interfaces de Repository
- Modelos de domínio

**Camada de Dados**
- Implementações de Repository
- Fonte de dados remota (API)
- Fonte de dados local (banco Room)

## Implementação do Design System

**Espaçamento e Dimensões**
- Objeto Dimens customizado com valores padronizados de espaçamento
- Espaçamento consistente em todos os componentes de UI
- Sistema de dimensões escalável para diferentes tamanhos de tela

**Gerenciamento de Strings**
- Recursos de string centralizados para todo conteúdo de texto
- Concatenação de strings através de referências de recursos
- Preparado para suporte multi-idioma
- Composição dinâmica de texto para elementos de UI complexos

## Funcionalidades

**Gestão de Clientes**
- Visualizar lista de clientes
- Tela de detalhes do cliente
- Verificação de status com Snackbar

**Histórico de Pedidos**
- Navegar lista de pedidos
- Pesquisar pedidos por número RCA
- Filtragem de pedidos com debounce (300ms)
- Indicadores de legenda

**Suporte Offline**
- Fallback automático para banco local
- Sincronização de dados em background a cada 15 minutos
- Monitoramento de conectividade de rede

**UI/UX**
- Interface moderna com Compose
- Design responsivo com espaçamento consistente
- Estados de loading e tratamento de erros
- Busca com filtragem em tempo real
- Tokens de design centralizados para tematização consistente

## Conformidade com Requisitos

**Requisitos Obrigatórios**
- Implementação de Splash Screen
- Retrofit para consumo de API
- Room para persistência de dados local
- Seleção de fonte de dados baseada na rede
- Injeção de dependência com Koin
- Princípios SOLID com MVVM
- Mistura Kotlin (80%) e Java (20%)
- Job periódica em background (15 minutos)
- Uso de Coroutines e Flow

**Funcionalidades Opcionais Implementadas**
- Testes unitários e de integração
- Sistema de design centralizado com dimensões customizadas
- Gerenciamento de recursos de string para concatenação de texto

Desenvolvido para o Desafio Android da Maxima Tech