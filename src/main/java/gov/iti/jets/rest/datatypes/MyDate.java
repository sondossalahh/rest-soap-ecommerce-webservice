package gov.iti.jets.rest.datatypes;


import java.time.LocalDate;

public class MyDate {
    private int day;
    private int month;
    private int year;

    public MyDate(LocalDate date) {
        this.day = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "MyDate [day=" + day + ", month=" + month + ", year=" + year + "]";
    }

    
}
