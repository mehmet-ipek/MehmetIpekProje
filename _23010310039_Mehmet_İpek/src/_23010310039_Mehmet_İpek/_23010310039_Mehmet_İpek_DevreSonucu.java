package _23010310039_Mehmet_İpek;

import java.io.*;
import java.util.*;

public class _23010310039_Mehmet_İpek_DevreSonucu {
    private String fileName;
    private List<_23010310039_Mehmet_İpek_DegilKapi> notGates = new ArrayList<>();
    private List<_23010310039_Mehmet_İpek_VeKapi> andGates = new ArrayList<>();
    private _23010310039_Mehmet_İpek_VeyaKapi orGate;
    private Map<String, _23010310039_Mehmet_İpek_Girişler> variables = new HashMap<>();

    public _23010310039_Mehmet_İpek_DevreSonucu(String fileName) {
        this.fileName = fileName;
    }

    public void buildCircuit() throws IOException {
        System.out.println("boole.txt dosyası okundu.");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        reader.close();

        if (line.contains("=")) {
            line = line.split("=")[1].replaceAll("\\s+", "");

        }

        String[] terms = line.split("\\+");

        for (String term : terms) {
            List<_23010310039_Mehmet_İpek_Gate> inputs = new ArrayList<>();
            for (int i = 0; i < term.length(); i++) {
                char ch = term.charAt(i);
                if (ch == '\'') continue;
                String varName = String.valueOf(ch);
                boolean isNegated = (i + 1 < term.length()) && term.charAt(i + 1) == '\'';

                _23010310039_Mehmet_İpek_Girişler var = variables.computeIfAbsent(varName, _23010310039_Mehmet_İpek_Girişler::new);
                if (isNegated) {
                	_23010310039_Mehmet_İpek_DegilKapi not = new _23010310039_Mehmet_İpek_DegilKapi(var);
                    notGates.add(not);
                    inputs.add(not);
                    i++;
                } else {
                    inputs.add(var);
                }
            }

            _23010310039_Mehmet_İpek_VeKapi and = (inputs.size() == 1) ? new _23010310039_Mehmet_İpek_VeKapi(List.of(inputs.get(0))) : new _23010310039_Mehmet_İpek_VeKapi(inputs);
            andGates.add(and);
        }

        orGate = new _23010310039_Mehmet_İpek_VeyaKapi(new ArrayList<>(andGates));
    }

    public void printCircuitInfo() {
        int levels = 1;
        if (!notGates.isEmpty()) levels++;
        if (andGates.size() > 1 || andGates.get(0).getInputs().size() > 1) levels++;

        System.out.println("Devre " + levels + " seviyelidir.");

        int currentLevel = 1;

        if (!notGates.isEmpty()) {
            System.out.println(currentLevel + ". Seviye İçin:");
            System.out.println("Kapı türü: DEĞİL, " + notGates.size() + " tane kapı var");
            for (int i = 0; i < notGates.size(); i++) {
                System.out.println(" " + (i + 1) + ". Kapının girişi: " + notGates.get(i).getInputName());
            }
            currentLevel++;
        }

        System.out.println(currentLevel + ". Seviye İçin:");
        System.out.println("Kapı türü: VE, " + andGates.size() + " tane kapı var");
        for (int i = 0; i < andGates.size(); i++) {
        	_23010310039_Mehmet_İpek_VeKapi gate = andGates.get(i);
            System.out.print(" " + (i + 1) + ". Kapı " + gate.getInputs().size() + "-girişli ve girişleri: ");
            System.out.println(gate.getInputNames());
        }

        currentLevel++;
        System.out.println(currentLevel + ". Seviye İçin:");
        System.out.println("Kapı türü: VEYA, 1 tane kapı var");
        System.out.println(" 1. Kapı " + orGate.getInputs().size() + "-girişli ve girişleri: " + orGate.getInputNames());
    }

    public void evaluateCircuitWithUserInput() {
        Scanner scanner = new Scanner(System.in);
        for (String name : variables.keySet()) {
            System.out.print(name + " değişkeninin değerini giriniz:\n");
            int value = Integer.parseInt(scanner.nextLine());
            variables.get(name).setValue(value == 1);
        }
        System.out.println("Devrenin Sonucu: " + (orGate.evaluate() ? "1" : "0"));
    }
}