package fr.iutblagnac.devmobtp1p2.model;

public class Todo {

    private static int lastId = 1000;

    private int id; // Identificateur unique du Todo - généré
    private String title; // titre (phrase courte)
    private String  todo; // Contenu descriptif du todo (détal de la tâche)
    private String withWho; // personnes concernées
    private String comment; // commentaire
    private int dayDue, monthDue, yearDue; // date sous forme jour mois année

    public Todo(String title, String todo) {
        super();
        this.id = Todo.lastId+1;
        Todo.lastId++;
        this.title = title;
        this.todo = todo;
        this.withWho = "";
        this.dayDue = 1;
        this.monthDue = 1;
        this.yearDue = 2023;
        this.comment = "";
    }

    public Todo(String title, String todo, String withWho, String comment,
                int day, int month, int year) {
        super();
        this.id = Todo.lastId+1;
        Todo.lastId++;
        this.title = title;
        this.todo = todo;
        this.withWho = withWho;
        this.comment = comment;
        this.dayDue = day;
        this.monthDue = month;
        this.yearDue = year;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTodo() {
        return this.todo;
    }

    public String getWithWho() {
        return this.withWho;
    }

    public String getComment() {
        return this.comment;
    }

    public int getDayDue() {
        return this.dayDue;
    }

    public int getMonthDue() {
        return this.monthDue;
    }

    public int getYearDue() {
        return this.yearDue;
    }

    public void setValues (String title, String todo, String withWho, String comment,
                           int day, int month, int year) {
        this.title = title;
        this.todo = todo;
        this.withWho = withWho;
        this.comment = comment;
        this.dayDue = day;
        this.monthDue = month;
        this.yearDue = year;
    }

    public String toString () {
        return ""+this.id
                +" "
                +this.title
                +" : "
                +this.todo
                +" - "
                +this.dayDue + "/" + this.monthDue + "/" + this.yearDue
                +" ("
                +this.withWho
                +")("
                +this.comment
                +")";
    }
}
