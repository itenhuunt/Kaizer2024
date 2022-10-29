package team.diamond.kaizer.creatTest;

// модель  вопросов и ответов    стандарт
public class testModel {

    String Question;
    String oA;
    String oB;
    String oC;
    String oD;
    String CustomTest;

    String documentId;



    public testModel(){
        //empty
    }

    public testModel(String question, String oA, String oB, String oC, String oD, String customTest) {
        Question = question;
        this.oA = oA;
        this.oB = oB;
        this.oC = oC;
        this.oD = oD;
        CustomTest = customTest;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public String getoD() {
        return oD;
    }

    public void setoD(String oD) {
        this.oD = oD;
    }

    public String getCustomTest() {
        return CustomTest;
    }

    public void setCustomTest(String customTest) {
        CustomTest = customTest;
    }
}
