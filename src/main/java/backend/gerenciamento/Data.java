package backend.gerenciamento;

import java.util.Calendar;
import java.util.List;

import backend.usuario.Uso;

//classe responsavel por verificacoes de hora
public class Data {
    
    private static int ultimaVerficacaoHorario = 0;

    //verifica apenas a hora para fins de simplificacao do prototipo
    //é enviado uma única vez pois o gerenciador realiza tarefas apenas a cada 1 hora
    public static boolean horaDoRemedio(Uso uso, Integer horario) {
        boolean ehHoraDoRemedio = false;
        
        if(verificarHora(horario) && verificarDia(uso.getHorarios())){
            ehHoraDoRemedio = true;
        }

        return ehHoraDoRemedio;
    }

    // retorna true se já tiver passado da meia noite para decrementar um dia na
    // duracao do tratamento
    public static boolean ehMeiaNoite() {
        boolean meiaNoite = false;
        Calendar c = Calendar.getInstance();
        int horaAtual = c.get(Calendar.HOUR_OF_DAY);

        if(horaAtual == 0){
            meiaNoite = true;
        }
        return meiaNoite;
    }

    //verifica se o horario do uso passado como parâmetro corresponde com a hora atual
    public static boolean verificarHora(Integer hora) {
        Calendar c = Calendar.getInstance();
        int horaAtual = c.get(Calendar.HOUR_OF_DAY);
        return (hora == horaAtual && verificarUltimaVerificacao(hora));
    }

    public static boolean verificarUltimaVerificacao(int hora) {
        boolean verificacao = true;
        
        if(hora == Data.ultimaVerficacaoHorario){
            verificacao = false;
        }
        
        return verificacao;
    }

    public static int formatarDia(String dia) {
        int d = 0;

        switch (dia) {
            case "seg":
                d = 2;
                break;
            case "ter":
                d = 3;
                break;
            case "qua":
                d = 4;
                break;
            case "qui":
                d = 5;
                break;
            case "sex":
                d = 6;
                break;
            case "sab":
                d = 7;
                break;
            default: //dom
                d = 1; 
                break;
        }

        return d;
    }

    public static boolean verificarDia(List<String> dias) {
        Calendar c = Calendar.getInstance();
        int hoje = c.get(Calendar.DAY_OF_WEEK);

        for(String dia: dias){
            if(formatarDia(dia) == hoje){
                return true;
            }
        }

        return false;
    }
}
