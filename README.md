# webflux-h2
app webflux wich H2

### Requisitos Java 8

### Como rodar

1 - Clone o projeto com sua conta do github

2 - Vá para o diretório raiz do aplicativo

3 - Se você estiver no Unix execute o comando sh gradlew clean build bootRun após alguns segundos o aplicativo deve rodar 
em http://localhost:4000.

4 - Se você estiver no Windows tem um gradlew.bat dentro do diretório raiz do aplicativo Boa sorte.



### Pontos finais e exemplos disponíveis:

curl -v -X POST -H "Content-Type: application/json" "http://localhost:4000/person" 
-d '{"email":"teste99@gmail.com","nickname":"teste99"}'

curl -v -X GET -H "Accept: application/json" "http://localhost:4000/person_list"

curl -v -X DELETE -H "Content-Type: application/json" "http://localhost:4000/person/1"

curl -v -X PUT -H "Content-Type: application/json" "http://localhost:4000/person/2" 
-d '{"nickname":"aaaaa"}'



### Observações finais

Eu acho que CI e CD são realmente bons para manter a qualidade do código crescendo, então eu decidi configurar um Ci com 
CircleCI, Codecov e Checkstyle para mostrar o quanto eu me importo.

Decidi usar o Spring Webflux, estou estudando essa nova maneira de programar reativa, as tecnologias não são um problema 
para mim. Se necessário, eu só preciso aprender e ler a documentação deles. Talvez o próximo desafio seja aprender 
Kotlin ou Go.

Se fosse um aplicativo real, eu usaria mais sobre o registro com o SLF4, estou acostumado a trabalhar com o Kibana para 
fazer o gerenciamento de logs e, para ser honesto, eu realmente gosto. Por que usar tail -n 1000 file | grep * regex se 
eu puder procurar em Kibana é mais rápido.

Se fosse um aplicativo real, seria bom usar o agente NewRelic para verificar a integridade, desempenho, métricas, estou 
acostumado a trabalhar com ele e é muito bom manter aplicativos 24/7 funcionando bem. 