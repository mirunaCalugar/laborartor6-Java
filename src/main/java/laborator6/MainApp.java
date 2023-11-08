package laborator6;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainApp {
    public static void scriere(List<Angajat> lista) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/angajati.json");
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.writeValue(file, lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Angajat> citire() {
        try {
            File file = new File("src/main/resources/angajati.json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<Angajat> angajati = mapper
                    .readValue(file, new TypeReference<List<Angajat>>() {
                    });

            return angajati;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void salariu2500(){
//    angajati
//    }

    public static void main(String[] args) {

        List<Angajat> angajatiScris = new ArrayList<Angajat>();
//    angajatiScris.add(new Angajat("Miruna", "student",  LocalDate.of(2019,05,23),3000));
//    angajatiScris.add(new Angajat("Stefan","manager",LocalDate.of(2019,01,6),8700));
//    angajatiScris.add(new Angajat("Sergiu","webdev",LocalDate.of(2017,07,7),9000));
//    angajatiScris.add(new Angajat("David", "sef", LocalDate.of(2022, 04,1),7500));
//        angajatiScris.add(new Angajat("David", "director", LocalDate.of(2019, 11,1),7500));
//   angajatiScris.add(new Angajat("Ion", "Muncitor", LocalDate.of(2021, 10,16),2300));
//        angajatiScris.add(new Angajat("Mirela", "director", LocalDate.of(2019, 06,1),7500));
//    scriere(angajatiScris);
        List<Angajat> angajati = citire();

        Scanner scanner = new Scanner(System.in);
        int optiune;

        do {
            System.out.println("Alege o opțiune:");
            System.out.println("1. Afișarea listei de angajați");
            System.out.println("2. Afișarea angajaților care au salariul peste 2500 RON.");
            System.out.println("3. Crearea unei liste cu angajații din luna aprilie, a anului trecut, care au funcție de\n" +
                    "conducere (postul conține unul din cuvintele sef sau director). ");
            System.out.println("4. Afișarea angajaților care nu au funcție de conducere ( ");
            System.out.println("5. Extragerea din lista de angajați a unei liste de String-uri care conține numele angajaților\n" +
                    "scrise cu majuscule.  ");
            System.out.println("6. Afișarea salariilor mai mici de 3000 de RON (doar salariile, fără alte informații)");
            System.out.println("7. Afișarea datelor primului angajat al firmei. ");
            System.out.println("8.Afișarea de statistici referitoare la salariul angajaților ");
            System.out.println("9. Afișarea unor mesaje care indică dacă printre angajați există cel puțin un “Ion”. ");
            System.out.println("10. Afișarea numărului de persoane care s-au angajat în vara anului precedent. Se va\n" +
                    "utiliza metoda count() din interfaţa Stream.\n ");

            optiune = scanner.nextInt();

            switch (optiune) {
                case 1:

                    angajati.forEach(System.out::println);
                    break;
                case 2:

                    angajati.stream().filter((angajat -> angajat.getSalariul() > 2500)).forEach(System.out::println);

                    break;
                case 3:
                    List<Angajat> angajatSef = angajati.stream().filter(angajat -> (LocalDate.now().getYear() - angajat.getDataAngajarii().getYear()) == 1).filter(angajat -> angajat.getDataAngajarii().getMonthValue() == 4).filter(angajat -> angajat.getPostul().contains("director") || angajat.getPostul().contains("sef")).toList();
                    angajatSef.forEach(System.out::println);
                    break;
                case 4:
                    angajati.stream().filter(angajat -> !(angajat.getPostul().contains("director") || angajat.getPostul().contains("sef"))).sorted(Comparator.comparing(Angajat::getSalariul).reversed()).forEach(System.out::println);
                    break;
                case 5:
                    angajati.stream().map(angajat -> angajat.getNume().toUpperCase()).forEach(System.out::println);
                    break;
                case 6:
                    angajati.stream().filter(angajat -> angajat.getSalariul()<3000).map(angajat -> angajat.getSalariul()).forEach(salariu-> System.out.println(salariu));
                    break;
                case 7:
                    angajati.stream().min(Comparator.comparing(Angajat::getDataAngajarii)).ifPresentOrElse(angajat -> System.out.println(angajat.toString()),()-> System.out.println("Nu exista primul angajat"));
                    break;
                case 8:
                    System.out.println(angajati.stream().collect(Collectors.summarizingDouble(Angajat::getSalariul)));
                    break;
                case 9:
                    angajati.stream().filter(angajat -> angajat.getNume().contains("Ion")).findAny().ifPresentOrElse(angajat -> System.out.println("Exista Ion"),
                            () -> System.out.println("Nu exista Ion"));
                    break;
                case 10:
                    angajati.stream().filter(angajat -> (LocalDate.now().getYear()-angajat.getDataAngajarii().getYear())==1).filter(angajat -> angajat.getDataAngajarii().getMonthValue()==6||angajat.getDataAngajarii().getMonthValue()==7|| angajat.getDataAngajarii().getMonthValue()==8).count();
                    angajati.stream().filter(angajat -> (LocalDate.now().getYear()-angajat.getDataAngajarii().getYear())==1).filter(angajat -> angajat.getDataAngajarii().getMonthValue()==6||angajat.getDataAngajarii().getMonthValue()==7|| angajat.getDataAngajarii().getMonthValue()==8).forEach(System.out::println);
                    break;




                default:
                    System.out.println("Opțiune invalidă.");
            }
        } while (optiune != 11); // Repetă bucla până când se alege Opțiunea 3
    }
}



