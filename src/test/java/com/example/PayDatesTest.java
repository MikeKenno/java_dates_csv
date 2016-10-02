package com.example;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class PayDatesTest {

    private LocalDate now;
    private PayDates payDates;

    private void generateData() {
        now = LocalDate.now();
        payDates = new PayDates();
    }

    @Test
    public void should_create_a_new_paydate_object_with_default_parameters() throws Exception {
        generateData();
        assertThat(payDates, notNullValue());
        assertThat(payDates.getNow(), is(now));
        assertThat(payDates.getDayOfMonth(), is(payDates.END_OF_MONTH));
        assertThat(payDates.getNumberOfMonths(), is(12));
    }

    @Test
    public void should_create_a_new_paydate_object_with_one_month() throws Exception {
        generateData();
        PayDates payDates = new PayDates(1);
        assertThat(payDates, notNullValue());
        assertThat(payDates.getNumberOfMonths(), is(1));
        assertThat(payDates.toString(), containsString("Payment day=0, Number of Months=1"));
    }

    @Test
    public void should_create_a_new_paydate_object_with_one_month_and_pay_date_on_the_15th() throws Exception {
        PayDates payDates = new PayDates(1, 15);
        assertThat(payDates, notNullValue());
        assertThat(payDates.getNumberOfMonths(), is(1));
        assertThat(payDates.getDayOfMonth(), is(15));
        assertThat(payDates.toString(), containsString("Payment day=15, Number of Months=1"));
    }

    @Test
    public void should_return_an_array_list_containing_the_payment_dates() throws Exception {
        LocalDate now = LocalDate.of(2016, 9, 30);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth);
        assertThat(payDate.toString(), containsString("Payment day="+ dayOfMonth +", Number of Months=1"));
        assertThat(payDate.getPaymentDates().toString(), is("[" + now + "]"));
    }

    @Test
    public void should_return_a_string_representation() throws Exception {
        LocalDate now = LocalDate.of(2016, 9, 30);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth);
        assertThat(payDate.toString(), is("PayDates{now=2016-09-30, Payment day=30, Number of Months=1, paymentDates=[2016-09-30]}"));
    }

    @Test
    public void should_return_friday_if_given_a_saturday_date() throws Exception {
        LocalDate now = LocalDate.of(2016, 10, 1);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth);
        assertThat(payDate.toString(), is("PayDates{now=2016-10-01, Payment day=1, Number of Months=1, paymentDates=[2016-09-30]}"));
    }

    @Test
    public void should_return_friday_if_given_a_sunday_date() throws Exception {
        LocalDate now = LocalDate.of(2016, 10, 2);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth);
        assertThat(payDate.toString(), is("PayDates{now=2016-10-02, Payment day=2, Number of Months=1, paymentDates=[2016-09-30]}"));
    }

    @Test
    public void should_return_wednesday_if_given_a_saturday_date_and_useFriday_is_false() throws Exception {
        LocalDate now = LocalDate.of(2016, 10, 1);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth, false);
        assertThat(payDate.toString(), is("PayDates{now=2016-10-01, Payment day=1, Number of Months=1, paymentDates=[2016-10-05]}"));
    }

    @Test
    public void should_return_wednesday_if_given_a_sunday_date_and_useFriday_is_false() throws Exception {
        LocalDate now = LocalDate.of(2016, 10, 2);
        int dayOfMonth = now.getDayOfMonth();
        PayDates payDate = new PayDates(now, 1, dayOfMonth, false);
        assertThat(payDate.toString(), is("PayDates{now=2016-10-02, Payment day=2, Number of Months=1, paymentDates=[2016-10-05]}"));
    }
}