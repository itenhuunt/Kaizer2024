package team.diamond.kaizer;

public class Modelclass {

    String Question;
    String oA;
    String oB;
    String oC;


    public Modelclass(){

    }

    public Modelclass(String question, String oA, String oB, String oC) {
        Question = question;
        this.oA = oA;
        this.oB = oB;
        this.oC = oC;
    }


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getoA() {
        return oA;
    }

    public void setoA(String oA) {
        this.oA = oA;
    }

    public String getoB() {
        return oB;
    }

    public void setoB(String oB) {
        this.oB = oB;
    }

    public String getoC() {
        return oC;
    }

    public void setoC(String oC) {
        this.oC = oC;
    }
}
