
## Rodar
Change Desktop

O change Desktop é um projeto de sistemas distribuídos, desenvolvido como
parcial para aprovação da disciplina de sistema distribuídos da UTFPR-PG.

Para inicialização do projeto deve-se clonar o repositório localmente e realizar o build
com o maven, utilizando **mvn clean install**

O projeto utiliza como dependência o json versão 20210307, até o momento não se tinha esta
versão no repositório do maven, portanto ele deve ser adiciona manualmente, podendo ser feito com o script abaixo:
##### Installar dependencia do org.json com:
mvn install:install-file -Dfile=json-20210307.jar -DgroupId=org.json -DartifactId=json -Dversion=20210307 -Dpackaging=jar

Após o build do projeto, pode-se executar o sistema diretamente pelo código fonte.
Os arquivos contendo o Main estão em:

**Servidor:** change/src/main/java/com/change/server/TCPServer.java

**Cliente:** change/src/main/java/com/change/client/ChangeApplication.java

#### Possivel erro ao compilar

Ao rodar o maven install, dependendo da maquina pade surgir um erro, causado pela versão
do java presente no maven compile.
Nesse caso, basta mudar a versão do java no maven para a versão presente na maquina.

**OBS: a aplicação precisa da jdk-11 ou superior**


Autoria: Carlos de Souza Lima