package backend;

/**
 * Classe exemplo para validação com SonarLint
 */
public class EnderecoSonarLintFix {
    private final String rua;
    private final String numero;
    private final String cep;

    public EnderecoSonarLintFix(String rua, String numero, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    public String formatar() {
        if (rua == null || numero == null) {
            return "Dados incompletos";
        }
        return rua + ", " + numero + (cep != null ? " - " + cep : "");
    }
}