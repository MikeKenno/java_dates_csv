package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

public class PayDates {

    public static final int END_OF_MONTH=0;

    private LocalDate now;
    private ArrayList paymentDates;
    private final int numberOfMonths;
    private final int dayOfMonth;
    boolean generatedDates;
    boolean useFriday = true;


    public PayDates() {
        numberOfMonths = 12;
        dayOfMonth = END_OF_MONTH;
        init();
    }

    public PayDates(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
        dayOfMonth = END_OF_MONTH;
        init();
    }

    public PayDates(LocalDate now, int numberOfMonths, int dayOfMonth) {
        this.now = now;
        this.numberOfMonths = numberOfMonths;
        this.dayOfMonth = dayOfMonth;
        init();
    }

    public PayDates(LocalDate now, int numberOfMonths, int dayOfMonth, boolean useFriday) {
        this.now = now;
        this.numberOfMonths = numberOfMonths;
        this.dayOfMonth = dayOfMonth;
        this.useFriday = useFriday;
        init();
    }

    public PayDates(int numberOfMonths, int dayOfMonth) {
        this.numberOfMonths = numberOfMonths;
        this.dayOfMonth = dayOfMonth;
        init();
    }

    public PayDates(int numberOfMonths, int dayOfMonth, boolean useFriday) {
        this.numberOfMonths = numberOfMonths;
        this.dayOfMonth = dayOfMonth;
        this.useFriday = useFriday;
        init();
    }

    private void init() {
        if (null == this.now){ this.now = LocalDate.now(); }
        this.generatedDates = false;
        this.paymentDates = new ArrayList();
    }

    public LocalDate getNow() { return now; }

    public int getNumberOfMonths() { return numberOfMonths; }

    public int getDayOfMonth() { return dayOfMonth; }

    public ArrayList getPaymentDates() {
        generatePayDates();
        return paymentDates;
    }

    @Override
    public String toString() {
        generatePayDates();
        return "PayDates{" +
                "now=" + now +
                ", Payment day=" + dayOfMonth +
                ", Number of Months=" + numberOfMonths +
                ", paymentDates=" + paymentDates +
                '}';
    }

    private void generatePayDates() {
        if(generatedDates) return;
        for (int i = 0; i < numberOfMonths; i++) {
            LocalDate localDate = now.plusMonths(i);
            LocalDate temp = getPaymentDate(dayOfMonth, localDate);
            paymentDates.add(temp);
        }
        flagPayDatesGenerated();
    }

    private void flagPayDatesGenerated() {
        generatedDates = true;
    }

    private LocalDate getPaymentDate(int day, LocalDate localDate) {
        int dayValue = getDayValueToUse(day, localDate);
        LocalDate validDate = (useFriday)
                ? getValidSalaryWeekDate(LocalDate.of(localDate.getYear(), localDate.getMonth(), dayValue))
                : getValidBonusWeekDate(LocalDate.of(localDate.getYear(), localDate.getMonth(), dayValue));
        return validDate;
    }

    private int getDayValueToUse(int day, LocalDate localDate) {
        return day == END_OF_MONTH ? getLastDayOfMonth(localDate).getDayOfMonth(): day;
    }

    private LocalDate getLastDayOfMonth(LocalDate date) {
        Long max = date.range(ChronoField.DAY_OF_MONTH).getMaximum();
        LocalDate lastDay = LocalDate.of(date.getYear(), date.getMonth(), max.intValue());
        return lastDay;
    }

    private LocalDate getValidSalaryWeekDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if("SATURDAY".equals(dayOfWeek.toString())) { date = date.plusDays(-1);}
        if ("SUNDAY".equals(dayOfWeek.toString())) {date = date.plusDays(-2);}

        return date;
    }

    private LocalDate getValidBonusWeekDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if("SATURDAY".equals(dayOfWeek.toString())) { date = date.plusDays(4);}
        if ("SUNDAY".equals(dayOfWeek.toString())) {date = date.plusDays(3);}

        return date;
    }
}
