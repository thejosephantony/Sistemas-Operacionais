# 🖥️ Sistemas Operacionais

Repositório com implementações, exercícios e atividades práticas desenvolvidas durante a disciplina de **Sistemas Operacionais**.

O objetivo deste repositório é reunir códigos e experimentos relacionados aos principais conceitos estudados na disciplina, como **processos**, **threads**, **concorrência**, **sincronização**, **exclusão mútua**, **semáforos**, **locks**, **regiões críticas** e problemas clássicos de Sistemas Operacionais.

---

## 📚 Sobre a disciplina

A disciplina de **Sistemas Operacionais** estuda como o sistema operacional gerencia os recursos de um computador, como:

- 🧠 CPU;
- 💾 memória;
- 📁 arquivos;
- ⌨️ dispositivos de entrada e saída;
- 🧵 processos;
- 🔀 threads;
- 🔒 recursos compartilhados.

O Sistema Operacional funciona como uma camada intermediária entre o **hardware** e os **programas do usuário**, controlando a execução dos programas e garantindo que os recursos sejam usados de forma organizada, segura e eficiente.

---

## 🎯 Objetivos do repositório

Este repositório tem como objetivo:

- aplicar na prática conceitos de Sistemas Operacionais;
- estudar processos e threads;
- implementar problemas clássicos de concorrência;
- analisar condições de corrida;
- controlar acesso a regiões críticas;
- usar semáforos, locks e mecanismos de sincronização;
- simular problemas reais envolvendo múltiplas tarefas;
- organizar atividades práticas da disciplina.

---

## 🧩 Estrutura do repositório

```text
Sistemas-Operacionais/
├── Algoritmo de Peterson/
├── Atividade prática/
│   ├── Exercicio1/
│   ├── Exercicio2/
│   ├── Exercicio3/
│   └── Exercicio4/
├── Barbeiro/
├── Implementação1/
├── Implementação2/
├── Implementação3/
├── Implementação4/
├── Implementação5/
├── Implementação6/
├── Livro/
├── ThreadSafe/
└── Threads em Java/
```

---

## 🚀 Principais temas estudados

## 🧵 Threads

Threads são fluxos de execução dentro de um processo.

Um processo pode ter várias threads executando tarefas diferentes, compartilhando a mesma memória do programa.

Exemplo simples em Java:

```java
public class MinhaThread extends Thread {
    @Override
    public void run() {
        System.out.println("Executando em uma thread!");
    }

    public static void main(String[] args) {
        MinhaThread t = new MinhaThread();
        t.start();
    }
}
```

Também é possível criar threads usando `Runnable`:

```java
public class ExemploRunnable {
    public static void main(String[] args) {
        Runnable tarefa = () -> {
            System.out.println("Thread executando com Runnable!");
        };

        Thread thread = new Thread(tarefa);
        thread.start();
    }
}
```

---

## 🔄 Concorrência

Concorrência ocorre quando várias tarefas avançam durante o mesmo intervalo de tempo.

Em um processador com apenas um núcleo, as tarefas podem se alternar rapidamente na CPU. Em processadores com múltiplos núcleos, elas podem realmente executar ao mesmo tempo.

Exemplo:

```java
Thread t1 = new Thread(() -> System.out.println("Tarefa 1"));
Thread t2 = new Thread(() -> System.out.println("Tarefa 2"));

t1.start();
t2.start();
```

A ordem de execução pode variar, pois depende do escalonador do Sistema Operacional.

---

## ⚡ Concorrência x Paralelismo

Concorrência e paralelismo são conceitos relacionados, mas diferentes.

**Concorrência** significa que várias tarefas estão progredindo no mesmo intervalo de tempo.

**Paralelismo** significa que várias tarefas estão executando literalmente ao mesmo tempo, geralmente em núcleos diferentes.

```text
Concorrência:

Tarefa A: ███     ███     ███
Tarefa B:    ███     ███     ███

Paralelismo:

Tarefa A: █████████████
Tarefa B: █████████████
```

---

## ⚠️ Condição de corrida

Uma condição de corrida ocorre quando duas ou mais threads acessam um recurso compartilhado ao mesmo tempo, e o resultado depende da ordem de execução.

Exemplo problemático:

```java
class Contador {
    int valor = 0;

    void incrementar() {
        valor++;
    }
}
```

O comando `valor++` parece simples, mas internamente envolve várias etapas:

```text
1. Ler o valor atual
2. Somar 1
3. Salvar o novo valor
```

Se duas threads executarem essas etapas ao mesmo tempo, o resultado final pode ficar incorreto.

---

## 🔒 Região crítica

Região crítica é a parte do código que acessa um recurso compartilhado.

Quando várias threads acessam a mesma região crítica ao mesmo tempo, podem ocorrer erros de concorrência.

Exemplo de região crítica protegida:

```java
synchronized void incrementar() {
    contador++;
}
```

Nesse caso, o `synchronized` impede que várias threads executem esse trecho simultaneamente.

---

## 🚪 Exclusão mútua

Exclusão mútua garante que apenas uma thread ou processo acesse determinada região crítica por vez.

Ela é importante para evitar inconsistências em dados compartilhados.

Exemplo:

```java
synchronized void acessarRecurso() {
    // região crítica protegida
}
```

---

## 🚦 Semáforos

Semáforos são mecanismos usados para controlar o acesso a recursos limitados.

Eles permitem definir quantas threads podem acessar determinado recurso ao mesmo tempo.

Exemplo com no máximo 3 threads acessando simultaneamente:

```java
import java.util.concurrent.Semaphore;

public class ExemploSemaforo {
    private static final Semaphore semaforo = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            int id = i;

            new Thread(() -> {
                try {
                    semaforo.acquire();

                    System.out.println("Thread " + id + " entrou.");
                    Thread.sleep(1000);
                    System.out.println("Thread " + id + " saiu.");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaforo.release();
                }
            }).start();
        }
    }
}
```

Nesse exemplo, apenas **3 threads** podem acessar a região controlada ao mesmo tempo.

---

## 📖 ReadWriteLock

O `ReadWriteLock` permite múltiplas leituras simultâneas, mas garante escrita exclusiva.

Ele é útil quando várias threads podem consultar dados ao mesmo tempo, mas alterações precisam ser feitas de forma isolada.

Exemplo:

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BancoSimples {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private String dado = "Valor inicial";

    public String ler() {
        lock.readLock().lock();

        try {
            return dado;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void escrever(String novoValor) {
        lock.writeLock().lock();

        try {
            dado = novoValor;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

Regras principais:

```text
READ + READ      = permitido
READ + WRITE     = bloqueado
WRITE + WRITE    = bloqueado
```

---

## 🧠 Implementações presentes

## 📌 Algoritmo de Peterson

O **Algoritmo de Peterson** é uma solução clássica para exclusão mútua entre dois processos ou threads.

Ele demonstra como controlar o acesso a uma região crítica usando apenas variáveis compartilhadas.

Conceitos envolvidos:

- exclusão mútua;
- região crítica;
- espera ocupada;
- sincronização;
- concorrência.

Ideia geral:

```text
Thread 1 quer entrar na região crítica
Thread 2 quer entrar na região crítica

O algoritmo decide quem entra primeiro,
evitando que as duas entrem ao mesmo tempo.
```

Exemplo conceitual:

```c
int interesse[2] = {0, 0};
int vez = 0;
```

Essas variáveis ajudam a indicar:

- quem quer entrar na região crítica;
- de quem é a vez;
- qual processo deve esperar.

---

## 💈 Problema do Barbeiro Dorminhoco

O problema do **Barbeiro Dorminhoco** é um problema clássico de sincronização.

Ele representa uma barbearia onde:

- o barbeiro dorme quando não há clientes;
- clientes chegam à barbearia;
- se houver cadeira livre, o cliente espera;
- se não houver cadeira livre, o cliente vai embora;
- o barbeiro atende um cliente por vez.

Representação simples:

```text
Cliente chega
│
├── Há cadeira livre?
│   ├── Sim: cliente espera atendimento
│   └── Não: cliente vai embora
│
└── Barbeiro atende um cliente por vez
```

Conceitos envolvidos:

- threads;
- semáforos;
- sincronização;
- espera;
- recursos limitados;
- controle de acesso concorrente.

---

## 🧪 ThreadSafe

A pasta `ThreadSafe` contém implementações relacionadas à criação de estruturas seguras para ambientes concorrentes.

Uma estrutura é considerada **thread-safe** quando pode ser acessada por várias threads sem causar inconsistência nos dados.

Exemplo de problema:

```java
ArrayList<Integer> lista = new ArrayList<>();

lista.add(10);
```

Se várias threads executarem `add` ao mesmo tempo, podem ocorrer inconsistências.

Algumas soluções possíveis em Java:

```java
Collections.synchronizedList(new ArrayList<>());
```

ou:

```java
CopyOnWriteArrayList<Integer> lista = new CopyOnWriteArrayList<>();
```

---

## 🗄️ Banco de Dados Concorrente

Uma das atividades práticas simula o acesso concorrente a um banco de dados.

A proposta considera as seguintes regras:

- no máximo 10 consultas podem ocorrer simultaneamente;
- apenas 1 operação de escrita pode ocorrer por vez;
- durante uma escrita, nenhuma consulta pode ocorrer;
- as operações CRUD devem ser implementadas.

Operações CRUD:

```text
CREATE  -> criar registro
READ    -> consultar registro
UPDATE  -> atualizar registro
DELETE  -> remover registro
```

Exemplo conceitual:

```text
READ 1  ┐
READ 2  ├── podem ocorrer juntas
READ 3  ┘

UPDATE ─── precisa de acesso exclusivo
DELETE ─── precisa de acesso exclusivo
CREATE ─── precisa de acesso exclusivo
```

Possível solução:

```java
private final Semaphore limiteConsultas = new Semaphore(10, true);
private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
```

O `Semaphore` limita o número máximo de consultas simultâneas.

O `ReadWriteLock` garante que:

- várias leituras possam ocorrer ao mesmo tempo;
- escritas sejam exclusivas;
- leituras e escritas não ocorram simultaneamente.

---

## 🧮 Pequeno exemplo de cálculo de multitarefa

Suponha duas tarefas, cada uma precisando de 20 minutos de CPU e com 20% de espera por E/S.

Com uma tarefa:

```text
Utilização da CPU = 1 - 0,2 = 0,8 = 80%
```

Tempo total para uma tarefa:

```text
20 * 100 / 80 = 25 minutos
```

Com duas tarefas em multitarefa:

```text
Utilização da CPU = 1 - (0,2)^2
Utilização da CPU = 1 - 0,04
Utilização da CPU = 0,96 = 96%
```

Tempo total:

```text
40 * 100 / 96 = 41,67 minutos
```

A multitarefa reduz o tempo total de conclusão porque aproveita melhor o tempo em que uma tarefa está esperando E/S.

---

## 🔔 Chamadas de sistema e TRAP

Chamadas de sistema, ou **system calls**, são mecanismos usados por programas em modo usuário para solicitar serviços do Sistema Operacional.

Exemplos de serviços:

- ler arquivos;
- escrever arquivos;
- criar processos;
- encerrar processos;
- acessar dispositivos;
- alocar memória.

Exemplo conceitual de chamada `read`:

```c
read(fd, buffer, quantidade);
```

Onde:

- `fd` identifica o arquivo;
- `buffer` indica onde os dados serão armazenados;
- `quantidade` indica quantos bytes serão lidos.

A instrução **TRAP** é usada para transferir o controle do modo usuário para o modo núcleo.

Fluxo simplificado:

```text
Programa em modo usuário
        │
        │ faz uma chamada de sistema
        v
Instrução TRAP
        │
        v
Modo núcleo
        │
        v
Sistema Operacional executa o serviço
        │
        v
Retorno ao programa em modo usuário
```

---

## 🔁 Estados de processos

No modelo clássico de Tanenbaum, um processo pode estar em três estados principais:

```text
1. Em execução
2. Pronto
3. Bloqueado
```

### Em execução

O processo está usando a CPU naquele momento.

### Pronto

O processo está apto para executar, mas está esperando sua vez de usar a CPU.

### Bloqueado

O processo está esperando algum evento externo, como uma operação de entrada e saída.

Transições principais:

```text
Pronto -> Em execução
Em execução -> Pronto
Em execução -> Bloqueado
Bloqueado -> Pronto
```

Representação:

```text
Pronto  --------->  Em execução
  ^                    |
  |                    |
  |                    v
Bloqueado  <-------  espera por E/S
```

Resumo:

- `Pronto -> Em execução`: o escalonador escolhe o processo;
- `Em execução -> Pronto`: o processo perde a CPU;
- `Em execução -> Bloqueado`: o processo espera E/S;
- `Bloqueado -> Pronto`: o evento esperado aconteceu.

---

## 🛠️ Tecnologias utilizadas

- ☕ Java;
- 🔧 C;
- 🧵 Threads;
- 🚦 Semáforos;
- 🔒 Locks;
- 📚 Programação concorrente;
- 🖥️ Conceitos de Sistemas Operacionais.

---

## ▶️ Como executar programas em Java

Alguns projetos Java usam pacotes, por exemplo:

```java
package com.mycompany.bancodedados;
```

Nesse caso, o programa deve ser compilado e executado a partir da pasta raiz dos pacotes.

Exemplo de estrutura:

```text
src/main/java/com/mycompany/bancodedados/Programa.java
```

Entre na pasta:

```bash
cd src/main/java
```

Compile:

```bash
javac com/mycompany/bancodedados/*.java
```

Execute:

```bash
java com.mycompany.bancodedados.Programa
```

No PowerShell do Windows:

```powershell
cd src\main\java
javac com\mycompany\bancodedados\*.java
java com.mycompany.bancodedados.Programa
```

Atenção: se a classe possui `package`, não execute assim:

```bash
java Programa
```

Execute usando o nome completo do pacote:

```bash
java com.mycompany.bancodedados.Programa
```

---

## ▶️ Como executar programas em C

Para compilar um programa em C usando `gcc`:

```bash
gcc arquivo.c -o programa
```

Depois execute:

```bash
./programa
```

No Windows, usando MinGW ou outro compilador C configurado:

```powershell
gcc arquivo.c -o programa.exe
.\programa.exe
```

---

## 🧪 Exemplo de saída esperada

Em programas com threads, a saída pode variar a cada execução.

Isso acontece porque o escalonamento das threads depende do Sistema Operacional.

Exemplo:

```text
Thread 1 entrou na região crítica
Thread 1 saiu da região crítica
Thread 3 entrou na região crítica
Thread 3 saiu da região crítica
Thread 2 entrou na região crítica
Thread 2 saiu da região crítica
```

Em outra execução, a ordem pode ser diferente:

```text
Thread 2 entrou na região crítica
Thread 2 saiu da região crítica
Thread 1 entrou na região crítica
Thread 1 saiu da região crítica
Thread 3 entrou na região crítica
Thread 3 saiu da região crítica
```

Esse comportamento é esperado em programas concorrentes.

---

## 📌 Conceitos importantes

## Processo

Um processo é um programa em execução.

Ele possui:

- espaço de memória próprio;
- registradores;
- contador de programa;
- pilha;
- recursos associados.

---

## Thread

Uma thread é uma unidade de execução dentro de um processo.

Threads do mesmo processo compartilham:

- memória;
- variáveis globais;
- arquivos abertos;
- recursos do processo.

---

## Escalonamento

Escalonamento é o mecanismo usado pelo Sistema Operacional para decidir qual processo ou thread deve usar a CPU.

O escalonador é responsável por alternar a execução entre tarefas.

---

## Quantum

Quantum é o tempo máximo que um processo pode usar a CPU antes de ser interrompido pelo Sistema Operacional.

Quando o quantum acaba, o processo pode voltar para a fila de prontos.

---

## Deadlock

Deadlock ocorre quando dois ou mais processos ficam bloqueados esperando recursos uns dos outros.

Exemplo:

```text
Processo A segura o Recurso 1 e espera o Recurso 2
Processo B segura o Recurso 2 e espera o Recurso 1

Resultado: nenhum dos dois consegue continuar
```

---

## Starvation

Starvation ocorre quando um processo fica esperando indefinidamente por um recurso, porque outros processos continuam sendo atendidos antes dele.

---

## Espera ocupada

Espera ocupada ocorre quando um processo ou thread fica verificando repetidamente uma condição enquanto espera.

Exemplo conceitual:

```c
while (recurso_ocupado) {
    // continua esperando
}
```

Esse tipo de espera pode desperdiçar CPU.

---

## 🧾 Organização acadêmica

Este repositório foi criado com finalidade acadêmica, como parte dos estudos da disciplina de **Sistemas Operacionais**.

Os códigos têm o objetivo de reforçar conceitos vistos em sala de aula, permitindo observar na prática problemas como:

- acesso simultâneo a dados;
- disputa por recursos;
- bloqueio de threads;
- sincronização;
- escalonamento;
- condições de corrida;
- inconsistência causada por concorrência;
- soluções com mecanismos de controle.

---

## 📈 Aprendizados

Durante o desenvolvimento das atividades, são trabalhadas habilidades como:

- interpretar problemas clássicos de Sistemas Operacionais;
- implementar soluções concorrentes;
- depurar programas com múltiplas threads;
- entender comportamento não determinístico;
- aplicar semáforos e locks corretamente;
- controlar regiões críticas;
- organizar código acadêmico;
- relacionar teoria e prática.

---

## ⭐ Resumo do repositório

Este repositório reúne práticas de Sistemas Operacionais envolvendo:

```text
Processos
Threads
Concorrência
Paralelismo
Sincronização
Regiões críticas
Semáforos
Locks
Algoritmo de Peterson
Barbeiro Dorminhoco
Banco de Dados Concorrente
```

Com ele, é possível visualizar na prática como o Sistema Operacional lida com múltiplas tarefas e como o programador pode controlar corretamente o acesso a recursos compartilhados.

---

## 👨‍💻 Autor

**Joseph Antony dos Santos Leite**

Estudante de **Engenharia da Computação**.

GitHub: [thejosephantony](https://github.com/thejosephantony)

---

## 📌 Observação

Este repositório está em desenvolvimento e pode receber novas implementações conforme o avanço dos estudos na disciplina.

Os exemplos foram criados com foco didático, priorizando a compreensão dos conceitos fundamentais de Sistemas Operacionais.
