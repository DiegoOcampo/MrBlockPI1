package co.edu.eafit.mrblock.Entidades;

/**
 * Created by juan on 1/10/15.
 */
public class DateTime {
    int year1, month1, day1, hour1, minute1, second1, year2, month2, day2, hour2, minute2, second2;
    String number;

    public DateTime(){}

    public DateTime(String number,int year1, int month1, int day1, int hour1, int minute1, int second1, int year2,
                    int month2, int day2, int hour2, int minute2, int second2) {
        this.number = number;
        this.year1 = year1;
        this.month1 = month1;
        this.day1 = day1;
        this.hour1 = hour1;
        this.minute1 = minute1;
        this.second1 = second1;
        this.year2 = year2;
        this.month2 = month2;
        this.day2 = day2;
        this.hour2 = hour2;
        this.minute2 = minute2;
        this.second2 = second2;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public int getMonth1() {
        return month1;
    }

    public void setMonth1(int month1) {
        this.month1 = month1;
    }

    public int getDay1() {
        return day1;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public int getHour1() {
        return hour1;
    }

    public void setHour1(int hour1) {
        this.hour1 = hour1;
    }

    public int getMinute1() {
        return minute1;
    }

    public void setMinute1(int minute1) {
        this.minute1 = minute1;
    }

    public int getSecond1() {
        return second1;
    }

    public void setSecond1(int second1) {
        this.second1 = second1;
    }

    public int getYear2() {
        return year2;
    }

    public void setYear2(int year2) {
        this.year2 = year2;
    }

    public int getMonth2() {
        return month2;
    }

    public void setMonth2(int month2) {
        this.month2 = month2;
    }

    public int getDay2() {
        return day2;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public int getHour2() {
        return hour2;
    }

    public void setHour2(int hour2) {
        this.hour2 = hour2;
    }

    public int getMinute2() {
        return minute2;
    }

    public void setMinute2(int minute2) {
        this.minute2 = minute2;
    }

    public int getSecond2() {
        return second2;
    }

    public void setSecond2(int second2) {
        this.second2 = second2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
