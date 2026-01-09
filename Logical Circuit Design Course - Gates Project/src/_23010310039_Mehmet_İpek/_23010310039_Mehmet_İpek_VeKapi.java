package _23010310039_Mehmet_İpek;

import java.util.*;

public class _23010310039_Mehmet_İpek_VeKapi implements _23010310039_Mehmet_İpek_Gate {
    private List<_23010310039_Mehmet_İpek_Gate> inputs;

    public _23010310039_Mehmet_İpek_VeKapi(List<_23010310039_Mehmet_İpek_Gate> inputs) {
        this.inputs = inputs;
    }

    public boolean evaluate() {
        for (_23010310039_Mehmet_İpek_Gate g : inputs) {
            if (!g.evaluate()) return false;
        }
        return true;
    }

    public List<_23010310039_Mehmet_İpek_Gate> getInputs() {
        return inputs;
    }

    public String getInputNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.size(); i++) {
            sb.append(inputs.get(i).getInputName());
            if (i < inputs.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public String getInputName() {
        return "VE kapısı";
    }

    public String toString() {
        return "AND(" + getInputNames() + ")";
    }
}