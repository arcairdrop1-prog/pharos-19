# Pharos Multi-Protocol Aggregator Agent

Um agente avançado desenvolvido em **Java** para a Pharos Agent Arena que integra múltiplos protocolos e vaults sob uma única interface, realizando consultas, roteamento inteligente baseado em taxas, ações preparadas para assinatura e tolerância a falhas.

## 📋 Requisitos Cumpridos

1. **Interface Unificada (Req. 01):** Integra múltiplos protocolos/vaults (Vault A e Vault B) atrás de uma única interface comum.
2. **Consultas e Ações (Req. 02):** Suporta consultas de posições/APY e preparação de ações (depósitos e levantamentos) prontas para assinatura.
3. **Roteamento Inteligente (Req. 03):** Roteia automaticamente para a melhor venue (maior APY) e explica detalhadamente o motivo da escolha.
4. **Tolerância a Falhas (Req. 04):** Lida com a indisponibilidade de um protocolo de forma transparente, permitindo que os restantes continuem operando sem falhas catastróficas.
5. **Arquitetura (Req. 05):** Estrutura modular escalável pronta para expansão.

## 🚀 Como Executar

1. Certifique-se de ter o **Java JDK** instalado.
2. Compile o ficheiro:
   ```bash
   javac MultiProtocolAggregator.java
