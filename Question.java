public class Question {
    private String category;
    private int number;
    public Question(String category, int number){
        this.category = category;
        this.number = 0;
    }
    public int getNumber() {
        return number;
    }
    public String getCategory(){
        return category;
    }
    public void updateNumber(){
        this.number += 1;
    }

}
