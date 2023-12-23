package manicure.bot.model;

import jakarta.persistence.*;

@Entity// This tells Hibernate to make a table out of this class
@Table(name = "CLIENTS")
public class Client {
    @SequenceGenerator(
            name = "SequenceGenerator",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SequenceGenerator")
    private Long id;
    @Column(name = "client name")
    private String clientName;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    public Client(){}

    public Client(String clientName, String date, String time) {
        this.clientName = clientName;
        this.date = date;
        this.time = time;
    }

    public boolean existsbyClientName(String clientName){
        return clientName.equals(this.clientName);
    }
    public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public void setDate(String date) {
            this.date = date;
        }
    public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
        return date;
    }

    public String getClientName() {
        return clientName;
    }
    public String getTime() {
        return time;
    }
}