package _23010310039_Mehmet_İpek;

public class _23010310039_Mehmet_İpek_DegilKapi implements _23010310039_Mehmet_İpek_Gate {
    private _23010310039_Mehmet_İpek_Gate input;

    public _23010310039_Mehmet_İpek_DegilKapi(_23010310039_Mehmet_İpek_Gate input) {
        this.input = input;
    }

    public boolean evaluate() {
        return !input.evaluate();
    }

    public String getInputName() {
        return input.getInputName();
    }

    public String toString() {
        return "DEĞİL(" + input.getInputName() + ")";
    }
}