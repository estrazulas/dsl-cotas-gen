# API de classificação por cotas 


Este projeto utiliza as definições da Linguagem de Domínio Específica disponível em https://github.com/spgroup/dsl-cotas, para gerar a API que faz a aprovação de candidatos e distribuição de vagas conforme as diferentes versões diponíveis na [Lei de cotas nr 12.711](http://www.planalto.gov.br/ccivil_03/_ato2011-2014/2012/lei/l12711.htm)

Ferramentas utilizadas:
-Spring Boot 2.2.4
-WebFlux
-ProjectReactor


# Endpoints

* @GetMapping("/dsl-cotas/quadro-vagas/{versao}/{quantidade}")
* @PostMapping("/dsl-cotas/aprova-candidatos/{versao}/{quantidade}")
* @GetMapping("/dsl-cotas/ordem-prioridade/{versao}/")

