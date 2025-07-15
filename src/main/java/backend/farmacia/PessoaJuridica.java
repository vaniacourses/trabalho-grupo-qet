package backend.farmacia;

import backend.Pessoa;
import backend.usuario.PessoaFisica;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import backend.Agenda;
import backend.Endereco;
import backend.FuncoesArquivos;
import backend.Medicamento;

public class PessoaJuridica extends Pessoa{

    public static String nomeArquivoFarmacias = "src\\main\\java\\backend\\farmacia\\RegistroFarmacias.txt";

    public static String basePathEstoque = "src\\main\\java\\backend\\farmacia\\estoquesFarmacias";
    
    private String cnpj;
    private Endereco endereco;
    private Estoque estoque;
    private Agenda contatosClientes;
    private String nomeArquivoEstoque;


    public PessoaJuridica(String nome, String telefone, String email, String senha, String cnpj, Endereco endereco){
        super(nome, telefone, email, senha);
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.nomeArquivoEstoque = basePathEstoque + "\\Estoque" + this.getCnpj() + ".txt";
    }

    public String getCnpj(){
        return this.cnpj;
    }

    public void setCnpj(String novoCnpj, boolean modificarArquivo){
        this.cnpj = novoCnpj;
        if (modificarArquivo == true){
            FuncoesArquivos.alterarInfoArquivo(nomeArquivoFarmacias, this.getNome(), 4, novoCnpj);
        }
    }

    public Endereco getEndereco(){
        return this.endereco;
    }

    public void setEndereco(Endereco novoEndereco, boolean modificarArquivo){
        this.endereco = novoEndereco;
        if (modificarArquivo){
            FuncoesArquivos.alterarInfoArquivo(nomeArquivoFarmacias, this.getNome(), 5, novoEndereco.toString());
        }
    }

    public Estoque getEstoque(){
        return this.estoque;
    }

    public void setEstoque(Estoque novoEstoque, boolean modificarArquivo){
        this.estoque = novoEstoque;
        if (modificarArquivo){
            this.salvarEstoqueArquivo();
        }
    }

    public Agenda getContatosClientes(){
        return this.contatosClientes;
    }

    public void setContatosClientes(Agenda novaAgenda, boolean modificarArquivo){
        this.contatosClientes = novaAgenda;
        if (modificarArquivo){
            FuncoesArquivos.alterarInfoArquivo(nomeArquivoFarmacias, this.getNome(), 7, this.getContatosClientes().toString());
        }
    }

    public void addUsuarioAosContatos(PessoaFisica usuario){
        Agenda agendaTemp;

        if (this.getContatosClientes() == null){
            agendaTemp = new Agenda();
        }
        else{
            agendaTemp = this.getContatosClientes();
        }
        
        agendaTemp.adicionarContato(usuario);
        this.setContatosClientes(agendaTemp, true);
    }

    //get e set particularidade 
    @Override
    public Object getParticularidade() {
        return getEndereco();
    }

    @Override
    public <T> void setParticularidade(T novaParticularidade) {
        setEndereco((Endereco)novaParticularidade, false);
    }

    @Override
    public String toString(){

        String farmaciaString = this.PessoaToString();

        ArrayList<String> listaValoresAtributos = new ArrayList<>();
        
        if (this.getCnpj() != null){
            listaValoresAtributos.add(this.getCnpj());
        }
        else{
            listaValoresAtributos.add("null");
        }

        if (this.getEndereco() != null){
            listaValoresAtributos.add(this.getEndereco().toString());
        }
        else{
            String enderecoNull = "null";
            listaValoresAtributos.add(enderecoNull);
        }

        if (this.getEstoque() != null){
            listaValoresAtributos.add(this.getNomeArquivoEstoque());
        }
        else{
            // a string que iria para lista seria o nome do arquivo do estoque, so precisa de um null
            listaValoresAtributos.add("null");
        }

        if (this.getContatosClientes() != null){
            listaValoresAtributos.add(getContatosClientes().toString());
        }
        else{
            // um null ja é fuciente para mostrar que a farmacia n tem contatos
            listaValoresAtributos.add("null");
        }
        
        String outrosValores = String.join(",", listaValoresAtributos);
        farmaciaString += "," + outrosValores;
        return farmaciaString;

    }

    public void salvarDadosArquivo(){
        List<String> listaLinha = new ArrayList<>();
        listaLinha.add(this.toString());
        FuncoesArquivos.salvarListaEmArquivo(nomeArquivoFarmacias, listaLinha, true);

    }

    public String getNomeArquivoEstoque(){
        return nomeArquivoEstoque;
    }

    public void salvarEstoqueArquivo(){
        try{
            String nomeArquivoEstoque = getNomeArquivoEstoque();
            FileWriter fw = new FileWriter(nomeArquivoEstoque);
            BufferedWriter bw = new BufferedWriter(fw); 
            
            Estoque estoqueTemp = this.getEstoque();
            for (ItemEstoque itemEstoque : estoqueTemp.listaEstoque){
                String linha = itemEstoque.toString();
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void adicionarMedicamentoEstoque(Medicamento medicamento, int quantidade){
        Estoque estoqueTemp = this.getEstoque();

        if (estoqueTemp == null){
            estoqueTemp = new Estoque();
        }

        estoqueTemp.addMedicamentoEstoque(medicamento, quantidade);
        this.setEstoque(estoqueTemp, true);
    }

    public void retirarMedicamentoEstoque(String nomeMedicamento){
        if (this.getEstoque() == null){
            return;
        }
        Estoque estoqueTemp = this.getEstoque();

        estoqueTemp.listaEstoque.removeIf(
            itemEstoque -> itemEstoque.getMedicamento().getNome().equals(nomeMedicamento)
        );
        this.setEstoque(estoqueTemp, true);
    }

    public void atualizarQntMedicamentoEstoque(Medicamento medicamento, int novaQuantidade){
        if (this.getEstoque() == null){
            this.adicionarMedicamentoEstoque(medicamento, novaQuantidade);
        }

        Estoque estoqueTemp = this.getEstoque();

        for (ItemEstoque itemEstoque : estoqueTemp.listaEstoque){
            if (itemEstoque.getMedicamento().getNome().equals(medicamento.getNome())){
                estoqueTemp.atualizarQntMedicamento(medicamento, novaQuantidade);
                this.setEstoque(estoqueTemp, true);
            }
        }
    }
    
    public static Estoque resgatarEstoqueArquivo(String nomeArquivoEstoque){
        try{
            FileReader fr = new FileReader(nomeArquivoEstoque);
            BufferedReader br = new BufferedReader(fr);

            Estoque estoque = new Estoque();

            String linha = br.readLine();

            while (linha != null){
                ItemEstoque itemEstoque = ItemEstoque.stringToItemEstoque(linha);
                estoque.addMedicamentoEstoque(itemEstoque);
                linha = br.readLine();
            }
            br.close();
            return estoque;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

 public static PessoaJuridica resgatarFarmaciaArquivo(String emailEntrada, String senhaEntrada, Boolean ignorarSenha){
        try{
            System.out.println("entrou0");
            FileReader fr = new FileReader(PessoaJuridica.nomeArquivoFarmacias);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();

            while (linha != null){
                String[] dadosLinha = linha.split(",");
                String email = dadosLinha[2];
                String senha = dadosLinha[3];

                if (email.equals(emailEntrada) && (ignorarSenha || senha.equals(senhaEntrada))){
                    System.out.println("entrou1");
                    String telefone = dadosLinha[1];
                    String nome = dadosLinha[0];
                    String cnpj = dadosLinha[4];
                    Endereco endereco = Endereco.stringToEndereco(dadosLinha[5]);
                    PessoaJuridica recuperado = new PessoaJuridica(nome, telefone, email, senha, cnpj, endereco);

                    File arquivoEstoque = new File(recuperado.getNomeArquivoEstoque());
                    if(arquivoEstoque.exists()){
                        System.out.println("entrou2");
                        Estoque estoque = PessoaJuridica.resgatarEstoqueArquivo(recuperado.getNomeArquivoEstoque());
                        recuperado.setEstoque(estoque,false);
                    }
                    br.close();
                    return recuperado;
                }
                linha = br.readLine();
            }
            br.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
    }
}
}
