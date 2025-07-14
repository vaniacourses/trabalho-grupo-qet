package backend;

import java.util.ArrayList;
import java.util.Collections;

import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class Agenda {
    private ArrayList<Pessoa> contatos = new ArrayList<>();

    private int encontraContato(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        int i = 0;
        for (Pessoa contato : this.contatos) {
            if (contato.getNome().equals(nome))
                return i;
            i++;
        }
        return -1;
    }

    public void adicionarContato(Pessoa contato) throws IllegalArgumentException {
        if (contato == null) {
            throw new IllegalArgumentException("É necessário informar um contato válido");
        } else {
            contatos.add(contato);
            System.out.println("Contato Adicionado!");
        }
    }

    public boolean alterarNomeContato(String nome, String novoNome) {
        if (nome == null || novoNome == null) {
            throw new IllegalArgumentException("Nome e novo nome não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            System.out.println("Contato não encontrado! A alteração não foi realizada.");
            return false;
        } else {
            contatos.get(pos).setNome(novoNome);
            System.out.println("O nome de " + contatos.get(pos).getNome() + " foi alterado!");
        }
        return true;
    }

    public boolean alterarTelContato(String nome, String novoTelefone) {
        if (nome == null || novoTelefone == null) {
            throw new IllegalArgumentException("Nome e telefone não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            System.out.println("Contato não encontrado! A alteração não foi realizada.");
            return false;
        } else {
            contatos.get(pos).setTelefone(novoTelefone);
            System.out.println("O telefone de " + contatos.get(pos).getNome() + " foi alterado!");
        }
        return true;
    }

    public <T> boolean alterarParticularidadeContato(String nome, T novaParticularidade) {
        if (nome == null || novaParticularidade == null) {
            throw new IllegalArgumentException("Nome e particularidade não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            System.out.println("Contato não encontrado! A alteração não foi realizada.");
            return false;
        } else {
            contatos.get(pos).setParticularidade(novaParticularidade);
            System.out.println("O atributo de " + contatos.get(pos).getNome() + " foi alterado!");
        }
        return true;
    }

    public boolean alterarEmailContato(String nome, String novoEmail) {
        if (nome == null || novoEmail == null) {
            throw new IllegalArgumentException("Nome e email não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            System.out.println("Contato não encontrado! A alteração não foi realizada.");
            return false;
        } else {
            contatos.get(pos).setEmail(novoEmail);
            System.out.println("O endereço de " + contatos.get(pos).getNome() + " foi alterado!");
        }
        return true;
    }

    public boolean removerContato(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            System.out.println("Contato não encontrado!");
            return false;
        } else {
            Pessoa contatoRemovido = contatos.remove(pos);
            System.out.println("Contato removido: " + contatoRemovido.getNome());
        }
        return true;
    }

    private void ordenarListaDeContatos() {
        // Requer que Pessoa implemente Comparable!
        try {
            Collections.sort(contatos);
        } catch (ClassCastException e) {
            System.out.println("Ordenação não realizada: Classe Pessoa precisa implementar Comparable.");
        }
    }

    public ArrayList<Pessoa> getContatos() {
        ordenarListaDeContatos();
        return contatos;
    }

    public void setContatos(ArrayList<Pessoa> novosContatos) {
        this.contatos = novosContatos;
    }

    @Override
    public String toString() {
        if (this.getContatos().size() == 0) {
            return "null";
        } else {
            ArrayList<String> listaNomesAgenda = new ArrayList<>();

            for (Pessoa pessoa : this.contatos) {
                listaNomesAgenda.add(pessoa.getEmail());
            }

            String contatosString = String.join("/", listaNomesAgenda);
            return contatosString;
        }
    }

    public static Agenda stringToAgenda(String agendaString, String senhaFornecida,
                                        String tipoContato, Boolean ignorarSenha, Boolean ignorarAgenda) {
        Agenda agenda = new Agenda();
        String[] nomeContatos = agendaString.split("/");

        for (String nome : nomeContatos) {
            if (tipoContato.equals("usuario")) {
                PessoaFisica contato = PessoaFisica.resgatarUsuarioArquivo(
                        nome, senhaFornecida, ignorarSenha, ignorarAgenda);
                agenda.adicionarContato(contato);
            }
            if (tipoContato.equals("farmacia")) {
                PessoaJuridica farmacia = PessoaJuridica.resgatarFarmaciaArquivo(
                        nome, senhaFornecida, ignorarSenha, ignorarAgenda);
                agenda.adicionarContato(farmacia);
            }

            if (tipoContato.equals("medico")) {
                Medico medico = Medico.resgatarMedicoArquivo(
                        nome, senhaFornecida, ignorarSenha);
                agenda.adicionarContato(medico);
            }
        }
        return agenda;
    }
}
