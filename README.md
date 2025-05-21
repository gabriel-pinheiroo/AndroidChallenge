Você deverá criar um app que irá consumir uma API, persistir as informações no banco e apresentar os dados no layout proposto. 

**Instruções**

Faça um fork deste projeto em seu repositório. 

Caso o projeto seja privado, adicione o usuário **MaximaTechAndroid** como colaborador do projeto, caso seja publico, nos envie somente o caminho do repositório. 

### API

O App deverá consumir os seguintes endpoints:

Clientes:
[Máxima Tech · Apiary](https://maximatech.docs.apiary.io/#reference/0/android/cliente)

Pedidos:
[Máxima Tech · Apiary](https://maximatech.docs.apiary.io/#reference/0/android/pedido)
### Layouts

Os layouts a serem seguidos estão presentes no fim do documento (clique na imagem para amplia-la).

1. Splash
2. Lista de clientes
3. Tela de detalhe dos clientes
4. Lista de pedidos
5. Legendas

### Fluxo do app

Deverá ser apresentada a Splash e logo em seguida a tela contendo um navegação onde o primeiro item será a lista de clientes e a segunda a lista de pedidos. 

Ao clicar em um cliente, deverá ser apresentada uma tela contendo as informações do cliente e deverá ser possível retornar a a tela anterior utilizando a seta de navegação.

Ao clicar em "Verificar status do cliente", deverá ser apresentado um Toast/Snackbar informando o Status do Cliente

Na tela de pedidos, deverá ser possível pesquisar o pedido por **número do pedido RCA**.

Na tela de pedidos, deverá haver um menu onde será possível ver a legenda dos indicadores. 

### Requisitos obrigatórios

- Deverá conter a Splash.
- Requisição da API utilizando **Retrofit**.
- Os dados deverão ser persistidos no banco de dados utilizando **Room**.
- A origem da consulta (banco ou rest) deverá ser definida pelo status de conectividade do aparelho.
- Deverá ser feita injeção de dependência utilizando **Koin**
- O projeto deverá seguir os padrões SOLID e ser feito na arquitetura **MVVM** utilizando coroutines (flow/liveData)
- O projeto deverá conter código Java e Kotlin (80% kotlin e 20% Java)
- Deverá ser criada uma job periódica (com intervalo de 15 minutos), onde deverá ser feita a requisição dos dados.
- Faça um readme informando as técnologias escolhidas e o porque.

### Opcionais

- Testes unitários/integração
- Módulos

### O que iremos avaliar

- Organização do projeto
- Utilização de padrões arquiteturais
- Clareza do código
- Escolha de estruturas e bibliotecas
- Ausência de crashs e bugs
- Detalhes de UI
- Estrutura e patterns utilizados
- Clareza e objetividade dos commits

Caso encontre algum erro, ou tenha alguma sugestão para melhorar esta avaliação, nos informe 😁

Imagens             |  Imagens
:-------------------------:|:-------------------------:
<img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Android%20Compact%20-%201.png" width=50% height=50%>  |  <img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Android%20Compact%20-%202.png" width=50% height=50%>
:-------------------------:|:-------------------------:
<img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Android%20Compact%20-%203.png" width=50% height=50%> | <img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Android%20Compact%20-%204.png" width=50% height=50%>
:-------------------------:|:-------------------------:
<img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Android%20Compact%20-%205.png" width=50% height=50%> | <img  src="https://github.com/MaximaTechAndroid/ProvaAndroid/blob/master/telas_app/Splash.png" width=50% height=50%>
