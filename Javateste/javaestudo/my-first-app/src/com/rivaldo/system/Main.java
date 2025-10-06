package src.com.rivaldo.system;

import src.com.rivaldo.system.entidades.Entidade;
import src.com.rivaldo.system.services.EntidadeService;
import src.com.rivaldo.system.services.EntidadeServiceImpl;

public class Main {

    public static void main(String[] args) {

        //write your code here
        EntidadeService service = new EntidadeServiceImpl();
        service.altaEntidade(new Entidade());
    }

}
