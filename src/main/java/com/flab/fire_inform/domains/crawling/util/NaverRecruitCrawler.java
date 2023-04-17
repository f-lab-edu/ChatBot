package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.exception.ElementParseException;
import com.flab.fire_inform.domains.crawling.exception.InvalidUrlException;
import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class NaverRecruitCrawler implements JobCrawler {

    private final RecruitMapper recruitMapper;
    private static final String NAVER_URL = "https://recruit.navercorp.com/rcrt/list.do?subJobCdArr=1010001%2C1010002%2C1010003%2C1010004%2C1010005%2C1010006%2C1010007%2C1010008%2C1010020%2C1020001%2C1030001%2C1030002%2C1040001%2C1050001%2C1050002%2C1060001&sysCompanyCdArr=&empTypeCdArr=&entTypeCdArr=&workAreaCdArr=&sw=&subJobCdData=1010001&subJobCdData=1010002&subJobCdData=1010003&subJobCdData=1010004&subJobCdData=1010005&subJobCdData=1010006&subJobCdData=1010007&subJobCdData=1010008&subJobCdData=1010020&subJobCdData=1020001&subJobCdData=1030001&subJobCdData=1030002&subJobCdData=1040001&subJobCdData=1050001&subJobCdData=1050002&subJobCdData=1060001";
    private static final String NAVER_DETAIL_URL = "https://recruit.navercorp.com/rcrt/view.do?annoId=";
    private static final long SCROLL_PAUSE_TIME = 3000L;

    public NaverRecruitCrawler(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    @Transactional
    public void crawling() {
        log.info("================== Naver Recruits Crawling Start ===================");

        WebDriver driver = runWebDriver();

        List<String> recruitDetailLinks = getRecruitDetailLinks(driver);

        List<Recruit> crawledRecruits = parseLinkToRecruit(driver, recruitDetailLinks);

        for (Recruit crawledRecruit : crawledRecruits) {
            recruitMapper.upsertRecruits(crawledRecruit);
        }

        driver.quit();

        log.info("================== Naver Recruits Crawling End =====================");
    }

    private WebDriver runWebDriver() {
        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // WebDriver option
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");          // 최대크기로
        options.addArguments("--headless");                 // Browser를 띄우지 않음
        options.addArguments(
            "--disable-gpu");              // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments(
            "--no-sandbox");               // Sandbox 프로세스를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함

        return new ChromeDriver(options);
    }

    private List<String> getRecruitDetailLinks(WebDriver webDriver) {
        List<String> result = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;

        try {
            webDriver.get(NAVER_URL);

            Long beforeHeight = (Long) javascriptExecutor.executeScript(
                "return document.body.scrollHeight;");
            while (true) {
                javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(SCROLL_PAUSE_TIME);
                Long afterHeight = (Long) javascriptExecutor.executeScript(
                    "return document.body.scrollHeight;");
                if (beforeHeight.equals(afterHeight)) {
                    break;
                } else {
                    beforeHeight = afterHeight;
                }
            }

            List<WebElement> webElements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//*[@class='card_link']")));

            for (WebElement e : webElements) {
                String annoId = e.getAttribute("onclick").replaceAll("[^0-9]", "");
                String link = String.format("%s%s", NAVER_DETAIL_URL, annoId);
                result.add(link);
            }

        } catch (InvalidArgumentException e) {
            webDriver.quit();
            throw new InvalidUrlException();
        } catch (InterruptedException e) {

        }

        return result;
    }

    private List<Recruit> parseLinkToRecruit(WebDriver webDriver, List<String> recruitDetailLinks) {
        List<Recruit> result = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        for (String link : recruitDetailLinks) {
            try {
                webDriver.get(link);

                String title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h4[@class='card_title']"))).getText();

                String career = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='card_info']/dd[4]"))).getText();

                String workerType = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='card_info']/dd[5]"))).getText();

                String duration = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//dl[@class='card_info']/dd[6]"))).getText();
                String dueDate = null;
                if (duration.contains("~")) {
                    String[] temp = duration.split(" ~ ");
                    dueDate = temp[1];
                }

                result.add(new Recruit.Builder(title, CompanyType.NAVER.getValue(), link)
                    .career(career)
                    .dueDate(dueDate)
                    .workerType(workerType)
                    .build());
            } catch (InvalidArgumentException e) {
                webDriver.quit();
                throw new InvalidUrlException();
            } catch (WebDriverException e) {
                webDriver.quit();
                throw new ElementParseException();
            }
        }

        return result;
    }
}
