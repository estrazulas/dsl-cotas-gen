# API de classificação por cotas 


Este projeto utiliza as definições da Linguagem de Domínio Específica disponível em https://github.com/spgroup/dsl-cotas, para gerar a API que faz a aprovação de candidatos e distribuição de vagas conforme as diferentes versões diponíveis na [Lei de cotas nr 12.711](http://www.planalto.gov.br/ccivil_03/_ato2011-2014/2012/lei/l12711.htm)

Ferramentas utilizadas:

- Spring Boot 2.2.4
- WebFlux
- ProjectReactor


# Endpoints

* @GetMapping("/dsl-cotas/quadro-vagas/{versao}/{quantidade}")
* @PostMapping("/dsl-cotas/aprova-candidatos/{versao}/{quantidade}")
* @GetMapping("/dsl-cotas/ordem-prioridade/{versao}/")

# Exemplo de uso

Passo 1) Após gerar o arquivo json na DSL, inserir na pasta templates dos resources do projeto, o nome do arquivo json será o nome a ser enviado no parâmetro /{versao} dos endpoints acima.

Passo 2) Run as na classe DslCotasGenApplication.java (br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen)

Passo 3) Criar request POST em um cliente como PostmanCanary:

![](https://github.com/estrazulas/dsl-cotas-gen/blob/master/img/postmancanary.png)

O retorno da API será a mesma lista de candidatos porém com a classificação por cotas conforme legislação passada.



