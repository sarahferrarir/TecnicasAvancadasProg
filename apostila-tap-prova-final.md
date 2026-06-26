# Técnicas Avançadas de Programação — Apostila Completa para a Prova Final

**Curso:** Engenharia de Software — IBMEC
**Disciplina:** Técnicas Avançadas de Programação
**Professor (estilo de referência):** Thiago Souza
**Linguagem:** Java

---

## Como usar esta apostila

Esta apostila foi escrita para ser lida do começo ao fim, como um livro da disciplina. Ela não é um resumo: cada conceito é construído da intuição até o nível necessário para resolver provas de interpretação de código.

A disciplina foi construída nesta ordem, e a apostila segue exatamente ela:

```
POO  →  Java Code Conventions / Checkstyle  →  Clean Code  →  SOLID
   →  JDBC  →  Design Patterns  →  Spring Boot  →  API REST  →  Projeto Final
```

Cada capítulo dos **conceitos principais** segue esta estrutura fixa:

1. O que é
2. Por que existe
3. Como funciona
4. Exemplo simples (linha por linha)
5. Exemplo no estilo do professor
6. Exemplo do mundo real (analogia)
7. Erros comuns
8. Como reconhecer numa prova
9. Como o professor provavelmente cobraria (questão estilo AP1)
10. Como responder (raciocínio + por que as outras erram)
11. Resumo relâmpago

Ao final de cada parte há **10 questões objetivas + 2 discursivas**. No final da apostila há um **simulado de 30 questões** com **gabarito comentado**.

### O estilo do professor (leia isto com atenção)

Pela AP1, o professor:

- **Não cobra definição decorada.** Ele mostra **código** e pergunta o que está sendo aplicado/violado.
- Gosta de perguntar: *qual princípio é aplicado*, *qual é violado*, *qual padrão é esse*, *qual a melhor refatoração*, *qual alternativa segue Clean Code/SOLID*.
- Mistura distratores muito plausíveis. Quase sempre há uma alternativa "quase certa" que erra em **um detalhe técnico** (ex.: trocar *sobrescrita* por *sobrecarga*, dizer que comentário óbvio é "necessário", etc.).
- Tem questões discursivas curtas pedindo **refatoração** (reescrever um trecho aplicando exceptions, ISP, etc.).

Mantenha na cabeça durante toda a leitura: **a prova é de interpretação de código.** Por isso cada capítulo tem a seção "Como reconhecer numa prova".

---

# PARTE 1 — Programação Orientada a Objetos (POO)

A POO é a fundação de tudo. SOLID, Design Patterns, Spring — todos assumem que você domina classe, objeto, herança, interface, polimorfismo e encapsulamento. Vamos do zero.

## 1.1 Classes e Objetos

### 1. O que é

Uma **classe** é um molde, uma planta de construção. Ela descreve **como** um certo tipo de coisa é (quais dados ela guarda) e **o que** ela sabe fazer (quais operações ela tem). A classe em si não é a coisa — é a descrição da coisa.

Um **objeto** é uma coisa concreta construída a partir desse molde. Se `Carro` é a planta, um `Carro` específico, vermelho, com placa ABC-1234, parado na sua garagem, é um **objeto** (uma *instância*) dessa classe.

> Analogia central: a **classe** é a planta arquitetônica de uma casa; o **objeto** é cada casa de verdade construída a partir daquela planta. Uma planta, muitas casas.

Em Java:

- Os **dados** que o objeto guarda são chamados de **atributos** (ou campos / *fields*).
- As **operações** são chamadas de **métodos**.

### 2. Por que existe

Antes da POO, programava-se de forma **procedural**: dados soltos em variáveis e funções que manipulavam esses dados. À medida que o sistema crescia, ficava difícil saber *quais funções podiam mexer em quais dados*, e qualquer parte do programa podia corromper qualquer variável.

A POO resolve isso **juntando os dados e as operações que mexem neles dentro de uma mesma unidade (o objeto)** e controlando quem pode acessar o quê. Isso traz três ganhos enormes:

- **Organização:** o código fica agrupado por "coisa" do mundo real (Cliente, Pedido, Produto), e não por procedimento solto.
- **Reuso:** uma classe bem feita é usada em vários lugares.
- **Proteção:** o objeto controla seu próprio estado (veremos isso em Encapsulamento).

### 3. Como funciona

1. Você **declara** a classe com a palavra `class`.
2. Dentro dela, declara **atributos** (estado) e **métodos** (comportamento).
3. Para criar um objeto, usa o operador **`new`**, que reserva memória e devolve uma **referência** ao objeto.
4. A referência é guardada numa variável; por ela você acessa atributos e chama métodos com o operador ponto `.`.

Internamente: em Java, objetos vivem na **heap** (memória dinâmica). A variável guarda apenas uma **referência** (um "endereço") para o objeto. Por isso, quando você faz `b = a` com objetos, os dois apontam para o **mesmo** objeto — não há cópia.

### 4. Exemplo simples (linha por linha)

```java
public class Carro {          // 1. declara a classe (molde)
    String cor;               // 2. atributo: estado de cada carro
    int velocidade;           // 3. atributo

    void acelerar() {         // 4. método: comportamento
        velocidade = velocidade + 10;
    }
}

public class Main {
    public static void main(String[] args) {
        Carro c = new Carro();   // 5. cria um OBJETO (instância) na heap
        c.cor = "vermelho";      // 6. acessa atributo pelo ponto
        c.acelerar();            // 7. chama método -> velocidade vira 10
        System.out.println(c.velocidade); // 8. imprime 10
    }
}
```

- **Linha 1:** define o molde `Carro`.
- **Linhas 2–3:** todo `Carro` terá uma `cor` e uma `velocidade`.
- **Linha 4–6:** `acelerar()` modifica o estado **daquele** objeto.
- **Linha 5:** `new Carro()` constrói um objeto; `c` guarda a referência.
- **Linha 7:** `c.acelerar()` afeta apenas o objeto `c`. Outro carro teria sua própria `velocidade`.

### 5. Exemplo no estilo do professor

O professor usou na AP1 a classe `Funcionario`. Veja a mesma ideia:

```java
class Funcionario {
    String nome;
    double salarioBase;

    double calcularSalario() {
        return salarioBase;
    }
}
```

Aqui `Funcionario` é a classe; `new Funcionario()` cria um funcionário concreto. Guarde esse exemplo — ele reaparece em Herança e Polimorfismo.

### 6. Exemplo do mundo real

Um **formulário de cadastro** em branco é a classe: ele define quais campos existem (nome, CPF, e-mail). Cada formulário **preenchido** por uma pessoa é um objeto: mesma estrutura, dados diferentes.

### 7. Erros comuns

- Confundir **classe** com **objeto** ("a classe Carro está vermelha" — errado; o *objeto* está vermelho).
- Esquecer o `new` e tentar usar uma referência nula → `NullPointerException`.
- Achar que `b = a` copia o objeto. Não copia: copia a **referência**.

### 8. Como reconhecer numa prova

- A palavra `class` define molde; `new` cria objeto.
- Se a questão fala em "instância", está falando de **objeto**.
- Se vê `Tipo x = new Tipo();`, `x` é um objeto do tipo `Tipo`.

### 9. Como o professor cobraria

> Considere:
> ```java
> Carro a = new Carro();
> Carro b = a;
> a.cor = "azul";
> System.out.println(b.cor);
> ```
> O que é impresso?
> A) `null`  B) `"azul"`  C) erro de compilação  D) `"vermelho"`  E) string vazia

### 10. Como responder

**Resposta: B) "azul".** `b = a` faz `b` apontar para o **mesmo** objeto que `a`. Não há cópia. Mudar `a.cor` muda o objeto compartilhado, então `b.cor` também é `"azul"`.
- A) erra: o atributo foi atribuído, não é nulo.
- C) erra: o código compila normalmente.
- D) erra: pressupõe cópia independente, que não existe com referências.
- E) erra: o atributo recebeu valor.

### 11. Resumo relâmpago

Classe = molde. Objeto = instância criada com `new`. Variáveis de objeto guardam **referências**, não cópias.

---

## 1.2 Herança (`extends`)

### 1. O que é

**Herança** é quando uma classe (a **filha** / subclasse) **reaproveita e estende** outra classe (a **mãe** / superclasse). A filha já nasce com os atributos e métodos da mãe, e pode adicionar os seus ou modificar comportamento.

Em Java usamos a palavra-chave **`extends`**:

```java
class Gerente extends Funcionario { ... }
```

Lê-se: "Gerente **é um** Funcionário". Essa frase — "**é um**" — é o teste mental da herança.

### 2. Por que existe

Para **evitar duplicação** e modelar relações naturais de especialização. Se `Gerente`, `Desenvolvedor` e `Estagiario` são todos `Funcionario`, em vez de repetir `nome`, `matricula`, etc. em cada um, você coloca o que é comum na superclasse `Funcionario` e só o que é específico em cada filha.

### 3. Como funciona

1. A subclasse declara `extends Mae`.
2. Ela **herda** atributos e métodos (respeitando os modificadores de acesso).
3. Ela pode **adicionar** novos membros.
4. Ela pode **sobrescrever** (`@Override`) métodos herdados para mudar o comportamento.
5. Java tem **herança simples**: uma classe estende **no máximo uma** outra (mas pode implementar várias interfaces — veremos).
6. Toda classe que não estende nada estende implicitamente `Object`.

### 4. Exemplo simples (linha por linha)

```java
class Animal {                 // superclasse
    String nome;
    void respirar() {
        System.out.println(nome + " está respirando");
    }
}

class Cachorro extends Animal { // 'Cachorro é um Animal'
    void latir() {              // método novo, só do Cachorro
        System.out.println(nome + " fez Au au");
    }
}

public class Main {
    public static void main(String[] args) {
        Cachorro c = new Cachorro();
        c.nome = "Rex";   // atributo herdado de Animal
        c.respirar();     // método herdado
        c.latir();        // método próprio
    }
}
```

- `Cachorro` não declara `nome` nem `respirar()`, mas pode usá-los: **herdou**.
- `latir()` é exclusivo de `Cachorro`.

### 5. Exemplo no estilo do professor

Direto da AP1 (questão 1):

```java
abstract class Funcionario {
    String nome;
    abstract double calcularSalario();
}

class Gerente extends Funcionario {
    double bonus;
    @Override
    double calcularSalario() { return 5000 + bonus; }
}

class Desenvolvedor extends Funcionario {
    double horasTrabalhadas;
    double valorHora;
    @Override
    double calcularSalario() { return horasTrabalhadas * valorHora; }
}
```

`Gerente` e `Desenvolvedor` **são** `Funcionario` (`extends`), e cada um **sobrescreve** `calcularSalario()`. Esse exemplo casa Herança + Classe Abstrata + Polimorfismo (próximo tópico).

### 6. Exemplo do mundo real

Documentos oficiais: existe um **formulário-base** com campos comuns (nome, data). O "formulário de matrícula" e o "formulário de rematrícula" **herdam** os campos comuns e cada um acrescenta os seus. Você não reescreve os campos base toda vez.

### 7. Erros comuns

- Usar herança quando a relação não é "**é um**". Ex.: `Carro extends Motor` é errado — carro **tem um** motor (isso é **composição**, não herança).
- Esquecer que herança simples só permite **uma** superclasse.
- Confundir herança (`extends`, entre classes) com implementação (`implements`, com interfaces).

### 8. Como reconhecer numa prova

- A palavra **`extends`** entre duas **classes**.
- Subclasse usando atributo/método que ela não declarou (veio da mãe).
- Presença de `@Override` indica método **herdado e redefinido**.

### 9. Como o professor cobraria

> ```java
> class A { void f() { System.out.println("A"); } }
> class B extends A { void g() { System.out.println("B"); } }
>
> A obj = new B();
> obj.f();   // linha X
> obj.g();   // linha Y
> ```
> Sobre as linhas X e Y, é correto afirmar:
> A) Ambas compilam.
> B) X compila, Y **não** compila.
> C) Nenhuma compila.
> D) Y compila, X não.

### 10. Como responder

**Resposta: B.** A variável é do tipo `A`. O compilador só deixa chamar pela referência `A` o que **existe em `A`**. `f()` existe em `A` → X compila. `g()` só existe em `B`, não em `A` → Y **não** compila (mesmo o objeto sendo um `B`). Isso é a diferença entre **tipo da referência** (decide o que o compilador permite) e **tipo do objeto** (decide qual implementação roda).
- A) erra: ignora que `g()` não está no tipo `A`.
- C) erra: `f()` está em `A`, compila.
- D) inverte a lógica.

### 11. Resumo relâmpago

`extends` = "é um". Filha herda da mãe, herança **simples**. O **tipo da referência** limita o que o compilador deixa chamar.

---

## 1.3 Interfaces (`implements`)

### 1. O que é

Uma **interface** é um **contrato**: uma lista de métodos que uma classe se compromete a ter, **sem dizer como** eles funcionam. A interface diz *o quê*, a classe diz *como*.

```java
interface Pagavel {
    void pagar(double valor);   // só a assinatura, sem corpo
}
```

Quem **assina o contrato** usa **`implements`**:

```java
class Boleto implements Pagavel {
    public void pagar(double valor) { /* implementação real */ }
}
```

### 2. Por que existe

Java tem herança **simples** (uma só mãe). Mas muitas vezes precisamos que um objeto "saiba se comportar" de várias formas. Interfaces resolvem isso: uma classe pode **implementar várias interfaces**. Além disso, interfaces permitem programar voltado a **abstrações** em vez de classes concretas — base do polimorfismo e do princípio DIP (SOLID).

### 3. Como funciona

1. Declara-se `interface Nome { ... }` com métodos **sem corpo** (abstratos por padrão).
2. A classe usa `implements Nome` e é **obrigada** a fornecer corpo para todos os métodos (senão precisa ser abstrata).
3. Métodos da interface são `public` por padrão; ao implementar, devem ser `public`.
4. Uma classe pode `implements A, B, C` — várias interfaces.
5. Pode-se ter uma variável do tipo da interface apontando para qualquer implementação: `Pagavel p = new Boleto();`.

### 4. Exemplo simples (linha por linha)

```java
interface Forma {
    double area();            // contrato: toda Forma sabe calcular sua área
}

class Quadrado implements Forma {
    double lado;
    public double area() { return lado * lado; }   // o "como"
}

class Circulo implements Forma {
    double raio;
    public double area() { return 3.14 * raio * raio; }
}

public class Main {
    public static void main(String[] args) {
        Forma f = new Quadrado();   // referência do tipo da interface
        f.lado = 4;                 // ERRO: 'lado' não está no contrato Forma
    }
}
```

- `Forma` define **o que** toda forma faz: `area()`.
- `Quadrado` e `Circulo` dão implementações diferentes.
- Atenção na última linha: pela referência `Forma`, só dá para chamar o que está **no contrato** (`area()`), não `lado`.

### 5. Exemplo no estilo do professor

A questão 8 da AP1 (a do `DispositivoMultimidia`) é toda sobre interfaces. A versão **correta** (após aplicar ISP) tem várias interfaces pequenas:

```java
interface DispositivoDeSom   { void reproduzirAudio(); }
interface DispositivoDeVideo { void reproduzirVideo(); }
interface DispositivoDeImpressao { void imprimirDocumento(); }

class CaixaDeSom implements DispositivoDeSom {
    public void reproduzirAudio() { System.out.println("Reproduzindo áudio..."); }
}
class Televisao implements DispositivoDeVideo {
    public void reproduzirVideo() { System.out.println("Reproduzindo vídeo..."); }
}
```

Cada classe implementa **só o que usa**. Guarde isto: voltaremos nele em ISP.

### 6. Exemplo do mundo real

Uma **tomada padrão** é uma interface: qualquer aparelho com o plugue certo funciona, sem a tomada saber se é uma TV, ventilador ou carregador. O "contrato" é o formato do plugue; cada aparelho implementa do seu jeito.

### 7. Erros comuns

- Esquecer `public` ao implementar um método da interface.
- Tentar instanciar a interface diretamente: `new Forma()` → **erro** (interface não tem corpo).
- Confundir `implements` (com interface) e `extends` (com classe).
- Achar que interface guarda estado: interface **não tem atributos de instância** (apenas constantes `public static final`).

### 8. Como reconhecer numa prova

- Palavra **`interface`** na declaração e métodos **sem corpo** (terminam em `;`).
- Classe com **`implements`**.
- Uma classe implementando **mais de uma** interface (separadas por vírgula) = sinal forte de questão sobre interface/ISP.

### 9. Como o professor cobraria

> ```java
> interface A { void x(); }
> interface B { void y(); }
> class C implements A, B {
>     public void x() {}
>     public void y() {}
> }
> ```
> Sobre o código:
> A) Não compila, pois Java não permite implementar duas interfaces.
> B) Compila; `C` cumpre os dois contratos.
> C) Não compila, pois faltou `extends`.
> D) `C` deveria ser `abstract` obrigatoriamente.

### 10. Como responder

**Resposta: B.** Java **permite** implementar várias interfaces (o que não permite é herdar de várias classes). `C` forneceu corpo para `x()` e `y()`, então cumpriu os dois contratos e compila.
- A) erra: múltiplas interfaces são permitidas.
- C) erra: interfaces usam `implements`, não `extends`.
- D) erra: como implementou tudo, não precisa ser abstrata.

### 11. Resumo relâmpago

Interface = contrato (o quê). `implements` cumpre o contrato (o como). Uma classe implementa **várias** interfaces. Não se instancia interface.

---

## 1.4 `extends` vs `implements` — tabela decisiva

| Aspecto | `extends` | `implements` |
|---|---|---|
| Usado com | classe (ou interface↔interface) | classe → interface |
| Significado | "é um", herda implementação | "cumpre o contrato" |
| Quantidade | **uma** superclasse | **várias** interfaces |
| Herda corpo de método? | sim | não (interface não tem corpo*) |
| Palavra mental | especialização | capacidade/contrato |

\*A partir do Java 8 interfaces podem ter métodos `default` com corpo, mas o professor trabalha o conceito clássico: interface = contrato sem implementação.

---

## 1.5 Polimorfismo

### 1. O que é

**Polimorfismo** significa "muitas formas". É a capacidade de **uma mesma chamada de método se comportar de formas diferentes** dependendo do objeto real que está por trás da referência.

Quando você escreve `funcionario.calcularSalario()`, **a mesma linha** pode rodar o cálculo do gerente ou do desenvolvedor — quem decide é o **tipo real do objeto**, descoberto em **tempo de execução**.

### 2. Por que existe

Para escrever código **genérico e extensível**. Em vez de um `if` gigante perguntando "é gerente? é dev? é estagiário?", você trata todos como `Funcionario` e deixa cada um responder do seu jeito. Adicionar um novo tipo não exige mexer no código que já existe — isso conecta direto com o **OCP** do SOLID.

### 3. Como funciona

Existem dois polimorfismos que o professor distingue:

- **Polimorfismo de sobrescrita (dinâmico / de subtipo):** referência da superclasse aponta para objeto da subclasse; a chamada é resolvida **em tempo de execução** pelo tipo real. É o "polimorfismo" no sentido forte.
- **Polimorfismo de sobrecarga (estático):** mesmo nome de método com **assinaturas diferentes**, resolvido em **tempo de compilação**. (Detalhado em 1.6.)

Mecanismo interno do dinâmico: a JVM mantém uma **tabela de métodos virtuais** por classe. Ao chamar `obj.metodo()`, ela consulta a tabela do **objeto real** e executa a versão certa. Isso se chama *dynamic dispatch* (ligação tardia).

### 4. Exemplo simples (linha por linha)

```java
class Animal { void som() { System.out.println("som genérico"); } }
class Gato extends Animal { void som() { System.out.println("Miau"); } }
class Cao  extends Animal { void som() { System.out.println("Au"); } }

public class Main {
    public static void main(String[] args) {
        Animal a;            // referência do tipo da superclasse
        a = new Gato();
        a.som();             // imprime "Miau"  -> decidido pelo objeto real
        a = new Cao();
        a.som();             // imprime "Au"
    }
}
```

- A **variável** é `Animal`, mas o **objeto** muda.
- A mesma linha `a.som()` produz saídas diferentes: isso **é** polimorfismo dinâmico.

### 5. Exemplo no estilo do professor

A questão 1 da AP1 é o caso clássico:

```java
Funcionario f1 = new Gerente();
Funcionario f2 = new Desenvolvedor();
System.out.println(f1.calcularSalario());  // roda o cálculo do Gerente
System.out.println(f2.calcularSalario());  // roda o do Desenvolvedor
```

As referências são `Funcionario`, mas cada chamada de `calcularSalario()` é resolvida **em tempo de execução** pelo tipo real (`Gerente` / `Desenvolvedor`). Isso é **polimorfismo dinâmico** — exatamente a alternativa correta da prova.

### 6. Exemplo do mundo real

O botão "imprimir" do sistema operacional: você clica no **mesmo** botão, mas o resultado depende da impressora instalada (jato de tinta, laser, PDF). A ação é a mesma; o comportamento concreto varia.

### 7. Erros comuns

- Chamar de polimorfismo o que é só herança sem redefinição de comportamento.
- Confundir **sobrecarga** (estática, mesma classe) com **sobrescrita** (dinâmica, entre mãe e filha). A prova adora esse erro.
- Achar que o **tipo da referência** decide qual método roda. Quem decide **qual roda** é o **objeto**; o tipo da referência decide apenas o que **pode ser chamado** (compilação).

### 8. Como reconhecer numa prova

- Referência da superclasse/interface apontando para objeto da subclasse: `Super x = new Sub();`.
- `@Override` nas subclasses + chamada pela referência da mãe.
- Pergunta com palavras "tempo de execução", "tipo real do objeto", "resolvido dinamicamente".

### 9. Como o professor cobraria

(reproduzindo o espírito da questão 1 da AP1)

> ```java
> abstract class Forma { abstract double area(); }
> class Circulo extends Forma { double r; double area(){ return 3.14*r*r; } }
> class Quadrado extends Forma { double l; double area(){ return l*l; } }
>
> Forma f = new Circulo();
> System.out.println(f.area());
> ```
> A chamada `f.area()` executa a versão do `Circulo`. Isso ocorre porque:
> A) há sobrecarga, pois os métodos têm o mesmo nome.
> B) o polimorfismo é por composição.
> C) há polimorfismo dinâmico: a chamada é resolvida em tempo de execução pelo tipo real do objeto.
> D) não há polimorfismo, pois `Forma` é abstrata.
> E) `System.out.println` impede o polimorfismo.

### 10. Como responder

**Resposta: C.** A referência é `Forma`, mas o objeto é `Circulo`; a JVM resolve `area()` em runtime pelo tipo real → polimorfismo **dinâmico** (de sobrescrita).
- A) erra: **sobrecarga** seria mesmo nome com **assinaturas diferentes** na mesma classe. Aqui é **sobrescrita**.
- B) erra: não há delegação a outro objeto; é herança + override, não composição.
- D) erra: classe abstrata **não pode ser instanciada**, mas suas subclasses sim, e o polimorfismo acontece justamente por isso.
- E) erra: `println` apenas imprime o resultado; não interfere na resolução do método.

> **Macete da prova:** o distrator "sobrecarga" e o distrator "abstrata não instancia, logo não há polimorfismo" são os dois preferidos do professor. Decore: instanciar a **subclasse** é permitido, e é aí que o polimorfismo nasce.

### 11. Resumo relâmpago

Polimorfismo dinâmico: `Super ref = new Sub()`, chamada resolvida **em runtime** pelo objeto real. Sobrescrita ≠ sobrecarga.

---

## 1.6 Sobrecarga vs Sobrescrita

Esses dois termos parecem iguais e o professor explora isso. Domine a tabela.

### Sobrecarga (overload)

**Mesmo nome de método, assinaturas diferentes (parâmetros diferentes), na mesma classe.** Resolvida em **tempo de compilação** (estático). O tipo de retorno **não** conta para diferenciar.

```java
class Calc {
    int somar(int a, int b)            { return a + b; }
    double somar(double a, double b)   { return a + b; }
    int somar(int a, int b, int c)     { return a + b + c; }   // 3 parâmetros
}
```

São três métodos `somar` **diferentes**; o compilador escolhe pelo número/tipo dos argumentos.

### Sobrescrita (override)

**Mesma assinatura** de um método herdado, **redefinido na subclasse** para mudar o comportamento. Resolvida em **tempo de execução** (dinâmico). Marcada com `@Override`.

```java
class Animal { void som() { System.out.println("genérico"); } }
class Gato extends Animal {
    @Override
    void som() { System.out.println("Miau"); }   // mesma assinatura, novo corpo
}
```

### Tabela comparativa

| | Sobrecarga (overload) | Sobrescrita (override) |
|---|---|---|
| Onde | mesma classe | entre superclasse e subclasse |
| Assinatura | **diferente** (parâmetros) | **igual** |
| Quando resolve | compilação (estático) | execução (dinâmico) |
| Precisa herança? | não | sim |
| `@Override`? | não | sim (recomendado) |
| Liga-se a | polimorfismo estático | polimorfismo dinâmico |

### Erros comuns / pegadinhas

- Mudar **só o tipo de retorno** não é sobrecarga (não compila).
- Mudar a assinatura ao "sobrescrever" cria, na verdade, uma **sobrecarga** acidental (e o `@Override` daria erro de compilação — ótimo, ele te protege).
- Na AP1, a alternativa A da questão 1 dizia "sobrecarga, pois redefinem o mesmo método com assinaturas diferentes" — **contraditório de propósito**: redefinir o mesmo método é sobrescrita; assinaturas diferentes seria sobrecarga. As duas coisas juntas não existem.

---

## 1.7 Encapsulamento e Modificadores de Acesso

### 1. O que é

**Encapsulamento** é esconder os detalhes internos de um objeto e **controlar o acesso ao seu estado**. Em vez de deixar qualquer um mexer nos atributos diretamente, você os torna `private` e expõe apenas métodos controlados (getters/setters ou métodos de negócio).

### 2. Por que existe

Para proteger a **integridade do objeto**. Se qualquer parte do programa pode fazer `conta.saldo = -9999`, o objeto perde o controle das próprias regras. Encapsulando, o objeto valida tudo que entra: `sacar(valor)` pode recusar saque acima do saldo. O estado fica **consistente**.

### 3. Como funciona

1. Atributos viram `private`.
2. O acesso passa por métodos públicos que **validam**.
3. Os modificadores de acesso controlam **quem enxerga** cada membro.

### Modificadores de acesso — tabela essencial

| Modificador | Mesma classe | Mesmo pacote | Subclasse (outro pacote) | Qualquer lugar |
|---|:---:|:---:|:---:|:---:|
| `private` | ✅ | ❌ | ❌ | ❌ |
| *package-private* (sem palavra) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

### Quando usar cada um

- **`private`**: padrão para **atributos** e para métodos auxiliares internos. Use sempre que puder — é o mais seguro.
- **package-private** (não escrever modificador): para membros que só fazem sentido **dentro do mesmo pacote** (ex.: colaboração entre classes de um módulo). É o default quando você não escreve nada.
- **`protected`**: quando subclasses precisam acessar, mas o mundo externo não. Comum em frameworks e em classes-base.
- **`public`**: para a **API** da classe — métodos que outros vão usar de fora (ex.: `calcularSalario()`, um endpoint de controller).

> Regra prática: **comece tudo `private`** e só amplie o acesso quando houver necessidade real. Isso se chama "princípio do menor privilégio".

### 4. Exemplo simples (linha por linha)

```java
public class ContaBancaria {
    private double saldo;                  // 1. estado protegido

    public double getSaldo() {             // 2. leitura controlada
        return saldo;
    }

    public void depositar(double valor) {  // 3. regra de entrada
        if (valor > 0) {
            saldo += valor;
        }
    }

    public void sacar(double valor) {      // 4. valida antes de mexer
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
        }
    }
}
```

- **Linha 1:** ninguém de fora altera `saldo` diretamente.
- **Linhas 3–4:** toda mudança passa por validação. O objeto **garante** suas regras.
- Sem encapsulamento, alguém faria `conta.saldo = -50` e quebraria tudo.

### 5. Exemplo no estilo do professor

```java
public class Usuario {
    private String nomeCompleto;
    private LocalDate dataDeNascimento;

    public int calcularIdade() {
        return Period.between(dataDeNascimento, LocalDate.now()).getYears();
    }
}
```

(É praticamente a alternativa C da questão 3 da AP1.) Atributos `private`, comportamento exposto por método público com **nome significativo** — encapsulamento **e** Clean Code juntos. Esse foi o "exemplo bom" da prova.

### 6. Exemplo do mundo real

Um **caixa eletrônico**: você não abre o cofre e pega o dinheiro. Você pede pela interface (tela/teclado), e a máquina **valida** (senha, saldo, limite) antes de liberar. O cofre (estado) está encapsulado.

### 7. Erros comuns

- Tornar atributos `public` "para facilitar" — destrói o encapsulamento.
- Criar getters/setters para **tudo** sem validação (vira `public` disfarçado). Encapsular não é só pôr getter/setter; é **controlar e validar**.
- Confundir encapsulamento (esconder estado) com herança ou abstração.

### 8. Como reconhecer numa prova

- Atributos **`private`** + métodos `public` que mexem neles.
- Validação dentro de setters/métodos.
- Pergunta sobre "qual classe protege melhor seu estado".

### 9. Como o professor cobraria

> Qual classe aplica corretamente o **encapsulamento**?
> A)
> ```java
> public class Conta { public double saldo; }
> ```
> B)
> ```java
> public class Conta {
>     private double saldo;
>     public void depositar(double v){ if(v>0) saldo+=v; }
>     public double getSaldo(){ return saldo; }
> }
> ```
> C)
> ```java
> public class Conta { double saldo; void mexer(){ saldo=saldo; } }
> ```

### 10. Como responder

**Resposta: B.** O atributo é `private` e só muda por um método público que **valida** (`v>0`). O estado fica protegido e consistente.
- A) erra: `saldo` é `public` → qualquer um corrompe o estado. É o oposto de encapsulamento.
- C) erra: `saldo` é package-private e o método não valida nada; não há proteção real.

### 11. Resumo relâmpago

Encapsular = atributos `private` + acesso controlado/validado por métodos. Comece `private`, amplie só se precisar. Ordem de abertura: `private` < package-private < `protected` < `public`.

---

## Exercícios — Parte 1 (POO)

### Objetivas

**1.** `class B extends A` significa que:
A) B é uma interface de A  B) B "é um" A e herda seus membros  C) A herda de B  D) B copia A sem vínculo

**2.** Java permite que uma classe:
A) estenda várias classes  B) implemente várias interfaces  C) estenda várias interfaces com `implements`  D) instancie interfaces com `new`

**3.** `Funcionario f = new Gerente(); f.calcularSalario();` executando o método do `Gerente` é exemplo de:
A) sobrecarga  B) composição  C) polimorfismo dinâmico  D) encapsulamento

**4.** Sobrecarga se caracteriza por:
A) mesma assinatura na subclasse  B) mesmo nome, parâmetros diferentes, mesma classe  C) `@Override` obrigatório  D) resolução em tempo de execução

**5.** O modificador que permite acesso na mesma classe, no mesmo pacote e em subclasses (inclusive de outro pacote), mas não em qualquer lugar, é:
A) `private`  B) `public`  C) `protected`  D) package-private

**6.** Instanciar `new Forma()`, sendo `Forma` uma interface:
A) compila  B) gera erro: interface não pode ser instanciada  C) cria objeto vazio  D) só funciona com `abstract`

**7.** "Atributos `private` acessados por métodos `public` que validam dados" descreve:
A) herança  B) polimorfismo  C) encapsulamento  D) sobrecarga

**8.** Em `Super x = new Sub();`, o que o **compilador** permite chamar é decidido pelo:
A) tipo do objeto  B) tipo da referência (`Super`)  C) pacote  D) construtor

**9.** Qual relação justifica herança (`extends`)?
A) "tem um"  B) "usa um"  C) "é um"  D) "depende de"

**10.** Sobrescrita (`@Override`) é resolvida em:
A) compilação  B) execução  C) linkedição  D) nunca

### Discursivas

**D1.** Explique, com suas palavras e um exemplo curto, a diferença entre **sobrecarga** e **sobrescrita**, indicando quando cada uma é resolvida (compilação vs execução).

**D2.** Refatore a classa abaixo aplicando **encapsulamento** adequado, justificando cada mudança:
```java
public class Produto {
    public String nome;
    public double preco;
}
```

> **Gabarito Parte 1:** 1-B, 2-B, 3-C, 4-B, 5-C, 6-B, 7-C, 8-B, 9-C, 10-B.
> **D2 esperado:** tornar `nome` e `preco` `private`; criar getters; criar setter de `preco` que rejeite valores negativos (`if (preco >= 0)`); assim o objeto garante a regra "preço não-negativo". Sem isso, qualquer código poderia fazer `produto.preco = -10`.


---

# PARTE 2 — Exceptions (Tratamento de Exceções)

## 2.1 Visão geral

### 1. O que é

Uma **exceção** (*exception*) é um objeto que representa um **erro ou situação anormal** que acontece durante a execução do programa: divisão por zero, arquivo inexistente, conexão de banco que caiu, índice fora do array. Quando algo dá errado, o método "**lança**" (`throw`) uma exceção, e o fluxo normal é interrompido até alguém "**capturar**" (`catch`) esse erro.

### 2. Por que existe

Para **separar o código que faz o trabalho do código que trata erros**, e para impedir que o programa simplesmente quebre (*crash*). Sem exceptions, você teria que checar códigos de erro a cada linha (`if (resultado == -1) ...`), poluindo tudo. Com exceptions, o caminho feliz fica limpo e os erros são tratados em blocos próprios.

### 3. Como funciona — a hierarquia

```
            Throwable
           /         \
        Error       Exception
                    /         \
        (checked: IOException,   RuntimeException
         SQLException, etc.)     (unchecked: NullPointer,
                                  ArithmeticException,
                                  ArrayIndexOutOfBounds...)
```

- **`Throwable`**: raiz de tudo que pode ser lançado.
- **`Error`**: problemas graves da JVM (ex.: `OutOfMemoryError`). **Não** se trata normalmente.
- **`Exception`**: erros que o programa pode/deve tratar. Divide-se em:
  - **Checked (verificadas):** descendem de `Exception` mas **não** de `RuntimeException`. O compilador **obriga** a tratar (com `try/catch`) ou declarar (`throws`). Ex.: `IOException`, `SQLException`.
  - **Unchecked (não verificadas):** descendem de `RuntimeException`. O compilador **não** obriga. Geralmente indicam **erro de programação**. Ex.: `ArithmeticException`, `NullPointerException`, `ArrayIndexOutOfBoundsException`.

### Tabela Checked vs Unchecked

| | Checked | Unchecked (RuntimeException) |
|---|---|---|
| Herda de | `Exception` (não Runtime) | `RuntimeException` |
| Compilador obriga tratar? | **Sim** | Não |
| Tipicamente indica | condição externa (I/O, BD) | erro de lógica/programação |
| Exemplos | `IOException`, `SQLException` | `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException` |
| Como lidar | `try/catch` ou `throws` | corrigir o código (ou tratar se fizer sentido) |

## 2.2 As palavras-chave

- **`try`**: delimita o bloco onde um erro **pode** acontecer.
- **`catch`**: captura e trata um tipo específico de exceção. Pode haver vários `catch`.
- **`finally`**: bloco que executa **sempre** — com ou sem exceção. Usado para liberar recursos (fechar conexão, arquivo).
- **`throw`**: **lança** uma exceção manualmente (`throw new IllegalArgumentException("msg")`).
- **`throws`**: na **assinatura** do método, **declara** que ele pode lançar tal exceção (repassa a responsabilidade para quem chama).

> Não confunda **`throw`** (ação: lançar agora, dentro do corpo) com **`throws`** (declaração: "este método pode lançar", na assinatura). O `s` muda tudo.

### Exemplo linha por linha

```java
public class LeitorArquivo {
    public String ler(String caminho) throws IOException {   // 1. declara que pode lançar
        BufferedReader r = null;
        try {                                                // 2. zona de risco
            r = new BufferedReader(new FileReader(caminho));
            return r.readLine();                             // pode lançar IOException
        } catch (FileNotFoundException e) {                  // 3. trata caso específico
            System.out.println("Arquivo não encontrado: " + caminho);
            return null;
        } finally {                                          // 4. SEMPRE executa
            if (r != null) r.close();                        // libera o recurso
        }
    }
}
```

- **Linha 1:** `throws IOException` avisa: "quem me chamar precisa tratar".
- **Linha 2:** o que pode falhar fica dentro do `try`.
- **Linha 3:** se o arquivo não existe, tratamos com mensagem amigável.
- **Linha 4:** `finally` fecha o arquivo independentemente do que aconteceu.

## 2.3 Exemplo no estilo do professor (questões 7 da AP1)

A AP1 trouxe **duas** versões da questão 7. Uma objetiva e uma discursiva, ambas sobre `Calculadora`.

**Versão discursiva (questão 7, 2,0 pts):** o código abaixo quebra ao dividir por zero. Reescreva tratando a exceção:

```java
// ANTES (quebra com ArithmeticException)
public class Calculadora {
    public static int dividir(int a, int b) {
        return a / b;
    }
    public static void main(String[] args) {
        int resultado = dividir(10, 0);                 // estoura aqui
        System.out.println("Resultado: " + resultado);
    }
}
```

**DEPOIS (resposta esperada):**

```java
public class Calculadora {
    public static int dividir(int numero, int divisor) {
        try {
            return numero / divisor;
        } catch (ArithmeticException e) {
            System.out.println("Erro! Divisão por 0");
            return 0;   // valor de fallback / ou relançar tratado
        }
    }
    public static void main(String[] args) {
        int resultado = dividir(10, 0);
        System.out.println("Resultado: " + resultado);
    }
}
```

Pontos que o professor valoriza nessa refatoração:
- Envolver a operação de risco no `try`.
- Capturar o tipo **correto e específico**: `ArithmeticException` (não `Exception` genérico).
- Exibir **mensagem apropriada ao usuário** (o enunciado pede isso explicitamente).
- Tratar de forma que o programa **continue** em vez de quebrar.

> A imagem manuscrita da AP1 mostra exatamente essa solução, com `catch (ArithmeticException e)` e a mensagem `"Erro! divisão por 0"`. Era isso que valia ponto.

## 2.4 Exemplo do mundo real

Dirigir um carro: o **airbag** é o `catch`. No fluxo normal você dirige (try). Se ocorre o "erro" (batida), o airbag dispara (catch) para tratar a situação sem que tudo seja destruído. O `finally` é como o cinto: atua sempre, batida ou não.

## 2.5 Erros comuns

- Capturar `Exception` genérico quando dá para capturar o específico (esconde bugs).
- Bloco `catch` **vazio** (engole o erro silenciosamente) — péssima prática.
- Confundir `throw` e `throws`.
- Achar que `finally` não roda quando há `return` no `try` — **roda sim** (antes de o método retornar de fato).
- Tentar tratar `RuntimeException` (como `NullPointer`) com `try/catch` em vez de **corrigir** a causa.

## 2.6 Como reconhecer numa prova

- Blocos `try`/`catch`/`finally`.
- `throw new XxxException(...)` no corpo → lançando.
- `throws XxxException` na assinatura → declarando.
- Operação `a / b` com `b` possivelmente 0 → cobrança de `ArithmeticException`.
- Acesso a objeto possivelmente nulo → `NullPointerException`.
- Acesso a índice de array/list → `ArrayIndexOutOfBoundsException`.

## 2.7 Como o professor cobraria (objetiva)

> ```java
> public static int dividir(int a, int b) {
>     return a / b;
> }
> // chamada:
> System.out.println(dividir(10, 0));
> ```
> O que ocorre ao executar?
> A) imprime 0
> B) imprime infinito
> C) lança `ArithmeticException` em tempo de execução (unchecked)
> D) erro de compilação
> E) lança `IOException`

## 2.8 Como responder

**Resposta: C.** Divisão **inteira** por zero lança `ArithmeticException`, que é **unchecked** (`RuntimeException`) — por isso compila normalmente e só quebra **em execução**.
- A) erra: não retorna 0; estoura antes.
- B) erra: "infinito" só acontece com **ponto flutuante** (`10.0/0.0` dá `Infinity`); com `int`, é exceção.
- D) erra: compila — o erro é em runtime.
- E) erra: `IOException` é de entrada/saída, sem relação aqui.

> **Pegadinha de ouro:** `int / 0` → exceção; `double / 0.0` → `Infinity` (sem exceção). O professor pode explorar essa diferença.

## 2.9 Resumo relâmpago

Exception = objeto de erro. Checked: compilador obriga tratar. Unchecked (Runtime): não obriga. `try` arrisca, `catch` trata, `finally` sempre roda, `throw` lança, `throws` declara. `int/0` → `ArithmeticException`.

## Exercícios — Parte 2

### Objetivas

**1.** `ArithmeticException` é:
A) checked  B) unchecked (RuntimeException)  C) um Error  D) uma interface

**2.** O bloco que executa sempre, com ou sem exceção, é:
A) try  B) catch  C) finally  D) throws

**3.** `throws` na assinatura do método serve para:
A) lançar a exceção imediatamente  B) declarar que o método pode lançar tal exceção  C) capturar  D) ignorar erros

**4.** Exceção **checked** obriga o programador a:
A) nada  B) tratar com try/catch ou declarar com throws  C) reiniciar a JVM  D) usar `finally`

**5.** `10.0 / 0.0` em Java resulta em:
A) ArithmeticException  B) 0  C) Infinity  D) erro de compilação

**6.** `NullPointerException` ocorre quando:
A) divide por zero  B) acessa membro de referência nula  C) índice inválido  D) arquivo não existe

**7.** Capturar `Exception` genérico em vez do tipo específico é considerado:
A) boa prática sempre  B) má prática, pois esconde erros distintos  C) obrigatório  D) impossível

**8.** `throw` (sem s) é usado para:
A) declarar exceções na assinatura  B) lançar uma exceção manualmente no corpo  C) capturar  D) finalizar recurso

**9.** A raiz de toda a hierarquia de exceções é:
A) Exception  B) Error  C) Throwable  D) RuntimeException

**10.** Um `catch` vazio (`catch(Exception e){}`):
A) é recomendado  B) engole o erro silenciosamente (má prática)  C) não compila  D) relança automaticamente

### Discursivas

**D1.** Reescreva, usando tratamento de exceções, o método que lê o elemento de índice `i` de um array e trate o caso de índice inválido com mensagem apropriada.

**D2.** Explique a diferença entre exceções **checked** e **unchecked**, dando um exemplo de cada e justificando por que o compilador trata cada uma de forma diferente.

> **Gabarito Parte 2:** 1-B, 2-C, 3-B, 4-B, 5-C, 6-B, 7-B, 8-B, 9-C, 10-B.


---

# PARTE 3 — Java Code Conventions

## 1. O que é

As **Java Code Conventions** são um conjunto de **regras de estilo** oficiais (originalmente publicadas pela Sun/Oracle) sobre **como escrever** código Java: como nomear coisas, como indentar, onde pôr chaves, como organizar o arquivo. Não mudam o que o programa **faz**; mudam o quanto ele é **legível e padronizado**.

## 2. Por que existe

Código é lido **muito** mais vezes do que é escrito, e quase sempre por **outras pessoas** (e pelo seu "eu" do futuro). Se cada um nomeasse e indentasse do seu jeito, ler o código de um colega seria um inferno. As convenções criam uma "língua comum": qualquer dev Java reconhece na hora o que é classe, o que é constante, o que é variável.

## 3. As regras principais (as que caem)

### Nomenclatura

| Elemento | Convenção | Exemplo |
|---|---|---|
| Classe / Interface | `PascalCase` (substantivo) | `ContaBancaria`, `Pagavel` |
| Método | `camelCase` (verbo) | `calcularSalario()`, `enviarEmail()` |
| Variável / atributo | `camelCase` | `saldoAtual`, `nomeCompleto` |
| Constante (`static final`) | `UPPER_SNAKE_CASE` | `TAXA_JUROS`, `MAX_ALUNOS` |
| Pacote | tudo minúsculo | `com.ibmec.projeto` |

### Organização e formatação

- **Indentação** consistente (4 espaços é o padrão clássico).
- **Chave de abertura** na mesma linha da declaração: `if (x) {`.
- Um **statement por linha**.
- Nomes em **inglês ou português**, mas **consistentes** no projeto.
- Métodos e classes com **uma responsabilidade clara** (conecta com Clean Code/SRP).
- Espaçamento ao redor de operadores: `a + b`, não `a+b`.

## 4. Exemplo simples (antes/depois)

```java
// FORA do padrão
public class contaBancaria{
private double Saldo;
public void Depositar(double V){if(V>0)Saldo+=V;}}
```

```java
// DENTRO do padrão
public class ContaBancaria {
    private double saldo;

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }
}
```

Mudanças: classe em `PascalCase`; atributo `saldo` em `camelCase` minúsculo; método `depositar` (verbo, camelCase); indentação; chaves e espaçamento corretos.

## 5. Exemplo no estilo do professor

Direto da questão 3 da AP1: comparar nomes. `public class C { int x; void m(){...} }` (nomes sem significado: `C`, `x`, `m`) **viola** as convenções de nomes significativos; já `Usuario` com `nomeCompleto`, `dataDeNascimento` e `calcularIdade()` **segue**.

## 6. Exemplo do mundo real

Convenções de trânsito: vermelho = pare, em qualquer cidade. Você não precisa "pensar" — o padrão é universal. Code conventions fazem o mesmo pelo código.

## 7. Erros comuns

- Classe em minúsculo (`conta`) ou método em PascalCase (`Calcular()`).
- Constante em camelCase em vez de `UPPER_SNAKE_CASE`.
- Misturar idiomas e estilos no mesmo projeto.
- Nomes de uma letra (`x`, `a`, `tmp`) fora de laços simples.

## 8. Como reconhecer numa prova

- O professor mostra trechos e pergunta **qual segue as convenções / nomes significativos**.
- Procure: classe PascalCase, método/variável camelCase, constante UPPER_SNAKE, nome que **revela intenção**.

## 9–10. Questão e resposta

> Qual declaração segue as Java Code Conventions?
> A) `class banco { ... }`
> B) `public class ContaBancaria { private double saldoAtual; }`
> C) `public class Conta { double Saldo; void Calcular(){} }`
> D) `public class conta { final int taxa = 5; }`

**Resposta: B.** Classe em PascalCase, atributo em camelCase, `private`. As demais erram: A (classe minúscula), C (`Saldo`/`Calcular` capitalizados errado), D (classe minúscula e constante deveria ser `TAXA` em UPPER_SNAKE).

## 11. Resumo relâmpago

Classe = PascalCase; método/variável = camelCase; constante = UPPER_SNAKE; pacote = minúsculo. Indentação e espaçamento consistentes.

---

# PARTE 4 — Checkstyle

## 1. O que é

**Checkstyle** é uma **ferramenta automática** que lê seu código-fonte Java e **verifica se ele segue um conjunto de regras de estilo** (geralmente baseadas nas Java Code Conventions ou em guias como o do Google). Ele aponta violações como um "professor robô de estilo".

## 2. Por que existe

Para **automatizar** a checagem de convenções. Ninguém quer revisar manualmente se cada nome está em camelCase. O Checkstyle roda no build (ou na IDE) e marca tudo que está fora do padrão, garantindo consistência no time inteiro **sem depender de boa vontade**.

## 3. Como funciona

1. Você define um arquivo de configuração XML com as regras (ex.: `checkstyle.xml`).
2. O Checkstyle analisa cada `.java` e gera uma lista de **violações** (arquivo, linha, regra).
3. Pode ser integrado ao Maven/Gradle, à IDE ou ao CI; o build pode **falhar** se houver violações.

## 4. O que ele tipicamente verifica

- **Nomenclatura**: classes em PascalCase, métodos/variáveis em camelCase, constantes em UPPER_SNAKE.
- **Tamanho**: linhas longas demais, métodos longos demais, classes grandes demais.
- **Formatação**: indentação, espaços, chaves obrigatórias em `if`/`for`.
- **Imports**: imports não usados, `import *` (com curinga) proibido.
- **Javadoc**: presença de comentários de documentação em métodos públicos (quando configurado).
- **Estrutura**: chaves obrigatórias mesmo em `if` de uma linha; evitar números mágicos.

## 5. Erros comuns que o Checkstyle acusa

- `import java.util.*;` (curinga) → preferir imports específicos.
- Linha com mais de 80/100/120 caracteres (depende da config).
- Falta de chaves: `if (x) fazer();` sem `{}`.
- Nome fora do padrão.
- Método/arquivo grande demais.
- Espaço faltando ou sobrando (`if(x)` em vez de `if (x)`).

## 6. Analogia do mundo real

Checkstyle é o **corretor ortográfico** do código: ele não julga se a "história" (lógica) é boa, mas marca toda "palavra escrita errada" (violação de estilo) automaticamente.

## 7. Como reconhecer numa prova

- Menção a "ferramenta que verifica o estilo", "regras de formatação automáticas", "violação de convenção".
- Trechos com problema de estilo perguntando "qual regra de Checkstyle é violada".

## 8–9. Questão estilo professor

> Um time configurou o Checkstyle. Qual trecho **mais provavelmente** gera violação?
> A) `private int idadeUsuario;`
> B) `import java.util.*;`
> C) `public class Pedido {`
> D) `static final int MAX = 10;`

**Resposta: B.** Imports com curinga (`*`) são uma violação clássica do Checkstyle (regra `AvoidStarImport`). As demais seguem o padrão.

## 10. Resumo relâmpago

Checkstyle = ferramenta que **automatiza** a verificação de estilo/convenções. Acusa nomes fora do padrão, imports curinga, linhas longas, falta de chaves, métodos grandes.

## Exercícios — Partes 3 e 4

### Objetivas

**1.** Constantes em Java devem ser nomeadas em:
A) camelCase  B) PascalCase  C) UPPER_SNAKE_CASE  D) minúsculo

**2.** Classes seguem:
A) camelCase  B) PascalCase  C) UPPER_SNAKE  D) kebab-case

**3.** Checkstyle serve para:
A) compilar  B) verificar estilo/convenções automaticamente  C) rodar testes  D) gerar banco

**4.** `import java.util.*;` costuma ser:
A) recomendado  B) violação (star import)  C) obrigatório  D) sintaxe inválida

**5.** Métodos devem ser nomeados como:
A) substantivo PascalCase  B) verbo camelCase  C) UPPER_SNAKE  D) tudo minúsculo sem separação

**6.** Pacotes em Java:
A) PascalCase  B) UPPER_SNAKE  C) tudo minúsculo  D) camelCase

**7.** O nome `int x;` para "idade do aluno" viola principalmente:
A) sintaxe  B) nomes significativos / convenções  C) encapsulamento  D) herança

**8.** Checkstyle normalmente é integrado a:
A) apenas Word  B) build (Maven/Gradle), IDE, CI  C) banco de dados  D) navegador

**9.** Espaçamento `if(x){` em vez de `if (x) {`:
A) é erro de compilação  B) pode ser acusado pelo Checkstyle como violação de formatação  C) é obrigatório  D) melhora performance

**10.** As Java Code Conventions impactam principalmente:
A) a performance  B) a legibilidade e padronização  C) o uso de memória  D) o resultado dos cálculos

### Discursivas

**D1.** Liste cinco violações de convenção/Checkstyle no trecho abaixo e reescreva-o corrigido:
```java
public class conta{double Saldo;public void Depositar(double V){if(V>0)Saldo=Saldo+V;}}
```

**D2.** Explique a diferença entre **Java Code Conventions** (o "o quê") e **Checkstyle** (o "como verificar"), e por que usar a ferramenta no time é melhor do que confiar na disciplina individual.

> **Gabarito Partes 3-4:** 1-C, 2-B, 3-B, 4-B, 5-B, 6-C, 7-B, 8-B, 9-B, 10-B.
> **D1 (correções):** classe `conta`→`Conta`; atributo `Saldo`→`saldo`; método `Depositar`→`depositar`; parâmetro `V`→`valor`; faltam chaves no `if` e indentação/espaçamento. Versão corrigida idêntica ao "depois" da Parte 3.


---

# PARTE 5 — Clean Code

Clean Code (de Robert C. Martin, o "Uncle Bob") é um conjunto de princípios para escrever código **legível, simples e fácil de manter**. Na AP1, **três questões** (2, 3 e 4) foram puro Clean Code. É um dos temas mais cobrados.

## 5.1 Nomes significativos

### 1. O que é

Dar a variáveis, métodos e classes nomes que **revelam a intenção**: o que aquilo é ou faz, sem precisar de comentário para explicar.

### 2. Por que existe

Nomes ruins (`x`, `tmp`, `dados`, `m()`) forçam o leitor a "decifrar" o código. Bons nomes fazem o código se **autoexplicar**, reduzindo erros e tempo de leitura.

### 3/4. Antes e depois

```java
// RUIM
public class C {
    int x;
    void m() { x = x + 1; }
}
```

```java
// BOM
public class Contador {
    private int total;
    public void incrementar() { total = total + 1; }
}
```

`Contador`, `total`, `incrementar()` dizem **o que** é e **o que faz**. `C`, `x`, `m` não dizem nada.

### 5. Estilo do professor (questão 3 da AP1)

A questão 3 pediu a classe com **nome mais alinhado às boas práticas de nomes significativos**. As alternativas ruins eram `class C { int x; void m(){...} }` e `class Dados { int a; int b; int c; }`. A boa era a `class Usuario` com `nomeCompleto`, `dataDeNascimento` e `calcularIdade()` — nomes que revelam intenção.

### 7. Erros comuns

- Nomes genéricos: `dados`, `info`, `valor`, `processar()`, `doIt()`, `Xpto`, `valorzinho`.
- Abreviações obscuras: `qtd`, `usr`, `calcSal`.
- Nomes que **mentem** (chamar de `lista` algo que é um único objeto).

### 8. Como reconhecer

Procure classes chamadas `C`, `Dados`, `Xpto`, `DataManager`; variáveis `x`, `a`, `valorzinho`; métodos `m()`, `doIt()`, `process()`. São red flags de **nome ruim**.

---

## 5.2 Funções pequenas e responsabilidade única

### 1. O que é

Uma função (método) deve ser **pequena** e fazer **uma única coisa**. Se você precisa de "e" para descrever o que ela faz ("gera o relatório **e** salva **e** envia"), ela faz demais.

### 3/4. Antes e depois

```java
// RUIM: uma função faz tudo
void processarPedido(Pedido p) {
    // valida
    if (p.getItens().isEmpty()) throw new RuntimeException();
    // calcula total
    double total = 0;
    for (Item i : p.getItens()) total += i.getPreco();
    // salva no banco
    banco.salvar(p);
    // envia email
    email.enviar(p.getCliente(), "Pedido confirmado");
}
```

```java
// BOM: cada função uma responsabilidade
void processarPedido(Pedido p) {
    validar(p);
    double total = calcularTotal(p);
    salvar(p);
    notificarCliente(p);
}
```

A versão boa **lê como um texto**: valida, calcula, salva, notifica. Cada passo é um método pequeno.

### 6. Analogia

Uma receita de bolo não mistura "bater os ovos" com "limpar a cozinha" e "pagar as contas" no mesmo passo. Cada passo, uma ação clara.

---

## 5.3 Comentários: bons e ruins

### 1. O que é

Clean Code prega: **o melhor comentário é o que você não precisou escrever** porque o código já se explica. Comentários existem para explicar o **porquê** (intenção, decisão), nunca o **o quê** óbvio.

### Comentário RUIM (óbvio / redundante)

```java
int total = lista.size();   // pega o tamanho da lista   <- inútil
int status = 2;             // 2 significa "pago"         <- esconde número mágico
// método que soma dois números
int somar(int a, int b) { return a + b; }                 // redundante
```

Esses comentários só repetem o que o código já diz. Pior: `// 2 significa "pago"` revela um **número mágico** — o certo seria um `enum` ou constante `PAGO`.

### Comentário BOM (explica o porquê / aviso importante)

```java
try {
    pagamento.processar();
} catch (GatewayException e) {
    // O gateway lança E123 quando a sessão expira; capturamos para reabrir
    // a sessão conforme manual do fornecedor.
    reabrirSessao();
}
```

Esse comentário explica **algo que o código não consegue dizer sozinho**: um comportamento externo (regra do fornecedor) que justifica a decisão. É **necessário** e agrega valor.

### 5. Estilo do professor (questão 2 da AP1)

A questão 2 pediu o comentário **necessário e de acordo com Clean Code**. As opções A, B, C, E eram comentários **óbvios/redundantes** (`// pega o tamanho da lista`, `// divide a soma...`, `// 2 significa pago`, `// método que soma dois números`). A correta (marcada na prova) foi a do **`try/catch` do gateway**, porque explica o **porquê** de uma decisão não óbvia.

### 7. Erros comuns

- Comentar o óbvio (`i++; // incrementa i`).
- Usar comentário para "consertar" nome ruim em vez de **renomear**.
- Comentários desatualizados que mentem sobre o código.
- `// TODO` esquecidos (veja Regra do Escoteiro).

---

## 5.4 Código autoexplicativo e duplicação (DRY)

### Autoexplicativo

Código autoexplicativo dispensa comentário porque os **nomes** e a **estrutura** já contam a história. Compare:

```java
if (d > 30 && s == 1) { ... }                    // ??? precisa adivinhar
if (diasAtraso > 30 && status == ATIVO) { ... }  // claro sozinho
```

### Duplicação (DRY — Don't Repeat Yourself)

Código repetido é perigoso: se a regra muda, você tem que lembrar de mudar em **todos** os lugares. Extraia para um método único.

```java
// RUIM: regra de desconto repetida
double precoA = valorA - (valorA * 0.1);
double precoB = valorB - (valorB * 0.1);

// BOM
double aplicarDesconto(double valor) { return valor - (valor * 0.1); }
double precoA = aplicarDesconto(valorA);
double precoB = aplicarDesconto(valorB);
```

---

## 5.5 Regra do Escoteiro e Metáfora do Jornal

### Regra do Escoteiro (*Boy Scout Rule*)

> "Deixe o acampamento mais limpo do que você encontrou."

Sempre que mexer num código, faça uma **pequena melhoria**: renomear uma variável confusa, remover um `// TODO` resolvido, apagar código morto. O código melhora aos poucos, continuamente.

### Metáfora do Jornal (*Newspaper Metaphor*)

Um arquivo de código deve ser lido como uma **notícia de jornal**: o topo traz o **mais importante e geral** (a manchete: o nome da classe, os métodos públicos principais); conforme você desce, vêm os **detalhes** (métodos auxiliares privados, implementação). Você entende o geral sem precisar ler tudo.

### 5. Estilo do professor (questão 4 da AP1)

A questão 4 mostrou a classe `Relatorio` com um método `gerar()` cheio de detalhes e um `// TODO: ajustar formatação depois`. Perguntou quais práticas/metáforas se relacionam à melhoria. A resposta correta foi **"Regra do escoteiro e Nomes significativos"**:
- **Regra do escoteiro**, porque há um `// TODO` pendente que deveria ser resolvido/limpo ao mexer no código.
- **Nomes significativos**, porque variáveis como `a`, `b`, `c` deveriam ter nomes que revelam intenção.

As outras misturavam práticas que **não** se aplicavam diretamente ao trecho (ex.: "Metáfora do jornal", "Comentários", "Funções pequenas" não eram o foco daquele código específico).

---

## 5.6 Como reconhecer Clean Code numa prova

| Pista no código | Conceito |
|---|---|
| `class C`, `int x`, `void m()`, `Xpto`, `valorzinho`, `DataManager`, `info` | **nome ruim** (viola nomes significativos) |
| `Usuario`, `nomeCompleto`, `calcularIdade()` | **nome bom** |
| `// pega o tamanho da lista`, `// soma dois números` | **comentário ruim** (óbvio) |
| `// gateway lança E123 quando a sessão expira` | **comentário bom** (porquê não óbvio) |
| `// 2 significa "pago"` | número mágico → use constante/enum |
| `// TODO: ...` deixado no código | viola **Regra do Escoteiro** |
| função que "faz isso E aquilo E aquilo" | viola **funções pequenas / SRP** |
| mesma fórmula repetida | viola **DRY** |

## 5.7 Resumo relâmpago

Nomes revelam intenção. Funções pequenas, uma responsabilidade. Comentário bom explica o **porquê**, não o óbvio. Código autoexplicativo > comentário. DRY: não repita. Regra do escoteiro: melhore ao passar. Jornal: geral no topo, detalhe embaixo.

## Exercícios — Parte 5 (Clean Code)

### Objetivas

**1.** Qual é um comentário **necessário** (bom)?
A) `i++; // incrementa i`  B) `int total = lista.size(); // tamanho da lista`  C) `// workaround: API externa retorna data em UTC, convertemos para local`  D) `int x = 2; // dois`

**2.** O melhor caminho para um nome ruim de variável é:
A) adicionar um comentário explicando  B) renomear para algo significativo  C) deixar como está  D) usar UPPER_CASE

**3.** `class Dados { int a; int b; int c; }` viola principalmente:
A) encapsulamento  B) nomes significativos  C) herança  D) exceptions

**4.** DRY significa:
A) escreva tudo duas vezes  B) não repita a mesma lógica  C) documente tudo  D) delete tudo

**5.** A Regra do Escoteiro recomenda:
A) reescrever o sistema do zero  B) deixar o código um pouco melhor a cada passagem  C) nunca mexer em código antigo  D) comentar cada linha

**6.** A Metáfora do Jornal diz que:
A) o detalhe vem primeiro  B) o mais geral/importante fica no topo, detalhes abaixo  C) tudo deve ser comentado  D) classes devem ser enormes

**7.** Função que "gera, salva e envia" relatório viola:
A) DRY  B) funções pequenas / responsabilidade única  C) nomes  D) Checkstyle

**8.** Comentário `// 2 significa "pago"` indica problema de:
A) número mágico (devia ser constante/enum)  B) herança  C) polimorfismo  D) finally

**9.** Código autoexplicativo se baseia principalmente em:
A) muitos comentários  B) nomes e estrutura claros  C) UPPER_CASE  D) try/catch

**10.** A classe `Usuario` com `nomeCompleto` e `calcularIdade()` é exemplo de:
A) nome ruim  B) nome significativo  C) duplicação  D) número mágico

### Discursivas

**D1.** Refatore aplicando Clean Code (nomes + remover comentário óbvio):
```java
public class P {
    int v; // valor
    void d() { v = v - (v * 0.1); } // aplica desconto de 10%
}
```

**D2.** Explique a diferença entre um comentário "bom" e um "ruim" segundo o Clean Code, usando os exemplos da questão 2 da AP1 (lista.size, gateway/sessão).

> **Gabarito Parte 5:** 1-C, 2-B, 3-B, 4-B, 5-B, 6-B, 7-B, 8-A, 9-B, 10-B.


---

# PARTE 6 — SOLID

SOLID são **cinco princípios de design orientado a objetos** que tornam o código flexível, testável e fácil de manter. O professor cobrou SOLID **três vezes** na AP1 (questões 5 = SRP, 6 = OCP, 8 = ISP) e usa exatamente os exemplos clássicos. Domine cada um com os exemplos dele.

| Letra | Princípio | Frase-chave |
|---|---|---|
| **S** | Single Responsibility | uma classe, **um motivo** para mudar |
| **O** | Open/Closed | **aberto** para extensão, **fechado** para modificação |
| **L** | Liskov Substitution | subclasse deve **substituir** a mãe sem quebrar |
| **I** | Interface Segregation | interfaces **pequenas e específicas** |
| **D** | Dependency Inversion | dependa de **abstrações**, não de implementações |

---

## 6.1 SRP — Single Responsibility Principle

### 1. O que é

Uma classe deve ter **uma única responsabilidade**, ou seja, **um único motivo para mudar**. Se uma classe faz coisas que mudam por razões diferentes, ela viola o SRP.

### 2. Por que existe

Quando uma classe faz muitas coisas, qualquer mudança em uma delas arrisca quebrar as outras, e a classe vira um "monstro" difícil de testar e entender. Separar responsabilidades isola o impacto das mudanças.

### 3. Exemplo ruim (o do professor — questão 5 da AP1)

```java
public class RelatorioAluno {
    public void gerarRelatorio() {
        System.out.println("Relatório gerado...");
    }
    public void salvarEmArquivo() {
        System.out.println("Relatório salvo em arquivo...");
    }
    public void enviarPorEmail() {
        System.out.println("Relatório enviado por e-mail...");
    }
}
```

Essa classe tem **três motivos para mudar**: regra do relatório, forma de salvar (arquivo/banco/nuvem) e forma de enviar (email/SMS). Três responsabilidades numa classe só → **viola SRP**.

### 4. Exemplo bom (refatoração esperada)

```java
public class RelatorioAluno {
    public void gerar() { System.out.println("Relatório gerado..."); }
}

public class RelatorioArquivoSalvador {
    public void salvar(RelatorioAluno r) { System.out.println("Salvo em arquivo..."); }
}

public class RelatorioEmailEnviador {
    public void enviar(RelatorioAluno r) { System.out.println("Enviado por e-mail..."); }
}
```

Cada classe tem **uma** responsabilidade. Mudar a forma de envio não toca na geração.

### 5. Analogia

Um **canivete suíço** que também é furadeira, liquidificador e secador de cabelo: quando um quebra, mexer pra consertar arrisca quebrar os outros. Melhor ter ferramentas separadas, cada uma boa no seu trabalho.

### 6. Como reconhecer na prova

- Uma classe com métodos de **assuntos diferentes**: gerar + salvar + enviar; cadastrar + validar + imprimir + persistir.
- Nome de classe genérico que "centraliza" tudo (`Gerenciador`, `Util`, `Manager`).
- Enunciado citando "alterações em funcionalidades **distintas** exigiam mudanças na **mesma** classe" (texto literal da questão 5).

### 7. Erros comuns

- Confundir SRP com "um método por classe" (não é isso; é **um motivo para mudar**).
- Escolher "criar subclasses para cada método" como solução (errado — não separa responsabilidades, e foi um distrator da prova).
- Achar que tornar métodos estáticos resolve (não resolve — outro distrator).

### 8/9. Questão estilo professor (= questão 5 da AP1)

> A classe `RelatorioAluno` gera, salva e envia relatórios. Manutenções em funcionalidades distintas exigem mudar a mesma classe. Pela SRP, a melhor refatoração é:
> A) Criar subclasses para cada método.
> B) Separar as funcionalidades em classes distintas responsáveis por gerar, salvar e enviar.
> C) Tornar todos os métodos estáticos.
> D) Adicionar novos métodos à mesma classe.
> E) Criar uma única interface com todos os métodos e manter a implementação na mesma classe.

### 10. Como responder

**Resposta: B.** SRP = um motivo para mudar. Separar em classes distintas (gerar / salvar / enviar) dá a cada uma uma única responsabilidade.
- A) erra: subclasses por método não separam responsabilidades; herança não é a ferramenta aqui.
- C) erra: tornar estático muda escopo, não responsabilidade — continua tudo na mesma classe.
- D) erra: adicionar mais métodos **piora** o acúmulo de responsabilidades.
- E) erra: a interface com tudo e a implementação ainda concentrada na mesma classe **não** separa nada (e fere também o ISP).

### 11. Resumo SRP

Uma classe, um motivo para mudar. Sintoma: gera + salva + envia juntos. Cura: classes separadas.

---

## 6.2 OCP — Open/Closed Principle

### 1. O que é

Uma classe deve estar **aberta para extensão** (você consegue adicionar comportamento novo) e **fechada para modificação** (sem precisar alterar o código já existente e testado).

### 2. Por que existe

Toda vez que você **modifica** código que já funciona, arrisca introduzir bugs no que estava OK. O ideal é **adicionar** o novo sem **mexer** no antigo. Conecta direto com polimorfismo e com o padrão **Strategy**.

### 3. Exemplo ruim (o do professor — questão 6 da AP1)

```java
public double calcular(String tipoCliente, double valor) {
    if (tipoCliente.equals("VIP")) {
        return valor * 0.8;
    } else if (tipoCliente.equals("REGULAR")) {
        return valor * 0.9;
    }
    return valor;
}
```

Cada novo tipo de cliente exige **adicionar um `if`** e **modificar** este método → viola OCP.

### 4. Exemplo bom (estratégias de desconto)

```java
interface Desconto {
    double aplicar(double valor);
}

class DescontoVip implements Desconto {
    public double aplicar(double valor) { return valor * 0.8; }
}
class DescontoRegular implements Desconto {
    public double aplicar(double valor) { return valor * 0.9; }
}
class SemDesconto implements Desconto {
    public double aplicar(double valor) { return valor; }
}

// uso: para um novo tipo, basta CRIAR uma nova classe Desconto.
public double calcular(Desconto desconto, double valor) {
    return desconto.aplicar(valor);
}
```

Para um cliente **PREMIUM** novo, você **cria** `class DescontoPremium implements Desconto` — sem tocar no `calcular`. Aberto para extensão, fechado para modificação. (Isso é literalmente o padrão **Strategy**.)

### 5. Analogia

Tomada com adaptadores: para usar um aparelho novo, você **encaixa** um adaptador (extensão) — não **quebra a parede** para refazer a fiação (modificação).

### 6. Como reconhecer na prova

- Cadeia de `if/else if` ou `switch` comparando **tipos/categorias** (`equals("VIP")`, `case "X"`).
- Enunciado: "adicionar novos tipos **sem modificar** o código existente".
- Solução envolvendo **interface/classe abstrata** + implementações.

### 7. Erros comuns

- Escolher "criar mais blocos `if`" ou "trocar `if` por `switch`" (não resolve — continua modificando).
- Achar que `protected` resolve OCP (distrator da prova).
- Confundir OCP com herança pura; o coração é **abstração + polimorfismo**.

### 8/9. Questão estilo professor (= questão 6 da AP1)

> A equipe quer adicionar novos tipos de cliente **sem modificar** o método `calcular`. Pelo OCP, a estratégia mais adequada é:
> A) Criar novos blocos `if` para cada tipo.
> B) Criar subclasses específicas para cada cliente sem alterar a classe principal.
> C) Utilizar **interfaces ou classes abstratas para representar estratégias de desconto**.
> D) Tornar o método `calcular` protegido (`protected`).
> E) Substituir os `if` por `switch-case`.

### 10. Como responder

**Resposta: C.** Representar cada desconto como uma implementação de uma abstração (`Desconto`) permite **adicionar** novos tipos criando novas classes, **sem modificar** o código existente.
- A) erra: mais `if` = mais modificação no método existente (o oposto do OCP).
- B) erra: "subclasses de cliente" não é o eixo; o desconto é que precisa virar estratégia abstrata.
- D) erra: `protected` é modificador de acesso, não tem relação com extensibilidade.
- E) erra: `switch` ainda exige modificar o método a cada novo tipo.

### 11. Resumo OCP

Aberto para extensão, fechado para modificação. Sintoma: `if/else` por tipo. Cura: interface/abstração + implementações (Strategy).

---

## 6.3 LSP — Liskov Substitution Principle

### 1. O que é

Onde se espera um objeto da **superclasse**, deve ser possível usar um objeto de **qualquer subclasse** sem que o programa quebre ou se comporte de forma errada. A subclasse precisa **honrar o contrato** da mãe.

### 2. Por que existe

Herança mal usada cria subclasses que "são filhas no papel" mas **quebram** quando usadas no lugar da mãe. Isso destrói o polimorfismo confiável.

### 3. Exemplo ruim (o do professor — Ave / Pinguim)

```java
class Ave {
    void voar() { System.out.println("Voando..."); }
}

class Pinguim extends Ave {
    @Override
    void voar() { throw new UnsupportedOperationException("Pinguim não voa!"); }
}
```

`Pinguim` **é uma** `Ave`, mas **não voa**. Qualquer código que faça `ave.voar()` quebra ao receber um `Pinguim`. A subclasse **não substitui** a mãe com segurança → viola LSP.

### 4. Exemplo bom (separar a capacidade que nem todos têm)

```java
class Ave { void comer() { /* ... */ } }

interface Voadora { void voar(); }

class Aguia extends Ave implements Voadora {
    public void voar() { System.out.println("Voando..."); }
}

class Pinguim extends Ave {
    void nadar() { System.out.println("Nadando..."); }
}
```

Agora "voar" só pertence a quem **realmente** voa. `Pinguim` continua sendo `Ave`, mas não promete algo que não cumpre. (Repare: a solução usa **interface segregada** — LSP e ISP andam juntos.)

### 5. Analogia

Se um contrato diz "todo funcionário recebe o salário no quinto dia útil", uma subclasse "EstagiárioFantasma" que **lança erro** quando você tenta pagar quebra o contrato. Ela não pode substituir um funcionário comum.

### 6. Como reconhecer na prova

- Subclasse que **sobrescreve** um método herdado só para **lançar exceção** (`throw new UnsupportedOperationException`) ou **não fazer nada**.
- Subclasse que **muda o significado** esperado do método.
- Hierarquia "X é um Y" em que X **não consegue** fazer algo que todo Y deveria.

### 7. Erros comuns

- Usar herança só por "parecer" da mesma família, sem checar se a subclasse cumpre o contrato.
- Confundir LSP com ISP (LSP = substituibilidade; ISP = interfaces enxutas). Eles se relacionam, mas a pergunta foca em qual aspecto.

### 8/9. Questão estilo professor

> ```java
> class Retangulo {
>     int largura, altura;
>     void setLargura(int l){ largura = l; }
>     void setAltura(int a){ altura = a; }
>     int area(){ return largura * altura; }
> }
> class Quadrado extends Retangulo {
>     void setLargura(int l){ largura = altura = l; }   // força lados iguais
>     void setAltura(int a){ largura = altura = a; }
> }
> ```
> Qual princípio SOLID é **violado**?
> A) SRP  B) OCP  C) **LSP**  D) ISP  E) DIP

### 10. Como responder

**Resposta: C (LSP).** Um código que use `Retangulo` esperando que `setLargura` **não** altere a altura quebra quando recebe um `Quadrado` (que altera os dois). A subclasse **não substitui** a mãe sem mudar o comportamento esperado → viola Liskov.
- SRP/OCP/ISP/DIP não são o foco: não há acúmulo de responsabilidades, nem `if` por tipo, nem interface inflada, nem dependência de implementação concreta. O problema é **substituibilidade**.

### 11. Resumo LSP

Subclasse deve poder substituir a mãe sem surpresas. Sintoma: override que lança exceção / muda o contrato (Pinguim que não voa).

---

## 6.4 ISP — Interface Segregation Principle

### 1. O que é

Nenhuma classe deve ser **obrigada a implementar métodos que não usa**. Prefira **várias interfaces pequenas e específicas** a uma interface grande e genérica ("gorda").

### 2. Por que existe

Uma interface enorme força implementações a "encher linguiça" com métodos vazios ou que lançam exceção (o que também fere o LSP). Interfaces pequenas deixam cada classe implementar **só o que faz sentido**.

### 3. Exemplo ruim (o do professor — questão 8 da AP1)

```java
interface DispositivoMultimidia {
    void reproduzirAudio();
    void reproduzirVideo();
    void imprimirDocumento();
}

class CaixaDeSom implements DispositivoMultimidia {
    public void reproduzirAudio() { System.out.println("Reproduzindo áudio..."); }
    public void reproduzirVideo() { throw new UnsupportedOperationException(); }
    public void imprimirDocumento() { throw new UnsupportedOperationException(); }
}
```

A `CaixaDeSom` é forçada a implementar `reproduzirVideo()` e `imprimirDocumento()`, que ela **não faz** — sobrando exceções. A interface é "gorda demais" → viola ISP.

### 4. Exemplo bom (refatoração esperada — versão manuscrita da AP1)

```java
interface DispositivoDeSom      { void reproduzirAudio(); }
interface DispositivoDeVideo    { void reproduzirVideo(); }
interface DispositivoDeImpressao{ void imprimirDocumento(); }

class CaixaDeSom implements DispositivoDeSom {
    public void reproduzirAudio() { System.out.println("Reproduzindo áudio..."); }
}

class Televisao implements DispositivoDeVideo {
    public void reproduzirVideo() { System.out.println("Reproduzindo vídeo..."); }
}

class Impressora implements DispositivoDeImpressao {
    public void imprimirDocumento() { System.out.println("Imprimindo documento..."); }
}
```

Cada classe implementa **só o que usa**. Um aparelho que faça áudio **e** vídeo (ex.: `SmartTV`) pode implementar **as duas** interfaces. Era exatamente essa a resposta da discursiva 8.

### 5. Analogia

Um cardápio único e gigante obrigando todo restaurante a servir tudo (sushi, pizza, feijoada) é absurdo. Cardápios menores e especializados deixam cada restaurante oferecer **só o seu**.

### 6. Como reconhecer na prova

- Interface com **muitos** métodos de naturezas diferentes.
- Implementações com métodos que **lançam exceção** ou ficam **vazios** por não fazerem aquilo.
- Enunciado pedindo para "criar as interfaces necessárias" / citando ISP.

### 7. Erros comuns

- Confundir ISP com SRP (SRP é sobre **classes**; ISP é sobre **interfaces**).
- Quebrar a interface de qualquer jeito sem agrupar por **capacidade coesa**.

### 8/9. Questão estilo professor (= discursiva 8 da AP1)

> O código de `DispositivoMultimidia` viola o ISP. Refatore criando as interfaces e classes necessárias.

### 10. Como responder

Resposta esperada: **quebrar a interface gorda em interfaces pequenas e coesas** (`DispositivoDeSom`, `DispositivoDeVideo`, `DispositivoDeImpressao`) e fazer cada classe implementar **apenas** as que correspondem ao seu comportamento real, **eliminando** os `throw new UnsupportedOperationException()`. Mencione que classes multifuncionais podem implementar mais de uma interface. (Código do item 4 acima.)

### 11. Resumo ISP

Interfaces pequenas e específicas. Sintoma: implementação cheia de métodos vazios/com exceção por causa de uma interface inchada. Cura: segregar em várias interfaces.

---

## 6.5 DIP — Dependency Inversion Principle

### 1. O que é

Módulos de alto nível **não devem depender** de módulos de baixo nível diretamente; **ambos devem depender de abstrações** (interfaces). E detalhes (implementações) dependem da abstração, não o contrário.

### 2. Por que existe

Quando uma classe importante depende de uma classe concreta específica, trocar essa implementação obriga a mexer na classe importante. Dependendo de uma **interface**, você troca a implementação livremente (inclusive por uma versão "fake" para testes).

### 3. Exemplo ruim (o do professor — Computador/Teclado)

```java
class TecladoABNT {
    String ler() { return "tecla"; }
}

class Computador {
    private TecladoABNT teclado = new TecladoABNT();   // depende do CONCRETO
    String lerEntrada() { return teclado.ler(); }
}
```

`Computador` está amarrado a `TecladoABNT`. Para usar um teclado USB diferente, ou um mock em teste, você precisa **modificar** `Computador` → viola DIP.

### 4. Exemplo bom (dependa da interface)

```java
interface Teclado {
    String ler();
}

class TecladoABNT implements Teclado {
    public String ler() { return "tecla ABNT"; }
}
class TecladoUSB implements Teclado {
    public String ler() { return "tecla USB"; }
}

class Computador {
    private final Teclado teclado;
    public Computador(Teclado teclado) {   // recebe a abstração (injeção)
        this.teclado = teclado;
    }
    String lerEntrada() { return teclado.ler(); }
}

// uso:
Computador pc = new Computador(new TecladoUSB());   // troca livre
```

`Computador` depende de **`Teclado`** (abstração), não de uma marca específica. Trocar a implementação não exige mudar `Computador`. Repare que isso é a base da **Injeção de Dependência** do Spring (Parte 9).

### 5. Analogia

A tomada da parede (interface padrão) não depende de qual aparelho você vai ligar. Qualquer aparelho com o plugue certo (implementação da interface) funciona. A parede depende do **padrão**, não da marca do aparelho.

### 6. Como reconhecer na prova

- Classe instanciando diretamente uma dependência concreta com `new ClasseConcreta()` dentro dela.
- Atributo do tipo de uma **classe concreta** em vez de **interface**.
- Solução envolvendo **receber a interface pelo construtor** (injeção).

### 7. Erros comuns

- Confundir DIP (depender de abstração) com simplesmente "usar interface" sem inverter a dependência.
- Achar que injeção de dependência é "automágica" do Spring; o **princípio** é independente do framework.

### 8/9. Questão estilo professor

> ```java
> class ServicoEmail {
>     private SmtpGmail smtp = new SmtpGmail();   // concreto
>     void enviar(String m){ smtp.mandar(m); }
> }
> ```
> Qual refatoração aplica corretamente o **DIP**?
> A) Tornar `smtp` público.
> B) Criar interface `EnviadorEmail` e fazer `ServicoEmail` depender dela, recebendo a implementação pelo construtor.
> C) Adicionar mais métodos a `SmtpGmail`.
> D) Usar `static` em `smtp`.

### 10. Como responder

**Resposta: B.** Criar a abstração `EnviadorEmail` e injetar a implementação (`SmtpGmail`, `SmtpOutlook`, ou um mock) faz o `ServicoEmail` depender da **interface**, não do concreto.
- A) erra: visibilidade não inverte dependência.
- C) erra: piora o acoplamento ao concreto.
- D) erra: `static` não tem relação com inversão de dependência.

### 11. Resumo DIP

Dependa de **abstrações** (interfaces), não de implementações. Sintoma: `new Concreto()` dentro da classe importante. Cura: interface + injeção pelo construtor. (Base do Spring.)

---

## 6.6 Tabela-resumo SOLID (cole na parede)

| Princípio | Sintoma no código | Cura | Exemplo do professor |
|---|---|---|---|
| **SRP** | classe que gera+salva+envia | separar em classes | RelatorioAluno |
| **OCP** | `if/else` por tipo | interface + implementações | descontos VIP/REGULAR |
| **LSP** | override que lança exceção / muda contrato | tirar da hierarquia / interface | Ave/Pinguim |
| **ISP** | implementação com métodos vazios/exceção | interfaces pequenas | DispositivoMultimidia |
| **DIP** | `new Concreto()` interno | abstração + injeção | Computador/Teclado |

## Exercícios — Parte 6 (SOLID)

### Objetivas

**1.** Classe que gera, salva e envia relatórios viola:
A) OCP  B) SRP  C) LSP  D) DIP

**2.** `if (tipo.equals("VIP")) ... else if (tipo.equals("REGULAR")) ...` para adicionar tipos sem mexer no código deve virar:
A) switch  B) mais ifs  C) interface de estratégias (OCP)  D) método protegido

**3.** `Pinguim extends Ave` cujo `voar()` lança exceção viola:
A) SRP  B) LSP  C) ISP  D) OCP

**4.** Interface com `reproduzirAudio`, `reproduzirVideo`, `imprimirDocumento` forçando exceções viola:
A) DIP  B) SRP  C) ISP  D) LSP

**5.** "Dependa de abstrações, não de implementações" é:
A) SRP  B) OCP  C) ISP  D) DIP

**6.** A melhor cura para violação de OCP é:
A) tornar tudo estático  B) abstração + polimorfismo (Strategy)  C) mais if/else  D) herança simples

**7.** Receber uma interface pelo construtor em vez de dar `new` no concreto aplica:
A) SRP  B) DIP  C) LSP  D) ISP

**8.** SRP fala sobre, e ISP fala sobre, respectivamente:
A) interfaces / classes  B) classes / interfaces  C) ambos sobre herança  D) ambos sobre exceções

**9.** "Aberto para extensão, fechado para modificação" é:
A) SRP  B) OCP  C) LSP  D) ISP

**10.** O exemplo Computador/Teclado do professor ilustra:
A) LSP  B) ISP  C) DIP  D) SRP

### Discursivas

**D1.** Refatore aplicando OCP a lógica de frete:
```java
double frete(String regiao, double peso){
    if(regiao.equals("SUL")) return peso*2;
    else if(regiao.equals("NORTE")) return peso*4;
    return peso*3;
}
```

**D2.** O código de `DispositivoMultimidia` viola um princípio SOLID. Diga qual, explique por quê e reescreva aplicando-o.

> **Gabarito Parte 6:** 1-B, 2-C, 3-B, 4-C, 5-D, 6-B, 7-B, 8-B, 9-B, 10-C.
> **D1 esperado:** interface `CalculadoraFrete` com implementações `FreteSul`, `FreteNorte`, `FretePadrao`; selecionar a estratégia sem `if` no método principal — novo região = nova classe, sem modificar o existente.
> **D2 esperado:** viola ISP; refatorar em três interfaces pequenas (som/vídeo/impressão), cada classe implementa só a sua, eliminando os `UnsupportedOperationException`.


---

# PARTE 7 — Design Patterns (GoF)

**Design Patterns** são **soluções comprovadas para problemas recorrentes** de design de software. Não são código pronto para copiar, e sim "receitas" de estrutura. O professor ensinou **exatamente seis** padrões (e você não precisa de outros):

| Categoria | Padrões |
|---|---|
| **Criação** | Singleton, Factory Method |
| **Estrutural** | Adapter, Decorator |
| **Comportamental** | Observer, Strategy |

Para cada um: intenção, estrutura, exemplo Java, vantagens, desvantagens, quando usar, quando NÃO usar, comparação.

---

## 7.1 Singleton (Criação)

### Intenção

Garantir que uma classe tenha **uma única instância** em todo o programa e fornecer um **ponto global de acesso** a ela.

### Estrutura

- Construtor **`private`** (ninguém cria de fora).
- Atributo **`static`** que guarda a única instância.
- Método **`static getInstance()`** que devolve sempre a mesma instância.

### Exemplo Java (linha por linha)

```java
public class ConexaoBanco {
    private static ConexaoBanco instancia;     // 1. a única instância (static)

    private ConexaoBanco() {                    // 2. construtor privado
        System.out.println("Conexão criada");
    }

    public static ConexaoBanco getInstance() {  // 3. ponto global de acesso
        if (instancia == null) {                // 4. cria só na 1ª vez (lazy)
            instancia = new ConexaoBanco();
        }
        return instancia;                       // 5. sempre a mesma
    }

    public void executar(String sql) {
        System.out.println("Executando: " + sql);
    }
}

// uso:
ConexaoBanco c1 = ConexaoBanco.getInstance();
ConexaoBanco c2 = ConexaoBanco.getInstance();
// c1 == c2  -> verdadeiro: é o MESMO objeto
```

- **Linha 2:** ninguém faz `new ConexaoBanco()` de fora.
- **Linha 4:** "lazy initialization" — cria só quando pedido pela primeira vez.
- `c1` e `c2` apontam para a **mesma** instância.

### Vantagens

- Uma única instância controlada (útil para conexão de BD, logger, configuração).
- Ponto de acesso global e previsível.
- Economiza recursos (não recria objetos caros).

### Desvantagens

- Vira um "estado global" — pode esconder dependências e dificultar testes.
- Em ambiente **multithread**, o `if (instancia == null)` precisa de cuidado (sincronização) para não criar duas instâncias.
- Acoplamento: muitas classes passam a depender do Singleton.

### Quando usar

- Quando **realmente** só pode existir um (pool de conexão, configuração global, logger).
- No **projeto final**, o professor citou Singleton — tipicamente para a **conexão com o banco (MySQL)**.

### Quando NÃO usar

- Quando você só quer "facilitar acesso" — vira global disfarçado.
- Quando atrapalha testes (dependência escondida); prefira injeção (DIP).

### Comparação

- vs **Factory Method**: ambos são de **criação**, mas Singleton garante **uma** instância; Factory decide **qual classe** instanciar (pode criar várias).

### Como reconhecer na prova

- **Construtor `private`** + atributo `static` da própria classe + `getInstance()`. Esse trio é a assinatura inconfundível do Singleton.

---

## 7.2 Factory Method (Criação)

### Intenção

Definir uma interface/método para **criar objetos**, deixando as **subclasses (ou um método central) decidirem qual classe concreta** instanciar. O código cliente pede "me dê um produto" sem dar `new` no concreto.

### Estrutura

- Uma **abstração** do produto (interface/classe).
- Um **método fábrica** que retorna a abstração, escolhendo a implementação concreta.

### Exemplo Java

```java
interface Notificacao {                 // produto abstrato
    void enviar(String msg);
}

class NotificacaoEmail implements Notificacao {
    public void enviar(String msg){ System.out.println("Email: " + msg); }
}
class NotificacaoSms implements Notificacao {
    public void enviar(String msg){ System.out.println("SMS: " + msg); }
}

class NotificacaoFactory {              // a fábrica
    public static Notificacao criar(String tipo) {
        if (tipo.equals("EMAIL")) return new NotificacaoEmail();
        if (tipo.equals("SMS"))   return new NotificacaoSms();
        throw new IllegalArgumentException("Tipo inválido");
    }
}

// uso:
Notificacao n = NotificacaoFactory.criar("EMAIL");
n.enviar("Olá");
```

O cliente não sabe (nem precisa saber) qual classe concreta foi criada — só conhece a interface `Notificacao`. Trocar/adicionar tipos fica centralizado na fábrica.

### Vantagens

- Centraliza a criação (um lugar só para mudar).
- Desacopla o cliente das classes concretas (conecta com DIP).
- Facilita adicionar novos tipos de produto.

### Desvantagens

- Adiciona classes/indireção; pode ser overkill para casos simples.
- A fábrica com `if/switch` pode crescer (pode combinar com Strategy/registro).

### Quando usar

- Quando a criação do objeto tem lógica e você não quer `new` espalhado pelo código.
- No **projeto final**, o professor citou Factory Method — tipicamente para **criar objetos de serviço/DAO** ou variações de uma entidade sem acoplar o cliente.

### Quando NÃO usar

- Criação trivial (um `new` direto resolve).

### Comparação

- vs **Singleton**: Singleton garante instância única; Factory escolhe **qual** classe criar.
- vs **Strategy**: Factory **cria** o objeto; Strategy **usa** um algoritmo já escolhido. Frequentemente combinam (fábrica cria a estratégia).

### Como reconhecer na prova

- Um método/classe `criar...()` / `...Factory` que **retorna uma interface** e decide internamente qual concreta instanciar. Cliente sem `new` do concreto.

---

## 7.3 Strategy (Comportamental)

### Intenção

Definir uma **família de algoritmos**, encapsular cada um numa classe e torná-los **intercambiáveis**. Permite trocar o comportamento em tempo de execução **sem `if/else`**.

### Estrutura

- Interface **`Strategy`** com o método do algoritmo.
- Implementações concretas (uma por algoritmo).
- Um **contexto** que recebe uma estratégia e a usa.

### Exemplo Java

```java
interface Desconto {                     // a estratégia
    double aplicar(double valor);
}
class DescontoVip implements Desconto {
    public double aplicar(double v){ return v * 0.8; }
}
class DescontoRegular implements Desconto {
    public double aplicar(double v){ return v * 0.9; }
}

class Carrinho {                         // o contexto
    private Desconto desconto;
    public Carrinho(Desconto desconto){ this.desconto = desconto; }
    public double total(double valor){ return desconto.aplicar(valor); }
}

// uso: troca de algoritmo sem if
Carrinho c = new Carrinho(new DescontoVip());
c.total(100);   // 80
```

É **o mesmo exemplo do OCP** (descontos). Strategy é a **implementação concreta** do OCP: cada algoritmo é uma classe, e o contexto escolhe qual usar.

### Vantagens

- Elimina `if/else`/`switch` por tipo de comportamento.
- Aberto para extensão (novo algoritmo = nova classe) → respeita OCP.
- Algoritmos testáveis isoladamente.

### Desvantagens

- Mais classes.
- O cliente precisa conhecer as estratégias para escolher (mitigado por uma Factory).

### Quando usar

- Vários jeitos de fazer a mesma coisa (descontos, frete, ordenação, formas de pagamento).

### Quando NÃO usar

- Existe só um algoritmo e não há previsão de variação.

### Comparação

- vs **OCP**: Strategy é o padrão que **realiza** o OCP.
- vs **Factory**: Factory **cria**; Strategy **executa**. Costumam ser usados juntos.
- vs **Observer**: Strategy troca **como** algo é feito; Observer avisa **quando** algo aconteceu.

### Como reconhecer na prova

- Interface com um método "fazer algo" + várias implementações + um contexto que **recebe** a interface e delega. Se a alternativa fala em "trocar o algoritmo sem alterar o cliente", é Strategy.

---

## 7.4 Adapter (Estrutural)

### Intenção

Fazer **duas interfaces incompatíveis trabalharem juntas**. O Adapter "traduz" a interface de uma classe para a interface que o cliente espera.

### Estrutura

- Uma **interface alvo** (o que o cliente espera).
- Uma classe **existente incompatível** (o *adaptee*).
- O **Adapter** implementa a interface alvo e, por dentro, chama o adaptee.

### Exemplo Java

```java
// o que o sistema espera:
interface Pagamento {
    void pagar(double valor);
}

// biblioteca externa, interface diferente (não dá para mudar):
class PayPalApi {
    void enviarPagamento(double valorEmDolar){
        System.out.println("PayPal: $" + valorEmDolar);
    }
}

// Adapter: traduz Pagamento -> PayPalApi
class PayPalAdapter implements Pagamento {
    private PayPalApi paypal = new PayPalApi();
    public void pagar(double valor){
        paypal.enviarPagamento(valor);   // tradução da chamada
    }
}

// uso: o cliente só conhece Pagamento
Pagamento p = new PayPalAdapter();
p.pagar(100);
```

O sistema fala `pagar(...)`; o PayPal fala `enviarPagamento(...)`. O Adapter encaixa um no outro.

### Vantagens

- Reaproveita código/bibliotecas **legadas ou de terceiros** sem alterá-los.
- Desacopla o cliente da interface estranha.

### Desvantagens

- Mais uma camada de indireção.
- Pode mascarar um design ruim se usado em excesso.

### Quando usar

- Integrar com biblioteca externa cuja interface não casa com a sua.
- Migrar de uma API antiga para uma nova mantendo o cliente intacto.

### Quando NÃO usar

- Quando você **controla** as duas pontas e pode simplesmente padronizar a interface.

### Comparação

- vs **Decorator**: ambos "envolvem" um objeto, mas o **Adapter muda a interface** (faz encaixar); o **Decorator mantém a interface e adiciona comportamento**.
- vs **Factory**: Adapter adapta interface; Factory cria objetos.

### Como reconhecer na prova

- Uma classe que **implementa a interface esperada** e, por dentro, **delega para outra classe de interface diferente**, "traduzindo" os nomes/parâmetros dos métodos. Palavra-chave: **compatibilizar interfaces**.

---

## 7.5 Decorator (Estrutural)

### Intenção

**Adicionar responsabilidades a um objeto dinamicamente**, sem alterar sua classe e sem herança explosiva, **envolvendo-o** em "decoradores" que têm a mesma interface.

### Estrutura

- Uma **interface comum** (componente).
- Um **componente concreto** (o objeto base).
- **Decoradores** que implementam a mesma interface, guardam uma referência ao componente e **acrescentam** comportamento antes/depois de delegar.

### Exemplo Java

```java
interface Cafe {                          // componente
    double preco();
    String descricao();
}

class CafeSimples implements Cafe {       // base
    public double preco(){ return 5.0; }
    public String descricao(){ return "Café"; }
}

abstract class CafeDecorator implements Cafe {  // decorador base
    protected Cafe cafe;
    public CafeDecorator(Cafe cafe){ this.cafe = cafe; }
}

class ComLeite extends CafeDecorator {
    public ComLeite(Cafe cafe){ super(cafe); }
    public double preco(){ return cafe.preco() + 2.0; }       // adiciona
    public String descricao(){ return cafe.descricao() + " + leite"; }
}

class ComChocolate extends CafeDecorator {
    public ComChocolate(Cafe cafe){ super(cafe); }
    public double preco(){ return cafe.preco() + 3.0; }
    public String descricao(){ return cafe.descricao() + " + chocolate"; }
}

// uso: empilha responsabilidades
Cafe c = new ComChocolate(new ComLeite(new CafeSimples()));
c.descricao();  // "Café + leite + chocolate"
c.preco();      // 5 + 2 + 3 = 10.0
```

Cada decorador **envolve** o anterior e soma comportamento. Sem precisar de classes `CafeComLeiteEChocolate`, `CafeComLeite`, etc. (que explodiriam por combinação).

### Vantagens

- Combina comportamentos em tempo de execução, sem subclasses para cada combinação.
- Respeita OCP (novo decorador = nova classe).

### Desvantagens

- Muitos objetos pequenos aninhados → debug mais difícil.
- A ordem dos decoradores pode importar.

### Quando usar

- Acrescentar funcionalidades opcionais e combináveis (ex.: streams de I/O do Java, que são decorators; toppings; permissões).

### Quando NÃO usar

- Quando as combinações são poucas e fixas (herança simples basta).

### Comparação

- vs **Adapter**: Decorator **mantém** a interface e **adiciona** comportamento; Adapter **muda** a interface.
- vs **Strategy**: Strategy troca **o algoritmo inteiro**; Decorator **acrescenta camadas** ao redor do objeto.

### Como reconhecer na prova

- Classe que **implementa a mesma interface** do objeto que recebe **e guarda uma referência a ele**, chamando-o e somando algo (`cafe.preco() + 2`). Empilhamento `new A(new B(new C()))`.

---

## 7.6 Observer (Comportamental)

### Intenção

Definir uma dependência **um-para-muitos**: quando um objeto (**Subject/Observável**) muda de estado, **todos os seus observadores são notificados automaticamente**.

### Estrutura

- **Subject**: mantém lista de observadores e os notifica.
- **Observer** (interface): método `atualizar()` chamado nas notificações.
- **Observadores concretos**: reagem à notificação.

### Exemplo Java

```java
interface Observer {                    // o contrato do observador
    void atualizar(String evento);
}

class Inscrito implements Observer {
    private String nome;
    public Inscrito(String nome){ this.nome = nome; }
    public void atualizar(String evento){
        System.out.println(nome + " recebeu: " + evento);
    }
}

class Canal {                           // o Subject
    private List<Observer> inscritos = new ArrayList<>();
    public void inscrever(Observer o){ inscritos.add(o); }
    public void publicarVideo(String titulo){
        for (Observer o : inscritos) {  // notifica todos
            o.atualizar("Novo vídeo: " + titulo);
        }
    }
}

// uso:
Canal canal = new Canal();
canal.inscrever(new Inscrito("Ana"));
canal.inscrever(new Inscrito("Beto"));
canal.publicarVideo("SOLID na prática");
// Ana e Beto são notificados automaticamente
```

O `Canal` não conhece os inscritos concretos — só a interface `Observer`. Adicionar inscritos não muda o `Canal`.

### Vantagens

- Desacopla quem **gera** o evento de quem **reage** a ele.
- Vários observadores reagem sem o subject saber quem são.

### Desvantagens

- Notificações em cadeia podem ficar difíceis de rastrear.
- Risco de vazamento de memória se não desinscrever.

### Quando usar

- Sistemas de eventos/notificação: newsletters, UI reagindo a dados, pub/sub.

### Quando NÃO usar

- Relação simples e direta entre dois objetos (chamada direta basta).

### Comparação

- vs **Strategy**: Strategy escolhe **como** algo é feito; Observer reage a **quando** algo acontece.
- O nome "inscrever/notificar" e a **lista de observadores** são a marca do Observer.

### Como reconhecer na prova

- Uma **lista de `Observer`** + métodos `inscrever`/`notificar` + um laço chamando `atualizar()` em todos. Relação **um-para-muitos** com notificação automática.

---

## 7.7 Tabela-resumo dos padrões

| Padrão | Categoria | Resolve | Marca registrada no código |
|---|---|---|---|
| Singleton | Criação | uma única instância | construtor `private` + `static getInstance()` |
| Factory Method | Criação | criar sem acoplar ao concreto | método `criar()`/`Factory` retornando interface |
| Strategy | Comportamental | trocar algoritmo sem `if` | contexto recebe interface e delega |
| Adapter | Estrutural | compatibilizar interfaces | implementa interface alvo, delega a outra incompatível |
| Decorator | Estrutural | adicionar comportamento em camadas | mesma interface, envolve e soma (`new A(new B(...))`) |
| Observer | Comportamental | notificar muitos automaticamente | lista de observers + `notificar()`/`atualizar()` |

## Exercícios — Parte 7 (Design Patterns)

### Objetivas

**1.** Construtor `private` + `static getInstance()` indica:
A) Factory  B) Singleton  C) Observer  D) Adapter

**2.** Trocar o algoritmo de desconto sem `if/else`, via interface + implementações + contexto, é:
A) Decorator  B) Adapter  C) Strategy  D) Singleton

**3.** Classe que implementa a interface esperada e por dentro delega a uma API de interface diferente é:
A) Adapter  B) Observer  C) Strategy  D) Factory

**4.** `new ComLeite(new CafeSimples())` somando preço a cada camada é:
A) Adapter  B) Decorator  C) Singleton  D) Factory

**5.** Lista de inscritos notificados automaticamente quando o estado muda é:
A) Strategy  B) Observer  C) Adapter  D) Singleton

**6.** Um método `criar(tipo)` que retorna uma interface decidindo a classe concreta é:
A) Singleton  B) Factory Method  C) Decorator  D) Observer

**7.** O padrão que **realiza** o princípio OCP de descontos é:
A) Singleton  B) Strategy  C) Adapter  D) Observer

**8.** Adapter difere de Decorator porque:
A) Adapter muda a interface; Decorator mantém e adiciona comportamento  B) são idênticos  C) Decorator cria instância única  D) Adapter notifica observadores

**9.** Singleton, no projeto final, é tipicamente usado para:
A) enviar email  B) a conexão única com o banco  C) validar formulário  D) renderizar HTML

**10.** O padrão usado para **criar** objetos sem espalhar `new` é:
A) Observer  B) Factory Method  C) Decorator  D) Strategy

### Discursivas

**D1.** Implemente um `Singleton` para uma classe `Configuracao` com método `get(String chave)`. Explique por que o construtor é privado.

**D2.** Dado um sistema de pagamento que precisa suportar `PIX`, `Cartao` e `Boleto` de forma intercambiável, diga qual padrão usar e escreva o esqueleto das classes/interfaces.

> **Gabarito Parte 7:** 1-B, 2-C, 3-A, 4-B, 5-B, 6-B, 7-B, 8-A, 9-B, 10-B.
> **D2 esperado:** Strategy — interface `Pagamento { void pagar(double v); }` com `Pix`, `Cartao`, `Boleto` implementando, e um contexto que recebe `Pagamento` e delega.


---

# PARTE 8 — JDBC (Java Database Connectivity)

O segundo bimestre é fortemente prático: conectar Java ao banco. **JDBC** é a API padrão do Java para isso. Domine os objetos, o CRUD e os conceitos de SQL Injection e DAO.

## 8.1 Os componentes do JDBC

### 1. O que é

**JDBC** é o conjunto de classes/interfaces (`java.sql`) que permite ao Java **conectar a um banco relacional**, enviar comandos SQL e ler resultados. É a "ponte" entre o programa Java e o MySQL (no caso do projeto).

### 2. Por que existe

Para dar uma forma **padronizada** de falar com qualquer banco. Trocando apenas o **driver**, o mesmo código fala com MySQL, PostgreSQL, Oracle, etc.

### 3. Os objetos principais (em ordem de uso)

| Objeto | Papel |
|---|---|
| **Driver** | implementação específica do banco (ex.: driver MySQL). É registrado para a JVM saber "falar MySQL". |
| **DriverManager** | fábrica que **abre conexões**: `DriverManager.getConnection(url, user, senha)`. |
| **Connection** | a **conexão aberta** com o banco. Por ela você cria statements e controla transações. |
| **Statement** | executa SQL **estático** (sem parâmetros). Vulnerável a SQL Injection — evite com dados do usuário. |
| **PreparedStatement** | executa SQL **parametrizado** (`?`). Seguro contra SQL Injection e mais eficiente. **Preferido.** |
| **ResultSet** | a **tabela de resultados** de um `SELECT`. Você percorre linha a linha com `next()`. |

### Fluxo geral

```
DriverManager.getConnection(...)  ->  Connection
Connection.prepareStatement(sql)  ->  PreparedStatement
ps.executeQuery()  ->  ResultSet   (para SELECT)
ps.executeUpdate() ->  int linhas  (para INSERT/UPDATE/DELETE)
fechar tudo (Connection, PreparedStatement, ResultSet)
```

## 8.2 SQL Injection (e por que PreparedStatement)

### O problema

Montar SQL concatenando strings com dados do usuário é perigoso:

```java
// PERIGOSO - NUNCA faça isso
String sql = "SELECT * FROM usuario WHERE login = '" + login + "'";
```

Se o usuário digitar `login = ' OR '1'='1`, o SQL vira `... WHERE login = '' OR '1'='1'`, que é **sempre verdadeiro** — ele entra sem senha, ou pior, pode apagar dados (`'; DROP TABLE usuario; --`). Isso é **SQL Injection**.

### A solução: PreparedStatement

```java
String sql = "SELECT * FROM usuario WHERE login = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, login);   // o valor é TRATADO como dado, nunca como código SQL
```

O `?` é um **placeholder**. O `setString` envia o valor **separado** do comando, então o banco nunca interpreta o conteúdo como SQL. Imune a injection **e** mais rápido (o banco reaproveita o plano de execução).

> **Na prova:** ver `PreparedStatement` + `?` + `setX(...)` → tema **SQL Injection / segurança**. Ver SQL concatenado com `+` e variável do usuário → **vulnerabilidade**.

## 8.3 DAO (Data Access Object)

### O que é

**DAO** é um **padrão de projeto** que isola toda a lógica de **acesso a dados** (SQL, JDBC) numa classe dedicada. O resto do sistema fala com o DAO por métodos como `salvar()`, `buscarPorId()`, `listar()`, `atualizar()`, `deletar()` — **sem saber** que por baixo há SQL.

### Por que existe

- **Separa responsabilidades** (SRP): a regra de negócio não se mistura com SQL.
- Troca de banco/tecnologia fica isolada no DAO.
- Facilita testes e manutenção.

### Estrutura típica

- Uma **Entity** (ex.: `Aluno`) — só dados.
- Um **DAO** (ex.: `AlunoDAO`) — métodos CRUD com JDBC.

## 8.4 CRUD completo com JDBC (explicado linha por linha)

Vamos modelar a entidade `Aluno` e seu DAO.

### A Entity

```java
public class Aluno {
    private int id;
    private String nome;
    private String email;

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

`Aluno` é só **estado** (atributos `private` + acessadores) — encapsulamento puro.

### A conexão (Singleton — como no projeto final)

```java
public class ConexaoFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USER = "root";
    private static final String SENHA = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, SENHA);
    }
}
```

`getConnection()` centraliza a abertura da conexão. (No projeto, isso costuma ser um Singleton.)

### O DAO com os 4 métodos CRUD

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // CREATE -----------------------------------------------------
    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, email) VALUES (?, ?)";  // 1
        try (Connection conn = ConexaoFactory.getConnection();         // 2
             PreparedStatement ps = conn.prepareStatement(sql)) {      // 3
            ps.setString(1, aluno.getNome());                          // 4
            ps.setString(2, aluno.getEmail());                         // 5
            ps.executeUpdate();                                        // 6
        } catch (SQLException e) {                                     // 7
            throw new RuntimeException("Erro ao inserir aluno", e);
        }
    }

    // READ (todos) ----------------------------------------------
    public List<Aluno> listar() {
        String sql = "SELECT id, nome, email FROM aluno";              // 8
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {                       // 9
            while (rs.next()) {                                        // 10
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));                             // 11
                a.setNome(rs.getString("nome"));
                a.setEmail(rs.getString("email"));
                alunos.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar", e);
        }
        return alunos;
    }

    // READ (por id) ---------------------------------------------
    public Aluno buscarPorId(int id) {
        String sql = "SELECT id, nome, email FROM aluno WHERE id = ?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                                       // 12
                    Aluno a = new Aluno();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setEmail(rs.getString("email"));
                    return a;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar", e);
        }
        return null;   // não encontrado
    }

    // UPDATE -----------------------------------------------------
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setInt(3, aluno.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar", e);
        }
    }

    // DELETE -----------------------------------------------------
    public void deletar(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar", e);
        }
    }
}
```

**Explicação dos pontos numerados:**

1. SQL com `?` (parametrizado, seguro).
2. `try (...)` é **try-with-resources**: fecha a `Connection` automaticamente no fim (mesmo com erro). Evita vazamento.
3. `prepareStatement(sql)` cria o comando preparado.
4–5. `setString(posição, valor)` preenche os `?` na ordem (começa em **1**, não 0).
6. `executeUpdate()` é para `INSERT/UPDATE/DELETE` — retorna o número de linhas afetadas.
7. `SQLException` é **checked** — o compilador obriga tratar (try/catch ou throws).
8. `SELECT` lista as colunas explicitamente (boa prática vs `SELECT *`).
9. `executeQuery()` é para `SELECT` — retorna um `ResultSet`.
10. `rs.next()` avança para a próxima linha; retorna `false` quando acaba. O `while` percorre todas.
11. `rs.getInt("coluna")` / `rs.getString("coluna")` leem os valores da linha atual.
12. `if (rs.next())` (em vez de `while`) porque esperamos no máximo **uma** linha.

### Mundo real / analogia

JDBC é como um **garçom** entre você (programa) e a cozinha (banco): você dá o pedido (SQL), ele leva, busca o prato pronto (ResultSet) e traz de volta. O DAO é o **cardápio com nomes amigáveis** (`listar`, `salvar`) — você não fala "cozinha" direto.

## 8.5 Erros comuns

- Esquecer de **fechar** Connection/Statement/ResultSet (use try-with-resources).
- Usar `Statement` + concatenação com dados do usuário → **SQL Injection**.
- Índice do `setString` começando em 0 (é **1**).
- Confundir `executeQuery()` (SELECT, retorna ResultSet) com `executeUpdate()` (INSERT/UPDATE/DELETE, retorna int).
- Não tratar `SQLException` (é checked).

## 8.6 Como reconhecer na prova

- `DriverManager.getConnection` → abrindo conexão.
- `prepareStatement` + `?` + `setX` → PreparedStatement (segurança).
- `executeQuery` + `ResultSet` + `rs.next()` → leitura (SELECT).
- `executeUpdate` → escrita (INSERT/UPDATE/DELETE).
- Classe `XxxDAO` com métodos CRUD → padrão DAO.
- SQL com `+ variavel +` → vulnerabilidade a injection.

## 8.7 Questões estilo professor

> **(1)** O método correto para executar um `INSERT` é:
> A) `executeQuery()`  B) `executeUpdate()`  C) `next()`  D) `getConnection()`
>
> **(2)** O principal motivo para usar `PreparedStatement` com `?` é:
> A) deixar o código maior  B) prevenir SQL Injection e melhorar desempenho  C) evitar herança  D) substituir o DAO

### Como responder

**(1) B.** `executeUpdate()` é para comandos que **alteram** dados (INSERT/UPDATE/DELETE) e retorna linhas afetadas. `executeQuery()` é só para SELECT.
**(2) B.** O `?` separa **dado** de **comando**, neutralizando injection, e o banco reaproveita o plano de execução (mais rápido).

## 8.8 Resumo relâmpago

DriverManager abre Connection. PreparedStatement (`?` + `setX`) é seguro contra injection. `executeQuery`→SELECT→ResultSet (`next()`); `executeUpdate`→INSERT/UPDATE/DELETE. DAO isola o acesso a dados. Feche os recursos (try-with-resources).

## Exercícios — Parte 8 (JDBC)

### Objetivas

**1.** Abre a conexão com o banco:
A) ResultSet  B) DriverManager.getConnection  C) executeQuery  D) Statement.close

**2.** `PreparedStatement` com `?` protege contra:
A) NullPointer  B) SQL Injection  C) herança  D) overflow

**3.** Para um `SELECT` usa-se:
A) executeUpdate  B) executeQuery  C) commit  D) setString

**4.** `rs.next()` faz:
A) abre conexão  B) avança para a próxima linha do ResultSet  C) executa update  D) fecha o banco

**5.** O índice do primeiro `?` em `setString(?, ...)` é:
A) 0  B) 1  C) -1  D) qualquer

**6.** A classe que isola o acesso a dados (CRUD) é o padrão:
A) Singleton  B) DAO  C) Observer  D) Adapter

**7.** `SQLException` é:
A) unchecked  B) checked (obriga tratar)  C) Error  D) interface

**8.** Concatenar SQL com `" + login + "` é:
A) recomendado  B) vulnerável a SQL Injection  C) mais rápido  D) obrigatório

**9.** `executeUpdate()` retorna:
A) ResultSet  B) número de linhas afetadas  C) Connection  D) void

**10.** Para fechar recursos automaticamente usa-se:
A) finally manual sempre  B) try-with-resources  C) Garbage Collector  D) System.exit

### Discursivas

**D1.** Escreva o método `inserir(Produto p)` de um `ProdutoDAO` usando `PreparedStatement`, explicando por que ele é seguro contra SQL Injection.

**D2.** Explique o papel de `Connection`, `PreparedStatement` e `ResultSet` num `SELECT`, e a diferença entre `executeQuery` e `executeUpdate`.

> **Gabarito Parte 8:** 1-B, 2-B, 3-B, 4-B, 5-B, 6-B, 7-B, 8-B, 9-B, 10-B.


---

# PARTE 9 — Spring Boot

## 9.1 O que é Spring Boot

### 1. O que é

**Spring** é um framework Java para construir aplicações (especialmente backend/web). **Spring Boot** é uma camada em cima do Spring que **elimina configuração manual**: você cria um projeto que **já vem pronto para rodar** (servidor embutido, configurações padrão), e foca só na lógica.

### 2. Por que existe

Configurar Spring "puro" antigamente era trabalhoso (muito XML, muito setup). Spring Boot adota **"convenção sobre configuração"**: ele assume padrões sensatos e só pede o que é específico do seu app. Você escreve menos e entrega mais rápido.

### 3. Como funciona — Injeção de Dependência e Beans

O coração do Spring é o **container de Injeção de Dependência (DI)**:

- Um **Bean** é um objeto **gerenciado pelo Spring** (ele cria, guarda e entrega quando preciso).
- **Injeção de Dependência** é o Spring **entregar automaticamente** as dependências de uma classe, em vez de você dar `new`. Isso é o **DIP** do SOLID aplicado pelo framework.
- **Autowiring** é o mecanismo que **liga** as peças: quando uma classe precisa de outra, o Spring encontra o Bean certo e injeta.

Anotações-chave:

| Anotação | Significado |
|---|---|
| `@SpringBootApplication` | classe principal que inicia o app |
| `@RestController` | classe que recebe requisições HTTP (camada web) |
| `@Service` | classe de regra de negócio (Bean de serviço) |
| `@Repository` | classe de acesso a dados (Bean de persistência/DAO) |
| `@Component` | Bean genérico gerenciado pelo Spring |
| `@Autowired` | injeta o Bean correspondente |
| `@Entity` | classe que mapeia uma tabela do banco |

## 9.2 As camadas (arquitetura em camadas)

A arquitetura típica do projeto final separa responsabilidades em camadas — isso **é SRP em escala de arquitetura**:

```
Cliente (Postman/navegador)
        │  HTTP (JSON)
        ▼
┌─────────────────┐
│   Controller    │  recebe a requisição, devolve a resposta (camada web)
└────────┬────────┘
         ▼
┌─────────────────┐
│    Service      │  regra de negócio (validações, orquestração)
└────────┬────────┘
         ▼
┌─────────────────┐
│ Repository/DAO  │  acesso ao banco (SQL/JDBC)
└────────┬────────┘
         ▼
      Banco (MySQL)
```

| Camada | Responsabilidade | NÃO faz |
|---|---|---|
| **Controller** | mapear rotas, receber/retornar dados HTTP | regra de negócio, SQL |
| **Service** | regra de negócio, validações, orquestrar | falar HTTP, SQL direto |
| **Repository/DAO** | persistência (CRUD no banco) | regra de negócio, HTTP |
| **Entity** | representar a tabela/dado | lógica de aplicação |
| **DTO** | transportar dados entre camadas/cliente | lógica |

### Entity vs DTO

- **Entity**: espelha a **tabela do banco** (`@Entity`). Tem tudo, inclusive campos internos.
- **DTO (Data Transfer Object)**: objeto **enxuto** para **trafegar** dados com o cliente, expondo só o necessário (e escondendo o que é sensível, como senha). Evita acoplar a API à estrutura do banco.

## 9.3 Exemplo de cada camada (Aluno)

### Entity

```java
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    // getters e setters
}
```

### DTO

```java
public class AlunoDTO {
    private String nome;
    private String email;   // sem id interno, sem campos sensíveis
    // getters e setters
}
```

### Repository

```java
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // CRUD já vem pronto: save, findById, findAll, deleteById...
}
```

> No projeto puramente JDBC, o `Repository` é um **DAO** escrito à mão (Parte 8). Com Spring Data JPA, a interface acima **gera** o CRUD automaticamente.

### Service

```java
@Service
public class AlunoService {

    private final AlunoRepository repository;

    @Autowired                                    // injeção de dependência
    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno criar(AlunoDTO dto) {
        Aluno a = new Aluno();
        a.setNome(dto.getNome());
        a.setEmail(dto.getEmail());
        return repository.save(a);                // delega ao repositório
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Aluno buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }
}
```

Repare: o `Service` **recebe** o `Repository` pelo construtor (DIP). Ele não dá `new AlunoRepository()`.

### Controller

```java
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    @Autowired
    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody AlunoDTO dto) {
        Aluno criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);   // 201 Created
    }

    @GetMapping
    public List<Aluno> listar() {
        return service.listar();                           // 200 OK
    }

    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();         // 204 No Content
    }
}
```

Como as camadas se relacionam: o **Controller** recebe o HTTP e chama o **Service**; o **Service** aplica a regra e chama o **Repository/DAO**; o **Repository** fala com o **banco**. Cada camada conhece só a de baixo.

## 9.4 Analogia do mundo real

Um restaurante: o **garçom** (Controller) anota o pedido do cliente; a **cozinha** (Service) prepara seguindo as regras; o **estoque/despensa** (Repository) fornece os ingredientes do depósito (banco). O garçom não cozinha; a cozinha não vai ao mercado; cada um faz o seu.

## 9.5 Erros comuns

- Colocar regra de negócio no **Controller** (deveria ir no Service).
- Colocar SQL no **Service** (deveria ir no Repository/DAO).
- Expor a **Entity** diretamente na API em vez de usar **DTO**.
- Dar `new` nas dependências em vez de **injetar** (quebra DIP e testabilidade).
- Esquecer anotações (`@Service`, `@RestController`) — o Spring não cria o Bean.

## 9.6 Como reconhecer na prova

- `@RestController`/`@Service`/`@Repository` → identificar a **camada**.
- `@Autowired` ou injeção por construtor → **DIP / injeção de dependência**.
- Pergunta "onde deve ficar a regra de negócio / o SQL" → **camadas**.

## 9.7 Questão estilo professor

> Em qual camada a **regra de negócio** (validações, cálculos) deve ficar?
> A) Controller  B) Service  C) Repository  D) Entity

**Resposta: B.** O **Service** concentra a regra de negócio. Controller só lida com HTTP; Repository só com dados; Entity só representa o dado. Misturar viola SRP.

## 9.8 Resumo relâmpago

Spring Boot = Spring com tudo pré-configurado. Bean = objeto gerenciado; DI/Autowiring = Spring injeta dependências (DIP). Camadas: Controller (HTTP) → Service (regra) → Repository/DAO (dados) → Entity. DTO transporta dados ao cliente.

---

# PARTE 10 — API REST e Métodos HTTP

## 10.1 O que é uma API REST

### 1. O que é

Uma **API** é uma forma de um sistema oferecer funcionalidades para outros sistemas. Uma **API REST** é uma API que usa o **protocolo HTTP** e trata tudo como **recursos** (ex.: `alunos`, `produtos`), manipulados por **métodos HTTP** (GET, POST, PUT, PATCH, DELETE), normalmente trocando **JSON**.

### 2. Por que existe

Para que clientes diferentes (site, app mobile, outro servidor) consumam o **mesmo backend** de forma padronizada, sobre a infraestrutura já existente da web (HTTP). REST é simples, sem estado (*stateless*) e amplamente adotado.

### 3. Recursos e URLs

Cada **recurso** tem uma URL. Os métodos HTTP definem **o que fazer** com ele:

```
GET    /alunos        -> lista todos os alunos
GET    /alunos/5      -> busca o aluno de id 5
POST   /alunos        -> cria um novo aluno
PUT    /alunos/5      -> atualiza (todo) o aluno 5
PATCH  /alunos/5      -> atualiza parte do aluno 5
DELETE /alunos/5      -> remove o aluno 5
```

## 10.2 CRUD × HTTP × SQL — a tabela que une tudo

| Operação CRUD | Método HTTP | SQL equivalente | Código de sucesso típico |
|---|---|---|---|
| Create | **POST** | INSERT | 201 Created |
| Read | **GET** | SELECT | 200 OK |
| Update (total) | **PUT** | UPDATE | 200 OK (ou 204) |
| Update (parcial) | **PATCH** | UPDATE (parte) | 200 OK |
| Delete | **DELETE** | DELETE | 204 No Content |

> Essa tabela conecta **três** partes da apostila: CRUD (conceito), HTTP (transporte) e SQL/JDBC (persistência). É a espinha dorsal do projeto final.

### Quando usar cada método

- **GET**: apenas **lê**, nunca altera. Deve ser *seguro* e *idempotente* (repetir não muda nada).
- **POST**: **cria** um recurso novo. **Não** é idempotente (dois POST = dois recursos).
- **PUT**: **substitui** o recurso inteiro. Idempotente (repetir dá o mesmo resultado).
- **PATCH**: altera **parte** do recurso (só os campos enviados).
- **DELETE**: **remove**. Idempotente (deletar de novo continua "deletado").

### PUT vs PATCH (pegadinha clássica)

- **PUT** = você manda o objeto **completo**; o que não vier é sobrescrito/zerado.
- **PATCH** = você manda **só o campo** que mudou.

## 10.3 Códigos de status HTTP

O servidor responde com um **código** que resume o que aconteceu. Famílias:

| Faixa | Significado geral |
|---|---|
| 2xx | sucesso |
| 4xx | erro **do cliente** (quem chamou errou) |
| 5xx | erro **do servidor** |

Os que o professor cobra:

| Código | Nome | Quando |
|---|---|---|
| **200** | OK | sucesso geral (GET, PUT que retorna corpo) |
| **201** | Created | recurso **criado** com sucesso (resposta a POST) |
| **204** | No Content | sucesso **sem corpo** de resposta (típico de DELETE) |
| **400** | Bad Request | requisição **malformada/inválida** (cliente errou os dados) |
| **404** | Not Found | recurso **não existe** (ex.: GET /alunos/999) |
| **500** | Internal Server Error | erro **inesperado no servidor** (exceção não tratada) |

### Como decidir o código

- Criou algo? → **201**.
- Leu/atualizou com retorno? → **200**.
- Apagou sem retornar corpo? → **204**.
- O cliente mandou dado inválido? → **400**.
- Pediu algo que não existe? → **404**.
- Quebrou no servidor? → **500**.

## 10.4 Exemplo no estilo do projeto

```java
@PostMapping
public ResponseEntity<Aluno> criar(@RequestBody AlunoDTO dto) {
    Aluno criado = service.criar(dto);
    return ResponseEntity.status(201).body(criado);   // POST -> 201 Created
}

@GetMapping("/{id}")
public ResponseEntity<Aluno> buscar(@PathVariable Long id) {
    Aluno a = service.buscar(id);
    if (a == null) return ResponseEntity.notFound().build();   // 404
    return ResponseEntity.ok(a);                               // 200
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deletar(@PathVariable Long id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();         // 204
}
```

## 10.5 Erros comuns

- Usar **GET** para alterar dados (GET nunca altera).
- Retornar **200** ao criar (o correto é **201**).
- Retornar **200 com corpo vazio** num DELETE (o correto é **204**).
- Confundir **400** (cliente errou) com **500** (servidor quebrou).
- Confundir **PUT** (total) com **PATCH** (parcial).

## 10.6 Como reconhecer na prova

- `@PostMapping` + criação → 201; `@DeleteMapping` → 204; busca inexistente → 404.
- Enunciado "qual método para **criar**" → POST; "para **buscar**" → GET; "para **remover**" → DELETE.
- "Cliente enviou dados inválidos" → 400; "recurso não encontrado" → 404.

## 10.7 Questão estilo professor

> **(1)** Para **criar** um novo recurso, o método e o código de sucesso são:
> A) GET / 200  B) POST / 201  C) DELETE / 204  D) PUT / 404
>
> **(2)** Um GET em `/alunos/999` para um aluno inexistente deve retornar:
> A) 200  B) 201  C) 404  D) 500

### Como responder

**(1) B.** POST cria; o sucesso de criação é 201 Created.
**(2) C.** O recurso não existe → 404 Not Found. Não é 500 (não houve erro do servidor, foi pedido de algo inexistente), nem 200 (não há recurso para retornar).

## 10.8 Resumo relâmpago

REST = recursos via HTTP. POST/cria/201, GET/lê/200, PUT/substitui, PATCH/parcial, DELETE/remove/204. 4xx = cliente errou (400 inválido, 404 não existe); 5xx = servidor quebrou (500).

---

# PARTE 11 — Swagger / OpenAPI

## 1. O que é

**OpenAPI** é um **padrão para descrever APIs REST** (rotas, parâmetros, respostas). **Swagger** é o conjunto de ferramentas que usa esse padrão — em especial a **Swagger UI**, uma página web gerada automaticamente que **documenta e permite testar** sua API pelo navegador.

## 2. Por que serve / vantagens

- **Documentação automática**: a cada endpoint criado, a doc se atualiza sozinha.
- **Testes pelo navegador**: dá para chamar os endpoints (GET/POST/...) direto na Swagger UI, sem Postman.
- **Contrato claro** entre back e front: o time do front vê exatamente o que enviar e o que recebe.
- Reduz documentação manual desatualizada.

## 3. Integração com Spring Boot

Adiciona-se uma dependência (ex.: **springdoc-openapi**) e a Swagger UI fica disponível numa URL (tipicamente `/swagger-ui.html`). Anotações opcionais enriquecem a doc:

```java
@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Operações de alunos")
public class AlunoController {

    @Operation(summary = "Cria um novo aluno")
    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody AlunoDTO dto) { ... }
}
```

O Swagger lê os mapeamentos (`@GetMapping`, `@PostMapping`, etc.) e monta a documentação interativa.

## 4. Analogia

Swagger é o **manual interativo** de um aparelho: além de explicar cada botão, ele deixa você **apertar os botões ali mesmo** para ver o que acontece.

## 5. Como reconhecer na prova

- Menção a "documentação automática da API", "testar endpoints no navegador", "Swagger UI / OpenAPI".
- Anotações `@Operation`, `@Tag`, ou URL `/swagger-ui.html`.

## 6. Questão estilo professor

> O Swagger/OpenAPI em um projeto Spring Boot serve principalmente para:
> A) compilar o código  B) documentar e testar a API automaticamente  C) substituir o banco  D) criar as entidades

**Resposta: B.** Swagger gera **documentação interativa** e permite **testar** os endpoints. Não compila, não substitui banco nem cria entidades.

## 7. Resumo relâmpago

Swagger/OpenAPI = documentação automática + interface para testar a API no navegador. Integra ao Spring Boot por dependência; expõe a Swagger UI.

## Exercícios — Partes 9, 10 e 11

### Objetivas

**1.** A camada que concentra a regra de negócio é:
A) Controller  B) Service  C) Repository  D) Entity

**2.** `@RestController` marca a camada:
A) de dados  B) web (HTTP)  C) de negócio  D) de entidade

**3.** Para **criar** um recurso, método e código:
A) GET/200  B) POST/201  C) PUT/204  D) DELETE/200

**4.** GET em recurso inexistente deve retornar:
A) 200  B) 201  C) 404  D) 500

**5.** DELETE bem-sucedido sem corpo retorna:
A) 200  B) 201  C) 204  D) 400

**6.** Injeção de dependência no Spring aplica o princípio:
A) SRP  B) DIP  C) LSP  D) ISP

**7.** DTO serve para:
A) mapear a tabela  B) transportar dados ao cliente sem expor a entidade  C) abrir conexão  D) documentar a API

**8.** Dado inválido enviado pelo cliente → código:
A) 200  B) 400  C) 404  D) 500

**9.** Swagger serve para:
A) compilar  B) documentar/testar API  C) criar tabelas  D) injetar beans

**10.** PUT vs PATCH:
A) PUT parcial, PATCH total  B) PUT total, PATCH parcial  C) idênticos  D) ambos só leem

### Discursivas

**D1.** Descreva o caminho de uma requisição `POST /alunos` pelas camadas (Controller → Service → Repository → banco), citando a responsabilidade de cada uma e o código HTTP de resposta.

**D2.** Explique a diferença entre os códigos 400, 404 e 500, dando um cenário concreto para cada.

> **Gabarito Partes 9-11:** 1-B, 2-B, 3-B, 4-C, 5-C, 6-B, 7-B, 8-B, 9-B, 10-B.


---

# PARTE 12 — Banco de Dados e Relacionamentos entre Entidades

No projeto final as entidades não vivem isoladas: um aluno tem matrículas, um pedido tem itens, um autor escreve livros. Modelar essas ligações é o tema desta parte.

## 12.1 O que é um relacionamento

### 1. O que é

Um **relacionamento** é uma **ligação entre duas tabelas/entidades**, expressando como os dados de uma se conectam aos da outra. No mundo OO isso vira **referências entre objetos**; no banco vira **chaves estrangeiras (Foreign Keys)**.

### 2. Por que existe

Para **evitar repetição** e manter a integridade. Em vez de copiar todos os dados do curso dentro de cada aluno, o aluno guarda apenas uma **referência** (a chave) ao curso. Assim, mudar o nome do curso é feito num lugar só.

### 3. Os três tipos de cardinalidade

| Tipo | Significado | Exemplo |
|---|---|---|
| **1:1** | um registro de A liga-se a no máximo um de B | Pessoa ↔ Passaporte |
| **1:N** | um registro de A liga-se a vários de B | Curso → Alunos (um curso, muitos alunos) |
| **N:N** | vários de A ligam-se a vários de B | Aluno ↔ Disciplina (aluno faz várias disciplinas; disciplina tem vários alunos) |

## 12.2 Foreign Key (Chave Estrangeira)

### O que é

Uma **Foreign Key (FK)** é uma coluna de uma tabela que **aponta para a chave primária (PK)** de outra. Ela é o mecanismo físico que **implementa** o relacionamento no banco e garante a **integridade referencial** (você não consegue inserir um aluno para um curso que não existe).

### Exemplo SQL (1:N — um curso, muitos alunos)

```sql
CREATE TABLE curso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    curso_id INT,                              -- a Foreign Key
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);
```

A coluna `curso_id` em `aluno` é a FK: cada aluno **referencia** um curso. Vários alunos podem ter o mesmo `curso_id` → **1:N**. O lado "muitos" (aluno) é quem carrega a FK.

## 12.3 Mapeando relacionamentos no JPA/Spring (@JoinColumn e @JoinTable)

Quando usamos JPA (Spring Data), anotações descrevem o relacionamento nas **entidades**.

### 1:N e N:1 com @JoinColumn

```java
@Entity
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "curso")
    private List<Aluno> alunos;     // lado "um": uma lista de muitos
}

@Entity
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "curso_id")   // a coluna FK nesta tabela
    private Curso curso;             // lado "muitos": referencia um curso
}
```

- **`@ManyToOne`** + **`@JoinColumn`**: o lado "muitos" (Aluno) guarda a FK `curso_id`. `@JoinColumn` diz **qual coluna** é a chave estrangeira.
- **`@OneToMany(mappedBy = "curso")`**: o lado "um" (Curso) apenas reflete o relacionamento já mapeado pelo outro lado (`mappedBy` aponta para o atributo `curso` em `Aluno`).

### 1:1 com @JoinColumn

```java
@Entity
public class Pessoa {
    @Id @GeneratedValue private Long id;
    @OneToOne
    @JoinColumn(name = "passaporte_id")
    private Passaporte passaporte;
}
```

`@OneToOne` + `@JoinColumn`: a tabela `pessoa` guarda a FK `passaporte_id`. Cada pessoa, um passaporte.

### N:N com @JoinTable

Em N:N **não** dá para usar uma única FK — precisa de uma **tabela intermediária** (tabela de junção/associativa) com as duas FKs. No JPA isso é `@JoinTable`:

```java
@Entity
public class Aluno {
    @Id @GeneratedValue private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(
        name = "aluno_disciplina",                         // tabela de junção
        joinColumns = @JoinColumn(name = "aluno_id"),       // FK para Aluno
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")  // FK para Disciplina
    )
    private List<Disciplina> disciplinas;
}

@Entity
public class Disciplina {
    @Id @GeneratedValue private Long id;
    private String nome;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Aluno> alunos;
}
```

A tabela `aluno_disciplina` tem duas colunas (`aluno_id`, `disciplina_id`), cada uma FK. Cada linha = "este aluno cursa esta disciplina". É assim que N:N vira duas relações 1:N por dentro.

### Resumindo as anotações

| Relacionamento | Anotação do lado dono | Mapeamento da FK |
|---|---|---|
| 1:1 | `@OneToOne` | `@JoinColumn` |
| N:1 (lado muitos) | `@ManyToOne` | `@JoinColumn` |
| 1:N (lado um) | `@OneToMany(mappedBy=...)` | — (espelho) |
| N:N | `@ManyToMany` | `@JoinTable` (joinColumns + inverseJoinColumns) |

## 12.4 Analogia do mundo real

- **1:N**: uma **mãe** tem vários **filhos**; cada filho tem uma só mãe biológica. A FK ("quem é a mãe") fica no filho.
- **N:N**: **atores** e **filmes** — cada ator faz vários filmes, cada filme tem vários atores. Precisa de uma "lista de elenco" (tabela de junção) ligando os dois.

## 12.5 Erros comuns

- Colocar a FK no lado errado (a FK vai no lado **"muitos"** num 1:N).
- Tentar fazer N:N sem tabela de junção.
- Confundir `@JoinColumn` (uma FK numa tabela, para 1:1/N:1) com `@JoinTable` (tabela intermediária, para N:N).
- Esquecer `mappedBy` e acabar criando duas relações/colunas duplicadas.

## 12.6 Como reconhecer na prova

- `@ManyToOne` + `@JoinColumn` → lado "muitos" de um **1:N**.
- `@OneToMany(mappedBy=...)` → lado "um".
- `@ManyToMany` + `@JoinTable` → **N:N** com tabela de junção.
- `FOREIGN KEY ... REFERENCES` no SQL → relacionamento via FK.

## 12.7 Questão estilo professor

> ```java
> @Entity class Pedido {
>     @ManyToOne
>     @JoinColumn(name = "cliente_id")
>     private Cliente cliente;
> }
> ```
> Esse mapeamento representa qual relacionamento, do ponto de vista de `Pedido`?
> A) 1:1  B) N:1 (muitos pedidos para um cliente)  C) N:N  D) sem relacionamento

**Resposta: B.** `@ManyToOne` indica que **muitos** `Pedido` pertencem a **um** `Cliente`; a FK `cliente_id` fica na tabela `pedido`. Olhando do `Cliente`, é 1:N. Não é 1:1 (seria `@OneToOne`) nem N:N (precisaria de `@JoinTable`).

## 12.8 Resumo relâmpago

1:1, 1:N, N:N. FK liga uma tabela à PK de outra; no 1:N a FK fica no lado "muitos". JPA: `@JoinColumn` para 1:1/N:1; `@JoinTable` (com tabela de junção) para N:N; `mappedBy` no lado espelho.

## Exercícios — Parte 12

### Objetivas

**1.** Em um relacionamento 1:N (curso → alunos), a Foreign Key fica:
A) no curso  B) no aluno (lado muitos)  C) em ambos  D) em nenhum

**2.** N:N exige:
A) só uma FK  B) tabela de junção com duas FKs  C) nenhuma FK  D) herança

**3.** `@ManyToOne` + `@JoinColumn` representa:
A) 1:1  B) N:1  C) N:N  D) sem relação

**4.** `@JoinTable` é usada em:
A) 1:1  B) 1:N  C) N:N  D) nenhuma

**5.** Aluno ↔ Disciplina (cada um com vários do outro) é:
A) 1:1  B) 1:N  C) N:N  D) N:1

**6.** A integridade referencial é garantida por:
A) PRIMARY KEY  B) FOREIGN KEY  C) INDEX  D) VARCHAR

**7.** `@OneToMany(mappedBy = "curso")` indica:
A) o lado dono da FK  B) o lado espelho do relacionamento  C) N:N  D) 1:1

**8.** Pessoa ↔ Passaporte é tipicamente:
A) 1:1  B) 1:N  C) N:N  D) N:1

**9.** `@OneToOne` usa para mapear a FK:
A) @JoinTable  B) @JoinColumn  C) @Column  D) @Id

**10.** No JPA, `mappedBy` serve para:
A) criar a coluna FK  B) indicar qual lado é o dono e evitar coluna duplicada  C) deletar registros  D) ordenar

### Discursivas

**D1.** Modele (em JPA, com anotações) o relacionamento entre `Autor` e `Livro` sabendo que um autor escreve vários livros e cada livro tem um único autor. Indique onde fica a FK.

**D2.** Explique por que um relacionamento N:N precisa de uma tabela de junção, dando um exemplo concreto e mostrando as colunas dessa tabela.

> **Gabarito Parte 12:** 1-B, 2-B, 3-B, 4-C, 5-C, 6-B, 7-B, 8-A, 9-B, 10-B.
> **D1 esperado:** `Livro` com `@ManyToOne @JoinColumn(name="autor_id") private Autor autor;` (FK fica em `livro`); `Autor` com `@OneToMany(mappedBy="autor") private List<Livro> livros;`.


---

# PARTE 13 — Projeto Final: Tudo Junto numa API REST

Esta parte mostra como **todos os conceitos da disciplina aparecem juntos** num projeto realista, parecido com o que vocês fizeram: uma API REST em **Spring Boot + JDBC/MySQL**, com **DAO**, **arquitetura em camadas**, **Singleton**, **Factory Method**, **Clean Code**, **SOLID** e **Swagger**.

Vamos construir uma API de **Biblioteca** (entidades `Livro` e `Autor`, relacionamento N:1).

## 13.1 Visão do mapa de conceitos

| Conceito da disciplina | Onde aparece no projeto |
|---|---|
| POO / Encapsulamento | Entities com atributos `private` + getters/setters |
| Herança / Polimorfismo | estratégias e exceptions especializadas |
| Exceptions | tratamento de "não encontrado" / erros de SQL |
| Clean Code | nomes significativos, métodos pequenos por camada |
| SRP | cada camada uma responsabilidade |
| OCP / Strategy | cálculo de multa por tipo de usuário |
| DIP | Service depende de interface do DAO (injeção) |
| Singleton | fábrica de conexão única com o MySQL |
| Factory Method | criação de objetos de notificação |
| JDBC / DAO | persistência com PreparedStatement |
| Relacionamento N:1 | `Livro` referencia `Autor` (FK) |
| REST / HTTP | endpoints CRUD com códigos corretos |
| Swagger | documentação automática |

## 13.2 Camada de conexão — Singleton (DIP na prática)

```java
public class ConexaoSingleton {
    private static ConexaoSingleton instancia;     // única instância
    private Connection connection;

    private ConexaoSingleton() throws SQLException { // construtor privado
        this.connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/biblioteca", "root", "1234");
    }

    public static ConexaoSingleton getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConexaoSingleton();
        }
        return instancia;
    }

    public Connection getConnection() { return connection; }
}
```

**Singleton** garante **uma** conexão central com o MySQL. (Conceito da Parte 7 + JDBC da Parte 8.)

## 13.3 Entities com relacionamento N:1 (Parte 12)

```java
public class Autor {
    private Long id;
    private String nome;
    // getters/setters (encapsulamento)
}

public class Livro {
    private Long id;
    private String titulo;
    private Long autorId;   // FK -> Autor (lado "muitos" do 1:N)
    // getters/setters
}
```

## 13.4 DAO — interface + implementação (DIP + SRP + JDBC)

```java
// abstração: o Service vai depender DESTA interface (DIP)
public interface LivroDAO {
    void inserir(Livro livro);
    List<Livro> listar();
    Livro buscarPorId(Long id);
    void atualizar(Livro livro);
    void deletar(Long id);
}

// implementação concreta com JDBC (Parte 8)
public class LivroDAOJdbc implements LivroDAO {

    public void inserir(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor_id) VALUES (?, ?)";
        try (Connection conn = ConexaoSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setLong(2, livro.getAutorId());
            ps.executeUpdate();                       // executeUpdate -> INSERT
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro", e);
        }
    }

    public List<Livro> listar() {
        String sql = "SELECT id, titulo, autor_id FROM livro";
        List<Livro> livros = new ArrayList<>();
        try (Connection conn = ConexaoSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {       // executeQuery -> SELECT
            while (rs.next()) {
                Livro l = new Livro();
                l.setId(rs.getLong("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutorId(rs.getLong("autor_id"));
                livros.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros", e);
        }
        return livros;
    }

    public Livro buscarPorId(Long id) {
        String sql = "SELECT id, titulo, autor_id FROM livro WHERE id = ?";
        try (Connection conn = ConexaoSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Livro l = new Livro();
                    l.setId(rs.getLong("id"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setAutorId(rs.getLong("autor_id"));
                    return l;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro", e);
        }
        return null;
    }

    public void atualizar(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor_id = ? WHERE id = ?";
        try (Connection conn = ConexaoSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setLong(2, livro.getAutorId());
            ps.setLong(3, livro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (Connection conn = ConexaoSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar livro", e);
        }
    }
}
```

Aqui convivem: **DAO** (SRP — só acesso a dados), **PreparedStatement** (anti-SQL-Injection), **try-with-resources** (fecha recursos), **interface** (DIP).

## 13.5 Factory Method (Parte 7) — criando notificações

```java
public interface Notificacao { void enviar(String msg); }
public class NotificacaoEmail implements Notificacao {
    public void enviar(String msg){ System.out.println("Email: " + msg); }
}
public class NotificacaoSms implements Notificacao {
    public void enviar(String msg){ System.out.println("SMS: " + msg); }
}
public class NotificacaoFactory {
    public static Notificacao criar(String tipo) {
        if ("EMAIL".equals(tipo)) return new NotificacaoEmail();
        if ("SMS".equals(tipo))   return new NotificacaoSms();
        throw new IllegalArgumentException("Tipo inválido: " + tipo);
    }
}
```

## 13.6 Service — regra de negócio (SRP + DIP + Exceptions)

```java
@Service
public class LivroService {

    private final LivroDAO livroDAO;   // depende da ABSTRAÇÃO (DIP)

    @Autowired
    public LivroService(LivroDAO livroDAO) {   // injeção pelo construtor
        this.livroDAO = livroDAO;
    }

    public Livro cadastrar(LivroDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório"); // -> 400
        }
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutorId(dto.getAutorId());
        livroDAO.inserir(livro);
        return livro;
    }

    public List<Livro> listar() { return livroDAO.listar(); }

    public Livro buscar(Long id) {
        Livro livro = livroDAO.buscarPorId(id);
        if (livro == null) {
            throw new RecursoNaoEncontradoException("Livro " + id + " não encontrado"); // -> 404
        }
        return livro;
    }
}
```

O Service **valida** (regra), **lança exceções** específicas e **delega** a persistência ao DAO. Não tem SQL nem HTTP — **SRP**.

## 13.7 Controller — camada web (REST + HTTP)

```java
@RestController
@RequestMapping("/livros")
@Tag(name = "Livros")   // Swagger
public class LivroController {

    private final LivroService service;

    @Autowired
    public LivroController(LivroService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra um livro")
    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody LivroDTO dto) {
        Livro criado = service.cadastrar(dto);
        return ResponseEntity.status(201).body(criado);   // 201 Created
    }

    @GetMapping
    public List<Livro> listar() {
        return service.listar();                           // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));      // 200 (ou 404 via handler)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();         // 204 No Content
    }
}
```

## 13.8 Tratamento global de exceções → códigos HTTP corretos

```java
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> naoEncontrado(RecursoNaoEncontradoException e) {
        return ResponseEntity.status(404).body(e.getMessage());   // 404
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> invalido(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());   // 400
    }
}
```

Isso liga a Parte 2 (Exceptions) com a Parte 10 (códigos HTTP): a exceção de negócio vira o **status HTTP correto**.

## 13.9 O fluxo completo de uma requisição

Quando o cliente faz `POST /livros` com `{ "titulo": "Clean Code", "autorId": 1 }`:

1. **Controller** recebe o JSON, transforma em `LivroDTO`, chama `service.cadastrar(dto)`.
2. **Service** valida o título (se vazio → `IllegalArgumentException` → **400**), cria o `Livro`, chama `livroDAO.inserir(livro)`.
3. **DAO** monta o `INSERT` com `PreparedStatement` (seguro), usa a `Connection` do **Singleton**, executa.
4. **MySQL** grava a linha (com a FK `autor_id`).
5. **Controller** devolve **201 Created** com o livro.

Cada camada fez **só a sua parte** (SRP); o Service dependeu da **interface** do DAO (DIP); a criação foi segura (PreparedStatement); o Swagger documentou tudo automaticamente.

## 13.10 Checklist de revisão do projeto

- [ ] Entities encapsuladas (`private` + acessadores).
- [ ] Camadas separadas (Controller / Service / DAO).
- [ ] DAO com `PreparedStatement` (sem concatenar SQL).
- [ ] Conexão via Singleton.
- [ ] Service depende de interface (DIP) e injeta por construtor.
- [ ] Exceptions mapeadas para 400/404/500.
- [ ] Códigos HTTP corretos (201 ao criar, 204 ao deletar).
- [ ] Relacionamentos com FK (`@JoinColumn` / `@JoinTable`).
- [ ] Swagger documentando a API.
- [ ] Nomes significativos e métodos pequenos (Clean Code).


---

# SIMULADO FINAL — 30 QUESTÕES

Estilo da prova: **interpretação de código**. Leia cada trecho com atenção. Marque uma alternativa. O gabarito **comentado** vem logo após — resista a olhar antes de tentar.

---

**Q1.** Considere:
```java
abstract class Forma { abstract double area(); }
class Circulo extends Forma { double r; double area(){ return 3.14*r*r; } }
Forma f = new Circulo();
System.out.println(f.area());
```
A chamada `f.area()` executar a versão do `Circulo` ocorre por:
A) sobrecarga  B) composição  C) polimorfismo dinâmico  D) encapsulamento  E) não há polimorfismo, pois `Forma` é abstrata

**Q2.** Qual trecho aplica corretamente **encapsulamento**?
A) `public class C { public double saldo; }`
B) `public class C { private double saldo; public void depositar(double v){ if(v>0) saldo+=v; } }`
C) `public class C { double saldo; void x(){ saldo = saldo; } }`
D) `public class C { protected double saldo; }`

**Q3.** O método abaixo é chamado com `dividir(10, 0)`:
```java
public static int dividir(int a, int b){ return a / b; }
```
O que ocorre?
A) imprime 0  B) `ArithmeticException` em runtime (unchecked)  C) erro de compilação  D) imprime Infinity

**Q4.** Qual é um comentário **necessário** (Clean Code)?
A) `int n = lista.size(); // tamanho da lista`
B) `i++; // incrementa i`
C) `// a API externa devolve a data em UTC; convertemos para o fuso local`
D) `int x = 2; // dois`

**Q5.** A classe viola qual princípio SOLID?
```java
class RelatorioAluno {
    void gerar(){}
    void salvarEmArquivo(){}
    void enviarPorEmail(){}
}
```
A) OCP  B) SRP  C) LSP  D) DIP

**Q6.** Para adicionar tipos de desconto **sem modificar** o método existente, a melhor estratégia (OCP) é:
A) mais `if/else`  B) `switch-case`  C) interface de estratégias + implementações  D) tornar o método `protected`

**Q7.** Identifique o padrão:
```java
class Conexao {
    private static Conexao instancia;
    private Conexao(){}
    public static Conexao getInstance(){
        if(instancia==null) instancia=new Conexao();
        return instancia;
    }
}
```
A) Factory  B) Singleton  C) Observer  D) Strategy

**Q8.** O código viola qual princípio?
```java
interface Multimidia {
    void audio(); void video(); void imprimir();
}
class CaixaSom implements Multimidia {
    public void audio(){ System.out.println("som"); }
    public void video(){ throw new UnsupportedOperationException(); }
    public void imprimir(){ throw new UnsupportedOperationException(); }
}
```
A) SRP  B) OCP  C) ISP  D) DIP

**Q9.** Qual comando JDBC é correto para um `SELECT`?
A) `ps.executeUpdate()`  B) `ps.executeQuery()`  C) `ps.commit()`  D) `ps.close()`

**Q10.** Por que usar `PreparedStatement` com `?`?
A) deixa o código maior  B) previne SQL Injection e melhora desempenho  C) substitui o DAO  D) evita herança

**Q11.** Para **criar** um recurso numa API REST, método e código de sucesso:
A) GET / 200  B) POST / 201  C) PUT / 204  D) DELETE / 200

**Q12.** Um `GET /produtos/999` para um produto inexistente deve retornar:
A) 200  B) 201  C) 404  D) 500

**Q13.** Identifique o padrão:
```java
interface Pagamento { void pagar(double v); }
class PayPalApi { void enviar(double d){} }
class PayPalAdapter implements Pagamento {
    private PayPalApi api = new PayPalApi();
    public void pagar(double v){ api.enviar(v); }
}
```
A) Decorator  B) Adapter  C) Strategy  D) Singleton

**Q14.** Qual nome segue melhor as boas práticas (Clean Code/convenções)?
A) `class C { int x; void m(){} }`
B) `class Dados { int a,b,c; }`
C) `class Usuario { String nomeCompleto; int calcularIdade(){...} }`
D) `class Xpto { int valorzinho; }`

**Q15.** Em Spring Boot, a **regra de negócio** deve ficar na camada:
A) Controller  B) Service  C) Repository  D) Entity

**Q16.** Sobre `Pinguim extends Ave` cujo `voar()` lança `UnsupportedOperationException`:
A) aplica polimorfismo corretamente  B) viola LSP  C) viola DIP  D) está perfeito

**Q17.** A anotação que injeta uma dependência gerenciada pelo Spring é:
A) `@Entity`  B) `@Autowired`  C) `@Override`  D) `@Id`

**Q18.** Considere:
```java
class A { void f(){} }
class B extends A { void g(){} }
A obj = new B();
obj.g();
```
O que acontece?
A) compila e roda  B) erro de compilação: `g()` não existe no tipo `A`  C) erro em runtime  D) imprime null

**Q19.** Qual representa **sobrecarga**?
A) duas classes com o mesmo método sobrescrito
B) `int somar(int a,int b)` e `double somar(double a,double b)` na mesma classe
C) `@Override` em subclasse
D) interface com um método

**Q20.** Relacionamento Aluno ↔ Disciplina (cada um com vários do outro) usa, no JPA:
A) `@OneToOne` + `@JoinColumn`  B) `@ManyToOne` + `@JoinColumn`  C) `@ManyToMany` + `@JoinTable`  D) nenhuma anotação

**Q21.** O código abaixo viola qual princípio?
```java
class Computador {
    private TecladoABNT teclado = new TecladoABNT();
}
```
A) SRP  B) OCP  C) DIP  D) ISP

**Q22.** Qual método HTTP é **idempotente e apenas lê**?
A) POST  B) GET  C) PATCH  D) DELETE

**Q23.** O Checkstyle **mais provavelmente** acusa violação em:
A) `private int idade;`  B) `import java.util.*;`  C) `public class Pedido {`  D) `static final int MAX = 10;`

**Q24.** Identifique o padrão:
```java
Cafe c = new ComChocolate(new ComLeite(new CafeSimples()));
```
A) Adapter  B) Decorator  C) Factory  D) Observer

**Q25.** Qual código de status indica que **o cliente enviou dados inválidos**?
A) 200  B) 201  C) 400  D) 500

**Q26.** Em um 1:N (Curso → Alunos), a Foreign Key fica:
A) na tabela curso  B) na tabela aluno (lado "muitos")  C) em ambas  D) em nenhuma

**Q27.** Identifique o padrão:
```java
class Canal {
    private List<Observer> inscritos = new ArrayList<>();
    void publicar(String v){ for(Observer o: inscritos) o.atualizar(v); }
}
```
A) Strategy  B) Observer  C) Adapter  D) Singleton

**Q28.** O método para um `DELETE` bem-sucedido sem corpo de resposta retorna:
A) 200  B) 201  C) 204  D) 404

**Q29.** Sobre exceções **checked** (ex.: `SQLException`):
A) o compilador não obriga tratar  B) o compilador obriga tratar (try/catch ou throws)  C) são `Error`  D) são `RuntimeException`

**Q30.** No projeto, o **DAO** (`LivroDAOJdbc`) tem como responsabilidade:
A) regra de negócio  B) receber requisições HTTP  C) acesso a dados (CRUD no banco)  D) documentar a API

---

# GABARITO COMENTADO — SIMULADO FINAL

> Para cada questão: alternativa correta, o **raciocínio**, e **por que as outras erram**. Estude as explicações — é nelas que mora o aprendizado.

**Q1 — C (polimorfismo dinâmico).** A referência é `Forma`, o objeto é `Circulo`; a JVM resolve `area()` em runtime pelo tipo real.
- A erra: sobrecarga é mesmo nome com assinaturas diferentes na mesma classe — aqui é **sobrescrita**.
- B erra: não há delegação a outro objeto (composição).
- D/E erram: abstrata não se instancia, mas a **subclasse** sim, e é daí que vem o polimorfismo.

**Q2 — B.** Atributo `private` alterado só por método que **valida** (`v>0`). É encapsulamento real.
- A erra: `public` expõe o estado.
- C erra: package-private e sem validação (`saldo=saldo` não faz nada).
- D erra: `protected` ainda permite acesso amplo e não há método validando.

**Q3 — B.** `int / 0` lança `ArithmeticException` (unchecked) → compila, quebra em runtime.
- A erra: não retorna 0, estoura.
- C erra: erro é em execução, não compilação.
- D erra: `Infinity` só com `double` (`10.0/0.0`), não com `int`.

**Q4 — C.** Explica o **porquê** (regra externa não óbvia). Os demais (A, B, D) apenas repetem o óbvio — comentários ruins.

**Q5 — B (SRP).** A classe gera, salva e envia: três motivos para mudar. Cura: separar em classes.
- OCP/LSP/DIP não se aplicam: não há `if` por tipo, nem herança quebrada, nem dependência concreta.

**Q6 — C.** Interface de estratégias permite **adicionar** sem **modificar** (OCP / Strategy).
- A, B erram: continuam modificando o método a cada tipo.
- D erra: `protected` é acesso, não extensibilidade.

**Q7 — B (Singleton).** Construtor `private` + `static instancia` + `getInstance()` é a assinatura inconfundível.
- Factory cria objetos variados; Observer notifica; Strategy troca algoritmo.

**Q8 — C (ISP).** A interface gorda obriga `CaixaSom` a implementar métodos que não usa (jogando exceção). Cura: interfaces pequenas.
- Não é SRP (é sobre **interface**, não classe), nem OCP, nem DIP.

**Q9 — B.** `executeQuery()` retorna `ResultSet` (para SELECT). `executeUpdate()` é para INSERT/UPDATE/DELETE.

**Q10 — B.** O `?` separa dado de comando (anti-injection) e o banco reaproveita o plano (desempenho).

**Q11 — B.** POST cria; sucesso de criação é **201 Created**.
- GET lê (200); PUT/DELETE não criam.

**Q12 — C (404).** Recurso não existe. Não é 500 (não houve erro do servidor) nem 200 (não há o que retornar).

**Q13 — B (Adapter).** Implementa a interface esperada (`Pagamento`) e **traduz** para uma API incompatível (`PayPalApi`).
- Decorator manteria a interface e **adicionaria** comportamento; aqui há **tradução** de interface.

**Q14 — C.** `Usuario`, `nomeCompleto`, `calcularIdade()` revelam intenção. A, B, D usam nomes vazios (`C`, `Dados`, `a/b/c`, `Xpto`, `valorzinho`).

**Q15 — B (Service).** Controller cuida do HTTP; Repository, dos dados; Entity, do dado. Regra de negócio = Service (SRP em camadas).

**Q16 — B (viola LSP).** O `Pinguim` não substitui `Ave` sem quebrar (`voar()` lança exceção). Subclasse deve honrar o contrato da mãe.

**Q17 — B (`@Autowired`).** Injeta o Bean gerenciado pelo Spring (DIP na prática).
- `@Entity` mapeia tabela; `@Override` redefine método; `@Id` marca PK.

**Q18 — B.** `obj` é do tipo `A`; o compilador só permite chamar o que existe em `A`. `g()` só existe em `B` → **erro de compilação** (mesmo o objeto sendo `B`). Tipo da **referência** limita o que pode ser chamado.

**Q19 — B.** Mesmo nome, **assinaturas diferentes**, mesma classe = sobrecarga (estático).
- A e C descrevem **sobrescrita**; D não tem relação.

**Q20 — C.** N:N usa `@ManyToMany` + `@JoinTable` (tabela de junção com duas FKs).
- A é 1:1; B é N:1; D ignora o relacionamento.

**Q21 — C (DIP).** `Computador` depende do **concreto** `TecladoABNT` (com `new` interno). Cura: depender de uma interface `Teclado` e injetar.
- Não é SRP/OCP/ISP — o problema é depender de implementação, não de abstração.

**Q22 — B (GET).** GET só lê, é seguro e idempotente.
- POST não é idempotente; PATCH/DELETE alteram.

**Q23 — B.** `import java.util.*;` (star import) é violação clássica do Checkstyle. As demais seguem o padrão.

**Q24 — B (Decorator).** Objetos da mesma interface envolvendo uns aos outros e **somando** comportamento (`new A(new B(new C()))`).

**Q25 — C (400).** Bad Request = cliente enviou dados inválidos/malformados.
- 500 seria erro **do servidor**; 200/201 são sucesso.

**Q26 — B.** No 1:N, a FK fica no lado **"muitos"** (aluno guarda `curso_id`).

**Q27 — B (Observer).** Lista de `Observer` + laço chamando `atualizar()` = notificação um-para-muitos automática.

**Q28 — C (204).** DELETE bem-sucedido sem corpo retorna **No Content**.
- 200 teria corpo; 201 é criação; 404 é não encontrado.

**Q29 — B.** Checked obriga tratar (try/catch) ou declarar (`throws`). `SQLException` é checked.
- Unchecked (Runtime) e Error **não** obrigam.

**Q30 — C.** DAO isola o **acesso a dados** (CRUD no banco). Regra = Service; HTTP = Controller; doc = Swagger.

---

## Tabela de respostas rápidas (simulado)

| Q | R | Q | R | Q | R | Q | R | Q | R |
|---|---|---|---|---|---|---|---|---|---|
| 1 | C | 7 | B | 13 | B | 19 | B | 25 | C |
| 2 | B | 8 | C | 14 | C | 20 | C | 26 | B |
| 3 | B | 9 | B | 15 | B | 21 | C | 27 | B |
| 4 | C | 10 | B | 16 | B | 22 | B | 28 | C |
| 5 | B | 11 | B | 17 | B | 23 | B | 29 | B |
| 6 | C | 12 | C | 18 | B | 24 | B | 30 | C |

---

# APÊNDICE — Mapa de Conexões entre Conceitos

Guarde estas pontes; o professor adora questões que conectam temas:

- **OCP ↔ Strategy:** Strategy é o padrão que realiza o "aberto para extensão, fechado para modificação".
- **DIP ↔ Injeção de Dependência (Spring):** depender de interface e injetar pelo construtor **é** o DIP aplicado pelo framework.
- **SRP ↔ Camadas / DAO:** separar Controller/Service/Repository é SRP em escala de arquitetura; o DAO isola a responsabilidade de persistência.
- **ISP ↔ LSP:** quebrar interfaces gordas (ISP) frequentemente resolve violações de substituibilidade (LSP), como no caso Ave/Pinguim e Dispositivos.
- **Encapsulamento ↔ Clean Code:** atributos `private` com nomes significativos atendem aos dois ao mesmo tempo.
- **CRUD ↔ HTTP ↔ SQL:** POST/INSERT/201, GET/SELECT/200, PUT/UPDATE, DELETE/DELETE/204 — a mesma operação vista em três camadas.
- **PreparedStatement ↔ Segurança:** `?` + `setX` neutraliza SQL Injection.
- **Singleton/Factory ↔ Projeto Final:** Singleton para a conexão; Factory para criar objetos sem acoplar ao concreto.
- **Exceptions ↔ Códigos HTTP:** exceções de negócio mapeadas para 400 (inválido), 404 (não encontrado), 500 (erro do servidor).

---

## Palavra final

Você chegou ao fim. Se conseguir, para cada trecho de código de uma prova, responder mentalmente **"qual conceito é esse? qual a pista no código? qual a cura?"**, está pronto. O professor não quer decoreba — quer que você **leia código e reconheça o conceito**. Releia as seções "Como reconhecer numa prova" e o gabarito comentado do simulado: é ali que está a mentalidade da prova.

Bons estudos e boa prova! 🎓

