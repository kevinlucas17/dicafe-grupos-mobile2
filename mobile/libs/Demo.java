import br.uerj.dicafe.info.Intercorrencias;
import br.uerj.dicafe.medicamentos.Medicamentos;

public class Demo {

    public static void main(String[] args) {
        Medicamentos m = new Medicamentos();

        try {
            System.out.println(m.podeHoje("starlix"));
        }
        catch (Exception e) {
            System.out.println("erro.");
        }
    }
}