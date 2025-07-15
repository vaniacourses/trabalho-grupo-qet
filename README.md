# MedAlerta

Link para doc editável

https://docs.google.com/document/d/1GaWSBPZyZQ7gFh2rxKg86xLV_KXtsKJ8cnUzHtmVNoA/edit?usp=sharing


Link para apresentação

https://docs.google.com/presentation/d/1bVQY4UeCYzAQFG3fTdnUHDlDHgGEMrGPNBpW0xUAMdo/edit?usp=sharing


Link para apresentação 2

https://docs.google.com/presentation/d/1nCWveW7pinoR9H9UUwEH9pUVptDsqu2pi0Ot1XTW43Y/edit?usp=sharing

## Coisas que foram faladas na apresentação mas não estão nos slides

Testes de integração estão com nome IT no final e foi usado a técnica top-down.

Para a técnica de teste funcional usamos os critérios de grafo causa-efeito e particionamento em classes de equivalênia para os testes de sistema de cadastro de estoque da farmácia e teste de login.

O requisito não funcional testado pelos testes de sistema foi segurança cujo o objetivo era garantir autenticidade básica nos logins, testando valores errados e não aceitos.

Para a técnica de teste estrutural usamos o jacoco para analisar a cobertura de arestas para assim melhorarmos os testes unitários.

Para a técnica de teste baseado em defeitos usamos o pitest para melhorarmos o score de mutação principalmente da classe Agenda que precisou de um esforço conjunto para melhorar.

As classes melhoradas com o SonarQube foram Agenda, Endereco, PessoaJuridica e Data.

Foi usado moquito na classe Data mas isso já erá desde a primeira entrega.

Foi usado a lib AssertJ-swing-junit para os testes de sistema.

O comando mvn clean verify executa todos os passos de teste incluindo os relatórios do jacoco e pitest.
