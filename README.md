## Banco de Dados Postgresql - Docker

Na pasta raiz do projeto usar o comando abaixo para criar o container do banco de dados:

```bash
docker-compose up -d
```

Obs.: Esse comando também cria outros dois containers, um com a API e outro com a interface WEB.

## Backend - Spring Boot

Foi criado um arquivo launch.json na pasta .vscode para facilitar o start da api no menu ```Run and Debug``` do VSCode.
Subindo a api dessa forma é possível debugar dentro do VSCode a aplicação

Em outras IDEs é possível criar um launch apenas rodando o método main da classe ```Application.java```

Caso não queira executar direto pela IDE, é possível subir a api usando o seguinte comando dentro da pasta ```backend```

```bash
mvn spring-boot:run
```

## Frontend - Angular

Foi criado um arquivo launch.json na pasta .vscode para facilitar o start da aplicação angular no menu ```Run and Debug``` do VSCode.
Subindo a aplicação dessa forma é possível debugar dentro do VSCode

Caso não queira executar direto pela IDE, é possível subir a aplicação usando o seguinte comando dentro da pasta ```frontend```

```bash
npm start
```

## Mobile - Flutter

Foi criado um arquivo launch.json na pasta .vscode para facilitar o start da aplicação flutter no menu ```Run and Debug``` do VSCode.
Subindo a aplicação dessa forma é possível debugar dentro do VSCode

Caso não queira executar direto pela IDE, é possível subir a aplicação usando o seguinte comando dentro da pasta ```mobile```

```bash
flutter run
```

Caso esteja usando emulador executar o comando para iniciar o emulador antes de rodar a aplicação:

```bash
flutter emulators --launch flutter_emulator
```

Para usar o HotReload ou HotRestart, deve-se digitar a tecla ```r``` (HotReload) ou ```R``` (HotRestart) 
do teclado no mesmo terminal que executou o comando anterior
