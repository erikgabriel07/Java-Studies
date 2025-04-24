import java.util.*;

public class Main {
  public static void main(String[] args) {
    
    Biblioteca biblioteca = new Biblioteca();
    
    // Adicionando livros à Biblioteca
    biblioteca.adicionarLivro(new Livro(12345678, "Livro I", "Jedi", 2025));
    biblioteca.adicionarLivro(new Livro(32945001, "Livro II", "John", 2015));
    biblioteca.adicionarLivro(new Livro(52389475, "Livro III", "Jane", 2018));
    biblioteca.adicionarLivro(new Livro(61982372, "Livro IV", "Jack", 2021));
    biblioteca.adicionarLivro(new Livro(46978453, "Livro IV Vol.2", "Jack", 2021));
    biblioteca.adicionarLivro(new Livro(29834759, "Livro Acqua", "Marmado", 2001));
    
    // Listagem de livros
    System.out.println("Livros da biblioteca:");
    biblioteca.listarLivros();
    linha();
    
    // Criando usuários
    Aluno aluno1 = new Aluno();
    Professor professor1 = new Professor();
    
    // Emprestando livro
    System.out.println("Livros que aluno pegou emprestado:");
    aluno1.pegarLivro(biblioteca, 61982372);
    aluno1.pegarLivro(biblioteca, 32945001);
    aluno1.pegarLivro(biblioteca, 52389475);
    aluno1.mostrarLivros();
    linha();
    
    // Listagem de livros
    System.out.println("Livros da biblioteca:");
    biblioteca.listarLivros();
    linha();
    
    // Devolvendo Livros
    System.out.println("Livros restantes do aluno para devolver:");
    aluno1.devolverLivro(biblioteca, 32945001);
    aluno1.mostrarLivros();
    linha();
    
    // Listagem de livros
    System.out.println("Livros da biblioteca:");
    biblioteca.listarLivros();
    linha();
    
    // Emprestando livro
    System.out.println("Livros que professor pegou emprestado:");
    professor1.pegarLivro(biblioteca, 29834759);
    professor1.pegarLivro(biblioteca, 46978453);
    professor1.mostrarLivros();
    linha();
  }
  
  public static void linha() {
    System.out.println("——————————————————————————————————————————————————————————");
  }
}

interface Emprestimo {
  void pegarLivro(Biblioteca biblioteca, int ISBN);
  void devolverLivro(Biblioteca biblioteca, int ISBN);
}

abstract class Usuario {
  protected int qtdLivros;
  protected int devolucao;
  protected List<Livro> livrosEmprestados = new ArrayList<>();
  
  public Usuario(int qtdLivros, int devolucao) {
    this.qtdLivros = qtdLivros;
    this.devolucao = devolucao;
  }
  
  public int getQTDLivro() {
    return qtdLivros;
  }
  
  public void mostrarLivros() {
    for (Livro l : livrosEmprestados) {
      System.out.println(l);
    }
  }
}

class Aluno extends Usuario implements Emprestimo {
  public Aluno() {
    super(3, 7);
  }
  
  @Override
  public void pegarLivro(Biblioteca biblioteca, int ISBN) { // Passa a biblioteca de onde pegar o livro
    if (qtdLivros == 0) {
      System.out.println("Limite de empréstimos atingido!");
      return;
    }
    
    Livro livro = biblioteca.emprestarLivro(ISBN);
    if(livro == null) {
      throw new RuntimeException("Nenhum livro foi encontrado com ISBN " + ISBN);
    }
    livrosEmprestados.add(livro);
    
    qtdLivros -= 1;
  }
  
  @Override
  public void devolverLivro(Biblioteca biblioteca, int ISBN) {
    for(Livro l : livrosEmprestados) {
      if (l.getISBN() == ISBN) {
        l.setEmprestado(false);
        biblioteca.devolucaoDeLivro(l);
        livrosEmprestados.remove(l);
      }
    }
  }
}

class Professor extends Usuario implements Emprestimo {
  public Professor() {
    super(5, 15);
  }
  
  @Override
  public void pegarLivro(Biblioteca biblioteca, int ISBN) { // Passa a biblioteca de onde pegar o livro
    if (qtdLivros == 0) {
      System.out.println("Limite de empréstimos atingido!");
      return;
    }
    
    Livro livro = biblioteca.emprestarLivro(ISBN);
    if(livro == null) {
      throw new RuntimeException("Nenhum livro foi encontrado com ISBN " + ISBN);
    }
    livrosEmprestados.add(livro);
    
    qtdLivros -= 1;
  }
  
  @Override
  public void devolverLivro(Biblioteca biblioteca, int ISBN) {
    for(Livro l : livrosEmprestados) {
      if (l.getISBN() == ISBN) {
        l.setEmprestado(false);
        biblioteca.devolucaoDeLivro(l);
        livrosEmprestados.remove(l);
      }
    }
  }
}

class Biblioteca {
  private static List<Livro> livros = new ArrayList<>();
  
  public static void adicionarLivro(Livro livro) {
    livros.add(livro);
  }
  
  public static void listarLivros() {
    for (Livro l : livros) {
      System.out.println(l);
    }
  }
  
  public static Livro emprestarLivro(int ISBN) {
    for (Livro l : livros) {
      if (l.getISBN() == ISBN) {
        
        if (l.getEmprestado()) throw new RuntimeException(
            "O livro ISBN '" + ISBN + "' já foi emprestado");
        
        l.setEmprestado(true);
        
        livros.remove(l);
        
        return l;
      } 
    }
    
    return null;
  }
  
  public static void devolucaoDeLivro(Livro livro) {
    livros.add(livro);
  }
}

class Livro {
  public int ISBN;
  public String titulo;
  public String autor;
  public int anoPublicacao;
  public Boolean emprestado = false;
  
  public Livro (int ISBN, String titulo, String autor, int anoPublicacao) {
    this.ISBN = ISBN;
    this.titulo = titulo;
    this.autor = autor;
    this.anoPublicacao = anoPublicacao;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public String getAutor() {
    return autor;
  }
  
  public int getAnoPublicacao() {
    return anoPublicacao;
  }
  
  public int getISBN() {
    return ISBN;
  }
  
  public Boolean getEmprestado() {
    return emprestado;
  }
  
  public void setEmprestado(Boolean emprestado) {
    this.emprestado = emprestado;
  }
  
  @Override
  public String toString() {
    return titulo + ", " + autor + ", " + anoPublicacao + ", ISBN: " + ISBN + ", EMPRESTADO: " + emprestado;
  }
}