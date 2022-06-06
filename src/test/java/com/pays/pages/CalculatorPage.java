package com.pays.pages;

import com.pays.utilities.BrowserUtils;
import com.pays.utilities.ConfigurationReader;
import com.pays.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CalculatorPage {

    public CalculatorPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(xpath = "//button[@data-ng-click='currencyExchangeVM.filterExchangeRates()']")
    public WebElement filterButton;

    @FindBy(xpath = "//button[@data-ng-click='currencyExchangeVM.clearFilter()']")
    public WebElement clearFilterButton;

    @FindBy(xpath = "//input[@data-ng-model='currencyExchangeVM.filter.from_amount']")
    private WebElement sellBox;

    @FindBy(xpath = "//input[@data-ng-model='currencyExchangeVM.filter.to_amount']")
    private WebElement buyBox;

    @FindBy(xpath = "//div[@data-ng-model='currencyExchangeVM.filter.from']//span[@aria-label='Select box activate']//span//span")
    public WebElement sellSelectedCurrencyOption;

    @FindBy(xpath = "//div[@data-ng-model='currencyExchangeVM.filter.to']//span[@aria-label='Select box activate']//span//span")
    public WebElement buySelectedCurrencyOption;

    @FindBy(xpath = "//span[@class='js-localization-popover']")
    public WebElement languageAndCountrySelectButton;

    @FindBy(xpath = "//button[@id='countries-dropdown']")
    public WebElement countrySelectButton;

    @FindBy(xpath = "//td[@data-title='Paysera rate']")
    public List<WebElement> payseraAmounts;

    @FindBy(xpath = "//input[@aria-owns='ui-select-choices-0']")
    public WebElement sellOptionInput;

    @FindBy(xpath = "//input[@aria-owns='ui-select-choices-1']")
    public WebElement buyOptionInput;


    public String getSellBoxValue() {
        return this.sellBox.getAttribute("value");
    }

    public void setSellBoxValue(Double sellBoxValue) {
        this.sellBox.sendKeys(doubleToStringFormatted(sellBoxValue));
    }

    public String getBuyBoxValue() {
        return this.buyBox.getAttribute("value");
    }

    public void setBuyBoxValue(Double buyBoxValue) {
        this.buyBox.sendKeys(doubleToStringFormatted(buyBoxValue));
    }

    public void clearBuyBox() {
        this.buyBox.clear();
    }

    public void clearSellBox() {
        this.sellBox.clear();
    }

    public void clickTheCountryName(String countryName){
        Driver.get().findElement(By.xpath("//a[normalize-space()='" + countryName + "']")).click();
    }

    public String getRateOfFirstRow(int rateIndex){
        return Driver.get().findElement(By.xpath("//tbody/tr[1]/td[" + rateIndex + "]")).getText();
    }

    public List<Double> getPayseraAmounts(){
        List<Double> payseraAmountList = new ArrayList<>();
        payseraAmounts.forEach(pa -> {
            payseraAmountList.add(Double.valueOf(removeSpecialChars(pa.getText())));
        });

        return payseraAmountList;
    }

    public boolean compareAmounts(double payseraAmount, int index, String fieldName){
        String cellXpath = "(//td[@data-title='" + fieldName + "'])[" + index + "]";
        String ofSpanChild = "//span";

        var spanElementList = Driver.get().findElements(By.xpath(cellXpath + ofSpanChild));

        if(spanElementList.size() == 1){

            return spanElementList.get(0).getText().equals("-");
        }

        spanElementList = Driver.get().findElements(By.xpath(cellXpath + ofSpanChild + ofSpanChild + ofSpanChild));

        if(spanElementList.size() != 2){

            return false;
        }

        String expectedDifference = removeSpecialChars(spanElementList.get(1).getText());

        String actualDifference = doubleToStringFormatted(Double.parseDouble(removeSpecialChars(spanElementList.get(0).getText())) - payseraAmount);
        return expectedDifference.equals(actualDifference);
    }

    private String removeSpecialChars(String value){
        return value.replace(",", "").replace("(", "").replace(")", "");
    }

    public String doubleToStringFormatted(double value){
        return String.format("%.2f", value).replace(",", ".");
    }

    public void getUrl(){
        Driver.get().get(ConfigurationReader.get("url"));
    }

    public String getTitle(){
        return Driver.get().getTitle();
    }

    public void waitForDataLoad(){
        BrowserUtils.waitForClickablility(filterButton,10);
        BrowserUtils.waitForClickablility(clearFilterButton,10);
    }

}
