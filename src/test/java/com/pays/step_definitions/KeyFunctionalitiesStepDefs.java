package com.pays.step_definitions;

import com.pays.pages.CalculatorPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import java.util.List;

public class KeyFunctionalitiesStepDefs {

    CalculatorPage calculatorPage = new CalculatorPage();

    private final int officialRateColumnIndex = 2;
    private final int payseraRateColumnIndex = 3;
    private final int payseraAmountColumnIndex = 4;

    private String firstOfficalRate;
    private String firstPayseraRate;

    private List<Double> payseraAmounts;
    private double sellAmount;

    @Given("user go to paysera exchange calculator website")
    public void user_go_to_paysera_exchange_calculator_website() {
        calculatorPage.getUrl();
        calculatorPage.waitForDataLoad();
    }

    @When("verify the user is on the webpage")
    public void verify_the_user_is_on_the_webpage() {
        Assert.assertEquals("Online Currency Exchange | Currency Converter | Paysera", calculatorPage.getTitle());
        calculatorPage.clearSellBox();
        calculatorPage.clearBuyBox();
    }

    @Given("user write {double} to Sell box")
    public void user_write_to_Sell_box(double amount) {
        calculatorPage.setSellBoxValue(amount);
        sellAmount = amount;
    }

    @When("user write {double} to Buy box")
    public void user_write_to_Buy_box(double amount) {
        calculatorPage.setBuyBoxValue(amount);
    }

    @Then("user sees that the number in the Sell box has been deleted")
    public void user_sees_that_the_number_in_the_Sell_box_has_been_deleted() {
        Assert.assertEquals("", calculatorPage.getSellBoxValue());
    }

    @Then("user sees that the number in the Buy box has been deleted")
    public void user_sees_that_the_number_in_the_Buy_box_has_been_deleted() {
        Assert.assertEquals("", calculatorPage.getBuyBoxValue());
    }

    @Given("user selects the {string} from the icon at the bottom of the page")
    public void user_selects_the_country_from_the_icon_at_the_bottom_of_the_page(String countryName) {
        firstOfficalRate = calculatorPage.getRateOfFirstRow(officialRateColumnIndex);
        firstPayseraRate = calculatorPage.getRateOfFirstRow(payseraRateColumnIndex);

        calculatorPage.languageAndCountrySelectButton.click();
        calculatorPage.countrySelectButton.click();
        calculatorPage.clickTheCountryName(countryName);
        calculatorPage.waitForDataLoad();
    }

    @When("user sees that Official rate and Paysera rate have changed")
    public void userSeesThatOfficialRateAndPayseraRateHaveChanged() {
        Assert.assertNotEquals(firstOfficalRate, calculatorPage.getRateOfFirstRow(officialRateColumnIndex));
        Assert.assertNotEquals(firstPayseraRate, calculatorPage.getRateOfFirstRow(payseraRateColumnIndex));
    }


    @Then("user sees that the currency has changed with the currency \\({string}) of the country they have chosen")
    public void user_sees_that_the_currency_has_changed_with_the_currency_of_the_country_they_have_chosen(String newCurrency) {
        Assert.assertEquals(newCurrency, calculatorPage.sellSelectedCurrencyOption.getText());
    }

    @Given("the user sees difference between Paysera amount and {string} and it should be calculated correctly")
    public void theUserSeesDifferenceBetweenPayseraAmountAndAndItShouldBeCalculatedCorrectly(String fieldName) {

        payseraAmounts = calculatorPage.getPayseraAmounts();

        for (int index = 0; index < payseraAmounts.size(); index++) {
            // This assertion should be assert in loop because some lines has not (X-Y) calculation
            // i.e. when user select USD in sell option and filter it, HUF currency has not this calculation

            Assert.assertTrue(calculatorPage.compareAmounts(payseraAmounts.get(index), index + 1, fieldName));
        }
    }


    @Given("user presses clear filter button")
    public void userPressesClearFilterButton() {
        calculatorPage.waitForDataLoad();
        calculatorPage.clearFilterButton.click();
    }

    @When("user sees the default number in the Sell box")
    public void userSeesTheDefaultNumberInTheSellBox() {
        calculatorPage.waitForDataLoad();
        Assert.assertEquals(String.valueOf(100), calculatorPage.getSellBoxValue());
    }


    @Then("user sees EUR inside left currency box")
    public void userSeesEURInsideLeftCurrencyBox() {

        Assert.assertEquals("EUR", calculatorPage.sellSelectedCurrencyOption.getText());
    }


    @And("user sees empty in the Buy box")
    public void userSeesEmptyInTheBuyBox() {
        Assert.assertEquals("", calculatorPage.getBuyBoxValue());

    }

    @Then("user sees All inside right currency box")
    public void userSeesAllInsideRightCurrencyBox() {
        Assert.assertEquals("All", calculatorPage.buySelectedCurrencyOption.getText());
    }


    @When("user selects currencies \\({string} to {string}) and filter it")
    public void userSelectsCurrenciesToAndFilterIt(String sellCurrency, String buyCurrency) {
        calculatorPage.sellSelectedCurrencyOption.click();
        calculatorPage.sellOptionInput.sendKeys(sellCurrency, Keys.ENTER);

        calculatorPage.buySelectedCurrencyOption.click();
        calculatorPage.buyOptionInput.sendKeys(buyCurrency, Keys.ENTER);

        calculatorPage.filterButton.click();
        calculatorPage.waitForDataLoad();
    }

    @Then("user sees Paysera rate and Paysera amount values confirm each other")
    public void userSeesPayseraRateAndPayseraAmountValuesConfirmEachOther() {
        var payseraRate = calculatorPage.getRateOfFirstRow(payseraRateColumnIndex);
        var payseraAmount = calculatorPage.getRateOfFirstRow(payseraAmountColumnIndex);

        Assert.assertEquals(payseraAmount, calculatorPage.doubleToStringFormatted(sellAmount * Double.valueOf(payseraRate)));
    }

}
