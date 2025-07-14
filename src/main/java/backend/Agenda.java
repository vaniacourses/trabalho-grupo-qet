package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.farmacia.PessoaJuridica;
import backend.usuario.Medico;
import backend.usuario.PessoaFisica;

public class Agenda {
    private List<Pessoa> contatos = new ArrayList<>();

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
        }
    }

    public boolean alterarNomeContato(String nome, String novoNome) {
        if (nome == null || novoNome == null) {
            throw new IllegalArgumentException("Nome e novo nome não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            return false;
        } else {
            contatos.get(pos).setNome(novoNome);
        }
        return true;
    }

    public boolean alterarTelContato(String nome, String novoTelefone) {
        if (nome == null || novoTelefone == null) {
            throw new IllegalArgumentException("Nome e telefone não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            return false;
        } else {
            contatos.get(pos).setTelefone(novoTelefone);
        }
        return true;
    }

    public <T> boolean alterarParticularidadeContato(String nome, T novaParticularidade) {
        if (nome == null || novaParticularidade == null) {
            throw new IllegalArgumentException("Nome e particularidade não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            return false;
        } else {
            contatos.get(pos).setParticularidade(novaParticularidade);
        }
        return true;
    }

    public boolean alterarEmailContato(String nome, String novoEmail) {
        if (nome == null || novoEmail == null) {
            throw new IllegalArgumentException("Nome e email não podem ser nulos.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            return false;
        } else {
            contatos.get(pos).setEmail(novoEmail);
        }
        return true;
    }

    public boolean removerContato(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        int pos = encontraContato(nome);
        if (pos == -1) {
            return false;
        } else {
            contatos.remove(pos);
        }
        return true;
    }

    private void ordenarListaDeContatos() {
        // Requer que Pessoa implemente Comparable!
        Collections.sort(contatos);
    }

    public List<Pessoa> getContatos() {
        ordenarListaDeContatos();
        return contatos;
    }

    public void setContatos(List<Pessoa> novosContatos) {
        this.contatos = novosContatos;
    }

    @Override
    public String toString() {
        if (this.getContatos().isEmpty()) {
            return "null";
        } else {
            ArrayList<String> listaNomesAgenda = new ArrayList<>();

            for (Pessoa pessoa : this.contatos) {
                listaNomesAgenda.add(pessoa.getEmail());
            }

            return String.join("/", listaNomesAgenda);
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
